package com.fundamentosplatzi.springboot.fundamentos.pojo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "user")
public class UserPojo {

    private String email;
    private String password;
    private int age;
}
