package com.mvp.controller;

import com.github.pagehelper.PageInfo;
import com.mvp.common.Result;
import com.mvp.dto.WareHouseInDTO;
import com.mvp.dto.WareHouseOutDTO;
import com.mvp.service.WareHouseInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/WareHouseIn")
public class WareHouseInController {

    @Autowired
    WareHouseInService wareHouseInService;

    /**
     * 库存管理查询方法
     * @param wareHouseInDTO
     * @return
     */
    @PostMapping("/wareHouseSearch")
    public Result<PageInfo<WareHouseInDTO>> wareHouseSearch(@RequestBody WareHouseInDTO wareHouseInDTO) {
        return Result.success(wareHouseInService.wareHouseSearch(wareHouseInDTO));
    }

    /**
     * 库存管理更新操作
     * @param wareHouseInDTO
     * @return
     */
    @PostMapping("/wareHouseUpdate")
    public Result wareHouseUpdate(@RequestBody WareHouseInDTO wareHouseInDTO) {
        wareHouseInService.wareHouseUpdate(wareHouseInDTO);
        return Result.success();
    }

    /**
     * 入库保存功能
     * @param wareHouseInDTO
     * @return
     */
    @PostMapping("/wareHouseSave")
    public Result wareHouseSave(@RequestBody WareHouseInDTO wareHouseInDTO) {
        Result result = wareHouseInService.wareHouseSave(wareHouseInDTO);
        return result;
    }

    /**
     * 查询大于0的库存数据用于出库下拉框展示
     * @return
     */
    @PostMapping("/getGoodsOptions")
    public Result<List<WareHouseInDTO>> getGoodsOptions() {
        List<WareHouseInDTO> list = wareHouseInService.getGoodsOptions();
        return Result.success(list);
    }


    @PostMapping("/wareHouseOut")
    public Result wareHouseOut(@RequestBody WareHouseOutDTO outDTO) {
        Result result = wareHouseInService.wareHouseOut(outDTO);
        return result;
    }

    /**
     * 入库记录查询
     * @param wareHouseInDTO
     * @return
     */
    @PostMapping("/wareHouseInSearch")
    public Result<PageInfo<WareHouseInDTO>> wareHouseInSearch(@RequestBody WareHouseInDTO wareHouseInDTO) {
        return Result.success(wareHouseInService.wareHouseInSearch(wareHouseInDTO));
    }

    /**
     * 出库记录查询
     * @param wareHouseInDTO
     * @return
     */
    @PostMapping("/wareHouseOutSearch")
    public Result<PageInfo<WareHouseOutDTO>> wareHouseOutSearch(@RequestBody WareHouseOutDTO wareHouseInDTO) {
        return Result.success(wareHouseInService.wareHouseOutSearch(wareHouseInDTO));
    }
}
