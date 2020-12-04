package com.example.demo.service;

import com.example.demo.model.Reservation;
import com.example.demo.model.Ticket;
import com.example.demo.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    public Reservation saveReservation(Reservation reservation) { return reservationRepository.save(reservation); }

    public Optional<Reservation> getReservation (Integer id) { return reservationRepository.findById(id); }

    public Iterable<Reservation> findAllById (HashSet<Integer> ids) { return reservationRepository.findAllById(ids); }

    public void deleteReservation (Integer id) { reservationRepository.deleteById(id); }

    public void deleteAllReservation(Iterable<Reservation> reservations)
    {
        reservationRepository.deleteAll(reservations);
    }

    public Collection<Reservation> getReservationsByClientId (Integer id)
    {
        Collection<Reservation> reservations = reservationRepository.findReservationByClient_Id(id);
        for (Reservation reservation : reservations)
        {
            Double finalPrice = calculateFinalPrice(reservation.getTickets());
            reservation.setFinalPrice(finalPrice);
        }
        return reservations;
    }

    public Double calculateFinalPrice (List<Ticket> tickets)
    {
        Double finalPrice = 0d;
        for (Ticket ticket : tickets)
        {
            finalPrice += ticket.getPrice();
        }
        return finalPrice;
    }
}
