package com.hhjang.spring.study.jpa.ch4;

import com.hhjang.spring.study.jpa.ch2.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class MergeTest {

    private final String MEMBER_ID = "member4";

    private final String UPDATED_USER_NAME = "updated user name";

    @Autowired
    private EntityManagerFactory emf;

    private EntityManager em;

    private EntityTransaction tx;

    private EntityManager em2;

    private EntityTransaction tx2;

    @Test
    @DisplayName("Entity를 준영속 상태로 변경 후 다시 Merge를 통해 영속 상태로 되는 테스트")
    public void mergeTest() {
        Member member = createMember(MEMBER_ID);
        member.setUsername(UPDATED_USER_NAME);
        mergeMember(member);
    }

    private void mergeMember(Member member) {
        em2 = emf.createEntityManager();
        tx2 = em2.getTransaction();
        tx2.begin();

        // merge는 1차캐시에 존재하지 않을 시 database에서 조회하고 merge 대상과 병합하여 영속상태로 만든다.
        // save or update
        Member mergedMember = em2.merge(member);

        tx2.commit();

        assertThat(member.getUsername()).isEqualTo(UPDATED_USER_NAME);
        assertThat(mergedMember.getUsername()).isEqualTo(UPDATED_USER_NAME);

        assertThat(em2.contains(member)).isFalse();
        assertThat(em2.contains(mergedMember)).isTrue();

        assertThat(member.hashCode()).isNotEqualTo(mergedMember.hashCode());
    }

    private Member createMember(String id) {
        em = emf.createEntityManager();
        tx = em.getTransaction();
        tx.begin();

        Member member = new Member();
        member.setId(id);
        member.setUsername("member name");

        em.persist(member);
        tx.commit();

        em.close();

        return member;
    }
}
