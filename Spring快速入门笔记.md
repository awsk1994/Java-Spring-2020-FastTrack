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
<!-- Note: We don't have constructor with 3 arg -->
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
<bean class="org.wong.ioc.model.User" id="user3" p:username="wong3" p:id="3"></bean>
```
```java
User user3 = ctx.getBean("user3", User.class);
System.out.println("user3 = " + user3);
```
```
user3 = User{username='wong3', address='null', id=3}
```
### 外部Bean的注入


### TODO

There are more configurations. Ref, Type.etc that you can insert into beans

https://blog.csdn.net/qq_20008183/article/details/86624209

