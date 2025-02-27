package com.example.StayEase.Service;

import com.example.StayEase.Entity.Hotel;
import com.example.StayEase.Entity.User;
import com.example.StayEase.Repocitory.HotelsRepocitory;
import com.example.StayEase.Repocitory.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class HotelsService {
    @Autowired
    private HotelsRepocitory hotelsRepocitory;
    @Autowired
    private UserRepository userRepository;
    Logger logger= LoggerFactory.getLogger(HotelsService.class);
    public Hotel saveHotel(Hotel hotel){
      return   hotelsRepocitory.save(hotel);
    }

    public List<Hotel> getHotels() {
        return hotelsRepocitory.findAll();
    }

    public Hotel bookHotel(User user, Hotel hotel) {
        hotel.setAvailable_rooms(hotel.getAvailable_rooms()-1);
        hotel.setUsers(Arrays.asList(user));
        user.setHotel(hotel);
        userRepository.save(user);
        logger.info("Hotel Is Bokked For Customer"+user.getUsername());
       return hotelsRepocitory.save(hotel);
    }

    public Object cancelHotel(User user, Hotel hotel) {
        hotel.setAvailable_rooms(hotel.getAvailable_rooms()+1);
        List<User>userList=hotel.getUsers();
        userList.removeIf(i->i.getId().equals(user.getId()));
        hotel.setUsers(userList);
        user.setHotel(null);
        userRepository.save(user);
        logger.info("Hotel Is Booking cancelled for User "+user.getUsername());
        return hotelsRepocitory.save(hotel);
    }

    public Object updateHotel(Hotel newhotel, Hotel oldhotel1) {
        oldhotel1.setName(newhotel.getName());
        oldhotel1.setLocation(newhotel.getLocation());
        oldhotel1.setDescription(newhotel.getDescription());
        logger.info("Hotel Is Updated");
        oldhotel1.setAvailable_rooms(newhotel.getAvailable_rooms());
      return   hotelsRepocitory.save(oldhotel1);
    }

    public Object deleteHotel(Hotel hotel) {
        logger.info("Hotel Is Deleted wih name"+hotel.getName());
        hotelsRepocitory.delete(hotel);
        return "Success";
    }
}
