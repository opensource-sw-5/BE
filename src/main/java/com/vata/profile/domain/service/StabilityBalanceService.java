package com.vata.profile.domain.service;

import com.vata.auth.domain.service.AccessKeyService;
import com.vata.profile.infrastructure.StabilityRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 데이터 변경이 없으므로 읽기 전용 트랜잭션
public class StabilityBalanceService {

    private final AccessKeyService accessKeyService;
    private final StabilityRestClient stabilityRestClient;

    public double getUserStabilityCredits(Long userId) {
        // 1. userId를 통해 Access Key를 조회
        String apiKey = accessKeyService.getValue(userId);

        // 2. StabilityRestClient를 통해 Stability AI API 호출하여 크레딧 잔액 조회
        return stabilityRestClient.getBalanceCredits(apiKey);
    }
}