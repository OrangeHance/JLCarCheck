package com.mvp.impl;

import com.mvp.dto.MenuDto;
import com.mvp.mapper.MenuMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MenuServiceImplTest {

    @Mock
    private MenuMapper menuMapper;

    @InjectMocks
    private MenuServiceImpl menuServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void MenuList() {
        MenuDto menu1 = new MenuDto();
        menu1.setId(1);
        menu1.setMenuName("Menu 1");
        menu1.setMenuUrl("/menu1");
        menu1.setCreateTime(LocalDate.now());

        MenuDto menu2 = new MenuDto();
        menu2.setId(2);
        menu2.setMenuName("Menu 2");
        menu2.setMenuUrl("/menu2");
        menu2.setCreateTime(LocalDate.now());

        List<MenuDto> expectedList = Arrays.asList(menu1, menu2);

        when(menuMapper.menuList()).thenReturn(expectedList);

        List<MenuDto> result = menuServiceImpl.menuList();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Menu 1", result.get(0).getMenuName());
        assertEquals("/menu2", result.get(1).getMenuUrl());
        verify(menuMapper, times(1)).menuList();
    }

    @Test
    public void NoMenus() {
        when(menuMapper.menuList()).thenReturn(Arrays.asList());

        List<MenuDto> result = menuServiceImpl.menuList();

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(menuMapper, times(1)).menuList();
    }
}