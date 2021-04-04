package com.vito.webapp.backend.repositories;

import com.vito.webapp.backend.entities.posts.FacebookPage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacebookPageRepository extends JpaRepository<FacebookPage, Long> {
}
