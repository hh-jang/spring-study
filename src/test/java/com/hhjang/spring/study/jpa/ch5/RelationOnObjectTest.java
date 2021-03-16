package com.hhjang.spring.study.jpa.ch5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import java.util.Arrays;
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

        em.flush();
        tx.commit();
    }

    @Test
    @DisplayName("JPQL을 이용하여 join 검색을 한다.")
    public void searchJoinOperationUsingJpql() {
        String teamName = "team2";

        Team team = new Team("teamId2", teamName);
        em.persist(team);

        Member member = new Member("memberId3", "member3");
        member.setTeam(team);
        em.persist(member);

        String jpql = "select m from CH5_MEMBER m join m.team t where " +
                "t.name = :teamName";

        List<Member> resultList = em.createQuery(jpql, Member.class)
                .setParameter("teamName", teamName)
                .getResultList();

        resultList.stream().forEach(searchedMember -> assertThat(searchedMember.getTeam().getName()).isEqualTo(teamName));
    }

    @Test
    @DisplayName("연관관계를 가진 Entity를 수정한다.")
    public void updateRelation() {
        tx = em.getTransaction();
        tx.begin();

        Team team = new Team("teamId3", "team3");
        em.persist(team);

        Member member = new Member("memberId4", "member4");
        member.setTeam(team);
        em.persist(member);

        // new team
        Team newTeam = new Team("teamId4", "team4");
        em.persist(newTeam);

        Member existMember = em.find(Member.class, "memberId4");
        existMember.setTeam(newTeam);

        em.persist(existMember);
        em.flush();

        tx.commit();
    }

    @Test
    @DisplayName("연관관계를 가진 Entity를 삭제한다.")
    public void deleteRelation() {
        tx = em.getTransaction();
        tx.begin();

        Team team = new Team("teamId5", "team5");
        em.persist(team);

        Member member = new Member("memberId5", "member5");
        member.setTeam(team);
        em.persist(member);

        Member existMember = em.find(Member.class, "memberId5");
        existMember.setTeam(null);

        em.persist(existMember);

        em.flush();
        tx.commit();
    }

    @Test
    @DisplayName("일대다 방향을 객체 그래프를 탐색한다.")
    public void biDirection() {
        tx = em.getTransaction();
        tx.begin();

        Team team = new Team("teamId6", "team6");
        em.persist(team);

        Member member = new Member("memberId6", "member6");
        member.setTeam(team);
        em.persist(member);

        Member member2 = new Member("memberId7", "member7");
        member2.setTeam(team);
        em.persist(member2);

//        team.setMembers(Arrays.asList(new Member[]{member, member2}));

        em.flush();
        tx.commit();

        // 양방향 연관관계일때 주인이 아닐때(team) 1차 캐시에는 team에서 member를 탐색할 수 없음. db에 flush 후 em이랑 db 동기화 후 가져올 수 있었음.
        em = emf.createEntityManager();

        Team team1 = em.find(Team.class, "teamId6");
        assertThat(team1.getMembers().size()).isEqualTo(2);
        team1.getMembers().stream().forEach(containMember -> System.out.println(containMember.getTeam().getName()));
    }

    @Test
    @DisplayName("연관관계의 주인에 값을 설정하지 않으면 저장되지 않는다.")
    public void testSaveNonOwner() {
        tx = em.getTransaction();
        tx.begin();

        Team team = new Team("teamId7", "team7");
        em.persist(team);

        Member member = new Member("memberId8", "member8");
        em.persist(member);

        Member member2 = new Member("memberId9", "member9");
        em.persist(member2);

        team.setMembers(Arrays.asList(new Member[]{member, member2}));

        em.flush();
        tx.commit();

        em = emf.createEntityManager();

        Team team1 = em.find(Team.class, "teamId7");
        assertThat(team1.getMembers().size()).isEqualTo(0);
    }
}