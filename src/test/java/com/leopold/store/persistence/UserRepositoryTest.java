package com.leopold.store.persistence;

import com.leopold.store.entity.User;
import com.leopold.store.persistence.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional // 测试完成后事务会自动回滚。
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        User user = User.builder().username("test").password("pwd").build();
        User savedUser = userRepository.save(user);

        assertNotNull(savedUser.getId());
        assertEquals("test", savedUser.getUsername());
        assertEquals("pwd", savedUser.getPassword());
    }

    @Test
    public void testFindUserById() {
        User user = User.builder().username("test").password("pwd").build();
        User savedUser = userRepository.save(user);

        assertNotNull(userRepository.findById(savedUser.getId()));
    }
}
