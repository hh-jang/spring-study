package com.hhjang.spring.study.jpa.ch7.inheritancemapping.singletable;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
@Getter @Setter
public class ItemInheritanceSingleTable {

    @Id @GeneratedValue
    private Long id;

    private String name;

    private Integer price;
}
