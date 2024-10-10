package com.leopold.store.service.impl;

import com.leopold.store.entity.User;
import com.leopold.store.persistence.repository.UserRepository;
import com.leopold.store.service.IUserService;
import com.leopold.store.service.ex.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional // 确保整个操作在一个事务中完成。
    // 1.检查重复 2.补全user创建信息 3.试图Register, 处理exception
    public void register(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserNameDuplicatedException("Username already in use!");
        }

        // 使用返回的 savedUser 都是一个好习惯，因为它代表了数据库中实际保存的状态。
        autoFillUserRegistration(user);
        User savedUser = userRepository.save(user);
    }

    @Override
    public String md5Encryption(String salt, String pwd) {
        String encryptedPwd = (salt + pwd + salt).toUpperCase();

        for (int i=0;i<3;i++) {
            encryptedPwd = DigestUtils.md5DigestAsHex(encryptedPwd.getBytes());  // md5 encryption
        }

        return encryptedPwd;
    }

    @Override
    public User login(String userName, String password) {
        User user = userRepository.findByUsername(userName);
        if (user == null || user.getIsDelete() == 1) {
            throw new UserNotFoundException("User does not exist!");
        }

        if (!md5Encryption(user.getSalt(), password).equals(user.getPassword())) {
            throw new PasswordNotMatchException("Incorrect password!");
        }

        return user;
    }

    @Override
    @Transactional
    public User updatePassword(Integer uid, String oldPassword, String newPassword, String modifiedUser, Date modifiedTime) {
        User user = findUserById(uid);

        if (!md5Encryption(user.getSalt(), oldPassword).equals(user.getPassword())) {
            throw new PasswordNotMatchException("Incorrect password!");
        }

        user.setPassword(md5Encryption(user.getSalt(), newPassword));
        user.setModifiedUser(modifiedUser);
        user.setModifiedTime(modifiedTime);

        try {
            return userRepository.save(user);
        } catch (UpdateInfoException e) {
            throw new DatabaseConnectionException("Failed to update password!");
        }
    }

    @Override
    @Transactional
    public User updateInfo(Integer uid, String email, String phone, Integer gender, String modifiedUser) {
        User user = findUserById(uid);

        user.setEmail(email == null? user.getEmail() : email);
        user.setPhone(phone == null? user.getPhone(): phone);
        user.setGender(gender == null? user.getGender(): gender);
        user.setModifiedUser(modifiedUser);
        user.setModifiedTime(new Date());

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateAvatar(Integer uid, String avatar, String modifiedUser) {
        User user = findUserById(uid);

        user.setAvatar(avatar);
        user.setModifiedUser(modifiedUser);
        user.setModifiedTime(new Date());

        return userRepository.save(user);

    }

    private void autoFillUserRegistration(User user) {
        // add account delete state & saltedPwd
        user.setIsDelete(0);
        user.setSalt(UUID.randomUUID().toString().toUpperCase());
        user.setPassword(md5Encryption(user.getSalt(), user.getPassword()));

        // add 4 log info
        Date date = new Date();
        user.setCreatedUser(user.getUsername());
        user.setCreatedUser(user.getUsername());
        user.setCreatedTime(date);
        user.setModifiedTime(date);

    }

    @Override
    public User findUserByName(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public User findUserById(Integer uid) {
        if (uid == null) {
            throw new SessionExpiredException("You have been logged out, please login again!");
        }

        User user = userRepository.findUserById(uid);

        if (user == null || user.getIsDelete() == 1) {
            throw new UserNotFoundException("User does not exist!");
        }

        return user;
    }


}
