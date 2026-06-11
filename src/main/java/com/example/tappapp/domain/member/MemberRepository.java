package com.example.tappapp.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 로그인 ID로 회원 찾기 기능 추가
    Optional<Member> findByLoginId(String loginId);
}