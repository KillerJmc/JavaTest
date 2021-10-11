package com.test.apply.spring.ioc.core;

import com.jmc.io.Files;
import com.jmc.lang.extend.Tries;
import com.test.apply.spring.ioc.util.XmlReader;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * 基于类加载路径的应用上下文对象
 * @author Jmc
 */
public class ClassPathApplicationContext implements ApplicationContext {
    /**
     * 配置文件对应的类加载路径
     */
    private final String configClassPath;

    /**
     * IOC容器，储存对象 <br>
     * bean的id -> bean对象
     */
    private Map<String, Object> iocContainer;

    public ClassPathApplicationContext(String configClassPath) {
        this.configClassPath = configClassPath;

        initIocContainer();
    }

    @SuppressWarnings("all")
    private void initIocContainer() {
        var startMilli = System.currentTimeMillis();

        this.iocContainer = new HashMap<>();

            var xmlRealPath = ClassPathApplicationContext.class
                    .getClassLoader()
                    .getResource(configClassPath)
                    .getPath();

        var xmlReader = new XmlReader(Files.read(xmlRealPath));
        // 获取bean的id，class以及构造器填入的ref属性（<constructor-arg标签下）
        xmlReader.getProperties("bean", "id", "class", "ref")
                 .forEach(Tries.throwsE(m -> {
                     String injectedBeanName;
                     Object beanInstance;
                     // 判断是否需要通过构造器注入
                     if ((injectedBeanName = m.get("ref")) != null) {
                         var injectedBean = getBean(injectedBeanName);
                         if (injectedBean == null) {
                             throw new NoSuchElementException("不存在名字为：" + injectedBeanName + "的bean");
                         }
                         beanInstance = Class.forName(m.get("class"))
                                 // 构造器注入的类型是bean实现的接口
                                 .getDeclaredConstructor(injectedBean.getClass().getInterfaces()[0])
                                 .newInstance(injectedBean);
                     } else {
                         beanInstance = Class.forName(m.get("class"))
                                 .getDeclaredConstructor()
                                 .newInstance();
                     }
                     iocContainer.put(m.get("id"), beanInstance);
                 }));

        System.err.printf("ApplicationContext @ %s: ioc容器初始化完成，耗时%dms！\n\n", this.hashCode(), System.currentTimeMillis() - startMilli);
    }

    @Override
    public Object getBean(String beanId) {
        return iocContainer.get(beanId);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> beanType) {
        return iocContainer.values().stream()
                // 查找实现接口的Class对象与传入Class对象相同的实例或者本身与传入Class对象相同的实例
                .filter(bean -> {
                    var targetI = bean.getClass().getInterfaces();
                    return beanType.equals(targetI.length >= 1 ? targetI[0] : bean.getClass());
                })
                .map(t -> (T) t)
                .toList()
                .get(0);
    }
}
