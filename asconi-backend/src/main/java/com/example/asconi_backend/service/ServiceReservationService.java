package com.example.asconi_backend.service;
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
    @Autowired
    private ReservationServiceRepository serviceReservationRepository;
    @Autowired
    private TouristicServiceRepository touristicServiceRepository;

    // verificare disponiblitate serviciu
    public boolean checkAvailability(Integer serviceId, Integer nrPeople, LocalDate date, LocalTime hour) {
        List<Object[]> availableService = serviceReservationRepository.findAvailableServices(serviceId, date, hour, nrPeople);
        return !availableService.isEmpty();
    }

    public ServiceReservation reserveService(Integer serviceId, Integer nrPeople,
                                             String nameSurname, String email, String phone,
                                             LocalDate date, LocalTime hour, String language, String specifications) {

        if(!checkAvailability(serviceId, nrPeople, date, hour)) {
            throw new RuntimeException("Il n'y a pas de places disponibles");
        }
        Optional<TouristicService> serviceOpt = touristicServiceRepository.findById(serviceId);
        TouristicService service = serviceOpt.get();


        touristicServiceRepository.addServiceIndisponibility(serviceId,date,hour,nrPeople);

        ServiceReservation serviceReservation = new ServiceReservation();
        serviceReservation.setTouristicService(service);
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
}
