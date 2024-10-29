package com.hans.codereviewer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hans.codereviewer.biz.DeepSeekBIz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class ReviewerController {

    @Autowired
    private DeepSeekBIz deepSeekBIz;

    @RequestMapping(value = "/codeAnalyse", method = RequestMethod.POST)
    public String process(@RequestBody String body) throws JsonProcessingException {
        String process = deepSeekBIz.sendContent(body);
        log.info(process);
        return process;
    }

    @RequestMapping(value = "/noticeCommit", method = RequestMethod.POST)
    public String noticeCommit(@RequestBody String body) throws JsonProcessingException {
        log.info(body);
        return "success";
    }

}
