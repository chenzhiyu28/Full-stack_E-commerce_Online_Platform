package com.leopold.store.persistence.repository;

import com.leopold.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends
        JpaRepository<User, Integer>,
        JpaSpecificationExecutor<User> {
    User findByUsername(String username);

    User findUserById(Integer id);

    // 这里是一个例子,如何自定义SQL. 平时直接用save(user)就行
    //value= 明确指定了这是一个 SQL 查询字符串。
    //nativeQuery = true 告诉 JPA 这是一个原生 SQL 查询，而不是 JPQL。
    @Modifying
    @Query(value = "INSERT INTO t_user (username, password, email) VALUES (:#{#user.username}, :#{#user.password}, :#{#user.email})",
            nativeQuery = true)
    int saveUser(@Param("user") User user);  // 返回值表示影响的行数


}
