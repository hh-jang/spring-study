package com.hhjang.spring.study.jpa.ch5;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "CH5_MEMBER")
@Table(name = "CH5_MEMBER")
@Getter @Setter
@NoArgsConstructor
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    private String id;

    private String username;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public Member(String id, String username) {
        this.id = id;
        this.username = username;
    }
}
