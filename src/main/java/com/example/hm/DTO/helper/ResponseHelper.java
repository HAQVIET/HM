package com.example.hm.DTO.helper;

import com.example.hm.DTO.Response.PageDataDto;
import org.springframework.data.domain.Page;

public class ResponseHelper {
    public static <T> PageDataDto<T> convert2PageDataDto (Page<T> pageData){
        return PageDataDto.<T>builder()
                .Data(pageData.getContent())
                .page(pageData.getNumber()+1)
                .totalPage(pageData.getTotalPages())
                .total(pageData.getNumberOfElements())
                .pageSize(pageData.getSize())
                .build();
    }
}
