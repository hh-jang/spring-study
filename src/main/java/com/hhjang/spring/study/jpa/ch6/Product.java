package com.hhjang.spring.study.jpa.ch6;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Product {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "products")
    private List<MemberForProduct> members = new ArrayList<>();

    public void addMember(MemberForProduct member) {
        members.add(member);
        member.getProducts().add(this);
    }
}
