package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Event")
public class Event {

    @Id
    @Column
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "venue_id", nullable = false)
    @NotNull
    private Venue venue;

    @Column(name = "description", nullable = false)
    @NotEmpty
    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "event")
    @Fetch(FetchMode.JOIN)
    private List<Ticket> tickets = new ArrayList<>();

    @Autowired
    public Event(LocalDate date, Venue venue, String description)
    {
        this.date = date;
        this.venue = venue;
        this.description = description;
    }

    @Autowired
    public Event()
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    public Venue getVenue()
    {
        return venue;
    }

    public void setVenue(Venue venue)
    {
        this.venue = venue;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @JsonIgnore
    public List<Ticket> getTickets() { return tickets; }

    public void setTickets(List<Ticket> tickets) { this.tickets = tickets; }

}
