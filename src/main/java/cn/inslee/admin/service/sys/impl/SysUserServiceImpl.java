package cn.inslee.admin.service.sys.impl;

import cn.inslee.admin.common.constant.Constant;
import cn.inslee.admin.common.constant.CacheConstant;
import cn.inslee.admin.model.dao.sys.SysUserGroupMapper;
import cn.inslee.admin.model.dao.sys.SysUserMapper;
import cn.inslee.admin.model.dao.sys.SysUserRoleMapper;
import cn.inslee.admin.model.domain.sys.SysUser;
import cn.inslee.admin.model.domain.sys.SysUserGroup;
import cn.inslee.admin.model.domain.sys.SysUserRole;
import cn.inslee.admin.model.dto.sys.SysUserDTO;
import cn.inslee.admin.model.query.sys.UserQuery;
import cn.inslee.admin.service.sys.SysUserService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;

import java.util.List;

/**
 * @author dean.lee
 * <p>
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysUserRoleMapper userRoleMapper;
    @Autowired
    private SysUserGroupMapper userGroupMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private Environment env;

    @Override
    public SysUser selectByUsername(String username) {
        SysUser user = new SysUser()
                .setUsername(username)
                .setDelFlag(false);
        return userMapper.selectOne(user);
    }

    @Override
    public PageInfo<SysUserDTO> list(UserQuery query) {
        query.setPageNum((query.getPageNum() - 1) * query.getPageSize());

        List<SysUserDTO> data = userMapper.selectList(query);
        long total = userMapper.countList(query);
        PageInfo<SysUserDTO> info = new PageInfo<>(data);
        info.setTotal(total);
        return info;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public String add(SysUser user, List<SysUserRole> sysUserRoleList, List<SysUserGroup> sysUserGroupList) {
        String password = user.getPassword();
        user.setPassword(DigestUtils.md5DigestAsHex((user.getPassword() + user.getSalt()).getBytes()));
        //校验添加的用户是否重复，此过程需要线程同步
        SysUser example = new SysUser()
                .setUsername(user.getUsername())
                .setDelFlag(false);
        synchronized (this) {
            Assert.isNull(userMapper.selectOne(example), "用户名已经存在");
            example.setUsername(null).setEmail(user.getEmail());
            Assert.isNull(userMapper.selectOne(example), "邮箱已经存在");
            userMapper.insertSelective(user);
        }

        //如果角色不为空，则添加用户关联的角色
        if (!sysUserRoleList.isEmpty()) {
            userRoleMapper.insertList(sysUserRoleList);
        }

        //如果用户组不为空，则添加用户关联的用户组
        if (!sysUserGroupList.isEmpty()) {
            userGroupMapper.insertList(sysUserGroupList);
        }

        //发送邮件通知用户
//        threadPool.execute(() -> {
        String content = "用户名：" + user.getUsername() + "\n" +
                "密码：" + password;

        SimpleMailMessage mailMsg = new SimpleMailMessage();
        mailMsg.setFrom(env.getProperty("spring.mail.username"));
        mailMsg.setTo(user.getEmail());
        mailMsg.setSubject(Constant.EMAIL_REGISTER);
        mailMsg.setText(content);
        mailSender.send(mailMsg);
//        });
        return "用户添加成功";
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    @CacheEvict(value = {CacheConstant.ROLE_ID, CacheConstant.ROLE_CHAR,
            CacheConstant.RES_CHAR, CacheConstant.RES_MENU}, key = "#p0.id")
    public String update(SysUser user, List<SysUserRole> sysUserRoleList, List<SysUserGroup> sysUserGroupList) {
        SysUser example = new SysUser()
                .setId(user.getId())
                .setDelFlag(false);
        SysUser sysUser = userMapper.selectOne(example);
        Assert.notNull(sysUser, "此用户不存在");

        synchronized (this) {
            userMapper.updateById(user);

            //删除之前关联的角色
            SysUserRole userRole = new SysUserRole()
                    .setUid(user.getId());
            userRoleMapper.delete(userRole);
            //如果角色不为空，添加新关联的角色
            if (!sysUserRoleList.isEmpty()) {
                userRoleMapper.insertList(sysUserRoleList);
            }

            //删除之前关联的用户组
            SysUserGroup userGroup = new SysUserGroup()
                    .setUid(user.getId());
            userGroupMapper.delete(userGroup);
            //如果用户组不为空，则添加新关联的用户组
            if (!sysUserGroupList.isEmpty()) {
                userGroupMapper.insertList(sysUserGroupList);
            }
        }
        return "用户修改成功";
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    @CacheEvict(value = {CacheConstant.ROLE_ID, CacheConstant.ROLE_CHAR,
            CacheConstant.RES_CHAR, CacheConstant.RES_MENU}, key = "#p0.id")
    public String delete(SysUser user) {
        userMapper.updateByPrimaryKeySelective(user);
        return "用户删除成功";
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    @CacheEvict(value = {CacheConstant.ROLE_ID, CacheConstant.ROLE_CHAR,
            CacheConstant.RES_CHAR, CacheConstant.RES_MENU}, key = "#p0.id")
    public String status(SysUser user) {
        userMapper.updateByPrimaryKeySelective(user);
        return "用户状态修改成功";
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public String updateSelf(SysUser user) {
        userMapper.updateBySelf(user);

        return "修改成功";
    }

    @Override
    public String randomPwd(SysUser user) {
        SysUser sysUser = userMapper.selectByPrimaryKey(user.getId());
        String password = user.getPassword();
        user.setPassword(DigestUtils.md5DigestAsHex((password + sysUser.getSalt()).getBytes()));
        userMapper.updateByPrimaryKeySelective(user);

        //获取用户邮箱发送重置密码邮件
//        threadPool.execute(() -> {
        String content = "用户名：" + sysUser.getUsername() + "\n" +
                "密码：" + password;

        SimpleMailMessage mailMsg = new SimpleMailMessage();
        mailMsg.setFrom(env.getProperty("spring.mail.username"));
        mailMsg.setTo(sysUser.getEmail());
        mailMsg.setSubject(Constant.EMAIL_RESET);
        mailMsg.setText(content);
        mailSender.send(mailMsg);
//        });

        return "重置密码成功";
    }

    @Override
    public String resetPwd(SysUser user, String ordPassword) {
        SysUser sysUser = userMapper.selectByPrimaryKey(user.getId());
        //校验旧密码是否相同
        String ordMd5Password = DigestUtils.md5DigestAsHex((ordPassword + sysUser.getSalt()).getBytes());
        Assert.isTrue(ordMd5Password.equals(sysUser.getPassword()), "旧密码校验失败");

        user.setPassword(DigestUtils.md5DigestAsHex((user.getPassword() + sysUser.getSalt()).getBytes()));
        userMapper.updateByPrimaryKeySelective(user);

        //删除redis中保存的验证码，即：验证码使用后立马失效
        redisTemplate.opsForHash().delete(String.format(Constant.EMAIL_CODE, user.getId()), Constant.CODE_KEY);
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "修改密码成功";
    }

}
