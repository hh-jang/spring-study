package com.hhjang.spring.study.jpa.ch2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import java.util.List;

@DataJpaTest
public class MemberTest {

    @Autowired
    private EntityManagerFactory emf;

    @Test
    public void create() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        try {
            tx.begin();

            businessLogic(entityManager);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            entityManager.close();
        }
    }

    private void businessLogic(EntityManager em) {
        // given
        String id = "hh-jang";
        Member member = new Member();
        member.setId(id);
        member.setAge(17);
        member.setUsername("hyeonha");

        // save - persistence context
        em.persist(member);
        // update
        member.setAge(27);
        member.setAge(37);

        Member foundMember = em.find(Member.class, id);
        System.out.println("found member : " + foundMember.getId() + " " + foundMember.getAge() + " " + foundMember.getUsername());

        List<Member> members = em.createQuery("select m from CH2_MEMBER m", Member.class).getResultList();
        System.out.println("member size : " + members.size());

        em.remove(member);
    }
}