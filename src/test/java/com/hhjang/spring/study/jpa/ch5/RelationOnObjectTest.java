package com.hhjang.spring.study.jpa.ch5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RelationOnObjectTest {

    @Autowired
    private EntityManagerFactory emf;

    private EntityManager em;

    private EntityTransaction tx;

    @BeforeEach
    public void init() {
        em = emf.createEntityManager();
    }

    @Test
    @DisplayName("객체는 참조를 통해 연관관계를 탐색할 수 있다.")
    public void discoverObjectGraph() {
        Member member1 = new Member("memberId1", "member1");
        Member member2 = new Member("memberId2", "member2");

        Team team = new Team("teamId1", "team1");

        member1.setTeam(team);
        member2.setTeam(team);

        assertThat(member1.getTeam().hashCode()).isEqualTo(member2.getTeam().hashCode());
        assertThat(member1.getTeam().getId()).isEqualTo("teamId1");
    }

    @Test
    @DisplayName("연관관계를 가진 회원과 팀을 저장한다.")
    public void saveTeamAndMember() {
        tx = em.getTransaction();
        tx.begin();

        Team team = new Team("teamId1", "team1");
        em.persist(team);

        Member member1 = new Member("memberId1", "member1");
        member1.setTeam(team);
        em.persist(member1);

        Member member2 = new Member("memberId2", "member2");
        member2.setTeam(team);
        em.persist(member2);

        tx.commit();
    }
}