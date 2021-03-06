package cn.inslee.admin.service.sys.impl;

import cn.inslee.admin.common.constant.CacheConstant;
import cn.inslee.admin.model.dao.sys.SysResMapper;
import cn.inslee.admin.model.domain.sys.SysRes;
import cn.inslee.admin.service.sys.SysResService;
import cn.inslee.admin.service.sys.SysRoleService;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Set;

/**
 * @author dean.lee
 * <p>
 */
@Service
public class SysResServiceImpl implements SysResService {
    @Autowired
    private SysRoleService roleService;
    @Autowired
    private SysResMapper resMapper;

    @Override
    @Cacheable(value = CacheConstant.RES_CHAR, key = "#p0")
    public Set<String> selectPremCharByUserId(Long uid) {
        List<Long> roleIds = roleService.selectIdByUserId(uid);
        List<String> permChars = resMapper.selectPermCharByRoleIds(roleIds);

        return Sets.newHashSet(permChars);
    }

    @Override
    @Cacheable(value = CacheConstant.RES_MENU, key = "#p0")
    public List<SysRes> resources(Long uid) {
        List<Long> roleIds = roleService.selectIdByUserId(uid);
        List<SysRes> resList = resMapper.selectMenuByRoleIds(roleIds);
        return resList;
    }

    @Override
    public List<SysRes> list() {
        //查询所有未删除的资源
        SysRes example = new SysRes().setDelFlag(false);
        List<SysRes> resList = resMapper.select(example);

        return resList;
    }

    @Override
    public List<SysRes> menuList() {
        Example example = new Example(SysRes.class);
        example.selectProperties("id", "name", "pid");
        example.and().andEqualTo("delFlag", false)
                .andEqualTo("type", 1);
        List<SysRes> menuList = resMapper.selectByExample(example);
        return menuList;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public String add(SysRes res) {
        SysRes example = new SysRes()
                .setDelFlag(false)
                .setName(res.getName());
        synchronized (this) {
            Assert.isNull(resMapper.selectOne(example), "资源名称重复");
            this.addLevel(res);
            resMapper.insertSelective(res);
        }
        return "添加资源成功";
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    @CacheEvict(value = {CacheConstant.RES_CHAR, CacheConstant.RES_MENU}, allEntries = true)
    public String update(SysRes res) {
        SysRes example = new SysRes()
                .setDelFlag(false)
                .setName(res.getName());
        synchronized (this) {
            SysRes sysRes = resMapper.selectOne(example);
            Assert.isTrue(sysRes == null || sysRes.getId().equals(res.getId()), "资源名称重复");
            this.addLevel(res);
            resMapper.updateById(res);
        }
        return "修改资源成功";
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    @CacheEvict(value = {CacheConstant.RES_CHAR, CacheConstant.RES_MENU}, allEntries = true)
    public String delete(SysRes res) {
        //判断当前删除的资源是否关联角色，关联角色则不能删除
        long roleTotal = resMapper.countRoleByResId(res.getId());
        Assert.isTrue(roleTotal == 0, "当前资源有角色关联，请先解除");

        resMapper.updateByPrimaryKeySelective(res);
        return "资源删除成功";
    }

    /**
     * 添加level层级字段
     *
     * @param res
     */
    private void addLevel(SysRes res) {
        //获取上级部门，添加层级字段level
        if (res.getPid().equals(0L)) {
            res.setLevel("0");
        } else {
            SysRes example = new SysRes()
                    .setDelFlag(false)
                    .setId(res.getPid());
            SysRes parentRes = resMapper.selectOne(example);
            //校验父级资源是否存在
            Assert.notNull(parentRes, "请选择正确的父级资源");
            //校验父级资源是否原本是自己的子级
            Assert.isTrue(!parentRes.getLevel().contains(res.getId().toString()), "父级资源不能向下级修改");
            res.setLevel(parentRes.getLevel() + "," + res.getPid());
        }
    }
}
