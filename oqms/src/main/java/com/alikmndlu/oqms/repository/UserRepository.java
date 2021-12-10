package com.alikmndlu.oqms.repository;

import com.alikmndlu.oqms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query("from User u where u.name like %?1% and u.username like %?2%")
    List<User> searchUsers(String name, String username);

    @Query("from User u where u.username like %?1%")
    List<User> findAllByUsernameLike(String username);

    @Query("from User u where u.name like %?1%")
    List<User> findAllByNameLike(String name);
}
