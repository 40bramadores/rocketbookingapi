package com.example.demo.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@SequenceGenerator(name="seq", initialValue=1, allocationSize=100)
@Entity
@Table(name = "Ticket")
public class Ticket {

    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
    @Id
    @Column
    private Integer id;

    @Column (name = "price", nullable = false)
    @NotNull
    private Double price;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn (name = "reservation_id")
    private Reservation reservation;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "event_id", nullable = false)
    @NotNull
    private Event event;

    @Autowired
    public Ticket(double price, Reservation reservation, Event event)
    {
        this.price = price;
        this.reservation = reservation;
        this.event = event;
    }

    @Autowired
    public Ticket ()
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

    public Reservation getReservation()
    {
        return reservation;
    }

    public void setReservation(Reservation reservation)
    {
        this.reservation = reservation;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public Event getEvent()
    {
        return event;
    }

    public void setEvent(Event event)
    {
        this.event = event;
    }
}
