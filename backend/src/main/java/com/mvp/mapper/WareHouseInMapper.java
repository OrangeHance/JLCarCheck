package com.mvp.mapper;

import com.mvp.dto.WareHouseInDTO;
import com.mvp.dto.WareHouseOutDTO;
import com.mvp.entity.WareHouseIn;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WareHouseInMapper {

    List<WareHouseInDTO> wareHouseSearch(WareHouseInDTO wareHouseInDTO);

    int countWareHouse(WareHouseInDTO wareHouseInDTO);
    void updateGoods(WareHouseInDTO wareHouseInDTO);
    void saveGoods(WareHouseInDTO wareHouseInDTO);

    void saveInGoods(WareHouseInDTO wareHouseInDTO);

    List<WareHouseInDTO> getGoodsOptions();
    WareHouseInDTO getGoodsByCode(String goodCode);
    void updateQuantity(WareHouseInDTO wareHouseInDTO);
    void  saveOutWarehouse(WareHouseOutDTO outDTO);

    List<WareHouseInDTO> wareHouseInSearch(WareHouseInDTO wareHouseInDTO);

    int countWareHouseIn(WareHouseInDTO wareHouseInDTO);

    List<WareHouseOutDTO> wareHouseOutSearch(WareHouseOutDTO wareHouseOutDTO);

    int countWareHouseOut(WareHouseOutDTO wareHouseOutDTO);

}
