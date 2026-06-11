package com.example.tappapp.domain.member;

import jakarta.persistence.*;

@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String loginId; // 로그인 ID

    @Column(nullable = false)
    private String password; // 비밀번호

    private String name; // 사용자 이름

    // 기본 생성자 (JPA 필수)
    public Member() {}

    public Member(String loginId, String password, String name) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
    }

    // Getter 메서드들
    public Long getId() { return id; }
    public String getLoginId() { return loginId; }
    public String getPassword() { return password; }
    public String getName() { return name; }
}