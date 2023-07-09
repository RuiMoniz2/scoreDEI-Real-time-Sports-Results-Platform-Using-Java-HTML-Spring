package com.example.demo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import com.example.data.UserWeb;

public interface UserWebRepository extends CrudRepository<UserWeb, Integer> {

    @Query("select u from UserWeb u where u.username like %?1")
    public List<UserWeb> findByUsername(String chars);
    //public List<User>
}    