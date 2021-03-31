package com.hhjang.spring.study.jpa.ch7.inheritancemapping.joined;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter
@DiscriminatorColumn(name = "DTYPE")
public class ItemInheritanceJoined {

    @Id @GeneratedValue
    private Long id;

    private String name;

    private Integer price;
}
