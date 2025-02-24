package com.example.asconi_backend.service;

import com.example.asconi_backend.model.RestaurantReservation;
import com.example.asconi_backend.model.Hall;
import com.example.asconi_backend.repository.RestaurantReservationRepository;
import com.example.asconi_backend.repository.HallRepository;
import com.example.asconi_backend.repository.HallTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantReservationService {

    @Autowired
    private HallTableRepository hallTableRepository;

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private RestaurantReservationRepository reservationRestaurantRepository;

    public Optional<Object[]> getFirstAvailableTable( Integer nrPeople, Integer hallId, LocalDate date, LocalTime arrivingTime, LocalTime leavingTime) {
        LocalTime arrivingTime1 = arrivingTime.plusMinutes(1);
        LocalTime leavingTime1 = leavingTime.minusMinutes(1);
        List<Object[]> availableTables = hallTableRepository.findAvailableTables(nrPeople, hallId, date, arrivingTime1, leavingTime1);
        return availableTables.stream().findFirst();
    }

    @Transactional
    public RestaurantReservation reserveTable(Integer nrPeople, Integer hallId, LocalDate date,
                                              LocalTime arrivingTime, LocalTime leavingTime,
                                              String nameSurname, String email, String phone, String specifications,
                                              Integer tableId) {

        Hall hall = hallRepository.findById(hallId)
                .orElseThrow(() -> new RuntimeException("Erreur"));

        //ocupare masa disponibila in intervalul dorit de utilizator
        hallTableRepository.addIndisponibility(tableId, date, arrivingTime, leavingTime);

        RestaurantReservation reservation = new RestaurantReservation();
        reservation.setHall(hall);
        reservation.setNameSurname(nameSurname);
        reservation.setEmail(email);
        reservation.setPhone(phone);
        reservation.setDate(date);
        reservation.setArrivingTime(arrivingTime);
        reservation.setLeavingTime(leavingTime);
        reservation.setNrPeople(nrPeople);
        reservation.setSpecifications(specifications);
        reservation.setTableId(tableId);
        return reservationRestaurantRepository.save(reservation);
    }
    public void createReservation(RestaurantReservation reservation) {
        reservationRestaurantRepository.save(reservation);
    }

    public List<RestaurantReservation> getReservationsByDateAndHallId(LocalDate date,Integer hallId) {
        return reservationRestaurantRepository.findByDateAndHallId(date,hallId);
    }
    public List<RestaurantReservation> getAllReservations() {
        return reservationRestaurantRepository.findAll();
    }

    // Ștergerea unei rezervări
    public void deleteReservation(Integer id) {
        Optional<RestaurantReservation> restaurantReservation = reservationRestaurantRepository.findById(id);
        hallTableRepository.deleteIndisponibility(restaurantReservation.get().getTableId(), restaurantReservation.get().getDate(), restaurantReservation.get().getArrivingTime(), restaurantReservation.get().getLeavingTime());
        reservationRestaurantRepository.deleteById(id);
    }
    public List<RestaurantReservation> getReservationsByDate(LocalDate date) {
        System.out.println("Filtrare rezervări pentru data: " + date);
            return reservationRestaurantRepository.findByDate(date);
    }
    public List<RestaurantReservation> getReservationsByHall(Integer hallId) {
        System.out.println("Filtrare rezervări pentru sala: " + hallId);
        return reservationRestaurantRepository.findByHallId(hallId);
    }
}

