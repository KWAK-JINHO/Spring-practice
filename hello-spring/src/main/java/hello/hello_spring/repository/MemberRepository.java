package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

  Member save(Member member);
  Optional<Member> findById(Long id);
  Optional<Member> findByName(String name);
  List<Member> findAll();

}
//optional은 findByID,findByName 가져올때 값이 없으면 null이 반환될 텐데 optional로 감싸서 반환하는걸 요즘에는 선호함