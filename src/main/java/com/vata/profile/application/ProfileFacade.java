package com.vata.profile.application;

import com.vata.auth.domain.service.AccessKeyService;
import com.vata.profile.controller.dto.ProfileListResponse;
import com.vata.profile.domain.service.ProfileService;
import com.vata.profile.infrastructure.StabilityRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileFacade {
    private final ProfileService profileService;
    private final AccessKeyService accessKeyService;
    private final StabilityRestClient stabilityRestClient;

    public Page<ProfileListResponse> getProfileList(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        return profileService.findAllByUserId(userId, pageable)
                .map(profile -> new ProfileListResponse(
                        profile.getId(),
                        profile.getProfileUrl(),
                        profile.getCreatedAt()
                ));
    }

    public double getUserStabilityCredits(Long userId) {
        // 1. userId를 통해 Access Key를 조회
        String apiKey = accessKeyService.getValue(userId);

        // 2. StabilityRestClient를 통해 Stability AI API 호출하여 크레딧 잔액 조회
        return stabilityRestClient.getBalanceCredits(apiKey);
    }
}
