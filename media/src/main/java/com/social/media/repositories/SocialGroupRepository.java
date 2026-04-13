package com.social.media.repositories;

import com.social.media.model.SocialGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialGroupRepository extends JpaRepository<SocialGroup, Long> {
}
