package com.example.demo.controller;

import com.example.demo.controller.modelRequest.EventCreationRequest;
import com.example.demo.model.Event;
import com.example.demo.model.Reservation;
import com.example.demo.model.Ticket;
import com.example.demo.model.Venue;
import com.example.demo.service.EventService;
import com.example.demo.service.ReservationService;
import com.example.demo.service.TicketService;
import com.example.demo.service.VenueService;
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

@RequestMapping("api/${api.version}/event")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class EventController {

    @Autowired
    EventService eventService;

    @Autowired
    TicketService ticketService;

    @Autowired
    VenueService venueService;

    @Autowired
    ReservationService reservationService;

    @Autowired
    MessageSource messageSource;

    @PostMapping
    public ResponseEntity<Event> createEvent (@Valid @NonNull @RequestBody EventCreationRequest eventCreationRequest)
    {
        Optional<Venue> venue = venueService.getVenue(eventCreationRequest.getVenueId());

        Event newEvent = new Event(eventCreationRequest.getEvent().getDate(),
                venue.get(),
                eventCreationRequest.getEvent().getDescription());

        Event eventCreated = eventService.saveEvent(newEvent);

        for (int i = 0; i < eventCreationRequest.getCapacity(); i++)
        {
            Ticket ticket = new Ticket (eventCreationRequest.getPrice(),  null, eventCreated);
            ticketService.saveTicket(ticket);
        }

        return new ResponseEntity(eventCreated, HttpStatus.CREATED);
    }

    @GetMapping(path = "{id}")
    @ResponseBody
    public ResponseEntity<?> getEvent (@PathVariable("id") Integer id)
    {
        Optional<Event> eventFound = eventService.getEvent(id);
        if (eventFound.isPresent())
        {
            return new ResponseEntity(eventFound, HttpStatus.OK);
        }
        return ResponseEntity.badRequest()
                .body(messageSource.getMessage("noEventFound", null, LocaleContextHolder.getLocale()));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteEvent (@PathVariable("id") Integer id)
    {
        Optional<Event> eventFound = eventService.getEvent(id);
        if (eventFound.isPresent())
        {
            Collection<Ticket> tickets = ticketService.findAllByEvent_Id(eventFound.get().getId());
            eventService.deleteEvent(id);

            ArrayList<Reservation> reservations = new ArrayList<>();

            for (Ticket ticket : tickets)
            {
                reservations.add(ticket.getReservation());
            }

            reservationService.deleteAllReservation(reservations);

            return new ResponseEntity(HttpStatus.OK);
        }
        return ResponseEntity.badRequest()
                .body(messageSource.getMessage("noEventFound", null, LocaleContextHolder.getLocale()));
    }

    @GetMapping(path = "{id}/tickets")
    @ResponseBody
    public ResponseEntity getAvailableTickets (@PathVariable("id") Integer id)
    {
        if (!ticketService.getAvailableTickets(id).isEmpty())
        {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ticketService.getAvailableTickets(id));
        }
        return ResponseEntity.badRequest().body(messageSource.getMessage("noTicketsAvailable",
                null, LocaleContextHolder.getLocale()));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<?> editEvent (@PathVariable("id") Integer id, @Valid @NonNull @RequestBody Event event)
    {
        if (eventService.getEvent(id).isPresent())
        {
            return new ResponseEntity(eventService.saveEvent(event), HttpStatus.OK);
        }
        return ResponseEntity.badRequest().body(messageSource.getMessage("noEventFound", null, LocaleContextHolder.getLocale()));
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity getAllEvents ()
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(eventService.getAllEvents());
    }
}
