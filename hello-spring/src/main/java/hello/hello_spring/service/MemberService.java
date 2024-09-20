package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {  // cmd + shift + T 하면 테스트 페이지 생성

  private final MemberRepository memberRepository = new MemoryMemberRepository();

  /**
   * 회원 가입
   */
  public Long join(Member member) {
    //같은 이름이 있는 중복 회원x
    /**
    Optional<Member> result = memberRepository.findByName(member.getName());
    result.ifPresent(m -> { // 값이 있으면
      throw new IllegalStateException("이미 존재하는 회원입니다.");
      // null이 있을 가능성이 있으면optional 로 감싸면 if null 같은 코드를 적을 필요가 없음
    });
    */
    validateDuplicateMember(member); //중복 회원 검증

    memberRepository.save(member);
    return member.getId();
  }


  private void validateDuplicateMember(Member member) {
    memberRepository.findByName(member.getName()) // finByName 반환값이 optional이라서 이렇게 해도 됨
            .ifPresent(m -> {
              throw new IllegalStateException("이미 존재하는 회원입니다.");
            });
  }

  /**
   * 전체 회원 조회
   */
  public List<Member> findMembers() {
    return memberRepository.findAll();
  }

  public Optional<Member> findOne(Long memberId) {
    return memberRepository.findById(memberId);
  }
}
// service는 비지니스적인 용어를 써야 나중에 확인이 빠르다. repository는 데이터를 넣고 빼는 작업을 담당하니까 단순기계적인 용어를 선택
