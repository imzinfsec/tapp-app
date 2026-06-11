package com.example.tappapp.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 기본적으로 전체 조회(findAll), 저장(save)을 제공하므로 비워둬도 작동합니다.
}