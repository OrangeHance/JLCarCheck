package com.mvp.service;

import com.mvp.dto.CarCheckItems;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public interface CheckItemService {

    List<CarCheckItems>  searchItemByJob(String job);


}
