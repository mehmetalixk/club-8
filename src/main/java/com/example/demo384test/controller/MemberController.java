package com.example.demo384test.controller;

import com.example.demo384test.model.Club.Subclub;
import com.example.demo384test.model.Member;
import com.example.demo384test.model.post.Comment;
import com.example.demo384test.model.post.Like;
import com.example.demo384test.model.post.Post;
import com.example.demo384test.repository.*;
import com.example.demo384test.request.MemberProfileUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collection;


@Controller
public class MemberController {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private SubclubRepository subclubRepository;

    @GetMapping(path="/members/all")
    public @ResponseBody Iterable<Member> getAllUsers() {
        return memberRepository.findAll();
    }

    @GetMapping(path="/members/{memberID}")
    public ModelAndView getProfile(@PathVariable String memberID, Model model) {
        Long id = Long.parseLong(memberID);
        Member member = memberRepository.findByID(id);

        if (member == null)
            return new ModelAndView("error");

        ArrayList<Like> likes = likeRepository.findAllByMemberUsername(member.getUsername());
        ArrayList<Post> posts = postRepository.findByMember_username(member.getUsername());
        ArrayList<Comment> comments = commentRepository.findAllByMemberUsername(member.getUsername());
        Collection<Subclub> subclubs = subclubRepository.findByMembers_username(member.getUsername());

        Post post = null;
        // Get the last index of posts
        if(posts.size() != 0)
            post = posts.get(posts.size() - 1);

        model.addAttribute("post", post);
        model.addAttribute("member", member);
        model.addAttribute("like", likes.size());
        model.addAttribute("comment", comments.size());
        model.addAttribute("subclubs", subclubs);

        return new ModelAndView("profile");
    }

    @RequestMapping(value="/members/{memberID}/edit", method = RequestMethod.GET)
    public ModelAndView getUpdateProfile(@PathVariable String memberID, Model model) {
        Long id = Long.parseLong(memberID);
        Member member = memberRepository.findByID(id);

        if (member == null)
            return new ModelAndView("error");

        MemberProfileUpdateRequest mpur = new MemberProfileUpdateRequest();
        mpur.setId(memberID);

        model.addAttribute("member", member);
        model.addAttribute("mpur", mpur);

        return new ModelAndView("edit_profile");
    }

    @PostMapping("/members/edit/process")
    public String processUpdateProfile(MemberProfileUpdateRequest mpur) {
        Member member = memberRepository.findByID(Long.parseLong(mpur.getId()));

        member.setBirthDate(mpur.getBirthDate());
        member.setPassword(mpur.getPassword());
        member.setName(mpur.getName());
        member.setSurname(mpur.getSurname());

        memberRepository.save(member);

        return String.format("redirect:/members/%s", mpur.getId());
    }
}