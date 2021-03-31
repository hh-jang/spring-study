package com.hhjang.spring.study.jpa.ch7.inheritancemapping.mappedsuperclass;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

// 직접 테이블과 매핑하지 않는다. 엔티티가 아니기 떄문에 find 불가
// 사실상 사용할일 거의 없으니 추상 클래스로 만드는걸 권장
@MappedSuperclass
@Getter @Setter
public abstract class BaseEntityMappedSuperclass {

    @Id @GeneratedValue
    private Long id;

    private String name;
}
