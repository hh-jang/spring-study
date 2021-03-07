package com.hhjang.spring.study.jpa.ch2;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "CH2_MEMBER")
@Table(name = "CH2_MEMBER")
@Setter @Getter
public class Member {

    @Id @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String username;

    private Integer age;
}
