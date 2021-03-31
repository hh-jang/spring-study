package com.hhjang.spring.study.jpa.ch7.inheritancemapping.tableperclass;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter @Setter
public class ItemInheritanceTablePerClass {

    @Id @GeneratedValue
    private Long id;

    private String name;

    private Integer price;
}
