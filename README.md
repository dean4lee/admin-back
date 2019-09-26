## admin-back
springboot2 + shiro实现rbac权限管理系统接口

> 项目前后端分离\
> 后端：springboot + shiro + redis + mybatis\
> 前端：vue + element-ui
> 
> 项目使用redis作为缓存, 单机版需要修改一些使用redis的地方. \
> session使用redis-session, 方便集群. 不使用只需要将依赖删除即可

[线上演示地址](https://admin.inslee.cn) 账号:admin, 密码:123456\
[前端代码](https://github.com/dean4lee/admin-web)


**项目结构**

|—— ctrl —— 请求层 \
|—— service —— 业务层 \
|—— common \
|—— |—— annotation —— 项目中使用的注解 \
|—— |—— aspect —— 项目中的aop \
|—— |—— config —— springboot配置bean \
|—— |—— constant —— 项目中使用的常量 \
|—— |—— exception —— 自定义的异常 \
|—— |—— handler —— 异常捕获统一处理 \
|—— |—— result —— json返回结果 \
|—— |—— util —— 工具类 \
|—— model \
|—— |—— base —— pojo的父类 \
|—— |—— dao —— 持久层 \
|—— |—— domain —— 与数据库对应的实体类 \
|—— |—— dto —— 封装返回给前端的数据 \
|—— |—— from —— 前端传过来的from对象 \
|—— |—— query —— 前端列表查询使用的对象 \
|—— shiro —— 方便更改架构分层，单独一个包 \
|—— |—— config —— springboot配置shiro的bean \
|—— |—— credential —— 自定义的密码验证 \
|—— |—— filter —— 自定义shiro的过滤器 \
|—— |—— realm —— shiro认证授权的实现类 \
|—— |—— util —— shiro工具类 

**实现功能**
1. 用户管理
2. 角色管理
3. 用户组管理
4. 资源管理
5. 登录日志
6. 操作日志
7. 部门管理 (待完成)
8. 如果你还想到其他功能, 请告诉我. 当然, bug也是

**功能展示**
<table>
    <tr>
        <td><img src="https://raw.githubusercontent.com/dean4lee/admin-back/master/src/main/resources/readme/login.jpg"/></td>
        <td><img src="https://raw.githubusercontent.com/dean4lee/admin-back/master/src/main/resources/readme/userinfo.jpg"/></td>
    </tr>
    <tr>
        <td><img src="https://raw.githubusercontent.com/dean4lee/admin-back/master/src/main/resources/readme/user.jpg"/></td>
        <td><img src="https://raw.githubusercontent.com/dean4lee/admin-back/src/main/resources/readme/group.jpg"/></td>
    </tr>
    <tr>
        <td><img src="https://raw.githubusercontent.com/dean4lee/admin-back/master/src/main/resources/readme/role.jpg"/></td>
        <td><img src="https://raw.githubusercontent.com/dean4lee/admin-back/master/src/main/resources/readme/res.jpg"/></td>
    </tr>
    <tr>
        <td><img src="https://raw.githubusercontent.com/dean4lee/admin-back/master/src/main/resources/readme/loginlog.jpg"/></td>
        <td><img src="https://raw.githubusercontent.com/dean4lee/admin-back/master/src/main/resources/readme/operationlog.jpg"/></td>
    </tr>    
</table>

