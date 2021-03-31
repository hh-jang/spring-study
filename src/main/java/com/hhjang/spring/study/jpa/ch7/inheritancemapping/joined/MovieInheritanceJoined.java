package com.hhjang.spring.study.jpa.ch7.inheritancemapping.joined;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("M")
@Getter @Setter
public class MovieInheritanceJoined extends ItemInheritanceJoined {

    private String director;

    private String actor;
}
