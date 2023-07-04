package com.driver.Service;

import com.driver.Repository.HotelManagementRepository;
import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class HotelManagementService {

    HotelManagementRepository hotelManagementRepository = new HotelManagementRepository();
    public String addHotel(Hotel hotel) {
        return hotelManagementRepository.addHotel(hotel);
    }

    public Integer addUser(User user) {
        return hotelManagementRepository.addUser(user);
    }

    public String getHotelWithMostFacilities(){
        int facilities = 0;
        String hotelName = "";

        List<Hotel> hotelList = hotelManagementRepository.getHotels();
        for(Hotel hotel : hotelList){
            if(hotel.getFacilities().size() > facilities){
                facilities = hotel.getFacilities().size();
                hotelName = hotel.getHotelName();
            }
            else if (facilities == hotel.getFacilities().size()){
                if(hotel.getHotelName().compareTo(hotelName)<0){
                    hotelName = hotel.getHotelName();
                }
            }
        }

        return hotelName;
    }

    public int bookRoom(Booking booking) {
        String uid = UUID.randomUUID().toString();
        booking.setBookingId(uid);

        String name = booking.getHotelName();
        Hotel hotel = hotelManagementRepository.getHotelByName(name);

        if(hotel.getAvailableRooms() < booking.getNoOfRooms()){
            return -1;
        }

        int pricePerNight = hotel.getPricePerNight();
        int totalAmount = pricePerNight * booking.getNoOfRooms();

        booking.setAmountToBePaid(totalAmount);

        int newAvailable = hotel.getAvailableRooms()-booking.getNoOfRooms();
        hotel.setAvailableRooms(newAvailable);

        hotelManagementRepository.bookRoom(booking, hotel);


        return totalAmount;
    }

    public int getBookings(Integer aadharCard) {
        return hotelManagementRepository.getBookings(aadharCard);
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        Hotel hotel = hotelManagementRepository.getHotelByName(hotelName);
        List<Facility> old = hotel.getFacilities();

        for(Facility facility : newFacilities){
            if(!old.contains(facility)){
                old.add(facility);
            }
            else continue;
        }
        hotel.setFacilities(old);
        hotelManagementRepository.updateFacilities(hotelName);
        return hotel;
    }
}
