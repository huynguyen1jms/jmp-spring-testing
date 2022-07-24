package com.jmp.springmvc.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by maksym_govorischev.
 */
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "title")
    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    private Date date;

    @Column(name = "ticket_price")
    private long ticketPrice;

    @OneToMany(mappedBy = "event")
    private Set<Ticket> tickets;

    public Event() {
    }

    public Event(long id, String title, Date date) {
        this.id = id;
        this.title = title;
        this.date = date;
    }

    /**
     * Event id. UNIQUE.
     *
     * @return Event Id
     */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(long ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }
}
