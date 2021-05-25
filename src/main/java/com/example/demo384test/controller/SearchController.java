package com.example.demo384test.controller;

import com.example.demo384test.model.Club.Subclub;
import com.example.demo384test.model.Member;
import com.example.demo384test.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

    @RequestMapping("/search")
    public void viewHomePage(Model model, @Param("keyword") String keyword) {
        List<Subclub> listSubclubs = searchService.listSubclubs(keyword);
        List<Member> listMembers = searchService.listMember(keyword);
        model.addAttribute("listSubclubs", listSubclubs);
        model.addAttribute("listMembers", listMembers);
        model.addAttribute("keyword", keyword);

    }

}
