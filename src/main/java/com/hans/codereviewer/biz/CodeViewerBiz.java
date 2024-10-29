package com.hans.codereviewer.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CodeViewerBiz {

    @Autowired
    private DeepSeekBIz deepSeekBIz;

    public void codeReview(String gitContent) {

    }

}
