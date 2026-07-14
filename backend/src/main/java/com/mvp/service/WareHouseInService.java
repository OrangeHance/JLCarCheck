package com.mvp.service;

import com.github.pagehelper.PageInfo;
import com.mvp.common.Result;
import com.mvp.dto.LoginRequestDTO;
import com.mvp.dto.LoginResponseDTO;
import com.mvp.dto.WareHouseInDTO;
import com.mvp.dto.WareHouseOutDTO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public interface WareHouseInService {

    PageInfo<WareHouseInDTO>  wareHouseSearch(WareHouseInDTO wareHouseInDTO);
    void wareHouseUpdate (WareHouseInDTO wareHouseInDTO);
    Result wareHouseSave (WareHouseInDTO wareHouseInDTO);
    List<WareHouseInDTO> getGoodsOptions();
    Result wareHouseOut(WareHouseOutDTO outDTO);
    PageInfo<WareHouseInDTO>  wareHouseInSearch(WareHouseInDTO wareHouseInDTO);
    PageInfo<WareHouseOutDTO>  wareHouseOutSearch(WareHouseOutDTO wareHouseInDTO);



}
