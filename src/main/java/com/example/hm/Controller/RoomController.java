package com.example.hm.Controller;


import com.example.hm.DTO.Filter.RoomFilter;
import com.example.hm.DTO.Response.PageDataDto;
import com.example.hm.DTO.Response.Roomdto;
import com.example.hm.DTO.RoomCreateDto;
import com.example.hm.DTO.RoomDto;
import com.example.hm.Entity.RoomEntity;
import com.example.hm.Service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping("/list")
    List<RoomEntity> getRoomList() {
      return roomService.findAll();
    }
    @GetMapping("/{id}")
    Optional<RoomEntity>findRoomById(@PathVariable("id") Long id) {
        return roomService.findById(id);
    }

    @PostMapping("/addroom")
    RoomDto addRoom(@RequestBody RoomCreateDto roomCreateDto) {
        return roomService.add(roomCreateDto);
    }
    @PutMapping("/updateroom/{id}")
    RoomDto updateRoom(@PathVariable("id") Long id,@RequestBody RoomCreateDto roomCreateDto) {
        return roomService.update(id,roomCreateDto);
    }
    @DeleteMapping("/deleteroom/{id}")
    void deleteRoom(@PathVariable("id") Long id) {
        roomService.delete(id);
    }
    @GetMapping("/getlist")
    PageDataDto<Roomdto> filterProperty
            (@RequestParam (name = "numberRoom",required = false ) Long numberRoom,
             @RequestParam (name = "typeRoom",required = false ) String typeRoom,
             @RequestParam (name = "price",required = false ) Long price,
             @RequestParam(name = "isAvailibe", required = false) Boolean isAvailibe,
             @RequestParam(name = "idAccount",required = false ) Long idAccount,
             @RequestParam (name = "page",required = false, defaultValue = "1") Integer page,
             @RequestParam (name = "pageSize",required = false , defaultValue = "10") Integer pageSize
            )
    {
        RoomFilter roomFilter = RoomFilter.builder()
                .numberRoom(numberRoom)
                .typeRoom(typeRoom)
                .price(price)
                .available(isAvailibe)
                .idAccount(idAccount)
                .page(page)
                .pageSize(pageSize)
                .build();
        return roomService.getlistroom(roomFilter);
    }

    @GetMapping("/getlist/{id}")
    List<RoomDto> getRoomList(@PathVariable("id") Long id) {
     return roomService.getlist(id);
    }
}
