package com.vata.join.repository;

import com.vata.join.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// 관리할 엔티티 타입 : User
// 엔티티의 primaryKey 타입 : Long
public interface UserRepository extends JpaRepository<User, Long> {

    // 주어진 매개변수로 사용자를 조회하는 메서드
    // 결과가 없을 경우 Optional.empty()를 반환
    Optional<User> findByName(String name);
    Optional<User> findByEmail(String email);
}
