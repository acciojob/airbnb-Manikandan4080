package com.driver.Repository;

import com.driver.model.Booking;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class HotelManagementRepository {
    Map<String, Hotel> hotelMap = new HashMap<>();
    Map<Integer, User> userMap = new HashMap<>();
    Map<String, Booking> bookingMap = new HashMap<>();
    Map<Integer, Integer> countOfBooking = new HashMap<>();
    public String addHotel(Hotel hotel){

        if(hotel == null || hotel.getHotelName() == null) return "FAILURE";
        if(hotelMap.containsKey(hotel.getHotelName())) return "FAILURE";

        hotelMap.put(hotel.getHotelName(), hotel);
        return "SUCCESS";
    }

    public Integer addUser(User user) {
        userMap.put(user.getaadharCardNo(), user);
        return user.getaadharCardNo();
    }

    public List<Hotel> getHotels(){
        List<Hotel> hotelList = new ArrayList<>();

        for(String hotelName : hotelMap.keySet()){
            hotelList.add(hotelMap.get(hotelName));
        }
        return hotelList;
    }

    public Hotel getHotelByName(String name) {
        return hotelMap.get(name);
    }

    public void bookRoom(Booking booking, Hotel hotel) {
        bookingMap.put(booking.getBookingId(), booking);
        hotelMap.put(hotel.getHotelName(), hotel);

        int personId = booking.getBookingAadharCard();
        Integer numberOfBooking = countOfBooking.getOrDefault(personId, 0);
        countOfBooking.put(personId, numberOfBooking+1);
    }

    public int getBookings(Integer aadharCard) {
        return countOfBooking.getOrDefault(aadharCard, 0);
    }

    public void updateFacilities(String hotelName) {
        Hotel hotel = hotelMap.get(hotelName);
        hotelMap.put(hotelName, hotel);
    }
}
