package com.homework.demo.wish;

import com.homework.demo.draw.Drawer;
import com.homework.demo.draw.DrawerRepository;
import com.homework.demo.exception.BusinessException;
import com.homework.demo.exception.ErrorCode;
import com.homework.demo.exception.UserNotFoundException;
import com.homework.demo.product.Product;
import com.homework.demo.product.ProductDto;
import com.homework.demo.product.ProductRepository;
import com.homework.demo.user.User;
import com.homework.demo.user.UserRepository;
import com.homework.demo.wish.dto.WishRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WishService {

    private final WishRepository wishRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final DrawerRepository drawerRepository;

    public Long wish(WishRequestDto wishRequestDto) {
        Product product = productRepository.findById(wishRequestDto.getProductId())
                .orElseThrow(() -> new BusinessException(ErrorCode.REQUEST_INVALID_R002));

        User user = userRepository.findById(wishRequestDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.REQUEST_INVALID_R003));

        validateDrawer(wishRequestDto, user);

        Drawer drawer = drawerRepository.findByIdAndUser(wishRequestDto.getDrawerId(), user);
        Wish wish = Wish.createWish(product, user, drawer);
        return wishRepository.save(wish).getId();
    }

    private void validateDrawer(WishRequestDto wishRequestDto, User user) {
        List<Drawer> drawers = drawerRepository.findAllByUser(user);

        // 찜한 상품이 내 다른 찜 서랍에 있을경우
        long count = drawers.stream()
                .flatMap(d -> d.getWishes().stream())
                .filter(wish -> wish.getProduct().getId() == wishRequestDto.getProductId())
                .count();

        if (count > 0) {
            throw new BusinessException(ErrorCode.REQUEST_INVALID_R006);
        }

        // 찜 서랍이 없을경우
        if (drawers.size() == 0) {
            throw new BusinessException(ErrorCode.REQUEST_INVALID_R004);
        }
    }

    public void release(Long id) {
        wishRepository.delete(wishRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.REQUEST_INVALID_R007)));
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getWishesProduct(Long userId, Long drawerId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending());
        return wishRepository.findProductDtosByUserIdAndDrawerId(userId, drawerId, pageRequest);
    }
}
