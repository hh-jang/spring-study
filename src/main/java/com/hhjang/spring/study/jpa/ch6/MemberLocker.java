package com.hhjang.spring.study.jpa.ch6;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class MemberLocker {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String username;

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    @PrimaryKeyJoinColumn
    private Locker locker;

    public MemberLocker(String username, Locker locker) {
        this.username = username;
        this.locker = locker;
    }
}
