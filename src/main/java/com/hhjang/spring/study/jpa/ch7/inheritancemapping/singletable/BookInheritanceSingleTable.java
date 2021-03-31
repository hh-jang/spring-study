package com.hhjang.spring.study.jpa.ch7.inheritancemapping.singletable;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("B")
@PrimaryKeyJoinColumn(name = "BOOK_ID")
@Getter @Setter
public class BookInheritanceSingleTable extends ItemInheritanceSingleTable {

    private String author;

    private String isbn;
}
