package com.vata.auth.domain.repository;

import com.vata.auth.domain.entity.AccessKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessKeyRepository extends JpaRepository<AccessKey, Long> {
}
