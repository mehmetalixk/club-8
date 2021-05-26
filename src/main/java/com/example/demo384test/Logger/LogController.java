package com.example.demo384test.Logger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

}
