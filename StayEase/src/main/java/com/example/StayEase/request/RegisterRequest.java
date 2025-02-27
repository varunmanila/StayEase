package com.example.StayEase.request;

import com.example.StayEase.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String name;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private Role role;
    private Integer hotel_id;
}