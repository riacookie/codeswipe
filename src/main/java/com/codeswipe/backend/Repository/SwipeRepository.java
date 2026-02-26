package com.codeswipe.backend.Repository;

import com.codeswipe.backend.Entity.Swipe;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SwipeRepository extends JpaRepository<Swipe, Long> {
    // Find only the projects the user swiped "LIKE" on
    List<Swipe> findByUserUserIdAndSwipeType(Long userId, String swipeType);
}
