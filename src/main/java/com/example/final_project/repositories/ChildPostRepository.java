package com.example.final_project.repositories;

import com.example.final_project.models.ChildPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildPostRepository extends JpaRepository<ChildPost, Long> {
}
