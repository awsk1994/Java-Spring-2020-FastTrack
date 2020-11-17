package org.wong.aware;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
@PropertySource(value = "wong.properties")  // Load wong.properties into environment
public class AwareService implements BeanNameAware, BeanFactoryAware, ResourceLoaderAware, EnvironmentAware {
    private String beanName;
    private ResourceLoader resourceLoader;
    private Environment environment;

    /**
     * 获取bean的生成工厂
     * @param beanFactory
     * @throws BeansException
     */
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {

    }

    public void output() throws IOException {
        System.out.println("Bean name = " + beanName);

        // Read from wong.txt
        Resource resource = resourceLoader.getResource("wong.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        String s = br.readLine();
        System.out.println("s = " + s);
        br.close();

        // Get from wong.properties
        String address = environment.getProperty("wong.address");
        System.out.println("address = " + address);
    }

    /**
     * 获取bean的名称
     * @param s
     */
    public void setBeanName(String s) {
        this.beanName = s;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
