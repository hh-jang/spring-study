package com.hhjang.spring.study.jpa.ch4;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "CH3_MEMBER")
@Table(name = "CH3_MEMBER", uniqueConstraints = {@UniqueConstraint(
        name = "NAME_AGE_UNIQUE",
        columnNames = {"NAME", "AGE"})})
// sequence 사용 시 시퀀스 생성
//@SequenceGenerator(
//        name = "MEMBER_SEQ_GENERATOR",
//        sequenceName = "MEMBER_SEQ",
//        initialValue = 1, allocationSize = 1)
@Setter
@Getter
public class Member {

    // mysql과 같이 AutoIncrement와 같은 방식
//    @GeneratedValue(strategy = GenerationType.IDENTITY)

    // oracle과 같이 sequence를 사용하는 방식
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")

    // database에 따라 자동으로 선택
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME", nullable = false, length = 10)
    private String username;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @CreatedDate
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    private LocalDateTime updatedDateTime;

    //CLOB String, char[], javca.sql.CLOB
    @Lob
    private String description;

    //BLOB byte[] java.sql.BLOB
    @Lob
    private byte[] blob;

    //database와 매핑하지 않음
    @Transient
    private String unmappingValue;
}