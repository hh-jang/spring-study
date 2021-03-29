package com.hhjang.spring.study.jpa.ch6;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

@DataJpaTest
public class RelationMappingTest {

    @Autowired
    private EntityManagerFactory emf;

    private EntityManager em;

    private EntityTransaction tx;

    @BeforeEach
    public void init() {
        em = emf.createEntityManager();
    }

    @Test
    @DisplayName("일대다 관계 매핑에서 저장 시 update가 추가로 필요하다.")
    public void insertAndUpdateInOneToMany() {
        tx = em.getTransaction();
        tx.begin();

        Member member1 = new Member("memberId1", "member1");
        Member member2 = new Member("memberId2", "member2");

        Team team = new Team("teamId1", "team1");

        team.getMembers().add(member1);
        team.getMembers().add(member2);

        em.persist(member1);
        em.persist(member2);
        em.persist(team);

        // 3번의 insert문 후 각 member에서 관리하는 teamid(fk)를 전부 update한다.
        tx.commit();
    }

    @Test
    @DisplayName("다대다 관계 매핑에서 저장 후 조회에 성공한다.")
    public void insertInManyToMany() {
        tx = em.getTransaction();
        tx.begin();

        Product product = new Product();
        product.setName("test product");

        MemberForProduct member = new MemberForProduct();
        member.setUsername("test user name");

        product.addMember(member);
        member.addProduct(product);

        em.persist(product);
        em.persist(member);

        tx.commit();
        em.clear();

        // member
        MemberForProduct savedMember = em.find(MemberForProduct.class, member.getId());
        List<Product> products = savedMember.getProducts();

        products.forEach(savedProduct -> System.out.println("savedProduct name : " + savedProduct.getName()));

        em.clear();

        // product
        Product savedProduct = em.find(Product.class, product.getId());
        List<MemberForProduct> members = savedProduct.getMembers();

        members.forEach(savedMemberVar -> System.out.println("savedMembers name : " + savedMemberVar.getUsername()));
    }
}
