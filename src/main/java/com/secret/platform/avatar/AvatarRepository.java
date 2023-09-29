package com.secret.platform.avatar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    @Query("SELECT COUNT(s) FROM Secret s WHERE s.avatar.id = :avatarId")
    long countSecretsUsingAvatar(@Param("avatarId") Long avatarId);

    default boolean isAvatarBeingUsed(Long avatarId) {
        return countSecretsUsingAvatar(avatarId) > 0;
    }

    // other methods...
}
