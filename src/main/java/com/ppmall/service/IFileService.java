package com.ppmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * com.ppmall.service
 * 2019/1/16
 */
public interface IFileService {
    /**
     * 上传文件
     * @param file 文件
     * @param path 路径
     * @return String 新路径
     */
    String upload(MultipartFile file, String path);
}
