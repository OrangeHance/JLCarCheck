package com.mvp.dto;

import lombok.Data;
import java.util.List;

@Data
public class TrackApiResp {
    private String code;
    private List<TrackDataItem> data;
}

