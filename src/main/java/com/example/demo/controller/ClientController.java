package com.example.demo.controller;

import com.example.demo.model.Client;
import com.example.demo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RequestMapping("api/${api.version}/client")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ClientController {

    @Autowired
    ClientService clientService;

    @Autowired
    MessageSource messageSource;

    @PostMapping
    public ResponseEntity<?> createClient (@Valid @NonNull @RequestBody Client client)
    {
        Client clientFound = clientService.findByNameAndEmailEquals(client.getName(), client.getEmail()).orElse(null);
        if (clientFound == null)
        {
            Client clientCreated = clientService.saveClient(client);
            return new ResponseEntity(clientCreated, HttpStatus.CREATED);
        }
        return ResponseEntity.badRequest()
                .body(messageSource.getMessage("clientAlreadyExists",
                        null,
                        LocaleContextHolder.getLocale()));
    }

    @GetMapping(path = "{id}")
    @ResponseBody
    public ResponseEntity<?> getClient(@PathVariable("id") Integer id)
    {
        Optional<Client> clientFound = clientService.getClient(id);
        if (clientFound.isPresent())
        {
            return new ResponseEntity(clientFound, HttpStatus.OK);
        }
        return ResponseEntity.badRequest()
                .body(messageSource.getMessage("noClientFound", null, LocaleContextHolder.getLocale()));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteClient(@PathVariable("id") Integer id)
    {
        if (clientService.getClient(id).isPresent())
        {
            clientService.deleteClient(id);
            return new ResponseEntity(HttpStatus.OK);
        }
        return ResponseEntity.badRequest()
                .body(messageSource.getMessage("noClientFound", null, LocaleContextHolder.getLocale()));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<?> editClient(@PathVariable("id") Integer id, @Valid @NonNull @RequestBody Client client)
    {
        if (clientService.getClient(id).isPresent())
        {
            return new ResponseEntity(clientService.saveClient(client), HttpStatus.OK);
        }
        return ResponseEntity.badRequest()
                .body(messageSource.getMessage("noClientFound", null, LocaleContextHolder.getLocale()));
    }
}
