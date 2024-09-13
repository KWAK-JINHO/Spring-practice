package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MemoryMemberRepository implements  MemberRepository{
  // implements 단축키 opt + enter 하면 메서드 가져오기 가능

  private static Map<Long, Member> store = new HashMap<>();
  // HashMap은 스레드에 안전하지 않기 떄문에 여러 스레드가 동시에 접근하는 경우 문제가 발생할 수 있다.
  // 실무에서는 안전한 컬렉션 클래스인 ConcurrentHashMap을 사용한다. 여러 스레드가 안전하게 동시에 읽고 쓸 수 있도록 설계 되어 있다.
  private  static long sequence = 0L;
  // sequence 는 key 값을 0,1,2... 이런식으로 생성해주는애;
  // 실무에서는 동시성 문제 때문에 Long 보다 AtomicLong 을 쓴다

  @Override
  public Member save(Member member) {
    member.setId(++sequence); //여기에서 Id를 세팅하고
    store.put(member.getId(), member); // Id를 store에 정리한다. 그럼 map에 저장이 된다.
    return member; // 스펙에 따라서 저장이된 결과를 반환
  }

  @Override
  public Optional<Member> findById(Long id) {
    // return store.get(id); 이렇게 하면 결과가 없으면 null이 될텐데 요즘에는 null 이 반환될 가능성이 있으면 optional로 감싼다
    return Optional.ofNullable(store.get(id)); // 이렇게 하면 클라이언트에서 뭘 할수가 있다.
  }

  @Override
  public Optional<Member> findByName(String name) {
    return store.values().stream()
        .filter(member -> member.getName().equals(name))  // getName과 파라미터로 넘어온name과 같은지 확인 같은경우 필터링
        .findAny(); // 결과가 optional로 반환(루프 돌다가 찾으면 바로 반환, 끝까지 돌았는데도 없으면 optional에 null이 포함되서 반환)
  }

  @Override
  public List<Member> findAll() {
    return new ArrayList<>(store.values()); // 실무에서는 리스트 많이 쓴다. store에 있는 values가 위에 12번째 줄 member
  }

  public void clearStore() {
    store.clear();
  }
}
// 잘 돌아가는지 알아보는 젤 좋은방법 -> Test case 작성하는법