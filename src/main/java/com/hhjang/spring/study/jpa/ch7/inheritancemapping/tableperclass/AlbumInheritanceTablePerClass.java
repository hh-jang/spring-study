package com.hhjang.spring.study.jpa.ch7.inheritancemapping.tableperclass;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class AlbumInheritanceTablePerClass extends ItemInheritanceTablePerClass {

    private String artist;
}
