package com.example.hm.Service;


import com.example.hm.DTO.Filter.RoomFilter;
import com.example.hm.DTO.Response.PageDataDto;
import com.example.hm.DTO.Response.Roomdto;
import com.example.hm.DTO.RoomCreateDto;
import com.example.hm.DTO.RoomDto;
import com.example.hm.Entity.RoomEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    List<RoomEntity> findAll();
    Optional<RoomEntity> findById(Long id);
    RoomDto add(RoomCreateDto roomCreateDto);
    RoomDto update(Long id ,RoomCreateDto roomCreateDto);
    void delete(Long id);
    PageDataDto<Roomdto> getlistroom(RoomFilter roomFilter);
    List<RoomDto>getlist(Long idAccount);
}
