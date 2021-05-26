package com.example.demo384test.config;

import com.example.demo384test.model.Club.Subclub;
import com.example.demo384test.model.Member;
import com.example.demo384test.model.Security.Permission;
import com.example.demo384test.model.Security.Role;
import com.example.demo384test.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class Util {

    @Autowired
    MemberRepository memberRepository;

    public static String getCurrentUsername(){
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }


    public static boolean checkWritePermission(Member currentUser,String title, String subclub){
        boolean isMember = false;
        for(Permission permission : currentUser.getRoles().get(0).getPermissions()) {
            isMember = permission.getName().equals("WRITE_PERMISSION_" + title + "_" + subclub);
            if(isMember)
                break;
        }
        return isMember;
    }

    public static boolean checkReadPermission(Member currentUser,String title, String subclub){
        boolean isMember = false;
        for(Permission permission : currentUser.getRoles().get(0).getPermissions()) {
            isMember = permission.getName().equals("READ_PERMISSION_" + title + "_" + subclub);
            if(isMember)
                break;
        }
        return isMember;
    }

    public static boolean isAdmin(Member currentUser){
        return currentUser.getRoles().get(0).getName().equals("ROLE_ADMIN");
    }
}
