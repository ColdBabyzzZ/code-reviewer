package com.hans.codereviewer.biz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CodeViewerBiz {

    @Autowired
    private DeepSeekBIz deepSeekBIz;

    public void codeReview(String gitContent) {
        log.info("codeReview start");
    }

}
