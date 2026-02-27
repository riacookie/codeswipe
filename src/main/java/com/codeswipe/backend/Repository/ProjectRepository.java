package com.codeswipe.backend.Repository;

import com.codeswipe.backend.Entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query(value = "SELECT * FROM projects WHERE project_id NOT IN " +
            "(SELECT project_id FROM swipes WHERE user_id = :userId)",
            nativeQuery = true)
    List<Project> findProjectsNotSwipedByUser(@Param("userId") Long userId);
    List<Project> findByUserUserId(Long userId);
}
