# Spring 快速入门 2020 版【基础篇】

## 04.IoC 简介
## 05.Ioc 初体验
 - create new project
 - add spring-context into dependency (pom.xml)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>ioc01</groupId>
    <artifactId>org.wong</artifactId>
    <version>1.0-SNAPSHOT</version>

    <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.2.6.RELEASE</version>
        </dependency>

    </dependencies>
</project>
```

 - right click on resources -> new -> XML Configuration File -> Spring Config -> "applicationContext.xml"
 	 - For me, I couldn't find Spring Config, so I just created a xml file called applicationContext.xml and inserted the following:
 ```xml
<?xml version = "1.0" encoding = "UTF-8"?>
<beans xmlns = "http://www.springframework.org/schema/beans"
       xmlns:xsi = "http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation = "http://www.springframework.org/schema/beans
   http://www.springframework.org/schema/beans/spring-beans-3.0.xsd">
</beans>
```

 - Right click on java, create package, "org.wong.ioc.model".
 - Create class, "User"
```java
package org.wong.ioc.model;

public class User {
    private String username;
    private String address;
    private Integer id;

    public User(){
        System.out.println("----------- init User -----------");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
```

 - Create a Main class in "org.wong.ioc" (might want to click "Hide Empty Middle Package" to unhide for convenience. This is located in the file directory window)


 - The usual way to call User is to include the following in Main.java:
 ```java
package org.wong.ioc;
import org.wong.ioc.model.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        // 正常的方法
        User user = new User();
        System.out.println("user = " + user);
    }
}
```

 - But to do it via IoC, we need to do 2 things.
 - First, Go to applicationContext.xml and add:

```xml
<?xml version = "1.0" encoding = "UTF-8"?>
<beans xmlns = "http://www.springframework.org/schema/beans"
       xmlns:xsi = "http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation = "http://www.springframework.org/schema/beans
   http://www.springframework.org/schema/beans/spring-beans-3.0.xsd">

    <bean class="org.wong.ioc.model.User" id="user"></bean> <!-- (ADD THIS LINE) User reflection on class=... -->
</beans>
```

 - Second, Go to Main.java
 ```java
package org.wong.ioc;
import org.wong.ioc.model.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        // (ADD THIS LINE) This will instantiate User.
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    }
}
```
 - When you run new ClassPathXmlApplicationContext("applicationContext.xml"), it will instantiate User. We can prove this by running Main.java, and get:
```
===init===
```

 - Next, 怎么获取User？ 我用以下代码即可。
```java
User u1 = (User)ctx.getBean("user");
User u2 = ctx.getBean("user", User.class);
User u3 = ctx.getBean(User.class);
System.out.println("u1 = " + u1);
System.out.println("u2 = " + u2);
System.out.println("u3 = " + u3);
```
```
u1 = org.wong.ioc.model.User@91161c7
u2 = org.wong.ioc.model.User@91161c7
u3 = org.wong.ioc.model.User@91161c7
```

 - 可以看到u1,u2,u3的地址都是一样的。

 - There is actually another way to do what ClassPathXmlApplicationContext does. It's less used - FileSystemXmlApplicationContext
```java
FileSystemXmlApplicationContext ftx = new FileSystemXmlApplicationContext(<ABSOLUTE PATH, String>)
```

## 06.基本属性注入

 - If we add toString() to User.java, and run Main.java again.
```java
@Override
public String toString() {
    return "User{" +
            "username='" + username + '\'' +
            ", address='" + address + '\'' +
            ", id=" + id +
            '}';
}
```
```
----------- init User -----------
u1 = User{username='null', address='null', id=null}
u2 = User{username='null', address='null', id=null}
u3 = User{username='null', address='null', id=null}
```
 - You will see username, address and id are null.

### 构造方法注入
 - In applicationContext.xml, modify <bean>....

```xml
<bean class="org.wong.ioc.model.User" id="user"> <!-- class=... 使用反射 -->
    <constructor-arg name="username" value="wong"/>
    <constructor-arg name="id" value="1"/>  <!-- This only works for constructor with 2 args -->
</bean>

<bean class="org.wong.ioc.model.User" id="user2"> <!-- class=... 使用反射 -->
    <constructor-arg name="username" value="wong2"/>
    <constructor-arg name="id" value="2"/>  <!-- This only works for constructor with 2 args -->
</bean>
```
 - Run Main.java
```java
User user1 = ctx.getBean("user", User.class);
User user2 = ctx.getBean("user2", User.class);
System.out.println("user1 = " + user1);
System.out.println("user2 = " + user2);
```
```
user1 = User{username='wong', address='null', id=1}
user2 = User{username='wong2', address='null', id=2}
```

### set方法注入
```xml
<!-- Note: We don't have constructor with 3 arg, but require setter-->
<bean class="org.wong.ioc.model.User" id="user2">
    <property name="username" value="wong2"/>
    <property name="id" value="2"/>
    <property name="address" value="abc street"/>
</bean>
```

```java
User user2 = ctx.getBean("user2", User.class);
System.out.println("user2 = " + user2);
```

### p名称空间注入

```xml
<!-- require setter -->
<bean class="org.wong.ioc.model.User" id="user3" p:username="wong3" p:id="3"></bean>
```
```java
User user3 = ctx.getBean("user3", User.class);
System.out.println("user3 = " + user3);
```
```
user3 = User{username='wong3', address='null', id=3}
```

### TODO

There are more configurations. Ref, Type.etc that you can insert into beans

https://blog.csdn.net/qq_20008183/article/details/86624209

## 07. 工厂方法注入／外部Bean的注入

 - We need to first use some sort of builder method.

 - Import okhttp3 dependency in pom.xml:

```xml
<dependency>
    <groupId>com.squareup.okhttp3</groupId>
    <artifactId>okhttp</artifactId>
    <version>4.7.2</version>
</dependency>
```

 - Create OkHttpTest.java:
 ```java
 package org.wong.ioc;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class OkHttpTest {
    public static void main(String[] args) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
            .get()
            .url("http://www.baidu.com")
            .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("Err = " + e.getMessage());
            }

            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                System.out.println("Body = " + response.body().string());
            }
        });
    }
}
```

### i. 静态工厂注入

 - Create OkHttpStaticFactory.java:
 ```java
package org.wong.ioc;

import okhttp3.OkHttpClient;

public class OkHttpStaticFactory {
    private static OkHttpClient okHttpClient;
    public static OkHttpClient getInstance(){
        if(okHttpClient == null){
            okHttpClient = new OkHttpClient.Builder().build();
        }
        return okHttpClient;
    };
};
```

 - We can add this to bean (applicationContext.xml):
 ```xml
<bean class="org.wong.ioc.OkHttpStaticFactory" factory-method="getInstance" id="okHttpClient"/>
```

 - In OkHttpTest.java, we can create okHttpClient using ctx.
 ```java
//        // The normal way of creating okHttpClient
//        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        // Creating okHttpClient via ctx.
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        OkHttpClient okHttpClient = ctx.getBean("okHttpClient", OkHttpClient.class);
```



### ii. 实例工厂注入

 - Create OkHttpFactory.java:
```java
package org.wong.ioc;

import okhttp3.OkHttpClient;

public class OkHttpFactory {
    private OkHttpClient okHttpClient;
    public OkHttpClient getInstance(){
        if(okHttpClient == null){
            okHttpClient = new OkHttpClient.Builder().build();
        }
        return okHttpClient;
    }
}
```

 - Notice that OkHttpFactory is not static, and therefore to call in beans, we need to first instantiate OkHttpFactory and then call getInstance() to get okHttpClient.
 - in applicationContext.xml: 
```xml
<!--    <bean class="org.wong.ioc.OkHttpStaticFactory" factory-method="getInstance" id="okHttpClient"/>-->
<bean class="org.wong.ioc.OkHttpFactory" id="okHttpFactory"/>
<bean class="okhttp3.OkHttpClient" factory-bean="okHttpFactory" factory-method="getInstance" id="okHttpClient"/>
```

## 08.复杂属性注入

 - Create a Cat.java and add cat attribute to User.java:
```java
package org.wong.ioc.model;

public class Cat {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
```
```java
private Cat cat;
public void setCat(Cat cat) {
    this.cat = cat;
}
```

### i. 对象注入

```xml
<bean class="org.wong.ioc.model.User" id="user4">
    <property name="username" value="wong4"/>
    <property name="id" value="4"/>
    <property name="address" value="cat street"/>
    <property name="cat" ref="cat"/>
</bean>

<bean class="org.wong.ioc.model.Cat" id="cat">
    <property name="name" value="cat1"/>
    <property name="age" value="9"/>
</bean>
```
```java
User user4 = ctx.getBean("user4", User.class);
System.out.println("user4 = " + user4);
```
```
user4 = User{username='wong4', address='cat street', id=4, cat=Cat{name='cat1', age=9}}
```

### ii. 数组注入

 - Add Cat[] cats and String[] hobbies into User.java
```java
private Cat[] cats;
private String[] hobbies;
...
@Override
public String toString() {
    return "User{" +
            "username='" + username + '\'' +
            ", address='" + address + '\'' +
            ", id=" + id +
            ", cat=" + cat +
            ", cats=" + Arrays.toString(cats) +
            ", hobbies=" + Arrays.toString(hobbies) +
            '}';
}
```

 - Add to bean (applicationContext.xml)
```xml
<bean class="org.wong.ioc.model.User" id="user5">
    <property name="username" value="wong5"/>
    <property name="id" value="5"/>
    <property name="address" value="cats and hobbies street"/>
    <property name="cat" ref="cat"/>
    <property name="cats">
        <array>
            <!-- method number 1, bean reference -->
            <ref bean="cat"/>
            <!-- method number 2, create new cat here. -->
            <bean class="org.wong.ioc.model.Cat" id="cat2">
                <property name="name" value="cat2"/>
                <property name="age" value="10"/>
            </bean>
        </array>
    </property>
    <property name="hobbies">
        <list>
            <value>Basketball</value>
            <value>Football</value>
        </list>
    </property>
</bean>
```

```java
User user5 = ctx.getBean("user5", User.class);
System.out.println("user5 = " + user5);
```
```
user5 = User{username='wong5', address='cats and hobbies street', id=5, cat=Cat{name='cat1', age=9}, cats=[Cat{name='cat1', age=9}, Cat{name='cat2', age=10}], hobbies=[Basketball, Football]}
```

### iii. Map注入 + Properties注入

In User.java:
```java
private Map<String, Object> details;
private Properties info;
// update toString()
// set up getters + setters
```

 - Modify user5 in bean (applicationContext.xml)
```xml
<property name="details">
    <map>
        <entry key="gender" value="male"/>
        <entry key="age" value="99"/>
    </map>
</property>

<property name="info">
    <props>
        <prop key="phone">123456</prop>
    </props>
</property>
```
```
user5 = User{username='wong5', address='cats and hobbies street', id=5, cat=Cat{name='cat1', age=9}, cats=[Cat{name='cat1', age=9}, Cat{name='cat2', age=10}], hobbies=[Basketball, Football], details={gender=male, age=99}, info={phone=123456}}
```

## 09.Java 配置 (Java-Based Configuration)

在Spring 中，想要将一个Bean 注册到Spring 容器中，整体上来说，有三种不同的方式：
 - XML 注入，如前文所说
 - Java 配置（通过Java 代码将  Bean 注册到Spring 容器中）
 - 自动化扫描

 - First, create a new package, "javaconfig" and create a new class inside called "SayHello":
```java
package org.wong.ioc.javaconfig;

public class SayHello {
    public String sayHello(String name){
        return "hello " + name;
    }
}
```

 - Then, create a class "JavaConfig":
```java
package org.wong.ioc.javaconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * @Configuration 注解表示这是一个Java配置类，配置类的作用类似于 applicationContext.xml
 */
@Configuration
public class JavaConfig {
    @Bean("sh")
    SayHello sayHello(){
        return new SayHello();
    }
}
```
 - This is similar to what applicationContext.xml does. 
 - Annotating a class with the @Configuration indicates that the class can be **used by the Spring IoC container as a source of bean definitions.**
 -  The @Bean annotation tells Spring that a method annotated with @Bean will **return an object that should be registered as a bean in the Spring application context.**
 - Source: https://www.tutorialspoint.com/spring/spring_java_based_configuration.htm

 - To test, create a JavaConfigTest class:
```java
package org.wong.ioc;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.wong.ioc.javaconfig.JavaConfig;
import org.wong.ioc.javaconfig.SayHello;

public class JavaConfigTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(JavaConfig.class);
        SayHello sayHello = ctx.getBean("sh", SayHello.class); // 这个是根据 JavaConfig class里的方法名字.
        System.out.println(sayHello.sayHello("Wong"));
    }
}
```
## 10. 自动化配置

 - 可以通过Java配置 或 xml配置 来实现。
 - will automatically scan for all class with @Service.etc annotation. No need to manually add each one.

### @Service
 - 例如我有一个UserService，我希望在自动化扫描时，这个类能够自动注册到Spring容器中去，那么可以给该类添加一个@Service，作为一个标记。
 - 和@Service 注解功能类似的注解，一共有四个：
 	 - @Component
 	 - @Repository
 	 - @Service
 	 - @Controller
 - 这四个中，另外三个都是基于 @Component 
 - 做出来的，而且从目前的源码来看，功能也是一致的
 - 那么为什么要搞三个呢？主要是为了在不同的类上面添加时方便。
	 - 在Service 层上，添加注解时，使用@Service
	 - 在Dao 层，添加注解时，使用@Repository
	 - 在Controller 层，添加注解时，使用@Controller
	 - 在其他组件上添加注解时，使用@Component

### 例子（Java配置自动扫描）
 - Create UserService

```java
package org.wong.ioc.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    public List<String> getAllUsers(){
        List<String> users = new ArrayList<String>();
        for(int i=0; i<10; i++){
            users.add("wong:" + i);
        }
        return users;
    }
}
```

 - In JavaConfig, add @Component Scan on top of class
```java
@ComponentScan(basePackages = "org.wong.ioc.service")	// ADD THIS LINE
public class JavaConfig {
```

 - Test it:
```java
UserService userService = ctx.getBean(UserService.class);
List<String> allUsers = userService.getAllUsers();
System.out.println("allUsers = " + allUsers);
```

### 问题

 - Bean 的名字叫什么？默认情况下，Bean的名字是类名首字母小写。
 	 - 例如上面的UserService，它的实例名，默认就是UserService。
 	 - 如果开发者想要自定义名字，就直接在@Service 注解中添加即可
```java
@Service("us")
```

 - 有几种扫描方式？上面的配置，我们是按照包的位置来扫描的。
 - 也就是说，Bean 必须放在指定的扫描位置，否则，即使你有@Service注解，也扫描不到。
 - 除了按照包的位置来扫描，还有另外一种方式，就是根据注解来扫描。例如如下配置：
```java
@Configuration
@ComponentScan(basePackages = "org.javaboy.javaconfig", useDefaultFilters = true, excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Controller.class)}) 
public class JavaConfig {} 
```
 - 这个配置表示扫描org.javaboy.javaconfig 下的所有Bean，但是除了Controller
 - useDefaultFilters = True means scan all types (@service, @component.etc), = False means don't scan any.
 	 - you can set useDefaultFilters to False, and then add an includeFilters, or the opposite with excludeFilters.

### XML 配置自动扫描
 - in applicationContext.xml, insert this:
```xml
<context:component-scan base-package="org.wong.ioc.service" use-default-filters="false">
    <context:include-filter type="annotation" expression="org.springframework.stereotype.Service"/>
</context:component-scan>
```
 - This will scan org.wong.ioc.service package and inside it, scan for @Service.

 - To test, create a new class, "XMLTest":
```java
ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

UserService userService = ctx.getBean(UserService.class);
System.out.println(userService.getAllUsers());
```
```
[wong:0, wong:1, wong:2, wong:3, wong:4, wong:5, wong:6, wong:7, wong:8, wong:9]
```

## 11. 对象注入

自动扫描的对象注入有三种方法：
1. @Autowired
2. @Resources
3. @Injected

 - @Autowired 是**根据类型去查找**，然后赋值(fu4 zhi2, assignment)，这就有一个要求，**这个类型只可以有一个对象**，否则就会报错。
 - @Resources 是**根据名称去查找**，默认情况下，定义的变量名，就是查找的名称，当然开发者也可以在@Resources 注解中手动指定。
 - 所以，如果一个类存在多个实例，那么就应该使用@Resources去注入
 	 - 如果非得使用@Autowired，也是可以的，此时需要配合另外一个注解，@Qualifier，在@Qualifier中可以指定变量名，两个一起用（@Qualifier 和@Autowired）就可以实现通过变量名查找到变量。

### 例子

 - Create Service called "UserDao.java":
```java
package org.wong.ioc.javaconfig;

import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
    public String hello(){
        return "user dao saying hello";
    }
}
```

 - Modify UserService to add UserDao with @Autowired.
```java
@Service
public class UserService {	
    @Autowired 				// ADD THIS LINE
    UserDao userDao;		// ADD THIS LINE

    public List<String> getAllUsers(){
        String hello = userDao.hello();				// ADD THIS LINE
        System.out.println("hello = " + hello);		// ADD THIS LINE
```

 - Modify JavaConfig to scan for UserDao.
```java
@ComponentScan(basePackages = "org.wong.ioc")
```

 - output
```
hello = user dao saying hello
```

## 12.条件配置
 - 条件注解就是在满足**某一个条件的情况下，生效的配置**。

 - Create "ShowCmd" Interface:
```java
package org.wong.ioc;

public interface ShowCmd {
    String showCmd();
}
```

 - Create "MacShowCmd" and "WindowsShowCmd" class, which implements ShowCmd. These will become the beans.
```java
package org.wong.ioc;

public class WindowsShowCmd implements ShowCmd {
    public String showCmd(){
        return "dir";
    }
}
```
```java
package org.wong.ioc;

public class MacShowCmd implements ShowCmd {
    public String showCmd() {
        return "ls";
    }
}
```

 - Create "MacCondition" and "WindowsCondition", which implements Condition. These will be the condition to choose a particular bean over another. We do this by finding the environment and seeing if "win" or "mac" is in the environment name.
```java
package org.wong.ioc;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class WindowsCondition implements Condition {
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata){
        String osName = conditionContext.getEnvironment().getProperty("os.name");
        return osName.toLowerCase().contains("win");
    }
}
```
```java
package org.wong.ioc;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class MacCondition implements Condition {
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata){
        String osName = conditionContext.getEnvironment().getProperty("os.name");
        return osName.toLowerCase().contains("mac");
    }
}
```


 - Set up JavaConfig class: 2 Beans with same name + Condition.
```java
package org.wong.ioc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaConfig {
    @Bean("cmd")        // same name to allow @Condition to check which one to use.
    @Conditional(WindowsCondition.class)
    ShowCmd winCmd(){
        return new WindowsShowCmd();
    }

    @Bean("cmd")        // same name to allow @Condition to check which one to use.
    @Conditional(MacCondition.class)
    ShowCmd macCmd(){
        return new MacShowCmd();
    }
}
```

 - Note that both Beans have the same name, "cmd" so we have to select between one of the two beans. We select by testing the condition in @Condition (MacCondition.class or WindowsCondition.class)

 - Test:
```java
package org.wong.ioc;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(JavaConfig.class);
        ShowCmd cmd = (ShowCmd) ctx.getBean("cmd");
        String s = cmd.showCmd();
        System.out.println("cmd = " + s);
    }
}
```
```
cmd = ls
```

## 13. Profile环境切换

开发中，如何在开发／测试环境之间进行快速切换？Spring 中提供了Profile来解决这个问题。


```java
public class DataSource {
    private String url;
    private String username;
    private String password;

    // Getter + Setter + toString()
}
```

```java
    @Bean
    @Profile("dev")
    DataSource devOps() {
        DataSource ds = new DataSource();
        ds.setUrl("abc");
        ds.setUsername("do_uname");
        ds.setPassword("do_uname_pw");
        return ds;
    }

    @Bean
    @Profile("prod")
    DataSource prodOps() {
        DataSource ds = new DataSource();
        ds.setUrl("abc_prod");
        ds.setUsername("do_uname_prod");
        ds.setPassword("do_uname_pw_prod");
        return ds;
    }
```

```java
package org.wong.ioc;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestProfile {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().setActiveProfiles("dev");
        ctx.register(JavaConfig.class);
        ctx.refresh();
        System.out.println(ctx.getBean(DataSource.class));
    }
}
```
```
DataSource{url='abc', username='do_uname', password='do_uname_pw'}
```

## 14. Bean的作用域

如果我们执行以下：
```java
AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
ctx.getEnvironment().setActiveProfiles("dev");  // Configured with @Scope("prototype")
ctx.register(JavaConfig.class);
ctx.refresh();
DataSource d1 = ctx.getBean(DataSource.class);
DataSource d2 = ctx.getBean(DataSource.class);
System.out.println(d1 == d2);
```
```
true
```

 - Solution is to set a Scope of "prototype".

```java
    @Bean
    @Profile("dev")
    @Scope("prototype")     // # Setting a scope. 
    DataSource devOps() {
        DataSource ds = new DataSource();
        ds.setUrl("abc");
        ds.setUsername("do_uname");
        ds.setPassword("do_uname_pw");
        return ds;
    }
```

 - Test Code:

 ```java
AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
ctx.getEnvironment().setActiveProfiles("dev");  // Configured with @Scope("prototype")
ctx.register(JavaConfig.class);
ctx.refresh();
DataSource d1 = ctx.getBean(DataSource.class);
DataSource d2 = ctx.getBean(DataSource.class);
System.out.println(d1 == d2);

AnnotationConfigApplicationContext ctx2 = new AnnotationConfigApplicationContext();
ctx2.getEnvironment().setActiveProfiles("prod");    // Configured w/o @Scope("prototype")
ctx2.register(JavaConfig.class);
ctx2.refresh();
DataSource d3 = ctx2.getBean(DataSource.class);
DataSource d4 = ctx2.getBean(DataSource.class);
System.out.println(d3 == d4);
```
```
false 
true
```

 - Other types of scopes: singleton, prototype, request, session, global-session.
     - https://www.tutorialspoint.com/spring/spring_bean_scopes.htm

 - Skip xml version of implementing @scope.

## 15. id和name的区别
 - The only difference between an id and a name is that:
     - a name can contain multiple aliases separated by a comma, semicolon or whitespace
     - whereas, an id must be a single value.

 - Example:
```xml
<!-- Example of id(someId) -->
<bean class="org.wong.ioc.model.User" id="someId">
    <property name="username" value="wong5"/>
</bean>

<!-- Example of name(someName) -->
<bean class="org.wong.ioc.model.User" name="someName">
    <property name="username" value="wong5"/>
</bean>
```

 - We can create the instances using:

```java
User userById = ctx.getBean("someId")
User userByName = ctx.getBean("someName");
```

 - But if we modify it to be:
```xml
<!-- Example of multiple id -->
<bean class="org.wong.ioc.model.User" id="someId1,someId2">
    <property name="username" value="wong5"/>
</bean>

<!-- Example of multiple name -->
<bean class="org.wong.ioc.model.User" name="someName1,someName2">
    <property name="username" value="wong5"/>
</bean>
```

 - When we create using the following code, userById will not work.

```java
User userById = ctx.getBean("someId1")          // this will NOT WORK!
User userByName = ctx.getBean("someName1");     // works.
```
## 16. 混合配置

 - We can use a JavaConfig class that uses a xml(applicationContext.xml). 
 - We don't use this much in production though; choose either JavaConfig class or xml.

 - Code below:

```xml
<!-- applicationContext.xml -->
<?xml version = "1.0" encoding = "UTF-8"?>
<beans xmlns = "http://www.springframework.org/schema/beans"
       xmlns:xsi = "http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans-3.0.xsd">

    <bean class="org.wong.ioc.User" id="user2">
        <property name="username" value="wong2"/>
        <property name="id" value="2"/>
    </bean>
</beans>
```

```java
// User class
public class User {
    String username;
    int id;
    // Getters & Setters
}
```

```java
// JavaConfig class
package org.wong.ioc;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:applicationContext.xml")
public class JavaConfig2 {
}
```

```java
// Main method (testing)
AnnotationConfigApplicationContext ctx2 = new AnnotationConfigApplicationContext(JavaConfig2.class);
System.out.println("user = " + (User) ctx2.getBean("user2"));
```

 - Output:
```
user = User{username='wong2', id=2}
```

## 17. Aware 接口

 - Actually, don't really understand this part. 
 - Using Aware interface makes us more coupled with the Spring framework, which is not good.
 - Also, aware is not really used in production - so going to leave it for now.

## 18. AOP简介

 - Very popular.
 - AOP (Aspect Oriented Programming), 面向切面编程。
 - AOP就是在程序运行时，不改变程序源代码的情况下，动态地增强方法的功能，常见的使用场景非常多：
     - 日记
     - 事务
     - 数据库操作

 - 基本概念：
     - 切点：要添加代码的地方
     - 通知（增强）：向切点动态添加代码
     - 切面：切点+通知
     - 连接点：切点的位子

 - AOP的实现：
     - cglib
     - jdk

## 19. 基于JDK动态代理实现的AOP
 - Implemented proxy design pattern.

## 20. AOP 初体验
 - 这里介绍两种**切点的定义方式**：
     - 使用自定义注解
     - 使用规则
 - 总结
     - 其中，使用自定义注解标记切点，是侵入式的，所以这种方式在实际开发中不推荐，仅作为了解
     - 另一种使用规则来定义切点的方式，无侵入，一般推荐使用这种方式。

我们引入Spring依赖
```xml
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.9.5</version>
</dependency>
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjrt</artifactId>
    <version>1.9.5</version>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.2.6.RELEASE</version>
</dependency>
```

**自定义注解**

首先自定义一个注解：

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {
}
```

然后在需要拦截的方法上，添加该注解，在add 方法上添加了@Action 注解，表示该方法将会被AOP 拦截，而其他未添加该注解的方法则不受影响。
```java
@Component
public class MyCalculatorImp implements MyCalculator{
    @Action // 侵入型，不建议用
    public int add(int a, int b) {
        System.out.println("add(" + a + ", " + b + ")");
        return a-b;
    }

    public void min(int a, int b) {
        System.out.println("min | " + a + "-" + b + " = " + (a-b));
    }
}
```

接下来，定义增强（通知，advice）
```java
public class LogAspect {
    /**
     * 前置通知；在method逻辑／代码之前的
     * @param joinPoint
     */
    @Before("@annotation(Action)")  // To indicate to use this before the @Action
    public void before(JoinPoint joinPoint){
        String name = joinPoint.getSignature().getName();
        System.out.println(name + "方法开始执行了。");
    }
}
```

运行一下：
```java
AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(JavaConfig.class);
MyCalculator calculator = ctx.getBean(MyCalculator.class);
calculator.add(3,4);
calculator.min(3,4);
```
```
add方法开始执行了。 // 在add方法之前，我们的通知执行了。
add(3, 4)         // add方法执行。
3-4 = -1          // min方法执行，但没有执行通知因为没有注解。
```






