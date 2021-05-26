package com.example.demo384test.Logger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LogController {

    private static LogRepository logRepository;

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
    public LogController(LogRepository logRepository) {
        LogController.logRepository = logRepository;
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

}
