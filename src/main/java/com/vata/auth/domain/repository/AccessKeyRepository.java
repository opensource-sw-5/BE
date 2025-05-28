package com.vata.auth.domain.repository;

import com.vata.auth.domain.entity.AccessKey;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessKeyRepository extends JpaRepository<AccessKey, Long> {
    Optional<AccessKey> findFirstByUserIdOrderByCreatedAtDesc(Long userId); // 최근에 등록한 api key 반환
}
