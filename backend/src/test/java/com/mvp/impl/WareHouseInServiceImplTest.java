package com.mvp.impl;

import com.github.pagehelper.PageInfo;
import com.mvp.common.Result;
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

public class WareHouseInServiceImplTest {

    @Mock
    private WareHouseInMapper wareHouseInMapper;

    @InjectMocks
    private WareHouseInServiceImpl wareHouseInServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     public void list() {
        WareHouseInDTO dto1 = new WareHouseInDTO();
        dto1.setGoodsCode("CODE1");
        dto1.setGoodsName("Goods 1");

        WareHouseInDTO dto2 = new WareHouseInDTO();
        dto2.setGoodsCode("CODE2");
        dto2.setGoodsName("Goods 2");

        List<WareHouseInDTO> expectedList = Arrays.asList(dto1, dto2);

        when(wareHouseInMapper.getGoodsOptions()).thenReturn(expectedList);

        List<WareHouseInDTO> result = wareHouseInServiceImpl.getGoodsOptions();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void goodsCodeIsEmpty() {
        WareHouseOutDTO outDTO = new WareHouseOutDTO();
        outDTO.setOutQuantity(10);
        outDTO.setOutDate(LocalDate.now());

        Result result = wareHouseInServiceImpl.wareHouseOut(outDTO);

        assertNotNull(result);
        assertEquals(500, result.getCode());
        assertEquals("商品编码为空", result.getMessage());
    }

    @Test
    public void outQuantityIsZero() {
        WareHouseOutDTO outDTO = new WareHouseOutDTO();
        outDTO.setGoodsCode("CODE1");
        outDTO.setOutQuantity(0);
        outDTO.setOutDate(LocalDate.now());

        Result result = wareHouseInServiceImpl.wareHouseOut(outDTO);

        assertNotNull(result);
        assertEquals(500, result.getCode());
        assertEquals("出库数量为空", result.getMessage());
    }

    @Test
    public void stockIsSufficient() {
        WareHouseOutDTO outDTO = new WareHouseOutDTO();
        outDTO.setGoodsCode("CODE1");
        outDTO.setOutQuantity(5);
        outDTO.setOutDate(LocalDate.now());

        WareHouseInDTO goodsDto = new WareHouseInDTO();
        goodsDto.setGoodsCode("CODE1");
        goodsDto.setQuantity(10);

        when(wareHouseInMapper.getGoodsByCode("CODE1")).thenReturn(goodsDto);

        Result result = wareHouseInServiceImpl.wareHouseOut(outDTO);

        assertNotNull(result);
        assertEquals(200, result.getCode());
        assertEquals(5, goodsDto.getQuantity());
        verify(wareHouseInMapper, times(1)).updateQuantity(goodsDto);
        verify(wareHouseInMapper, times(1)).saveOutWarehouse(outDTO);
    }

    @Test
    public void stockIsInsufficient() {
        WareHouseOutDTO outDTO = new WareHouseOutDTO();
        outDTO.setGoodsCode("CODE1");
        outDTO.setOutQuantity(20);
        outDTO.setOutDate(LocalDate.now());

        WareHouseInDTO goodsDto = new WareHouseInDTO();
        goodsDto.setGoodsCode("CODE1");
        goodsDto.setQuantity(10);

        when(wareHouseInMapper.getGoodsByCode("CODE1")).thenReturn(goodsDto);

        Result result = wareHouseInServiceImpl.wareHouseOut(outDTO);

        assertNotNull(result);
        assertEquals(500, result.getCode());
        assertTrue(result.getMessage().contains("库存不足"));
    }

    @Test
    public void pageInfo() {
        WareHouseInDTO searchDTO = new WareHouseInDTO();
        searchDTO.setPageNum(1);
        searchDTO.setPageSize(10);

        WareHouseInDTO dto1 = new WareHouseInDTO();
        dto1.setId(1);

        List<WareHouseInDTO> dtoList = Arrays.asList(dto1);

        when(wareHouseInMapper.wareHouseInSearch(searchDTO)).thenReturn(dtoList);
        when(wareHouseInMapper.countWareHouseIn(searchDTO)).thenReturn(1);

        PageInfo<WareHouseInDTO> result = wareHouseInServiceImpl.wareHouseInSearch(searchDTO);

        assertNotNull(result);
        assertEquals(1, result.getList().size());
    }
}