package com.leopold.store.service;

import com.leopold.store.entity.User;

import java.util.Date;

public interface IUserService {
    void register(User user);

    User findUserByName(String name);

    User findUserById(Integer uid);

    String md5Encryption(String salt, String pwd);

    User login(String userName, String password);

    User updatePassword(Integer uid,
                           String oldPassword,
                           String newPassword,
                           String modifiedUser,
                           Date modifiedTime);

    User updateInfo(Integer uid, String email, String phone, Integer gender, String modifiedUser);

    User updateAvatar(Integer uid, String avatar, String modifiedUser);
}
