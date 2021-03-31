package com.hhjang.spring.study.jpa.ch7.inheritancemapping.mappedsuperclass;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter @Setter
public class SellerMappedSuperclass extends BaseEntityMappedSuperclass {

    private String shopName;
}
