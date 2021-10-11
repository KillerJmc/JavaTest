package com.test.apply.spring.test.pojo;

import lombok.Builder;
import lombok.Data;

/**
 * @author Jmc
 */
@Data
public class User {
    private Integer id;
    private String name;
    private Integer age;
    private Double money;
}
