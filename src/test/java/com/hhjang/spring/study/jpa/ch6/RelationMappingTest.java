package com.hhjang.spring.study.jpa.ch6;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

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
}
