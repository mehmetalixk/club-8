/*
*  Class to test models while implementation process
*  Also initial club-sub club-member-role-permission
*  definitions are made in here.
* */

package com.example.demo384test.listener;

import com.example.demo384test.model.Club.Club;
import com.example.demo384test.model.post.Comment;
import com.example.demo384test.model.Club.Subclub;
import com.example.demo384test.model.Member;
import com.example.demo384test.model.post.Like;
import com.example.demo384test.model.post.Post;
import com.example.demo384test.model.Security.Permission;
import com.example.demo384test.model.Security.Role;
import com.example.demo384test.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.*;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private SubclubRepository subclubRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (alreadySetup) {
            return;
        }

        Permission readPermission = createPermissionIfNotFound("READ_PERMISSION");
        Permission writePermission = createPermissionIfNotFound("WRITE_PERMISSION");
        List<Permission> adminPermissions = Arrays.asList(readPermission, writePermission);
        createRoleIfNotFound("ROLE_ADMIN", adminPermissions);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPermission));

        Member admin = createAdminIfNotFound();

        createClubIfNotFound("books");
        createClubIfNotFound("movies");
        createClubIfNotFound("sports");
        createClubIfNotFound("games");

        Subclub horror_books = createSubclubIfNotFound("horror", "books");
        createSubclubIfNotFound("drama", "books");
        createSubclubIfNotFound("sci-fi", "books");
        createSubclubIfNotFound("fantastic", "books");
        createSubclubIfNotFound("engineering", "books");

        createSubclubIfNotFound("horror", "movies");
        createSubclubIfNotFound("sci-fi", "movies");
        createSubclubIfNotFound("drama", "movies");
        createSubclubIfNotFound("fantastic", "movies");
        createSubclubIfNotFound("documentary", "movies");

        createSubclubIfNotFound("football", "sports");
        createSubclubIfNotFound("basketball", "sports");
        createSubclubIfNotFound("tennis", "sports");
        createSubclubIfNotFound("badminton", "sports");
        createSubclubIfNotFound("volleyball", "sports");

        createSubclubIfNotFound("rpg", "games");
        createSubclubIfNotFound("fps", "games");
        createSubclubIfNotFound("horror", "games");
        createSubclubIfNotFound("sci-fi", "games");

        horror_books.addMemberToSubclub(admin);

        Post post = createPostIfNotExists(admin, "Initial post", "Initial post from SetupDataLoader", horror_books);

        Comment c = createComment(admin, "This is a comment", post);

        Like l = createLike(admin, post);

        // Here create a user with test clubs assigned
        createUserWithClubsIfNotFound();

        alreadySetup = true;
    }

    /*
     * Add a like on the given post
     * This method will be deleted while publishing the source code
     * */
    @Transactional
    Like createLike(Member member, Post post) {
        // check if member is already liked the post
        Like like = likeRepository.findByPostTitleAndMember(post.getTitle(), member.getUsername());
        if (like == null) {
            like = new Like();
            like.setMember(member);
            like.setPost(post);
            likeRepository.save(like);
        }

        return like;
    }

    /*
     * Create a comment on the given post
     * This method will be deleted while publishing the source code
     * */
    @Transactional
    Comment createComment(Member member, String content, Post post) {
        Comment comment = new Comment();
        comment.setDate(java.time.LocalDate.now());
        comment.setTimestamp(java.time.LocalTime.now());
        comment.setContent(content);
        comment.setMember(member);
        comment.setPost(post);
        commentRepository.save(comment);
        return comment;
    }


    /*
    * Create a post if not exists on the given subclub
    * This method will be deleted while publishing the source code
    * */
    @Transactional
    Post createPostIfNotExists(Member member, String title, String content, Subclub subclub) {
        Post post = postRepository.findBySubclubTitleAndTitle(subclub.getTitle(), title);

        // If the post does not exists create it
        if(post == null) {
            post = new Post();
            post.setDate(java.time.LocalDate.now());
            post.setTimestamp(java.time.LocalTime.now());
            post.setContent(content);
            post.setTitle(title);
            post.setSubclub(subclub);
            post.setMember(member);
            post.setPhotoPath("/icons/img.png");
            postRepository.save(post);
        }
        return post;
    }

    /*
     * Create initial permissions while initializing the project for the very first time
     * */
    @Transactional
    Permission createPermissionIfNotFound(String name) {

        Permission permission = permissionRepository.findByName(name);
        if (permission == null) {
            permission = new Permission(name);
            permissionRepository.save(permission);
        }
        return permission;
    }

    /*
     * Create initial roles while initializing the project for the very first time
     * */
    @Transactional
    Role createRoleIfNotFound(String name, Collection<Permission> permissions) {
        Role role = roleRepository.findByName(name);

        if (role == null) {
            role = new Role();
            role.setName(name);
            role.setPermissions(permissions);
            roleRepository.save(role);
        }

        return role;
    }
    /*
    * Create an admin account while initializing the project for the very first time
    * */
    @Transactional
    Member createAdminIfNotFound() {
        Member member = memberRepository.findByUsername("admin");

        if(member == null) {
            Role adminRole = roleRepository.findByName("ROLE_ADMIN");
            member = new Member();
            member.setEmailAddress("admin@club8.com");
            member.setUsername("admin");
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPassword = encoder.encode("admin");
            member.setPassword(encodedPassword);

            member.setName("admin");
            member.setSurname("admin");
            member.setGender("Non-disclosed");
            member.setBirthDate(new Date(Calendar.getInstance().getTime().getTime()));

            member.setRoles(Arrays.asList(adminRole));

            member.setEnabled(true);

            memberRepository.save(member);
        }

        return member;
    }

    @Transactional
    Member createUserWithClubsIfNotFound(){
        Member member = memberRepository.findByUsername("Haydar");

        if(member == null) {

            member = new Member();
            member.setEmailAddress("haydar@haydar.com");
            member.setUsername("haydar");
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPassword = encoder.encode("haydar");
            member.setPassword(encodedPassword);
            member.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
            member.setName("Haydar");
            member.setSurname("Haydar");
            member.setGender("Male");
            member.setBirthDate(new Date(Calendar.getInstance().getTime().getTime()));

            member.setEnabled(true);

            Member haydar = memberRepository.save(member);
            memberRepository.flush();

            // Clubs to add

            Permission permission1 = permissionRepository.findByName("READ_PERMISSION_books_fantastic");
            Permission permission2 = permissionRepository.findByName("READ_PERMISSION_movies_horror");
            Permission permission3 = permissionRepository.findByName("READ_PERMISSION_games_horror");
            Permission permission4 = permissionRepository.findByName("READ_PERMISSION_games_sci-fi");

            Role specificRole = new Role();
            specificRole.setName(member.getUsername()+"_ROLE");
            List<Permission> customPermissions = new ArrayList<>();
            customPermissions.add(permission1);
            customPermissions.add(permission2);
            customPermissions.add(permission3);
            customPermissions.add(permission4);
            specificRole.setPermissions(customPermissions);
            roleRepository.save(specificRole);
            roleRepository.flush();

            Role customRole = roleRepository.findByName(member.getUsername()+"_ROLE");

            Member savedMember = memberRepository.findByUsername("haydar");

            List<Role> customRoleList = new ArrayList();
            customRoleList.add(customRole);
            savedMember.setRoles(customRoleList);
            memberRepository.save(savedMember);
            memberRepository.flush();
        }

        return member;
    }

    /*
     * Create initial clubs while initializing the project for the very first time
     * */
    @Transactional
    Club createClubIfNotFound(String title) {
        Club club = clubRepository.findByTitle(title);

        // create club if not exists in the repository
        if (club == null) {
            club = new Club();
            club.setTitle(title);

            clubRepository.save(club);
        }

        return club;
    }


    /*
     * Create initial sub clubs while initializing the project for the very first time
     * */
    @Transactional
    Subclub createSubclubIfNotFound(String title, String clubTitle) {
        Club club = clubRepository.findByTitle(clubTitle);
        // club does not exist
        if(club == null)
            return null;

        Subclub subclub = subclubRepository.findByClubTitle(title, clubTitle);

        // create subclub if not exists
        if (subclub == null) {
            System.out.println("SUBCLUB NOT FOUND");
            subclub = new Subclub();
            subclub.setTitle(title);
            subclub.setClub(club);
            subclubRepository.save(subclub);

            Permission subClubReadPermission = new Permission("READ_PERMISSION_" + clubTitle + "_" + title);
            Permission subClubWritePermission = new Permission("WRITE_PERMISSION_" + clubTitle + "_" + title);
            permissionRepository.save(subClubReadPermission);
            permissionRepository.save(subClubWritePermission);
            permissionRepository.flush();
        }

        return subclub;
    }
}
