package com.vata.auth.domain.service;

import com.vata.auth.domain.entity.AccessKey;
import com.vata.auth.domain.repository.AccessKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccessKeyService {
    private final AccessKeyRepository accessKeyRepository;

    @Transactional
    public void save(Long userId, String value) {
        AccessKey key = AccessKey.of(userId, value);
        accessKeyRepository.save(key);
    }

    @Transactional(readOnly = true)
    public String getValue(Long userId) {
        return accessKeyRepository.findFirstByUserIdOrderByCreatedAtDesc(userId)
                .map(AccessKey::getValue)
                .orElseThrow(() -> new IllegalArgumentException("API 키가 존재하지 않습니다. userId = " + userId));
    }
}
