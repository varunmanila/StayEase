package com.example.StayEase;

import com.example.StayEase.Entity.Hotel;
import com.example.StayEase.Entity.HotelDto;
import com.example.StayEase.Entity.Role;
import com.example.StayEase.Entity.User;
import com.example.StayEase.Repocitory.HotelsRepocitory;
import com.example.StayEase.Repocitory.UserRepository;
import com.example.StayEase.Service.AuthService;
import com.example.StayEase.Service.HotelsService;
import com.example.StayEase.request.RegisterRequest;
import com.example.StayEase.response.AuthResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class HotelsTest {

    @Autowired
    UserRepository usersRepocitory;
    @Autowired
    HotelsRepocitory hotelsRepocitory;
    @Autowired
    AuthService authService;
    @Autowired
    HotelsService hotelsService;
    @Test
    void testRegisterUser() {
        RegisterRequest request=new RegisterRequest();
        request.setFirst_name("Ram");
        request.setLast_name("Ram");
        request.setEmail("ram@gmail.com");
        request.setPassword("ram@1234");
        request.setName("Rk");
        AuthResponse response =authService.register(request);
        assertNotNull(response);
        assertEquals("Success", response.getMessage());
    }
    @Test
    void testAddHotels() {
        Hotel hotel=new Hotel();
        hotel.setName("Taj");
        hotel.setLocation("Bangalore");
        hotel.setDescription("Taj is One of the best Hotel");
        hotel.setAvailable_rooms(100);
        Hotel books1 =hotelsService.saveHotel(hotel);
        assertNotNull(books1);
        assertEquals("Taj", books1.getName());
    }

    @Test
    void testBookHotels() {
     List<Hotel> hotelList=hotelsRepocitory.findAll();
     List<User>userList=usersRepocitory.findAll().stream().filter(i->i.getRole().equals(Role.CUSTOMER)).toList();
     Hotel registered=   hotelsService.bookHotel(userList.get(0),hotelList.get(0));
        assertEquals(hotelList.get(0).getName(), registered.getName());
    }
}
