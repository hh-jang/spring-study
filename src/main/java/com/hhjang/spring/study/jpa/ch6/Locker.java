package com.hhjang.spring.study.jpa.ch6;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity @NoArgsConstructor
@Getter @Setter
public class Locker {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @OneToOne(mappedBy = "locker")
    private MemberLocker memberLocker;

    public Locker(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
