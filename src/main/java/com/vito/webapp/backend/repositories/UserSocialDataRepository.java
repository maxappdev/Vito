package com.vito.webapp.backend.repositories;

import com.vito.webapp.backend.entities.users.UserSocialData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSocialDataRepository extends JpaRepository<UserSocialData, Long> {
}
