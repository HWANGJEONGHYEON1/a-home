package com.ably.demo.draw;

import com.ably.demo.draw.dto.DrawerResponseDetailDto;
import com.ably.demo.draw.dto.DrawerCreateDto;
import com.ably.demo.draw.dto.DrawerResponseDto;
import com.ably.demo.exception.BusinessException;
import com.ably.demo.exception.ErrorCode;
import com.ably.demo.product.ProductDto;
import com.ably.demo.user.User;
import com.ably.demo.user.UserService;
import com.ably.demo.wish.WishService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DrawerService {

    private final DrawerRepository drawerRepository;
    private final UserService userService;
    private final WishService wishService;

    public Long createDrawer(DrawerCreateDto drawerCreateDto) {
        String drawerName = drawerCreateDto.getDrawerName();
        User user = userService.selectUserById(drawerCreateDto.getUserId());
        Drawer findDrawer = drawerRepository.findByNameAndUser(drawerName, user);

        if (Objects.nonNull(findDrawer)) {
            throw new BusinessException(ErrorCode.REQUEST_INVALID_R005);
        }

        Drawer drawer = Drawer.createDrawer(drawerName, user);
        return drawerRepository.save(drawer).getId();
    }

    @Transactional(readOnly = true)
    public List<DrawerResponseDto> getDrawers(Long userId, int page, int size) {
        User user = userService.selectUserById(userId);
        // id 기준으로 최신순 조회
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending());
        List<Drawer> drawers = drawerRepository.findByUser(user, pageRequest);

        if (drawers.size() == 0) {
            throw new BusinessException(ErrorCode.REQUEST_INVALID_R004);
        }

        return drawers.stream()
                .map(drawer -> new DrawerResponseDto(drawer.getName()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Drawer getDrawer(Long userId, Long drawerId) {
        User user = userService.selectUserById(userId);
        return drawerRepository.findByIdAndUser(drawerId, user);
    }

    public void deleteDrawer(Long drawerId, Long userId) {
        Drawer drawer = getDrawer(userId, drawerId);
        drawerRepository.delete(drawer);
    }

    @Transactional(readOnly = true)
    public DrawerResponseDetailDto getDrawerDetail(Long drawerId, Long userId, int page, int size) {
        Drawer drawer = getDrawer(userId, drawerId);
        User user = userService.selectUserById(userId);
        List<ProductDto> wishesProduct = wishService.getWishesProduct(user.getId(), drawer.getId(), page, size);
        return DrawerResponseDetailDto.of()
                .drawerName(drawer.getName())
                .wishCount(wishesProduct.size())
                .productDto(wishesProduct)
                .build();
    }
}
