package com.example.demo384test.config;

import com.example.demo384test.model.Club.Subclub;
import com.example.demo384test.model.Member;
import com.example.demo384test.model.Security.Role;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class Util {

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


    public static boolean checkRole(Subclub sc, Member m){
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
