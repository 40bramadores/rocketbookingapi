package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Client")
public class Client {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (name = "name", nullable = false)
    @NotEmpty
    private String name;

    @Column (name = "email", nullable =  false)
    @NotEmpty
    private String email;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Reservation> reservations = new ArrayList<>();

    @Autowired
    public Client(String name, String email)
    {
        this.name = name;
        this.email = email;
    }

    @Autowired
    public Client()
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

    @JsonIgnore
    public List<Reservation> getReservations()
    {
        return reservations;
    }
}
