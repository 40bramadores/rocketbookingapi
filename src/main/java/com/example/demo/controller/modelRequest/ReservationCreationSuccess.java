package com.example.demo.controller.modelRequest;

import com.example.demo.model.Ticket;

import java.util.ArrayList;

public class ReservationCreationSuccess {

    private Double finalPrice;
    private ArrayList<Ticket> ticketsReserved;

    public ReservationCreationSuccess(Double finalPrice, ArrayList<Ticket> ticketsReserved)
    {
        this.finalPrice = finalPrice;
        this.ticketsReserved = ticketsReserved;
    }

    public ReservationCreationSuccess(Double finalPrice)
    {
        this.finalPrice = finalPrice;
    }

    public Double getFinalPrice()
    {
        return finalPrice;
    }

    public void setFinalPrice(Double finalPrice)
    {
        this.finalPrice = finalPrice;
    }

    public ArrayList<Ticket> getTicketsReserved()
    {
        return ticketsReserved;
    }

    public void setTicketsReserved(ArrayList<Ticket> ticketsReserved)
    {
        this.ticketsReserved = ticketsReserved;
    }
}
