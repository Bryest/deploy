package com.guide.guides.repository;

import java.util.List;
import java.util.Optional;

import com.guide.guides.entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> getCategoryById(Long id);

    List<Category> findAllByGameId(Long id);
}
