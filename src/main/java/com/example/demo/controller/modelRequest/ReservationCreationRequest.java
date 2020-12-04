package com.example.demo.controller.modelRequest;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ReservationCreationRequest {

    @NotEmpty
    private String name;
    @NotEmpty
    private String email;
    @NotNull
    private Integer idEvent;
    @NotNull
    private Integer numberOfTickets;

    public ReservationCreationRequest(String name, String email, Integer idEvent, Integer numberOfTickets)
    {
        this.name = name;
        this.email = email;
        this.idEvent = idEvent;
        this.numberOfTickets = numberOfTickets;
    }

    public Integer getIdEvent()
    {
        return idEvent;
    }

    public void setIdEvent(Integer idEvent)
    {
        this.idEvent = idEvent;
    }

    public Integer getNumberOfTickets()
    {
        return numberOfTickets;
    }

    public void setNumberOfTickets(Integer numberOfTickets)
    {
        this.numberOfTickets = numberOfTickets;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
