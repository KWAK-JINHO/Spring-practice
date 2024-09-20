package hello.hello_spring.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import hello.hello_spring.domain.Member;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MemberServiceTest {

  MemberService memberService = new MemberService();

  @Test
  void 회원가입() { // 테스트코드는 한글로 적어도 무방함
    //given 무언가 주어졌을때
    Member member = new Member();
    member.setName("hello"); // 생성된 객체의 이름을 hello로 설정함

    //when 이걸 실행 했을때
    Long saveId = memberService.join(member); //memberService 의 join메서드를 가져와서 member를 저장하고 반환된 값을 saveId에 저장

    //then 결과가 이게 나와야 한다 -> 이렇게 세개를 나누어서 하면 나중에 편함 상황에 따라 안맞을때가 있는데 점점 변형시키면서 하면 좋음
    Member findMember = memberService.findOne(saveId).get(); // 제대로 저장되었는지 확인
    assertThat(member.getName()).isEqualTo(findMember.getName()); // 위에 할당한 hello와 저장된이름을 findMember로 불러와 비교
  }
  //테스트는 잘되는걸 보는것도 중요하지만 에러가 나는 상황을 체크하는것이 훨씬 중요하다.

  @Test
  public void 중복_회원_예외() {

  }

  @Test
  void findMembers() {

  }

  @Test
  void findOne() {

  }
}