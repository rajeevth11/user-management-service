package org.tvm.controller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.tvm.jpa.User;

import java.util.List;

@Component
public interface UserRepository extends JpaRepository<User, Integer>
{
    @Query(value = "select * from user where user_name =:userName", nativeQuery = true)
    List<User> findByUserName( @Param("userName") String userName );

}
