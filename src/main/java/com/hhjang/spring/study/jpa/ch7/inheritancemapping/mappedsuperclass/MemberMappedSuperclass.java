package com.hhjang.spring.study.jpa.ch7.inheritancemapping.mappedsuperclass;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter @Setter
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "MEMBER_ID")),
        @AttributeOverride(name = "name", column = @Column(name = "MEMBER_NAME")),
})
public class MemberMappedSuperclass extends BaseEntityMappedSuperclass {

    private String email;
}
