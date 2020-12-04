package com.example.demo.repository;

import com.example.demo.model.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Integer> {

    Collection<Reservation> findReservationByClient_Id(Integer id);

    void deleteAll(Iterable<? extends Reservation> reservations);
}
