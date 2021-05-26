package com.example.demo384test.controller;

import com.example.demo384test.Logger.LogController;
import com.example.demo384test.config.Util;
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

    @RequestMapping("/members/report/{commentID}")
    public String reportComment(@PathVariable String commentID) {
        Long id = Long.parseLong(commentID);
        Comment comment = commentRepository.findByID(id);

        if(comment == null) {
            LogController.createLog("WARN", String.format("Comment with id of %s not found!", commentID));
            return "redirect:/";
        }

        LogController.createLog("REP", String.format("%s;%s;%s;%s;%s", Util.getCurrentUsername(), comment.getMember().getUsername(), comment.getContent(), comment.getMember().getId().toString(), comment.getId().toString()));
        LogController.createLog("INFO", String.format("%s created a report on comment %s", Util.getCurrentUsername(), comment.getId()));
        return "redirect:/";
    }

    /*
    * Warn the user, if he had 3 warnings
    * ban him, delete his comment
    * */
    @RequestMapping("/members/warn/{memberID}_{commentID}")
    public String warnMember(@PathVariable String memberID, @PathVariable String commentID) {
        Long id = Long.parseLong(memberID);
        Member member = memberRepository.findByID(id);

        if(member == null) {
            LogController.createLog("WARN", "Member not found!");
            return "redirect:/admin";
        } else if (member.getUsername().equals("admin")) {
            LogController.createLog("WARN", "You cannot warn an admin!");
            return "redirect:/admin";
        }

        Comment comment = commentRepository.findByID(Long.parseLong(commentID));
        commentRepository.delete(comment);
        LogController.createLog("INFO", String.format("Comment %s deleted by the %s due to report accept...", commentID, Util.getCurrentUsername()));

        member.setWarnCount(member.getWarnCount() + 1);

        // if he received 3 warns ban him
        if(member.getWarnCount() >= 3)
            return String.format("redirect:/members/ban/%s", memberID);

        return "redirect:/admin";
    }

    /*
    * Ban the member
    * */
    @RequestMapping("/members/ban/{memberID}")
    public String banMember(@PathVariable String memberID) {
        Long id = Long.parseLong(memberID);
        Member member = memberRepository.findByID(id);

        if(member == null) {
            LogController.createLog("WARN", "Member not found!");
            return "redirect:/admin";
        } else if (member.getUsername().equals("admin")) {
            LogController.createLog("WARN", "You cannot ban an admin!");
            return "redirect:/admin";
        }

        if(!member.isBanned()) {
            member.setBanned(true);
            LogController.createLog("INFO", String.format("Member %s has banned by %s\n", member.getUsername(), Util.getCurrentUsername()));
        }else {
            member.setBanned(false);
            LogController.createLog("INFO", String.format("Member %s has unbanned by %s\n", member.getUsername(), Util.getCurrentUsername()));
        }

        memberRepository.save(member);

        return "redirect:/admin";
    }

}