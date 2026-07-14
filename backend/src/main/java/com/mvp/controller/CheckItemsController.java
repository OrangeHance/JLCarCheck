package com.mvp.controller;

import com.mvp.common.Result;
import com.mvp.dto.CarCheckItems;
import com.mvp.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkItem")
public class CheckItemsController {


    @Autowired
    CheckItemService checkItemService;


    /**
     * 根据岗位查询配置的检查项目
     * @param job 岗位
     * @return
     */
    @GetMapping("/items")
    public Result<List<CarCheckItems>> carCheckSearch(@RequestParam String job) {
        List<CarCheckItems> items =   checkItemService.searchItemByJob(job);
        return Result.success(items);
    }
}
