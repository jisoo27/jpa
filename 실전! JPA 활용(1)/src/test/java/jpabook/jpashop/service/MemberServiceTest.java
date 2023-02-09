package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

// 일반 단위테스트가 아닌 jpa 와 함께 돌아가는 테스트를 만들기 위해서 아래의 어노테이션을 사용한다.
@SpringBootTest
@Transactional // 테스트에서 @Transactional 이 있다면 기본적으로 Rollback 해버린다.
class MemberServiceTest {

    @Autowired private MemberService memberService;
    @Autowired private MemberRepository memberRepository;
    @Autowired private EntityManager em;

    @Test
    void 회원_회원가입_테스트() throws Exception{
        // JPA에서 같은 트랜잭션 안에서  같은 entity , id 값이 똑같으면, pk 값이 똑같으면  같은 영속성 컨텍스트에서 똑같은 애가 관리된다.
        Member member = new Member();
        member.setName("kim");

        Long saveId = memberService.join(member);

        em.flush(); // insert 문 까지나가는 것을 볼 수 있음
        assertEquals(member, memberRepository.findOne(saveId)); // 테스트가 끝날때 Rollback
    }

    @Test()
    void 중복_회원_일_경우_예외_테스트() {
        // given
        Member member = new Member();
        member.setName("kim");
        Member member1 = new Member();
        member1.setName("kim");

        memberService.join(member);

        assertThatThrownBy(()-> {
            memberService.join(member1);
        }).isInstanceOf(IllegalArgumentException.class);

    }

}