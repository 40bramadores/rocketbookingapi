package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Reservation")
public class Reservation {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Transient
    private Double finalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "client_id", nullable = false)
    @NotNull
    private Client client;

    @OneToMany(mappedBy = "reservation", fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private List<Ticket> tickets = new ArrayList<>();

    @Autowired
    public Reservation(Double finalPrice, Client client)
    {
        this.finalPrice = finalPrice;
        this.client = client;
    }

    @Autowired
    public Reservation(Client client)
    {
        this.client = client;
    }

    @Autowired
    public Reservation()
    {
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Double getFinalPrice()
    {
        return finalPrice;
    }

    public void setFinalPrice(Double price)
    {
        this.finalPrice = price;
    }

    public Client getClient()
    {
        return client;
    }

    public void setClient(Client client)
    {
        this.client = client;
    }

    @JsonIgnore
    public List<Ticket> getTickets()
    {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets)
    {
        this.tickets = tickets;
    }
}
