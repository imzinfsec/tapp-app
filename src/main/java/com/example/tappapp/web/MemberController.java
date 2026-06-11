package com.example.tappapp.web;

import com.example.tappapp.domain.member.Member;
import com.example.tappapp.domain.member.MemberRepository;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class MemberController {

    private final MemberRepository memberRepository;

    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 테스트용 계정 자동 생성 (서버 켜질 때 실행)
    @PostConstruct
    public void init() {
        if (memberRepository.findByLoginId("admin").isEmpty()) {
            memberRepository.save(new Member("admin", "1234", "관리자센세"));
        }
    }

    // 1. 로그인 창 띄우기
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // 2. 로그인 실제 처리
    @PostMapping("/login")
    public String login(@RequestParam String loginId, 
                        @RequestParam String password, 
                        HttpServletRequest request, 
                        Model model) {
                        
        Optional<Member> findMember = memberRepository.findByLoginId(loginId);

        // 아이디가 존재하고 비밀번호가 일치하면
        if (findMember.isPresent() && findMember.get().getPassword().equals(password)) {
            // 세션 생성 (로그인 도장 쾅!)
            HttpSession session = request.getSession();
            session.setAttribute("loginMember", findMember.get());
            return "redirect:/posts"; // 로그인 성공 시 게시판 리스트로 이동
        }

        // 로그인 실패 시
        model.addAttribute("error", "아이디 또는 비밀번호가 맞지 않습니다.");
        return "login";
    }

    // 3. 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 파기
        }
        return "redirect:/login";
    }
}