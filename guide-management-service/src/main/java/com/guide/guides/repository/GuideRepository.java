package com.guide.guides.repository;

import java.util.List;
import java.util.Optional;

import com.guide.guides.entity.Guide;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuideRepository extends JpaRepository<Guide, Long> {
    Optional<Guide> getGuideById(Long id);

    List<Guide> findAllByCategoryId(Long id);

    List<Guide> findAllByCoachId(Long id);
}
