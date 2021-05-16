package com.example.demo384test.controller;

import com.example.demo384test.detail.CustomMemberDetails;
import com.example.demo384test.model.Member;
import com.example.demo384test.model.post.Comment;
import com.example.demo384test.model.post.Post;
import com.example.demo384test.repository.CommentRepository;
import com.example.demo384test.repository.MemberRepository;
import com.example.demo384test.repository.PostRepository;
import com.example.demo384test.request.CommentCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PostRepository postRepository;

    @GetMapping(path="/comments/all")
    public @ResponseBody Iterable<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @PostMapping("/process_add_comment")
    public String processAddComment(CommentCreationRequest ccr) {
        Comment comment = new Comment();
        comment.setDate(java.time.LocalDate.now());
        comment.setTimestamp(java.time.LocalTime.now());
        comment.setContent(ccr.getContent());

        // Get logged member
        CustomMemberDetails principal = (CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member m = memberRepository.findByUsername(principal.getUsername());
        comment.setMember(m);

        Post p = postRepository.findByid(Long.parseLong(ccr.getId()));
        comment.setPost(p);
        commentRepository.save(comment);

        return "redirect:/posts/" + ccr.getId();
    }
}
