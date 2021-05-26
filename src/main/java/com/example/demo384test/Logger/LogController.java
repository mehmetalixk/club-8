package com.example.demo384test.Logger;


import com.example.demo384test.config.Util;
import com.example.demo384test.model.Club.Club;
import com.example.demo384test.repository.ClubRepository;
import com.example.demo384test.repository.MemberRepository;
import com.example.demo384test.repository.SubclubRepository;
import com.example.demo384test.request.SubclubCreationRequest;
import lombok.extern.java.Log;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LogController {

    private static LogRepository logRepository;
    private static ClubRepository clubRepository;
    private static SubclubRepository subclubRepository;
    private static MemberRepository memberRepository;

    public static Logger createLog(String level, String content) {
        Logger logger = new Logger();
        logger.setLevel(level);
        logger.setContent(content);
        logger.setTimestamp(java.time.LocalTime.now());
        logger.setDate(java.time.LocalDate.now());
        logRepository.save(logger);
        return logger;
    }

    @Autowired
    public LogController(LogRepository logRepository, ClubRepository clubRepository, SubclubRepository subclubRepository, MemberRepository memberRepository) {
        LogController.logRepository = logRepository;
        LogController.clubRepository = clubRepository;
        LogController.subclubRepository = subclubRepository;
        LogController.memberRepository = memberRepository;
    }

    @GetMapping(path="/logs/all")
    public @ResponseBody
    Iterable<Logger> getAllLogs() {
        return logRepository.findAll();
    }

    @RequestMapping(path="/reports/delete/{logID}")
    public String deleteReport(@PathVariable String logID) {

        Logger log = logRepository.findByID(Long.parseLong(logID));

        if(log == null) {
            LogController.createLog("WARN", String.format("Log with id %s not found!", logID));
            return "redirect:/admin";
        }else if(!log.getLevel().equals("REP") && !log.getLevel().equals("REQ") ) {
            LogController.createLog("ERR", String.format("You cannot delete a log with level: %s, only REQ and REP", log.getLevel()));
            return "redirect:/admin";
        }

        logRepository.delete(log);

        return "redirect:/admin";
    }

    @GetMapping(path="/requests/subclub")
    public ModelAndView deleteReport(Model model) {

        model.addAttribute("scr", new SubclubCreationRequest());
        model.addAttribute("clubs", LogController.clubRepository.findAllTitles());
        return new ModelAndView("requests");
    }

    @PostMapping(path="/process_submit_request")
    public String processSubmitRequest(SubclubCreationRequest scr) {
        Club club = LogController.clubRepository.findByTitle(scr.getClubTitle());

        // club not found
        if(club == null) {
            LogController.createLog("WARN", String.format("Club with title %s not found on the repository", scr.getClubTitle()));
            return "redirect:/requests/subclub";
        }

        // Otherwise create the subclub request
        LogController.createLog("INFO", String.format("Member %s requested to open subclub %s on club %s", Util.getCurrentUsername(), scr.getTitle(), scr.getClubTitle()));
        LogController.createLog("REQ", String.format("%s;%s;%s", Util.getCurrentUsername(), scr.getTitle(), scr.getClubTitle()));
        return "redirect:/";
    }

}
