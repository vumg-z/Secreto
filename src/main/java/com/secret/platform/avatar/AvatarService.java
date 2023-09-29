package com.secret.platform.avatar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvatarService {

    @Autowired
    private AvatarRepository avatarRepository;

    public void deleteAvatar(Long avatarId) {
        if(avatarRepository.isAvatarBeingUsed(avatarId)) {
            // Inform user or take appropriate action, e.g.:
            throw new IllegalStateException("Avatar is currently being used by one or more secrets.");
        } else {
            avatarRepository.deleteById(avatarId);
        }
    }

}