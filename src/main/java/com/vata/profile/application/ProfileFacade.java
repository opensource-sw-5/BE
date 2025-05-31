package com.vata.profile.application;

import com.vata.profile.controller.dto.ProfileListResponse;
import com.vata.profile.domain.service.ProfileService;
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

    public Page<ProfileListResponse> getProfileList(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        return profileService.findAllByUserId(userId, pageable)
                .map(profile -> new ProfileListResponse(
                        profile.getId(),
                        profile.getProfileUrl(),
                        profile.getCreatedAt()
                ));
    }
}
