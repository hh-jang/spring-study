package com.hhjang.spring.study.jpa.ch7.inheritancemapping.joined;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("B")
@PrimaryKeyJoinColumn(name = "BOOK_ID")
@Getter @Setter
public class BookInheritanceJoined extends ItemInheritanceJoined {

    private String author;

    private String isbn;
}
