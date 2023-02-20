package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

// 스프링 부트의 기본적 동작방식은
// @SpringBootApplication 어노테이션이 있으면 패키지 하위에 있는 것들은 다 컴포넌트 스캔을 한다.
// 그래서 스프링 빈으로 자동 등록하게 된다.

@Repository // 컴포넌트 스캔의 대상이 되서
@RequiredArgsConstructor
public class MemberRepository {

    //@PersistenceContext // 스프링이 이 엔티티매니저를 만들어서 주입해준다(injection)
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList(); // JPQL 사용
    } // JPQL 은 객체를 대상을 쿼리를 한다. (SQL 은 테이블)

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
