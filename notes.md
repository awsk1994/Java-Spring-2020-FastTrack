# Inversion of Control

Inversion of Control is a principle in software engineering by which the control of objects or portions of a program is transferred to a container or framework. It's most often used in the context of object-oriented programming.

By contrast with traditional programming, in which our custom code makes calls to a library, IoC enables a framework to take control of the flow of a program and make calls to our custom code. To enable this, frameworks use abstractions with additional behavior built in. If we want to add our own behavior, we need to extend the classes of the framework or plugin our own classes.

The advantages of this architecture are:
 - decoupling the execution of a task from its implementation
 - making it easier to switch between different implementations
 - greater modularity of a program
 - greater ease in testing a program by isolating a component or mocking its dependencies and allowing components to communicate through contracts
 - Inversion of Control can be achieved through various mechanisms such as: Strategy design pattern, Service Locator pattern, Factory pattern, and Dependency Injection (DI).

### Dependency Injection

Dependency injection is a pattern through which to implement IoC, where the control being inverted is the setting of object's dependencies.

The act of connecting objects with other objects, or “injecting” objects into other objects, is done by an assembler rather than by the objects themselves.

Here's how you would create an object dependency in traditional programming:

```java
public class Store {
    private Item item;
 
    public Store() {
        item = new ItemImpl1();    
    }
}
```

In the example above, we need to instantiate an implementation of the Item interface within the Store class itself.

By using DI, we can rewrite the example without specifying the implementation of Item that we want:

```java
public class Store {
    private Item item;
    public Store(Item item) {
        this.item = item;		// no need to do "new ItemImpl1()"
    }
}
```

### The Spring IoC Container

An IoC container is a common characteristic of frameworks that implement IoC.

In the Spring framework, the IoC container is represented by the interface ApplicationContext. The Spring container is responsible for instantiating, configuring and assembling objects known as beans, as well as managing their lifecycle.

The Spring framework provides several implementations of the ApplicationContext interface — ClassPathXmlApplicationContext and FileSystemXmlApplicationContext for standalone applications, and WebApplicationContext for web applications.

In order to assemble beans, the container uses configuration metadata, which can be in the form of XML configuration or annotations.

Here's one way to manually instantiate a container:

```java
ApplicationContext context
  = new ClassPathXmlApplicationContext("applicationContext.xml");
```

To set the item attribute in the example above, we can use metadata. Then, the container will read this metadata and use it to assemble beans at runtime.

Dependency Injection in Spring can be done through constructors, setters or fields.

### Constructor-Based Dependency Injection

In the case of constructor-based dependency injection, the container will invoke a constructor with arguments each representing a dependency we want to set.

Spring resolves each argument primarily by type, followed by name of the attribute and index for disambiguation. Let's see the configuration of a bean and its dependencies using annotations:

```java
@Configuration
public class AppConfig {
 
    @Bean
    public Item item1() {
        return new ItemImpl1();
    }
 
    @Bean
    public Store store() {
        return new Store(item1());
    }
}
```

 - The @Configuration annotation indicates that the class is a source of bean definitions. Also, we can add it to multiple configuration classes.
 - The @Bean annotation is used on a method to define a bean. If we don't specify a custom name, the bean name will default to the method name.

Another way to create the configuration of the beans is through XML configuration:
```java
<bean id="item1" class="org.baeldung.store.ItemImpl1" /> 
<bean id="store" class="org.baeldung.store.Store"> 
    <constructor-arg type="ItemImpl1" index="0" name="item" ref="item1" /> 
</bean>
```

### Setter-Based Dependency Injection

For setter-based DI, the container will call setter methods of our class, after invoking a no-argument constructor or no-argument static factory method to instantiate the bean. Let's create this configuration using annotations:

```java
@Bean
public Store store() {
    Store store = new Store();
    store.setItem(item1());
    return store;
}
```

We can also use XML for the same configuration of beans:
```java
<bean id="store" class="org.baeldung.store.Store">
    <property name="item" ref="item1" />
</bean>
```

Constructor-based and setter-based types of injection can be combined for the same bean. The Spring documentation recommends using constructor-based injection for mandatory dependencies, and setter-based injection for optional ones.


Link: https://www.baeldung.com/inversion-control-and-dependency-injection-in-spring


