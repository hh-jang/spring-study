package com.hhjang.spring.study.transaction.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Setter @Getter
public class User {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String email;
}
