package com.ably.demo.draw;

import com.ably.demo.draw.dto.DrawerCreateDto;
import com.ably.demo.draw.dto.DrawerResponseDto;
import com.ably.demo.exception.BusinessException;
import com.ably.demo.user.User;
import com.ably.demo.user.UserRepository;
import com.ably.demo.user.dto.UserRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class DrawerServiceTest {

    @Autowired
    DrawerRepository drawerRepository;

    @Autowired
    DrawerService drawerService;

    @Autowired
    UserRepository userRepository;

    User savedUser;

    @BeforeEach
    void setUp() {
        User aaa = User.createUser(new UserRequestDto("aaa@a-bly.com", "1234", "aaa"));
        savedUser = userRepository.save(aaa);

        drawerRepository.save(Drawer.createDrawer("a-bly-2", savedUser));
    }

    @AfterEach
    void clear() {
        drawerRepository.deleteAll();
        userRepository.deleteAll();

    }

    @Test
    @DisplayName("서랍이 정상적으로 생성")
    void createDrawer() {
        DrawerCreateDto drawerCreateDto = new DrawerCreateDto("a-bly-1", savedUser.getId());
        Long drawerId = drawerService.createDrawer(drawerCreateDto);

        Drawer drawer = drawerRepository.findById(drawerId).get();
        assertThat(drawer.getName()).isEqualTo("a-bly-1");
        assertThat(drawer.getUser().getEmail()).isEqualTo(savedUser.getEmail());
    }

    @Test
    @DisplayName("서랍의 이름이 중복될 경우 에러발생")
    void createDrawer_duplicate_name() {
        DrawerCreateDto drawerCreateDto = new DrawerCreateDto("a-bly-2", savedUser.getId());
        assertThatThrownBy(() -> drawerService.createDrawer(drawerCreateDto))
                .isInstanceOf(BusinessException.class);

    }

    @Test
    @DisplayName("유저가 가진 drawerList 조회")
    void selectDrawer() {
        drawerRepository.save(Drawer.createDrawer("a-bly-3", savedUser));
        List<DrawerResponseDto> drawers = drawerService.getDrawers(savedUser.getId(), 0, 20);
        assertThat(drawers.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("유저가 가진 drawer 삭제")
    void deleteDrawer() {
        Drawer savedDrawer = drawerRepository.save(Drawer.createDrawer("a-bly-3", savedUser));

        drawerService.deleteDrawer(savedDrawer.getId(), savedUser.getId());
        assertThat(drawerRepository.findByIdAndUser(savedDrawer.getId(), savedUser)).isNull();
    }
}