package com.example.StayEase.Controller;

import com.example.StayEase.Entity.Hotel;
import com.example.StayEase.Entity.HotelDto;
import com.example.StayEase.Entity.User;
import com.example.StayEase.Repocitory.HotelsRepocitory;
import com.example.StayEase.Repocitory.UserRepository;
import com.example.StayEase.Service.HotelsService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/hotels")
public class HotelsController {
    @Autowired
    private HotelsService hotelsService;
    @Autowired
    private HotelsRepocitory hotelsRepocitory;
    @Autowired
    private UserRepository userRepository;
    Logger logger= LoggerFactory.getLogger(HotelsController.class);
    @PostMapping("/createHotel")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?>createHotel(@Valid @RequestBody Hotel hotel)
    {
        try {
           return new ResponseEntity<>( hotelsService.saveHotel(hotel), HttpStatus.CREATED);
        }catch (Exception e){
            logger.error(e.getMessage());
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/updateHotel")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?>updateHotel( @Valid @RequestBody Hotel hotel,@RequestParam("hotelId") Integer hotelId)
    {
        try {
            Optional<Hotel> hotel1= hotelsRepocitory.findById(hotelId);
            if(hotel1.isPresent()){
                return new ResponseEntity<>( hotelsService.updateHotel(hotel,hotel1.get()), HttpStatus.CREATED);
            }else{
                logger.error("Hotel is Not found With id"+hotelId);
                return new ResponseEntity<>("Hotel is Not found With id"+hotelId,HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
        }
    }
    @GetMapping("/list")
    public ResponseEntity<?>getHotelLiat(){
        try {
            return new ResponseEntity<>(hotelsService.getHotels(),HttpStatus.OK);
        }catch (Exception e){
            logger.error(e.getMessage());
           return new ResponseEntity<>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
        }
    }
    @PostMapping("/book")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<?>bookHotels(@RequestBody HotelDto dto){
      Optional<Hotel> hotel= hotelsRepocitory.findById(dto.getHotelsId());
      Optional<User>user=userRepository.findById(dto.getUserId());
      if(!user.isPresent()){
          logger.error("No User Found with id "+dto.getUserId());
          return new ResponseEntity<>("User is Not found With id"+dto.getHotelsId(),HttpStatus.NOT_FOUND);
      }
      if(!hotel.isPresent()){
          logger.error("Hotel is Not found With id"+dto.getHotelsId());
          return new ResponseEntity<>("Hotel is Not found With id"+dto.getHotelsId(),HttpStatus.NOT_FOUND);
      }
      if(hotel.get().getAvailable_rooms()<1){
          logger.error("Hotel is Full ");
          return new ResponseEntity<>("Hotel is Full",HttpStatus.NOT_ACCEPTABLE);
      }
      return new ResponseEntity<>(hotelsService.bookHotel(user.get(),hotel.get()),HttpStatus.OK);
    }
    @PostMapping("/cancel")
    @PreAuthorize("hasAuthority('HOTEL_MANAGER')")
    public ResponseEntity<?>cancelHotels(@RequestBody HotelDto dto){
        Optional<Hotel> hotel= hotelsRepocitory.findById(dto.getHotelsId());
        Optional<User>user=userRepository.findById(dto.getUserId());
        if(!user.isPresent()){   logger.error("No User Found with id "+dto.getUserId());
            return new ResponseEntity<>("User is Not found With id"+dto.getHotelsId(),HttpStatus.NOT_FOUND);
        }
        if(!hotel.isPresent()){
            logger.error("Hotel is Not found With id"+dto.getHotelsId());
            return new ResponseEntity<>("Hotel is Not found With id"+dto.getHotelsId(),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(hotelsService.cancelHotel(user.get(),hotel.get()),HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?>deleteHotels( @PathVariable Integer id){
        Optional<Hotel> hotel= hotelsRepocitory.findById(id);
        if(!hotel.isPresent()){
            logger.error("Hotel is Not found With id"+id);
            return new ResponseEntity<>("Hotel is Not found With id"+id,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(hotelsService.deleteHotel(hotel.get()),HttpStatus.OK);
    }


}
