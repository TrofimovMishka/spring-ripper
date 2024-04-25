package com.example.demo.listener;

import com.example.demo.bob.post.PostProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class PostProxyInvoker implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        for (String name : applicationContext.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(name);
            String originalClassName = beanDefinition.getBeanClassName(); //original class name
            if (originalClassName != null) {
                try {
                    Class<?> originalClass = Class.forName(originalClassName);
                    for (Method method : originalClass.getMethods()) {
                        if (method.isAnnotationPresent(PostProxy.class)) {
                            // inside original class
                            //try find this method in proxy of this class:
                            Object proxyOfOriginalClass = applicationContext.getBean(name); //this if proxy of original class
                            Method currentMethodFromProxy = proxyOfOriginalClass.getClass().getMethod(method.getName(), method.getParameterTypes());
                            currentMethodFromProxy.invoke(proxyOfOriginalClass); // if add second param - exception occurred
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
