package com.poly.app.domain.client.repository;

import com.poly.app.domain.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement,Integer> {
}
