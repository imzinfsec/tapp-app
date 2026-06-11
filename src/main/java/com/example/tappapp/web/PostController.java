package com.example.tappapp.web;

import com.example.tappapp.domain.member.Member;
import com.example.tappapp.domain.post.Post;
import com.example.tappapp.domain.post.PostRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 1. 게시판 리스트 조회
    @GetMapping
    public String list(HttpServletRequest request, Model model) {
        // 로그인 체크
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginMember") == null) {
            return "redirect:/login"; // 로그인 안 했으면 로그인창으로 퇴출
        }

        // DB에서 전체 글 긁어와서 타임리프에 던지기
        List<Post> posts = postRepository.findAll();
        Member loginMember = (Member) session.getAttribute("loginMember");
        
        model.addAttribute("posts", posts);
        model.addAttribute("memberName", loginMember.getName());
        return "list";
    }

    // 2. 글쓰기 창 띄우기
    @GetMapping("/write")
    public String writeForm(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginMember") == null) {
            return "redirect:/login";
        }
        return "write";
    }

    // 3. 글쓰기 실제 저장 처리
    @PostMapping("/write")
    public String write(@RequestParam String title, 
                        @RequestParam String content, 
                        HttpServletRequest request) {
                        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginMember") == null) {
            return "redirect:/login";
        }

        Member loginMember = (Member) session.getAttribute("loginMember");
        
        // 새로운 글 생성 후 DB 저장
        Post post = new Post(title, content, loginMember.getName());
        postRepository.save(post);

        return "redirect:/posts"; // 글 쓰고 나면 리스트 창으로 이동
    }
}