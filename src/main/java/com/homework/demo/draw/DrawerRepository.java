package com.homework.demo.draw;

import com.homework.demo.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrawerRepository extends JpaRepository<Drawer, Long> {

    List<Drawer> findByUser(User user, Pageable pageable);
    List<Drawer> findAllByUser(User user);
    Drawer findByNameAndUser(String name, User user);
    Drawer findByIdAndUser(Long id, User user);
}