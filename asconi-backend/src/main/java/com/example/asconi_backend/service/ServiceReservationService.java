package com.example.asconi_backend.service;
import com.example.asconi_backend.model.RestaurantReservation;
import com.example.asconi_backend.model.ServiceReservation;
import com.example.asconi_backend.model.TouristicService;
import com.example.asconi_backend.repository.ReservationServiceRepository;
import com.example.asconi_backend.repository.TouristicServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceReservationService {
    private final ReservationServiceRepository serviceReservationRepository;
    private final TouristicServiceRepository touristicServiceRepository;
    private final ReservationServiceRepository reservationServiceRepository;

    @Autowired
    public ServiceReservationService(ReservationServiceRepository serviceReservationRepository, TouristicServiceRepository touristicServiceRepository, ReservationServiceRepository reservationServiceRepository) {
        this.serviceReservationRepository = serviceReservationRepository;
        this.touristicServiceRepository = touristicServiceRepository;
        this.reservationServiceRepository = reservationServiceRepository;
    }
    // verificare disponiblitate serviciu
    public boolean checkAvailability(Integer serviceId, Integer nrPeople, LocalDate date, LocalTime hour) {
        List<Object[]> availableService = serviceReservationRepository.findAvailableServices(serviceId, date, hour, nrPeople);
        return !availableService.isEmpty();
    }
    public ServiceReservation reserveService(Integer serviceId, Integer nrPeople,
                                             String nameSurname, String email, String phone,
                                             LocalDate date, LocalTime hour, String language, String specifications) {

        TouristicService touristicService = touristicServiceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service with ID " + serviceId + " not found"));

        touristicServiceRepository.addServiceIndisponibility(serviceId,date,hour,nrPeople);
        ServiceReservation serviceReservation = new ServiceReservation();
        serviceReservation.setTouristicService(touristicService);
        serviceReservation.setNrPeople(nrPeople);
        serviceReservation.setNameSurname(nameSurname);
        serviceReservation.setEmail(email);
        serviceReservation.setPhone(phone);
        serviceReservation.setDate(date);
        serviceReservation.setHour(hour);
        serviceReservation.setLanguage(language);
        serviceReservation.setSpecifications(specifications);
        return serviceReservationRepository.save(serviceReservation);
    }
    public List<ServiceReservation> getReservationsByDate(LocalDate date) {
        System.out.println("Filtrare rezervări pentru data: " + date);
        if (date != null) {
            return reservationServiceRepository.findByDate(date);
        }
        return reservationServiceRepository.findAll();
    }
}
