package com.example.demo.service;

import com.example.demo.model.Event;
import com.example.demo.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;

    public Event saveEvent(Event event) { return eventRepository.save(event); }

    public void deleteEvent (Integer id) { eventRepository.deleteById(id); }

    public Optional<Event> getEvent (Integer id) { return eventRepository.findById(id); }

    public Iterable<Event> getAllEvents () { return eventRepository.findAll(); }
}
