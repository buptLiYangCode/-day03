package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * DTO主要用于在不同层之间传递数据。
 * VO主要用于在视图层中展示数据。
 * Entity则是用于在数据库中存储数据的对象。
 */
@Data
public class EmployeeDTO implements Serializable {

    private Long id;
    private String username;

    private String name;

    private String phone;

    private String sex;

    private String idNumber;

}
