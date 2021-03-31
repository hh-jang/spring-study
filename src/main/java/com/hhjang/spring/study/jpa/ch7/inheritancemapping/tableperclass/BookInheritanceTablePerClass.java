package com.hhjang.spring.study.jpa.ch7.inheritancemapping.tableperclass;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class BookInheritanceTablePerClass extends ItemInheritanceTablePerClass {

    private String author;

    private String isbn;
}
