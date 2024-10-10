package com.leopold.store.service;

import com.leopold.store.entity.User;
import com.leopold.store.persistence.repository.UserRepository;
import com.leopold.store.service.ex.UserNameDuplicatedException;
import com.leopold.store.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/*
// 如果是完整测试（集成测试），则需要引入@SpringBootTest，触发spring上下文， 这里是单元测试，会冲突
@SpringBootTest
@Transactional
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    /*
    @Autowired
    private UserServiceImpl userService;

    @Test
    public void testRegisterSuccess() {
        User user = User.builder()
                .username("newUser")
                .password("password")
                .build();

        userService.register(user);

        User savedUser = userService.findUserByName("newUser");
        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertEquals("newUser", savedUser.getUsername());
        assertEquals("password", savedUser.getPassword());
        assertEquals(0, savedUser.getIsDelete());
        assertNotNull(savedUser.getCreatedTime());
        assertNotNull(savedUser.getModifiedTime());
        assertEquals("newUser", savedUser.getCreatedUser());
    }

    @Test
    public void testRegisterDuplicateUsername() {
        User user1 = User.builder()
                .username("duplicateUser")
                .password("password1")
                .build();
        userService.register(user1);

        User user2 = User.builder()
                .username("duplicateUser")
                .password("password2")
                .build();

        assertThrows(UserNameDuplicatedException.class, () -> userService.register(user2));
    }
    */

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testRegisterSuccessful() {
        User user = User.builder()
                .username("User")
                .password("password")
                .build();

        // 设置模拟行为
        when(userRepository.findByUsername("User")).thenReturn(null);
        // any(User.class) 是 Mockito 的参数匹配器。它表示 "匹配任何 User 类型的参数"。
        when(userRepository.save(any(User.class))).thenReturn(user);

        //执行被测试方法 assertDoesNotThrow 接受一个 Executable 接口（函数式接口）作为参数。
        assertDoesNotThrow(() -> userService.register(user));

        // 验证这些方法是否被调用
        verify(userRepository).findByUsername("User");
        verify(userRepository).save(user);

        // 验证自动填充的字段
        assertNotNull(user.getCreatedTime());
        assertNotNull(user.getModifiedTime());
        assertEquals(0, user.getIsDelete());
        assertEquals("User", user.getCreatedUser());
    }

    @Test
    public void testLogin() {
    }

    @Test
    public void testUpdatePassword() {
        User user = User.builder()
                .id(10000)
                .username("User")
                .password(userService.md5Encryption("salt", "password"))
                .salt("salt")
                .isDelete(0)
                .build();

        // behavior mock
        when(userRepository.findUserById(10000)).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // exception assert
        assertDoesNotThrow(() -> userService.updatePassword(
                10000,
                "password",
                "newPassword",
                user.getUsername(),
                new Date()));

        // assert method called
        verify(userRepository).findUserById(10000);
        verify(userRepository).save(user);

        assertEquals(userService.md5Encryption("salt", "newPassword"), user.getPassword());
        assertNotNull(user.getModifiedTime());
        assertNotNull(user.getModifiedUser());
    }
}
