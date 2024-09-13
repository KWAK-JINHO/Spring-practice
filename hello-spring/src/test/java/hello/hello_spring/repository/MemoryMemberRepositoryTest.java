package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class MemoryMemberRepositoryTest {

  MemoryMemberRepository repository = new MemoryMemberRepository();

  //모든 메서드는 순서가 의존적으로 설계하면 절대 안됨 떄문에 각테스트는 끝나면 데이터를 clear 해줘야함
  // afterEach는 메서드들이 끝날때마다 동작할 코드를 작성할 수 있음
  @AfterEach
  public void afterEach() {
    repository.clearStore();
  }

  @Test
  public void save() { // save를 테스트 할거니까
    Member member = new Member(); // Member클래스 기반 객체를 만들 수 있다.
    member.setName("spring"); // 이름 세팅

    repository.save(member);

    Member result = repository.findById(member.getId()).get();
    // Member는 객체로 id와 name 을 가지는 클래스로 구현해놓음
    // get으로 꺼내면 값이 없으면 NoSuchElementException을 던지기 때문에 실무에서는 사용 시 주의가 필요
    // 테스트 코드에서는 예외 발생 가능성보다는 빠르게 결과를 확인하기 위해 .get()을 사용하는 경우가 많다

    // 꺼낸 뒤에는 검증단계 -> new에서 받은 member랑 DB에서 getId로 꺼낸거랑 같으면 True
    // System.out.println("result = " + (result == member)); 이렇게 하면 result = ture 출력됨
    // Assertions.assertEquals(member, result); 이건 출력되는건 없지만 에러가 안뜨고 초록불뜨면서 확인 가능 null 넣어주면 에러 발생
    assertThat(member).isEqualTo(result); // 실무에서는 빌드 툴이랑 엮어서 둘이 같지 않으면 못넘어가게 막는다
  }

  @Test
  public void findByName() {
    Member member1 = new Member();
    member1.setName("spring1");
    repository.save(member1);

    Member member2 = new Member(); // 같은이름 복붙해서 이름 바꿔줄때 shift + F6
    member2.setName("spring2");
    repository.save(member2);

    Member result = repository.findByName("spring1").get();
    // cmd + opt + v : 메서드 호출의 결과를 새로운 변수에 할당하는데 사용
    //repository.findByName("spring1")의 반환 값이 새로운 변수 result에 할당

    assertThat(result).isEqualTo(member1); //spring1 을 가져오면 result = member1
  }

  @Test
  public void findAll() {
    Member member1 = new Member();
    member1.setName("spring1");
    repository.save(member1);

    Member member2 = new Member(); // 같은이름 복붙해서 이름 바꿔줄때 shift + F6
    member2.setName("spring2");
    repository.save(member2);

    List<Member> result = repository.findAll();

    assertThat(result.size()).isEqualTo(2);
  }
}

// 테스트 주도 개발 TDD : 테스트를 먼저 만들고 구현 클래스를 만들어서 돌려보는 것