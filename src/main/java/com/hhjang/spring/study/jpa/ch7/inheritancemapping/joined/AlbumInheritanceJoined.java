package com.hhjang.spring.study.jpa.ch7.inheritancemapping.joined;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("A")
@Getter @Setter
public class AlbumInheritanceJoined extends ItemInheritanceJoined {

    private String artist;
}
