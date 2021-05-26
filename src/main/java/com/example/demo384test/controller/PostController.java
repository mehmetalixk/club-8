package com.example.demo384test.controller;


import com.example.demo384test.config.Util;
import com.example.demo384test.model.Member;
import com.example.demo384test.model.post.Post;
import com.example.demo384test.model.Club.Subclub;
import com.example.demo384test.repository.*;
import com.example.demo384test.request.CommentCreationRequest;
import com.example.demo384test.request.PostCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    @GetMapping("/posts/{postID}")
    public ModelAndView getPostPage(@PathVariable String postID, Model model) {
        Long idLong = Long.parseLong(postID);
        Post p = postRepository.findByid(idLong);

        if(p == null)
            return new ModelAndView("error");

        CommentCreationRequest ccr = new CommentCreationRequest();
        ccr.setId(postID);

        model.addAttribute("ccr",  ccr);
        model.addAttribute("comments", commentRepository.findByPostID(idLong));
        model.addAttribute("post", p);
        return new ModelAndView("post_page");
    }


    @PostMapping("/process_add_post")
    public ModelAndView processAddPost(PostCreationRequest pcr) throws IOException {
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

        boolean isAdmin = Util.isAdmin(m);
        boolean isMember = Util.checkWritePermission(m, pcr.getClubTitle(), pcr.getSubclubTitle());

        if(isMember || isAdmin){
            post.setMember(m);
            post.setSubclub(sc);

            System.out.println(pcr.getPostImage());

            if(pcr.getPostImage() != null) {
                // get sub club image
                String folder = "src/main/resources/static/photos/posts/";
                String relative = "/photos/posts/";
                byte[] arr = pcr.getPostImage().getBytes();

                // Get file extension
                String extension = "";
                int i = pcr.getPostImage().getOriginalFilename().lastIndexOf('.');
                if (i > 0) extension = pcr.getPostImage().getOriginalFilename().substring(i+1);

                // set post photo path
                post.setPhotoPath(relative + pcr.getClubTitle() + "_" + pcr.getSubclubTitle() + "_" + pcr.getTitle() + "." + extension);

                // Add post image to the path
                Path path = Paths.get(folder + pcr.getClubTitle() + "_" + pcr.getSubclubTitle() + "_" + pcr.getTitle() + "." + extension);
                Files.write(path, arr);
            }else {
                post.setPhotoPath("/icons/img.png");
            }
            // CREATE a new post in post repository
            postRepository.save(post);

            return new ModelAndView("success");
        }else{
            return new ModelAndView("error");
        }
    }


}
