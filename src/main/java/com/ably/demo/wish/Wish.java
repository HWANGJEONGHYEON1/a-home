package com.ably.demo.wish;

import com.ably.demo.common.BaseEntity;
import com.ably.demo.draw.Drawer;
import com.ably.demo.product.Product;
import com.ably.demo.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Wish extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drawer_id")
    private Drawer drawer;

    public static Wish createWish(Product product, User user, Drawer drawer) {
        Wish wish = new Wish();
        wish.setProduct(product);
        wish.setUser(user);
        wish.setDrawer(drawer);
        return wish;
    }

    public void setDrawer(Drawer drawer) {
        this.drawer = drawer;
        drawer.getWishes().add(this);
    }
}