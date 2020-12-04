package com.example.demo.repository;

import com.example.demo.model.Ticket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Integer> {

    @Query(value = "SELECT * FROM TICKET WHERE reservation_id is NULL AND  event_id = ?1", nativeQuery = true)
    Collection<Ticket> findByReservationIsNull(Integer idEvent);

    @Query(value = "SELECT * FROM TICKET WHERE reservation_id is NULL AND  event_id = ?1 LIMIT ?2", nativeQuery = true)
    ArrayList<Ticket> findByReservationIsNull(Integer id, Integer quantity);

    Collection<Ticket> findAllByEvent_IdAndReservationIsNotNull (Integer id);

}
