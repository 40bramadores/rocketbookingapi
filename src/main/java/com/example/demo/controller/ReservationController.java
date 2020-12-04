package com.example.demo.controller;

import com.example.demo.controller.modelRequest.ReservationCreationRequest;
import com.example.demo.controller.modelRequest.ReservationCreationSuccess;
import com.example.demo.model.Client;
import com.example.demo.model.Event;
import com.example.demo.model.Reservation;
import com.example.demo.model.Ticket;
import com.example.demo.service.ClientService;
import com.example.demo.service.EventService;
import com.example.demo.service.ReservationService;
import com.example.demo.service.TicketService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RequestMapping("api/${api.version}/reservation")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @Autowired
    TicketService ticketService;

    @Autowired
    ClientService clientService;

    @Autowired
    EventService eventService;

    @Autowired
    MessageSource messageSource;

    @PostMapping
    @ResponseBody
    public ResponseEntity createReservation (@Valid @NonNull @RequestBody ReservationCreationRequest reservationCreationRequest)
    {
        Optional<Event> eventFound = eventService.getEvent(reservationCreationRequest.getIdEvent());
        if (eventFound.isPresent())
        {
            if (ticketService.getAvailableTickets(reservationCreationRequest.getIdEvent()).size() >= reservationCreationRequest.getNumberOfTickets())
            {
                Client client = clientService.findByNameAndEmailEquals(reservationCreationRequest.getName(),
                                                                        reservationCreationRequest.getEmail())
                                                                        .orElse(null);
                if (client == null)
                {
                    client = clientService.saveClient(new Client(reservationCreationRequest.getName(),
                                                                    reservationCreationRequest.getEmail()));
                }

                Reservation reservation = new Reservation(client);

                ArrayList<Ticket> availableTickets = ticketService.getAvailableTickets(reservationCreationRequest.getIdEvent(),
                                                                                        reservationCreationRequest.getNumberOfTickets());
                Double finalPrice = reservationService.calculateFinalPrice(availableTickets);
                ReservationCreationSuccess reservationCreationSuccess = new ReservationCreationSuccess(finalPrice);

                for (Ticket ticket : availableTickets)
                {
                    ticket.setReservation(reservation);
                    ticketService.saveTicket(ticket);
                }

                reservationCreationSuccess.setTicketsReserved(availableTickets);
                reservationService.saveReservation(reservation);

                return new ResponseEntity(reservationCreationSuccess, HttpStatus.CREATED);
            }
            return ResponseEntity.badRequest()
                    .body(messageSource.getMessage("noTicketsAvailable", null, LocaleContextHolder.getLocale()));
        }

        return ResponseEntity.badRequest()
                .body(messageSource.getMessage("noEventFound", null, LocaleContextHolder.getLocale()));
    }

    @GetMapping(path = "{id}")
    @ResponseBody
    public ResponseEntity getReservation (@PathVariable("id") Integer id)
    {

        Optional<Reservation> reservationFound = reservationService.getReservation(id);
        if (reservationFound.isPresent())
        {
            Double finalPrice = reservationService.calculateFinalPrice(reservationFound.get().getTickets());
            reservationFound.get().setFinalPrice(finalPrice);
            return new ResponseEntity(reservationFound, HttpStatus.OK);
        }

        return ResponseEntity.badRequest()
                .body(messageSource.getMessage("noEventFound", null, LocaleContextHolder.getLocale()));
    }

    @GetMapping(path = "client{id}")
    @ResponseBody
    public ResponseEntity getReservationsByClientId (@PathVariable("id") Integer id)
    {
        Optional<Client> client = clientService.getClient(id);
        if (client.isPresent())
        {
            return ResponseEntity.ok(reservationService.getReservationsByClientId(id));
        }
        return ResponseEntity.badRequest().body(messageSource.getMessage("noClientFound", null, LocaleContextHolder.getLocale()));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteReservation (@PathVariable("id") Integer id)
    {
        Optional<Reservation> reservationFound = reservationService.getReservation(id);

        if (reservationFound.isPresent())
        {
            for (Ticket ticket : reservationFound.get().getTickets())
            {
                ticket.setReservation(null);
            }
            reservationService.deleteReservation(id);
            return new ResponseEntity(HttpStatus.OK);
        }
        return ResponseEntity.badRequest().body(messageSource.getMessage("noReservationFound", null, LocaleContextHolder.getLocale()));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<?> editReservation (@PathVariable("id") Integer id, @Valid @NonNull @RequestBody Reservation reservation)
    {
        if (reservationService.getReservation(id).isPresent())
        {
            return new ResponseEntity(reservationService.saveReservation(reservation), HttpStatus.OK);
        }
        return ResponseEntity.badRequest().body(messageSource.getMessage("noReservationFound", null, LocaleContextHolder.getLocale()));
    }
}
