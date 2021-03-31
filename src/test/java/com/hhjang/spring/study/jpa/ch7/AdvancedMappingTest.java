package com.hhjang.spring.study.jpa.ch7;

import com.hhjang.spring.study.jpa.ch7.inheritancemapping.joined.BookInheritanceJoined;
import com.hhjang.spring.study.jpa.ch7.inheritancemapping.joined.ItemInheritanceJoined;
import com.hhjang.spring.study.jpa.ch7.inheritancemapping.joined.MovieInheritanceJoined;
import com.hhjang.spring.study.jpa.ch7.inheritancemapping.mappedsuperclass.MemberMappedSuperclass;
import com.hhjang.spring.study.jpa.ch7.inheritancemapping.mappedsuperclass.SellerMappedSuperclass;
import com.hhjang.spring.study.jpa.ch7.inheritancemapping.singletable.BookInheritanceSingleTable;
import com.hhjang.spring.study.jpa.ch7.inheritancemapping.singletable.ItemInheritanceSingleTable;
import com.hhjang.spring.study.jpa.ch7.inheritancemapping.singletable.MovieInheritanceSingleTable;
import com.hhjang.spring.study.jpa.ch7.inheritancemapping.tableperclass.BookInheritanceTablePerClass;
import com.hhjang.spring.study.jpa.ch7.inheritancemapping.tableperclass.ItemInheritanceTablePerClass;
import com.hhjang.spring.study.jpa.ch7.inheritancemapping.tableperclass.MovieInheritanceTablePerClass;
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
public class AdvancedMappingTest {

    @Autowired
    private EntityManagerFactory emf;

    private EntityManager em;

    private EntityTransaction tx;

    @BeforeEach
    public void init() {
        em = emf.createEntityManager();
    }

    @Test
    @DisplayName("상속 관계 매핑에서 조인전략을 사용한다.")
    public void inheritanceMapping_JoinStrategy() {
        tx = em.getTransaction();

        tx.begin();

        MovieInheritanceJoined movieInheritanceJoined = new MovieInheritanceJoined();
        movieInheritanceJoined.setName("movie name");
        movieInheritanceJoined.setActor("actor");
        movieInheritanceJoined.setDirector("director");
        em.persist(movieInheritanceJoined);

        // book의 id 컬럼명이 BOOK_ID이다.
        BookInheritanceJoined bookInheritanceJoined = new BookInheritanceJoined();
        bookInheritanceJoined.setName("book name");
        bookInheritanceJoined.setAuthor("author");
        bookInheritanceJoined.setIsbn("isbn");
        em.persist(bookInheritanceJoined);

        tx.commit();
        em.clear();

        tx = em.getTransaction();
        tx.begin();

        // movie와 item을 조인해서 가져오는 쿼리 확인 -> 성능 고민 해봐야함
        String query = "SELECT i FROM ItemInheritanceJoined i";
        List<ItemInheritanceJoined> resultList = em.createQuery(query, ItemInheritanceJoined.class).getResultList();

        resultList.stream().forEach(item -> System.out.println("class : " + item.getClass() + ", name : " + item.getName()));

        tx.commit();
    }

    @Test
    @DisplayName("상속 관계 매핑에서 단일테이블전략을 사용한다.")
    public void inheritanceMapping_SingleTableStrategy() {
        tx = em.getTransaction();

        tx.begin();

        MovieInheritanceSingleTable movieInheritanceSingleTable = new MovieInheritanceSingleTable();
        movieInheritanceSingleTable.setName("movie name");
        movieInheritanceSingleTable.setActor("actor");
        movieInheritanceSingleTable.setDirector("director");
        em.persist(movieInheritanceSingleTable);

        // book의 id 컬럼명이 BOOK_ID이다.
        BookInheritanceSingleTable bookInheritanceSingleTable = new BookInheritanceSingleTable();
        bookInheritanceSingleTable.setName("book name");
        bookInheritanceSingleTable.setAuthor("author");
        bookInheritanceSingleTable.setIsbn("isbn");
        em.persist(bookInheritanceSingleTable);

        tx.commit();
        em.clear();

        tx = em.getTransaction();
        tx.begin();

        // item 단일테이블에서 조회한다.(조인 안하는거 보임) -> 대신 안쓰는거 nulld임
        String query = "SELECT i FROM ItemInheritanceSingleTable i";
        List<ItemInheritanceSingleTable> resultList = em.createQuery(query, ItemInheritanceSingleTable.class).getResultList();

        resultList.stream().forEach(item -> System.out.println("class : " + item.getClass() + ", name : " + item.getName()));

        tx.commit();
    }

    @Test
    @DisplayName("상속 관계 매핑에서 단일테이블전략을 사용한다.")
    public void inheritanceMapping_TablePerClassStrategy() {
        tx = em.getTransaction();

        tx.begin();

        MovieInheritanceTablePerClass movieInheritanceTablePerClass = new MovieInheritanceTablePerClass();
        movieInheritanceTablePerClass.setName("movie name");
        movieInheritanceTablePerClass.setActor("actor");
        movieInheritanceTablePerClass.setDirector("director");
        em.persist(movieInheritanceTablePerClass);

        // book의 id 컬럼명이 BOOK_ID이다.
        BookInheritanceTablePerClass bookInheritanceTablePerClass = new BookInheritanceTablePerClass();
        bookInheritanceTablePerClass.setName("book name");
        bookInheritanceTablePerClass.setAuthor("author");
        bookInheritanceTablePerClass.setIsbn("isbn");
        em.persist(bookInheritanceTablePerClass);

        tx.commit();
        em.clear();

        tx = em.getTransaction();
        tx.begin();

        // 각 테이블 union all 해서 가져오는거 볼 수 있음. -> 성능 불리할듯, 책에서 필자분 언급으로 비추하는 전략이라함.
        String query = "SELECT i FROM ItemInheritanceTablePerClass i";
        List<ItemInheritanceTablePerClass> resultList = em.createQuery(query, ItemInheritanceTablePerClass.class).getResultList();

        resultList.stream().forEach(item -> System.out.println("class : " + item.getClass() + ", name : " + item.getName()));

        tx.commit();
    }

    @Test
    @DisplayName("상속 관계 매핑에서 부모클래스를 직접 테이블과 매핑하지 않고 자식에게 매핑 정보만 제공한다.")
    public void inheritanceMapping_MappedSuperClass() {
        tx = em.getTransaction();

        tx.begin();

        MemberMappedSuperclass member = new MemberMappedSuperclass();
        member.setName("member name");
        member.setEmail("email");
        em.persist(member);

        SellerMappedSuperclass seller = new SellerMappedSuperclass();
        seller.setName("seller name");
        seller.setShopName("shop name");

        tx.commit();
        em.clear();

        tx = em.getTransaction();
        tx.begin();

        // BaseEntity의 경우 실제 테이블과 매핑하지 않고 자식에게 정보만 상속함, 상속받은 정보를 기반으로 자식은 실제 테이블과의 매핑에서 해당 정보를 사용함
        // createdAt, lastModifiedAt 사용 고려가 좋을꺼 같음
        // member는 재정의해서 MEMBER_ID, MEMBER_NAME인거 볼 수 있음
        String memberQuery = "SELECT m FROM MemberMappedSuperclass ";
        List<MemberMappedSuperclass> memberResultList = em.createQuery(memberQuery, MemberMappedSuperclass.class).getResultList();

        memberResultList.stream().forEach(savedMember -> System.out.println("class : " + savedMember.getClass() + ", name : " + savedMember.getName() + ", email : " + savedMember.getEmail()));

        String sellerQuery = "SELECT m FROM SellerMappedSuperclass m";
        List<SellerMappedSuperclass> sellerResultList = em.createQuery(sellerQuery, SellerMappedSuperclass.class).getResultList();

        sellerResultList.stream().forEach(savedSeller -> System.out.println("class : " + savedSeller.getClass() + ", name : " + savedSeller.getName() + ", shopName : " + savedSeller.getShopName()));

        tx.commit();


    }
}
