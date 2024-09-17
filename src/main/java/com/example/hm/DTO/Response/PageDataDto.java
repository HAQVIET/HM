package com.example.hm.DTO.Response;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageDataDto<T> implements Serializable {

    private List<T> Data;

    private Integer page;

    private Integer pageSize;

    private Integer totalPage;

    private Integer total;
}