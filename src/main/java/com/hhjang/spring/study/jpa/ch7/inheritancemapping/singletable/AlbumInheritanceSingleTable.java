package com.hhjang.spring.study.jpa.ch7.inheritancemapping.singletable;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@DiscriminatorValue("A")
@Getter @Setter
public class AlbumInheritanceSingleTable extends ItemInheritanceSingleTable {

    private String artist;
}
