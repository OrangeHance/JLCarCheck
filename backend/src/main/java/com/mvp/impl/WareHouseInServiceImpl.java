package com.mvp.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mvp.common.Result;
import com.mvp.dto.WareHouseInDTO;
import com.mvp.dto.WareHouseOutDTO;
import com.mvp.mapper.WareHouseInMapper;
import com.mvp.service.WareHouseInService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WareHouseInServiceImpl implements WareHouseInService {

    @Autowired
    WareHouseInMapper wareHouseInMapper;
    @Override
    public PageInfo<WareHouseInDTO> wareHouseSearch(WareHouseInDTO wareHouseInDTO) {
        PageHelper.startPage(wareHouseInDTO.getPageNum(), wareHouseInDTO.getPageSize());
        List<WareHouseInDTO> dtos = wareHouseInMapper.wareHouseSearch(wareHouseInDTO);
        int total = wareHouseInMapper.countWareHouse(wareHouseInDTO);
        for (WareHouseInDTO dto : dtos) {
            dto.setTotal(total);
        }
        PageHelper.clearPage();
        return new PageInfo<>(dtos);
    }

    @Override
    public void wareHouseUpdate(WareHouseInDTO wareHouseInDTO) {
        wareHouseInMapper.updateGoods(wareHouseInDTO);
    }

    @Override
    public Result wareHouseSave(WareHouseInDTO wareHouseInDTO) {
        //校验参数
        if(wareHouseInDTO!=null){
            if(StringUtils.isNotEmpty(wareHouseInDTO.getGoodsCode())){
                WareHouseInDTO dto = wareHouseInMapper.getGoodsByCode(wareHouseInDTO.getGoodsCode());
                if (dto!=null){
                    return Result.fail(500, "该商品编码已存在");
                }
            }
        }
        //库存管理表存储数据
        wareHouseInMapper.saveGoods(wareHouseInDTO);
        //入库表存储数据
        wareHouseInMapper.saveInGoods(wareHouseInDTO);
        return Result.success();
    }

    @Override
    public List<WareHouseInDTO> getGoodsOptions() {
        return wareHouseInMapper.getGoodsOptions();
    }

    @Override
    public Result wareHouseOut(WareHouseOutDTO outDTO) {
        //校验参数不能为空

        if(StringUtils.isEmpty(outDTO.getGoodsCode())){
            return Result.fail(500, "商品编码为空");
        }
        if (outDTO.getOutQuantity() == 0) {
            return Result.fail(500, "出库数量为空");
        }
        if(StringUtils.isEmpty(outDTO.getOutDate().toString())){
            return Result.fail(500, "出库日期为空");
        }
        //根据商品编码获取
        if(null != outDTO && outDTO.getOutQuantity() >0){
            // 判断：数量必须是正整数（大于0）
            if (outDTO.getOutQuantity() <= 0) {
                return Result.fail(500, "出库数量必须是正整数");
            }
            WareHouseInDTO goodDto = wareHouseInMapper.getGoodsByCode(StringUtils.isNotEmpty(outDTO.getGoodsCode()) ? outDTO.getGoodsCode() : null);
            if(goodDto.getQuantity()< outDTO.getOutQuantity()){
                return Result.fail(500, "库存不足，当前库存为"+goodDto.getQuantity());
            }
            //减少原有库存
            goodDto.setQuantity(goodDto.getQuantity() - outDTO.getOutQuantity());
            wareHouseInMapper.updateQuantity(goodDto);
            //出库记录入表
            wareHouseInMapper.saveOutWarehouse(outDTO);
        }
        return Result.success();
    }

    @Override
    public PageInfo<WareHouseInDTO> wareHouseInSearch(WareHouseInDTO wareHouseInDTO) {
        PageHelper.startPage(wareHouseInDTO.getPageNum(), wareHouseInDTO.getPageSize());
        List<WareHouseInDTO> dtos = wareHouseInMapper.wareHouseInSearch(wareHouseInDTO);
        int total = wareHouseInMapper.countWareHouseIn(wareHouseInDTO);
        for (WareHouseInDTO dto : dtos) {
            dto.setTotal(total);
        }
        PageHelper.clearPage();
        return new PageInfo<>(dtos);
    }

    @Override
    public PageInfo<WareHouseOutDTO> wareHouseOutSearch(WareHouseOutDTO wareHouseOutDTO) {
        PageHelper.startPage(wareHouseOutDTO.getPageNum(), wareHouseOutDTO.getPageSize());
        List<WareHouseOutDTO> dtos = wareHouseInMapper.wareHouseOutSearch(wareHouseOutDTO);
        int total = wareHouseInMapper.countWareHouseOut(wareHouseOutDTO);
        for (WareHouseOutDTO dto : dtos) {
            dto.setTotal(total);
        }
        PageHelper.clearPage();
        return new PageInfo<>(dtos);
    }


}
