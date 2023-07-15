package com.ably.demo.wish;

import com.ably.demo.draw.Drawer;
import com.ably.demo.product.ProductDto;
import com.ably.demo.user.User;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.List;

public interface WishRepository extends JpaRepository<Wish, Long> {
    String COMMENT = "org.hibernate.comment";
    @QueryHints(value = {@QueryHint(name = COMMENT, value = "WishRepository.findProductDtosByUserIdAndDrawerId")})
    @Query("SELECT NEW com.ably.demo.product.ProductDto("
            + "product.name, product.image.thumbnailUrl, product.price"
            + ") "
            + "FROM Wish wish "
            + "JOIN wish.user user "
            + "JOIN wish.product product "
            + "JOIN wish.drawer drawer "
            + "WHERE wish.user.id = :userId "
            + "AND wish.drawer.id = :drawerId "
            + "ORDER BY wish.id DESC")
    List<ProductDto> findProductDtosByUserIdAndDrawerId(
            Long userId, Long drawerId,
            Pageable pageable);
}
