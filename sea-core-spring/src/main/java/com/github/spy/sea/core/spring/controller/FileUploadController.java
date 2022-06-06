package com.github.spy.sea.core.spring.controller;

import com.github.spy.sea.core.model.Result;
import com.github.spy.sea.core.util.PathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/6/6
 * @since 1.0
 */
@Slf4j
public class FileUploadController {

    /**
     * upload file
     *
     * @param uploadFile
     * @return
     */
    public Result upload(MultipartFile uploadFile) throws IOException {
        log.info("original file name={}, name={}", uploadFile.getOriginalFilename(), uploadFile.getName());
        log.info("file size={}", uploadFile.getSize());

        // save file
        uploadFile.transferTo(new File(PathUtil.getSeaHome(), "test/abc.txt"));

        return Result.success();
    }
}
