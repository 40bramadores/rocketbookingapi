package com.example.demo.controller;

import com.example.demo.model.Venue;
import com.example.demo.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RequestMapping("api/${api.version}/venue")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class VenueController {

    @Autowired
    VenueService venueService;

    @Autowired
    MessageSource messageSource;

    @PostMapping
    public ResponseEntity<Venue> createVenue (@Valid @NonNull @RequestBody Venue venue)
    {
        return new ResponseEntity(venueService.saveVenue(venue), HttpStatus.CREATED);
    }

    @GetMapping(path = "{id}")
    @ResponseBody
    public ResponseEntity<?> getVenue (@PathVariable("id") Integer id)
    {
        Optional<Venue> venueFound = venueService.getVenue(id);
        if (venueFound.isPresent())
        {
            return new ResponseEntity(venueFound, HttpStatus.OK);
        }
        return ResponseEntity.badRequest()
                .body(messageSource.getMessage("noVenueFound", null, LocaleContextHolder.getLocale()));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteVenue(@PathVariable("id") Integer id)
    {
        if (venueService.getVenue(id).isPresent())
        {
            venueService.deleteVenue(id);
            return new ResponseEntity(HttpStatus.OK);
        }

        return ResponseEntity.badRequest()
                .body(messageSource.getMessage("noVenueFound", null, LocaleContextHolder.getLocale()));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<?> editVenue(@PathVariable("id") Integer id, @Valid @NonNull @RequestBody Venue venue)
    {
        if (venueService.getVenue(id).isPresent())
        {
            return new ResponseEntity(venueService.saveVenue(venue), HttpStatus.OK);
        }
        return ResponseEntity.badRequest()
                .body(messageSource.getMessage("noVenueFound", null, LocaleContextHolder.getLocale()));
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity getAllVenues ()
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(venueService.getAllVenues());
    }
}
