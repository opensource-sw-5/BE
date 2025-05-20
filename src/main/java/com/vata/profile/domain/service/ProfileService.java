package com.vata.profile.domain.service;

import com.vata.profile.domain.entity.Profile;
import com.vata.profile.domain.repository.ProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;

    @Transactional
    public Profile save(Long userId, String imageUrl) {
        Profile profile = Profile.builder()
                .userId(userId)
                .profileUrl(imageUrl)
                .build();
        return profileRepository.save(profile);
    }
}
