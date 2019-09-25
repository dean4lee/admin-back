package cn.inslee.adminback.service.sys.impl;

import cn.inslee.adminback.common.constant.RedisConstant;
import cn.inslee.adminback.model.dao.sys.SysResMapper;
import cn.inslee.adminback.model.domain.sys.SysRes;
import cn.inslee.adminback.model.dto.sys.SysResDTO;
import cn.inslee.adminback.service.sys.SysResService;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author dean.lee
 * <p>
 */
@Service
public class SysResServiceImpl implements SysResService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private SysResMapper resMapper;

    @Override
    public Set<SysRes> selectByRoleIds(List<Long> roleIds) {
        List<SysRes> ress = resMapper.selectByRoleIds(roleIds);
        return new HashSet<>(ress);
    }

    @Override
    public List<SysResDTO> resources(List<Long> roleIds) {
        List<SysResDTO> ress = resMapper.selectMenuByRoleIds(roleIds);
        //封装查询到的资源到Multimap中
        Multimap<Long, SysResDTO> map = ArrayListMultimap.create();
        ress.forEach(e -> map.put(e.getParentId(), e));
        //递归加载树结构
        List<SysResDTO> result = new ArrayList<>(map.get(0L));
        result.forEach(sysResDTO -> loadRes(sysResDTO, map));
        result.sort(Comparator.comparingInt(SysResDTO::getSeq));
        return result;
    }

    @Override
    public List<SysResDTO> list() {
        //查询所有未删除的资源
        SysRes example = new SysRes().setDelFlag(false);
        List<SysRes> resList = resMapper.select(example);

        //copy bean到dto
        List<SysResDTO> resDTOList = Lists.newArrayList();
        resList.forEach(res -> {
            SysResDTO resDTO = new SysResDTO();
            BeanUtils.copyProperties(res, resDTO);
            resDTO.setDelFlag(null);
            resDTOList.add(resDTO);
        });

        //封装查询到的资源到Multimap中
        Multimap<Long, SysResDTO> map = ArrayListMultimap.create();
        resDTOList.forEach(e -> map.put(e.getParentId(), e));
        //递归加载树结构
        List<SysResDTO> result = Lists.newArrayList(map.get(0L));
        result.forEach(resDTO -> loadRes(resDTO, map));
        result.sort(Comparator.comparingInt(SysResDTO::getSeq));

        return result;
    }

    @Override
    public List<SysRes> menuList() {
        Example example = new Example(SysRes.class);
        example.selectProperties("id", "name");
        example.and().andEqualTo("delFlag", false)
                .andEqualTo("type", 1);
        List<SysRes> menuList = resMapper.selectByExample(example);
        return menuList;
    }

    @Override
    public String add(SysRes res) {
        SysRes example = new SysRes()
                .setDelFlag(false)
                .setName(res.getName());
        synchronized (this) {
            Assert.isNull(resMapper.selectOne(example), "资源名称重复");
            resMapper.insertSelective(res);
        }
        return "添加资源成功";
    }

    @Override
    public String update(SysRes res) {
        SysRes example = new SysRes()
                .setDelFlag(false)
                .setName(res.getName());
        synchronized (this) {
            SysRes sysRes = resMapper.selectOne(example);
            Assert.isTrue(sysRes == null || sysRes.getId().equals(res.getId()), "资源名称重复");
            resMapper.updateById(res);
        }
        //删除所有用户的权限缓存
        Set<String> keys = redisTemplate.keys(RedisConstant.PERM_KEY + "*");
        redisTemplate.delete(keys);
        return "修改资源成功";
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public String delete(SysRes res) {
        //判断当前删除的资源是否关联角色，关联角色则不能删除
        long roleTotal = resMapper.countRoleByResId(res.getId());
        Assert.isTrue(roleTotal == 0, "当前资源有角色关联，请先解除");

        resMapper.updateByPrimaryKeySelective(res);

        //删除所有用户的权限缓存
        Set<String> keys = redisTemplate.keys(RedisConstant.PERM_KEY + "*");
        redisTemplate.delete(keys);
        return "资源删除成功";
    }

    /**
     * 递归加载资源树结构
     * @param sysResDTO
     * @param map
     */
    public static void loadRes(SysResDTO sysResDTO, Multimap<Long, SysResDTO> map){
        List<SysResDTO> sysResDTOList = Lists.newArrayList(map.get(sysResDTO.getId()));
        if(sysResDTOList != null){
            //排序
            sysResDTOList.sort(Comparator.comparingInt(SysResDTO::getSeq));
            sysResDTO.setChildren(sysResDTOList);
            map.asMap().remove(sysResDTO.getId());
            sysResDTOList.forEach(e -> loadRes(e, map));
        }
    }
}
