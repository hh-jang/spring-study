package com.hhjang.spring.study.jpa.ch4;

import com.hhjang.spring.study.jpa.ch2.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class SelectTest {

    @Autowired
    private EntityManagerFactory emf;

    private EntityManager em;

    private EntityTransaction tx;

    @BeforeEach
    public void init() {
        em = emf.createEntityManager();
        tx = em.getTransaction();
        tx.begin();
    }

    @AfterEach
    public void destroy() {
        tx.commit();
    }

    // 엔티티가 영속성 컨텍스트에 의해 관리되는 상태라면 1차 캐시에 의해서 조회된다.
    @Test
    @DisplayName("Entity 조회 시 영속성 컨텍스트의 1차 캐시에서 조회 - Db를 통한 조회가 일어나지 않음.(select query 없음)")
    public void findByPersistenceContextCache() {
        String id = "member1";
        Member member = createMember(id);
        em.persist(member);

        Member member1 = em.find(Member.class, id);
    }

    // 엔티티가 영속성 컨텍스트에 의해 관리되지 않는다면 데이터베이스에 질의한다.
    @Test
    @DisplayName("Entity 조회 시 영속성 컨텍스트의 1차 캐시에서 존재하지 않으면 데이터베이스에 질의하고 1차캐시에 저장하고 반환한다. - Db를 통한 조회가 일어남.(select query 있)")
    public void findByDatabase() {
        String id = "member2";
        Member member = createMember(id);
        em.persist(member);
        em.flush();
        em.clear();

        Member member1 = em.find(Member.class, id);
    }

    // id가 같은 엔티티를 조회하면 동일성을 보
    @Test
    @DisplayName("Entity 조회 시 id가 같다면 동일성을 만족한다.(실제 인스턴스가 같음)")
    public void equalIdentity() {
        String id = "member3";
        Member member = createMember(id);
        em.persist(member);
        em.flush();

        Member member1 = em.find(Member.class, id);
        Member member2 = em.find(Member.class, id);

        assertThat(member1 == member2).isTrue();
        assertThat(member1.hashCode()).isEqualTo(member2.hashCode());
    }

    private Member createMember(String id) {
        Member member = new Member();
        member.setId(id);
        member.setUsername("member name");

        return member;
    }
}
