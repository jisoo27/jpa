package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // JPA에서 중요한 변경이나 어떠한 로직들은 트랜잭션 안에서 실행되야한다.
// @Transactional 은 2개인데 javax 와 spring . spring이 제공하는 @Transactional을 권장.
// 기본적으로 여기에 달아주면 public 메서드에 다 먹힌다, 그래서 변경이 필요한 부분에는 따로 달아주는 것! 따로 달아주면 그것이 우선권을 가짐.
@RequiredArgsConstructor // final이 붙은 필드만 생성자로 만든다.
public class MemberService {

    private final MemberRepository memberRepository; // final(불변) 로 설정 해 줄 경우 컴파일 시점에 체크를 해주기 때문에 좋다.
    // 강의(회원 서비스개발에서 주입방법의 장단점에 대해서 설명해주시는 것 여러번 반복해서 보기)

    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);// 중복 회원 검증
        memberRepository.save(member);
        return member.getId(); // 이렇게 꺼내면 값이 있다는게 인증이됨
    }

    private void validateDuplicateMember(Member member) { // 하지만 이런 로직은 문제를 일으킬 수 있다. 왜냐하면 동시에 누군가가 가입하면 둘 다 저장될 수 있기 때문이다. 따라서 unique 제약조건을 걸어주는 것이 좋다.
        List<Member> findMembers
                = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회 -> 조회를 하는 곳에서 @Transactional 을 readOnly = True 해주면 성능이 최적화 된다. readOnly = True를 하면 변경 불가능.
    // 하지만 읽기를 해야할 부분이 많을 경우 클래스 레벨에 달아주면 좋다.
    public List<Member> findAll() {
        return memberRepository.findAll();
    }
    // 단건 조회
    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }


    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name); // 변경감지에 의해 수정됨
    }
}
