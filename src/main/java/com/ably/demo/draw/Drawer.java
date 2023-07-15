package com.ably.demo.draw;

import com.ably.demo.common.BaseEntity;
import com.ably.demo.user.User;
import com.ably.demo.wish.Wish;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter(value = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Drawer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drawer_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static Drawer createDrawer(String drawerName, User user) {
        Drawer drawer = new Drawer();
        drawer.setName(drawerName);
        drawer.setUser(user);
        return drawer;
    }

    @OneToMany(mappedBy = "drawer")
    private List<Wish> wishes = new ArrayList<>();
}