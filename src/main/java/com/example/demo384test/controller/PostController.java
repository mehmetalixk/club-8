package com.example.demo384test.controller;


import com.example.demo384test.config.Util;
import com.example.demo384test.detail.CustomMemberDetails;
import com.example.demo384test.model.Member;
import com.example.demo384test.model.Security.Role;
import com.example.demo384test.model.post.Post;
import com.example.demo384test.model.Club.Subclub;
import com.example.demo384test.repository.*;
import com.example.demo384test.request.CommentCreationRequest;
import com.example.demo384test.request.PostCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private CommentRepository commentRepository;


    @GetMapping(path="/posts/all")
    public @ResponseBody Iterable<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/post")
    public ModelAndView post(Model model) {
        model.addAttribute("pcr", new PostCreationRequest());
        model.addAttribute("subclubList", subclubRepository.findAllTitles());
        model.addAttribute("clubList", clubRepository.findAllTitles());
        return new ModelAndView("post");
    }

    @GetMapping("/posts/{id}")
    public ModelAndView getPostPage(@PathVariable String id, Model model) {
        Long idLong = Long.parseLong(id);
        Post p = postRepository.findByid(idLong);

        if(p == null) {
            return new ModelAndView("error");
        }
        CommentCreationRequest ccr = new CommentCreationRequest();
        ccr.setId(id);

        model.addAttribute("ccr",  ccr);
        model.addAttribute("comments", commentRepository.findByPostID(idLong));
        model.addAttribute("post", p);
        return new ModelAndView("post_page");
    }


    @PostMapping("/process_add_post")
    public ModelAndView processAddPost(PostCreationRequest pcr) {
        // create new post object
        Post post = new Post();
        post.setDate(java.time.LocalDate.now());
        post.setTimestamp(java.time.LocalTime.now());
        post.setContent(pcr.getContent());
        post.setTitle(pcr.getTitle());

        // Get logged member
        String currentUsername = Util.getCurrentUsername();
        Member m = memberRepository.findByUsername(currentUsername);

        // Get subclub
        Subclub sc = subclubRepository.findByClubTitle(pcr.getSubclubTitle(), pcr.getClubTitle());

        boolean isMember = checkRole(sc, m);

        if(isMember){
            post.setMember(m);
            post.setSubclub(sc);

            // CREATE a new post in post repository
            postRepository.save(post);
            return new ModelAndView("success");
        }else{
            return new ModelAndView("error");
        }
    }

    public boolean checkRole(Subclub sc, Member m){
        try {
            for (Role role : m.getRoles()) {
                if (role.getName().equals("ROLE_ADMIN")) {
                    return true;
                }
            }
        }catch (NullPointerException e){
            return sc.getMembers().contains(m);
        }
        return sc.getMembers().contains(m);
    }
}
