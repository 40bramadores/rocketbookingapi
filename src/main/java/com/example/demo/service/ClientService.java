package com.example.demo.service;

import com.example.demo.model.Client;
import com.example.demo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ReservationService reservationService;

    public Client saveClient(Client client)
    {
        return clientRepository.save(client);
    }

    public Optional<Client> getClient (Integer id)
    {
        return clientRepository.findById(id);
    }

    public void deleteClient(Integer id)
    {
        clientRepository.deleteById(id);
    }

    public Optional<Client> findByNameAndEmailEquals(String name, String email)
    {
        return clientRepository.findByNameAndEmailEquals(name, email);
    }
}
