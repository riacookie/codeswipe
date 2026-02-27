package com.codeswipe.backend.Repository;

import com.codeswipe.backend.Entity.Swipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface SwipeRepository extends JpaRepository<Swipe, Long> {

    List<Swipe> findByUserUserIdAndSwipeType(Long userId, String swipeType);

    @Transactional
    @Modifying
    @Query("DELETE FROM Swipe s WHERE s.user.userId = :uId AND s.project.projectId = :pId")
    void deleteByUserIdAndProjectId(@Param("uId") Long userId, @Param("pId") Long projectId);

    @Transactional
    void deleteByUserUserId(Long userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Swipe s WHERE s.user.userId = :userId AND s.swipeType = 'SKIP'")
    void deleteSkipsByUserId(@Param("userId") Long userId);
}