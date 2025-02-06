package com.visualpathit.account.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.visualpathit.account.model.User;
import com.visualpathit.account.service.UserService;

@Controller
public class FileUploadController {

    @Autowired
    private UserService userService;

    private static final Logger log = LoggerFactory.getLogger(FileUploadController.class);

    /**
     * Displays the file upload page.
     */
    @RequestMapping(value = { "/upload" }, method = RequestMethod.GET)
    public String showUploadForm(Model model) {
        return "upload";
    }

    /**
     * Handles file upload.
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFile(@RequestParam("name") String name,
                             @RequestParam("userName") String userName,
                             @RequestParam("file") MultipartFile file) {

        log.info("Received request to upload file.");

        if (!file.isEmpty()) {
            try {
                byte[] fileData = file.getBytes();

                // Determine file storage directory
                String basePath = System.getProperty("catalina.home");
                log.info("Storage directory: " + basePath);

                File directory = new File(basePath + File.separator + "tmpFiles");
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // Define the file path
                File destinationFile = new File(directory.getAbsolutePath() + File.separator + name + ".png");

                // Save the file to the server
                BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(destinationFile));
                outputStream.write(fileData);
                outputStream.close();

                // Update user details with file information
                User user = userService.findByUsername(userName);
                user.setProfileImg(name + ".png");
                user.setProfileImgPath(destinationFile.getAbsolutePath());
                userService.save(user);

                log.info("File successfully stored at: " + destinationFile.getAbsolutePath());

                return "File uploaded successfully: " + name + ".png";
            } catch (Exception ex) {
                log.error("Error uploading file: " + name + ".png", ex);
                return "Upload failed for " + name + ".png" + " due to: " + ex.getMessage();
            }
        } else {
            return "Upload failed: The file is empty.";
        }
    }
}
