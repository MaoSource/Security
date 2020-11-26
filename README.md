**Spring Security 框架简介**

官网：https://projects.spring.io/spring-security/
Spring Security 是强大的，且容易定制的实现认证，与授权的基于 Spring 开发的框架。

**Spring Security 的功能：**
1）Authentication：认证，就是用户登录。
2）Authorization：授权，判断用户拥有什么权限，可以访问什么资源。
3）安全防护，防止跨站请求，session 攻击等
4）非常容易结合 SpringMVC 进行使用

**Spring Security 与 Shiro 的区别**

**相同点**

1）认证功能
2）授权功能
3）加密功能
4）会话管理
5）缓存支持
6）rememberMe 功能

**不同点**

优点：
1）Spring Security 基于 Spring 开发，项目如使用 Spring 作为基础，配合 Spring
Security 做权限更加方便。而 Shiro 需要和 Spring 进行整合开发。
2）Spring Security 功能比 Shiro 更加丰富些，例如安全防护方面
3）Spring Security 社区资源相对比 Shiro 更加丰富

缺点：
1）Shiro 的配置和使用比较简单，Spring Security 上手复杂些。
2）Shiro 依赖性低，不需要任何框架和容器，可以独立运行。Spring Security 依
赖 Spring 容器。

### 数据库表文件：[点击下载](https://maoyuan.lanzoux.com/i5wcdisa77a)

**过滤器链**
**核心流程图：**

![核心流程.png](https://asource.top/upload/2020/11/image-af5da76ce6bc4a85a61e4aede5c12723.png)

添加**login-processing-url:处理登录请求的地址**
添加后从启从新进行登录，会发现一直都会在登录页面跳转,
![image.png](https://asource.top/upload/2020/11/image-76ad9704f4db4a89b81009796c50824b.png)
解决办法关闭csrf，因为这个是Security的一个防止黑客什么的恶意攻击登录的一个保护
```xml
	<!-- 关闭Spring Security CSRF机制 -->
        <security:csrf disabled="true"/>
```
关闭就不会重复跳转了。

自定义权限不足
```xml
	<security:access-denied-handler error-page="/error"/>
```




security:form-login: 使用登录表单
login-page:自定义登录页面
login-processing-url:处理登录请求的地址
default-target-url:指定登录后跳转页面
authentication-failure-url:验证失败跳转
authentication-success-forward-url：验证成功跳转
authentication-success-handler-ref="MyAuthenticationSuccessHandler":自定义登录成功处理
authentication-failure-handler-ref="MyAuthenticationFailureHandler":自定义登录失败处理
```xml
	<security:form-login login-page="/login" default-target-url="/user/list" login-processing-url="/userLogin"/>
```

**动态授权**

创建一个User对象，属性要个Security的User一致然后继承UserDetails
```java
public class User implements UserDetails{
    private Integer id; //int(10) NOT NULL,
    private String username; //varchar(50) DEFAULT NULL,
    private String realname; //varchar(50) DEFAULT NULL,
    private String password; //varchar(50) DEFAULT NULL,
    private Date createDate; //date DEFAULT NULL,
    private Date lastLoginTime; //date DEFAULT NULL,
    private boolean enabled; //int(5) DEFAULT NULL,
    private boolean accountNonExpired; //int(5) DEFAULT NULL,
    private boolean accountNonLocked; //int(5) DEFAULT NULL,
    private boolean credentialsNonExpired; //int(5) DEFAULT NULL,
    //用户拥有的所有权限
    private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
}
```
authorities 为用户权限的集合，这个需要手动添加一下

释放静态资源

```xml
  <security:intercept-url pattern="/js/**" access="permitAll()"/>
  <security:intercept-url pattern="/**" access="isFullyAuthenticated()"/>
```
permitAll() 所有人可以反问
isFullyAuthenticated() 需要通过验证

```script
  alert($("#formInfo").serialize())	//获取表单的属性值
```
![image.png](https://asource.top/upload/2020/11/image-5a980723d8a8452683ed09f9b486bf8a.png)


**密码加密**

使用Security的PasswordEncoder进行密码加密

```java
	//哈希算法加盐
        PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123");
```
返回的字符串就是加密后的密码


**使用PasswordEncoder：**
```xml
<!-- 注入bean -->
<bean id="passwordEncoder" class="org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder"/>

<!-- 在manager中添加 -->
  <security:authentication-manager>
        <security:authentication-provider user-service-ref="MyUserDetailService">
            <!-- 使用加密算法 -->
            <security:password-encoder ref="passwordEncoder"/>
        </security:authentication-provider>
    </security:authentication-manager>
```

### 添加验证码校验:[点击跳转](https://asource.top/archives/security%E6%B7%BB%E5%8A%A0%E9%AA%8C%E8%AF%81%E7%A0%81%E6%A0%A1%E9%AA%8C)


**自定义过滤器**

```xml
	<!-- 自定义Spring Security过滤器 -->
        <security:custom-filter ref="MyValidateCodeFilter" before="FORM_LOGIN_FILTER"/>
```
可设在之前或者之后

### rememberMe 记住我

执行流程：
![image.png](https://asource.top/upload/2020/11/image-35268bd7471a49609513c69ebcd7e13f.png)

首先在登录页面添加单选框：
```html
记住我：<input type="checkbox" name="remember-me" value="true"><br>
```
name必须为：remember-me
value必须为：true

在配置文件中注入remember me相关的bean：
```xml
	<bean id="jdbcTokenRepository" class="org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl">
        <!-- 注入DataSource -->
        <property name="dataSource" ref="dataSource"/>
        <!-- 自动创建remember状态表 -->
        <property name="createTableOnStartup" value="true"/>
    	</bean>
```

再添加到Security:http中

```xml
...以上省略
	<security:http>
	<!-- rememberMe功能 -->
        <!-- token-validity-seconds:token有效秒数 -->
        <security:remember-me token-repository-ref="jdbcTokenRepository" token-validity-seconds="3600"/>
    	</security:http>
```


重启项目，勾选单选框登录成功就能在cookie中看到remember me的cookie：

![image](https://asource.top/upload/2020/11/image-81ec52e704ac4f179c2fa1da9603a709.png)

在数据库中也能看到自动建立的表：

![image.png](https://asource.top/upload/2020/11/image-f17a88339d124695ae0161de6080c746.png)
