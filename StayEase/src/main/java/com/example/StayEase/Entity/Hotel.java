package com.example.StayEase.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Table(name = "tblhotel")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotel_id")
    private Integer id;
    private String name;
    private String location;
    private String description;
    @NotNull(message = "Available rooms cannot be null")
    @Min(value = 1, message = "Available rooms must be at least 1")
    @Max(value = 1000, message = "Available rooms cannot exceed 1000")
    private Integer available_rooms;
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();
}
