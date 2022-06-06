package com.github.spy.sea.core.example.controller;

import com.github.spy.sea.core.spring.component.responseFile.ResponseFile;
import com.github.spy.sea.core.util.PathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/6/6
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/param/responseFile")
public class ResponseFileController {

    @PostMapping("/download")
    public ResponseFile download() throws Exception {
        ResponseFile responseFile = new ResponseFile();

        responseFile.setAttachmentName("文件名");
        responseFile.setFile(new File(PathUtil.getSeaHome() + "/test/file.txt"));
        return responseFile;
    }
}
