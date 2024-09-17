package com.example.hm.Respository;


import com.example.hm.DTO.Response.Roomdto;
import com.example.hm.Entity.RoomEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRespository extends JpaRepository<RoomEntity, Long>, JpaSpecificationExecutor<RoomEntity> {
    @Query(value = " select new com.example.hm.DTO.Response.Roomdto(r.numberRoom ,r.typeRoom ,r.price ,r.isAvailabile)  " +
            "   from RoomEntity r " +
            "   where( :numberRoom is null or r.numberRoom = :numberRoom) " +
            "   AND( :typeRoom IS NULL OR r.typeRoom ilike %:typeRoom% ) " +
            "   and( :price is null or r.price = :price) " +
            "   and( :isAvailabile is null or r.isAvailabile = :isAvailabile) " )
    Page<Roomdto> getPageRoom(@Param("numberRoom") Long numberRoom,
                                @Param("typeRoom") String typeRoom,
                                @Param("price") Long price,
                                @Param("isAvailabile") Boolean isAvailabile,
                                    Pageable pageable);
}
