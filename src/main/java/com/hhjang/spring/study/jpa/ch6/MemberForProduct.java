package com.hhjang.spring.study.jpa.ch6;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class MemberForProduct {

    @Id @GeneratedValue @Column(name = "MEMBER_ID")
    private Long id;

    private String username;

    // 실제로는 거의 500퍼 확률로 연결 테이블 ID만 있을리가 진짜 거어어어어어어의 없었으니 엔티티를 만들고 OneToMany, ManyToOne로 풀어나간다.
    @ManyToMany
    @JoinTable(name = "MEMBER_PRODUCT",
            joinColumns = @JoinColumn(name = "MEMBER_ID"),
            inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID"))
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        this.products.add(product);
        product.getMembers().add(this);
    }
}
