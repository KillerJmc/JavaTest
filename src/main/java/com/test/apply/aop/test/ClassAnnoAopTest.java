package com.test.apply.aop.test;

import com.test.apply.aop.ClassAnnoAop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jmc
 */
public class ClassAnnoAopTest {
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    private @interface AddPrefix {
        String value();
    }

    @AddPrefix("???")
    public static class UserService {
        public String getPassword(String name) {
            if ("Jmc".equals(name)) {
                return "123456";
            }
            return null;
        }

        public String getName() {
            return "Jmc";
        }
    }

    public static void main(String[] a) throws Exception {
        var userService = ClassAnnoAop.getInstance(UserService.class, AddPrefix.class, (classAnno, method, args) -> {
            var prefix = classAnno.value();
            // 全部方法返回值加前缀
            return prefix + method.apply(args);
        });

        var name = userService.getName();
        System.out.println("name = " + name);

        var password = userService.getPassword(name);
        System.out.println("password = " + password);
    }
}
