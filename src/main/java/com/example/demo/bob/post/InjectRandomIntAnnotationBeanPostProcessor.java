package com.example.demo.bob.post;

import com.example.demo.bob.InjectRandomInt;
import jakarta.annotation.Nonnull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Random;

@Component
public class InjectRandomIntAnnotationBeanPostProcessor implements BeanPostProcessor {

    private static final Random random = new Random();

    @Override
    @SuppressWarnings("java:S3011")
    public Object postProcessBeforeInitialization(@Nonnull Object bean, @Nonnull String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        for (Field field : beanClass.getDeclaredFields()) {
            InjectRandomInt annotation = field.getAnnotation(InjectRandomInt.class);
            if (annotation != null) {
                int min = annotation.min();
                int max = annotation.max();

                int randomNumber = min + random.nextInt(max - min);

                field.setAccessible(true);
                ReflectionUtils.setField(field, bean, randomNumber);
            }
        }
        return bean;
    }

    // init() method: @PostConstruct annotation invoiced between this two methods

    @Override
    public Object postProcessAfterInitialization(@Nonnull Object bean, @Nonnull String beanName) throws BeansException {
        return bean;
    }
}
