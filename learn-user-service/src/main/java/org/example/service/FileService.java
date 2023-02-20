package org.example.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author : Yang
 * @Date :  2023/2/19 22:28
 * @Description：
 */
public interface FileService {

    public String uploadUserImage(MultipartFile file);
}
