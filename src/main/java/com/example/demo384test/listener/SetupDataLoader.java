/*
*  Class to test models while implementation process
*  Also initial club-sub club-member-role-permission
*  definitions are made in here.
* */

package com.example.demo384test.listener;

import com.example.demo384test.Logger.LogController;
import com.example.demo384test.Logger.LogRepository;
import com.example.demo384test.model.Club.Club;
import com.example.demo384test.model.post.*;
import com.example.demo384test.model.Club.Subclub;
import com.example.demo384test.model.Member;
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

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private LogRepository logRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (alreadySetup) {
            return;
        }

        LogController lc = new LogController(logRepository, clubRepository, subclubRepository, memberRepository);

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

        // Here create a user with test clubs assigned
        createUserWithClubsIfNotFound();

        Question q1 = new Question();
        Question q2 = new Question();
        Question q3 = new Question();

        q1.setContent("Which one do you like the most?");
        q2.setContent("Which one do you like the most?");
        q3.setContent("Which one do you like the most?");

        ArrayList<String> q1_Options = new ArrayList<>();
        q1_Options.add("footbaall");
        q1_Options.add("basketball");
        q1_Options.add("volleyball");
        q1_Options.add("tennis");

        ArrayList<String> q2_Options = new ArrayList<>();
        q2_Options.add("rpg");
        q2_Options.add("fps");
        q2_Options.add("horror");
        q2_Options.add("sci-fi");

        ArrayList<String> q3_Options = new ArrayList<>();
        q3_Options.add("horror");
        q3_Options.add("drama");
        q3_Options.add("fantastic");
        q3_Options.add("documentary");

        q1.setOptions(String.join(";", q1_Options));
        q2.setOptions(String.join(";", q2_Options));
        q3.setOptions(String.join(";", q3_Options));

        questionRepository.save(q1);
        questionRepository.save(q2);
        questionRepository.save(q3);

        HashSet<Question> qs = new HashSet<>();
        qs.add(q1);
        qs.add(q2);
        qs.add(q3);

        Poll p = new Poll();
        p.setTitle("Initial Poll");
        p.setQuestions(qs);

        pollRepository.save(p);

        alreadySetup = true;
    }

    /*
    *
    *
    * */
    @Transactional
    Poll createPollIfNotFound() {

        return null;
    }

    @Transactional
    Question createQuestionIfNotFound() {

        return null;
    }

    /*
     * Add a like on the given post
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
            LogController.createLog("INFO",  permission.getName() + " created and added to the permission repository");
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
            LogController.createLog("INFO",  role.getName() + " created and added to the role repository");
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
            LogController.createLog("INFO",  member.getName() + " created and added to the member repository");
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
            Permission permission5 = permissionRepository.findByName("WRITE_PERMISSION_books_fantastic");
            Permission permission2 = permissionRepository.findByName("READ_PERMISSION_movies_horror");
            Permission permission6 = permissionRepository.findByName("WRITE_PERMISSION_movies_horror");
            Permission permission3 = permissionRepository.findByName("READ_PERMISSION_games_horror");
            Permission permission7 = permissionRepository.findByName("WRITE_PERMISSION_games_horror");
            Permission permission4 = permissionRepository.findByName("READ_PERMISSION_games_sci-fi");
            Permission permission8 = permissionRepository.findByName("WRITE_PERMISSION_games_sci-fi");

            Role specificRole = new Role();
            specificRole.setName(member.getUsername()+"_ROLE");
            List<Permission> customPermissions = new ArrayList<>();
            customPermissions.add(permission1);
            customPermissions.add(permission2);
            customPermissions.add(permission3);
            customPermissions.add(permission4);
            customPermissions.add(permission5);
            customPermissions.add(permission6);
            customPermissions.add(permission7);
            customPermissions.add(permission8);
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
            LogController.createLog("INFO",  club.getTitle() + " created and added to the club repository");
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
        if(club == null) {
            LogController.createLog("WARN", clubTitle + " not found in club repository. Sub club " + title + " could not created.");
            return null;
        }


        Subclub subclub = subclubRepository.findByClubTitle(title, clubTitle);

        // create subclub if not exists
        if (subclub == null) {
            System.out.println("SUBCLUB NOT FOUND");
            subclub = new Subclub();
            subclub.setTitle(title);
            subclub.setClub(club);
            subclubRepository.save(subclub);
            LogController.createLog("INFO",  subclub.getTitle() + " created and added to the sub club repository");

            Event event = new Event();
            event.setContent("We will meet");
            Member admin = memberRepository.findByUsername("admin");
            event.setMember(admin);
            event.setDate(java.time.LocalDate.now());
            Subclub sc = subclubRepository.findByClubTitle(title, clubTitle);
            event.setSubclub(sc);
            event.setLocation("Zoom");
            eventRepository.save(event);

            Permission subClubReadPermission = new Permission("READ_PERMISSION_" + clubTitle + "_" + title);
            Permission subClubWritePermission = new Permission("WRITE_PERMISSION_" + clubTitle + "_" + title);

            permissionRepository.save(subClubReadPermission);
            LogController.createLog("INFO",  subClubReadPermission.getName() + " created and added to the permission repository");

            permissionRepository.save(subClubWritePermission);
            LogController.createLog("INFO",  subClubWritePermission.getName() + " created and added to the permission repository");

            permissionRepository.flush();
        }

        return subclub;
    }
}
