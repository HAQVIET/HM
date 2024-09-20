package com.example.hm.Service;

import com.example.hm.DTO.Filter.RoomFilter;
import com.example.hm.DTO.Response.PageDataDto;
import com.example.hm.DTO.Response.Roomdto;
import com.example.hm.DTO.RoomCreateDto;
import com.example.hm.DTO.RoomDto;
import com.example.hm.DTO.helper.ResponseHelper;
import com.example.hm.Entity.AccountEntity;
import com.example.hm.Entity.RoomEntity;
import com.example.hm.Respository.AccountRepository;
import com.example.hm.Respository.RoomRespository;
import com.example.hm.handler_exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceimpl implements RoomService {
    @Autowired
    RoomRespository roomRespository;
    @Autowired
    AccountRepository accountRepository;


    @Override
    public List<RoomEntity> findAll() {
        return roomRespository.findAll();
    }

    @Override
    public Optional<RoomEntity> findById(Long id) {
        return roomRespository.findById(id);
    }

    @Override
    public RoomDto add(RoomCreateDto roomCreateDto) {
        if(roomCreateDto.getNumberRoom() == null|| roomCreateDto.getTypeRoom() == null){
            throw new CustomException("400","Number of room and Type of room is required");
        }
        if(roomCreateDto.getPrice() == null || roomCreateDto.getPrice() < 0){
            throw new CustomException("400","Price of room is required");
        }
        return new RoomDto(roomRespository.save(new RoomEntity(roomCreateDto)));
    }

    @Override
    public RoomDto update(Long id,RoomCreateDto roomCreateDto) {
    Optional<RoomEntity> roomEntity = roomRespository.findById(id);
    if(roomEntity.isEmpty()){
        throw new CustomException("404","Room not found");
    }
    RoomEntity room = roomEntity.get();
    room.setNumberRoom(roomCreateDto.getNumberRoom());
    room.setTypeRoom(roomCreateDto.getTypeRoom());
    room.setPrice(roomCreateDto.getPrice());
    roomRespository.save(room);
        return new RoomDto(room);
    }

    @Override
    public void delete(Long id) {
        roomRespository.deleteById(id);
    }
    @Override
    public PageDataDto<Roomdto> getlistroom(RoomFilter roomFilter) {
        Pageable pageable = PageRequest.of(roomFilter.getPage()-1, roomFilter.getPageSize());
        return ResponseHelper.convert2PageDataDto(roomRespository.getPageRoom(roomFilter.getNumberRoom(),roomFilter.getTypeRoom(),roomFilter.getPrice(),roomFilter.getAvailable(), roomFilter.getIdAccount(), pageable));

    }

    @Override
    public List<RoomDto> getlist(Long idAccount) {
        if(idAccount == null){
            throw new CustomException("400","Id of account is required");
        }
        Optional<AccountEntity> account = accountRepository.findById(idAccount);
        if(account.isEmpty()){
            throw new CustomException("404","Account not found");
        }
        return roomRespository.getListRoom(idAccount);
    }
}
