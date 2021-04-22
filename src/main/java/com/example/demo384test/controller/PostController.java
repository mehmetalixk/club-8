package com.example.demo384test.controller;


import com.example.demo384test.detail.CustomMemberDetails;
import com.example.demo384test.model.Member;
import com.example.demo384test.model.Post;
import com.example.demo384test.model.Subclub;
import com.example.demo384test.repository.MemberRepository;
import com.example.demo384test.repository.PostRepository;
import com.example.demo384test.repository.SubclubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PostController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private SubclubRepository subclubRepository;
    @Autowired
    private MemberRepository memberRepository;

    @GetMapping(path="/posts/all")
    public @ResponseBody Iterable<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/post")
    public ModelAndView post(Model model) {
        CustomMemberDetails principal = (CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null) {
            boolean isAllowed = principal.hasPermission("ROLE_ADMIN") || principal.hasPermission("ROLE_USER");
            if(!isAllowed)
                return null;
        }

        model.addAttribute("post", new Post());
        model.addAttribute("subclubList", subclubRepository.findAllTitles());
        return new ModelAndView("post");
    }

    @PostMapping("/process_add_post")
    public ModelAndView processAddPost(Post post) {
        // READ subclub from the subclub repository
        Subclub sc = subclubRepository.findByTitle(post.getSubclubTitle());
        post.setDate(java.time.LocalDate.now());
        post.setTimestamp(java.time.LocalTime.now());
        // Get logged member
        CustomMemberDetails principal = (CustomMemberDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member m = memberRepository.findByUsername(principal.getUsername());
        post.setMemberUsername(m.getUsername());
        m.addPost(post);
        sc.addPostToSubclub(post);

        // CREATE a new post in post repository
        postRepository.save(post);
        // UPDATE the subclub with new post
        subclubRepository.save(sc);
        // UPDATE the member with new post
        memberRepository.save(m);
        return new ModelAndView("success");

    }

}
