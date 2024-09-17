package com.example.hm.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "booking_service", schema = "hm",catalog = "postgres")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "id_booking", nullable = false)
    private Long idBooking;
    @Column(name = "id_service", nullable = false)
    private Long idService;

}
