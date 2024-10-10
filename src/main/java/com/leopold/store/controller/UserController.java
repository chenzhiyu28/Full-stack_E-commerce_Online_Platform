package com.leopold.store.controller;


import com.leopold.store.controller.ex.FileDamagedException;
import com.leopold.store.controller.ex.FileOverSizeException;
import com.leopold.store.controller.ex.FileTypeException;
import com.leopold.store.controller.ex.FileUploadIOException;
import com.leopold.store.entity.User;
import com.leopold.store.service.IUserService;
import com.leopold.store.service.impl.UserServiceImpl;
import com.leopold.store.util.JsonResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;


//@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("api/users")
public class UserController extends BaseController{
    private final IUserService userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping({"", "/"})
    public JsonResponse<Void> register(@RequestBody User user) {
        userService.register(user);
        return successResponse(null);
    }

    @GetMapping("{userName}")
    public JsonResponse<User> findUserByName(@PathVariable String userName) {
        User user = userService.findUserByName(userName);
        return successResponse(user);
        // http://localhost:8080/api/users/{userName}
    }

    @PostMapping("sessions")
    public JsonResponse<User> login(@RequestBody User user, HttpSession session) {
        User loginUser = userService.login(user.getUsername(),user.getPassword());

        //向session对象中完成数据的绑定(session is a global object)
        session.setAttribute("uid", loginUser.getId());
        session.setAttribute("username", loginUser.getUsername());
        session.setMaxInactiveInterval(1800); // 30min 不活跃自动过期

        return successResponse(loginUser);
        // http://localhost:8080/users/sessions
    }

    @DeleteMapping("sessions")
    public JsonResponse<Void> logout(HttpSession session) {
        session.invalidate();
        return successResponse(null);
    }


    @PostMapping("me/password")
    public JsonResponse<Void> changePassword(@RequestParam String oldPassword,
                                             @RequestParam String newPassword,
                                             HttpSession session) {
        Integer uid = getUIDFromSession(session);
        String username = getUsernameFromSession(session);

        userService.updatePassword(uid, oldPassword, newPassword, username, new Date());
        return successResponse(null);
    }

    @GetMapping("uid")
    public JsonResponse<User> getUserInfo(HttpSession session) {
        Integer id = getUIDFromSession(session);
        User user = userService.findUserById(id);
        return successResponse(user);
    }

    @PostMapping("info")
    public JsonResponse<Void> changeInfo(@RequestParam("phone") String phone,
                                         @RequestParam("email") String email,
                                         @RequestParam("gender") Integer gender,
                                         HttpSession session) {

        Integer id = getUIDFromSession(session);
        User user = userService.findUserById(id);
        userService.updateInfo(id, email, phone, gender, user.getUsername());
        return successResponse(null);
    }


    // 最大头像尺寸
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;
    // 允许上传的头像的格式
    public static final List<String> AVATAR_TYPES = Arrays.asList("image/jpeg", "image/png", "image/bmp", "image/gif");
    private static final String DEVELOP_PATH = "D:/Program_Data/java_programs/store/src/main/resources/static/upload";
    @PostMapping("avatar")
    public JsonResponse<String> changeAvatar(HttpSession session,
                                             @RequestParam("file") MultipartFile multiPartFile) {
        if (multiPartFile.isEmpty()) {
            throw new FileDamagedException("File is damaged or empty");
        }
        if (multiPartFile.getSize() > AVATAR_MAX_SIZE) { // file.getSize() 返回byte
            throw new FileOverSizeException("File is too large");
        }
        // file.getContentType: image/jpeg
        if (!AVATAR_TYPES.contains(multiPartFile.getContentType())) {
            throw new FileTypeException("File type is not allowed");
        }

        // get project context address
        String parent = session.getServletContext().getRealPath("upload");
        File dir = new File(parent);
        if (!dir.exists()) {
            dir.mkdir();
        }

        // reName uploaded file with UUID random string
        String originalFilename = multiPartFile.getOriginalFilename(); // e.g.: avatar.jpg
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString().toUpperCase() + suffix;

        // write file to disk
        File avatar = new File(dir, fileName);
        File synchronizeToDevEnv = new File(DEVELOP_PATH, fileName);
        try {
            multiPartFile.transferTo(avatar); //write data to avatar
            Files.copy(avatar.toPath(), synchronizeToDevEnv.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FileUploadIOException("File upload failed");
        }

        Integer uid = getUIDFromSession(session);
        String avatarPath = "/upload/" + fileName;
        String modifiedUser = getUsernameFromSession(session);
        userService.updateAvatar(uid, avatarPath, modifiedUser);


        return successResponse(avatarPath);
    }
}









