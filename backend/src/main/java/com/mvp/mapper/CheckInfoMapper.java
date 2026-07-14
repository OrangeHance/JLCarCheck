package com.mvp.mapper;

import com.mvp.dto.CarCheckItems;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CheckInfoMapper {

    List<CarCheckItems>  selectItemsByJob(String job);
}
