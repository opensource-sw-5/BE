package com.vata.profile.domain.service;

import com.vata.profile.domain.entity.Profile;
import com.vata.profile.domain.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public Page<Profile> findAllByUserId(Long userId, Pageable pageable) {
        return profileRepository.findAllByUserId(userId, pageable);
    }
}
