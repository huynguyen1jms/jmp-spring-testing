package com.jmp.springmvc.service;

import com.jmp.springmvc.dao.EventRepository;
import com.jmp.springmvc.dao.TicketRepository;
import com.jmp.springmvc.dao.UserAccountRepository;
import com.jmp.springmvc.dao.UserRepository;
import com.jmp.springmvc.model.Event;
import com.jmp.springmvc.model.Ticket;
import com.jmp.springmvc.model.User;
import com.jmp.springmvc.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TicketRepository ticketRepository;

    /**
     * Book ticket for a specified event on behalf of specified user.
     *
     * @param userId   User Id.
     * @param eventId  Event Id.
     * @param place    Place number.
     * @param category Service category.
     * @return Booked ticket object.
     * @throws java.lang.IllegalStateException if this place has already been booked.
     */
    @Transactional
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        List<Ticket> tickets = ticketRepository.findAll();
        Optional<User> user = userRepository.findById(userId);
        Optional<Event> event = eventRepository.findById(eventId);
        if (!user.isPresent() || !event.isPresent()) {
            return null;
        }

        UserAccount userAccount = user.get().getUserAccount();
        long currentMoney = userAccount.getMoney();
        if (event.get().getTicketPrice() > currentMoney) {
            return null;
        }

        for (Ticket ticket : tickets) {
            if (ticket.getPlace() == place) {
                throw new IllegalStateException("this place has already been booked");
            }
        }

        userAccount.setMoney(currentMoney - event.get().getTicketPrice());
        userAccountRepository.save(userAccount);

        Ticket ticket = new Ticket();
        ticket.setUser(user.get());
        ticket.setEvent(event.get());
        ticket.setPlace(place);
        ticket.setCategory(category);
        return ticketRepository.save(ticket);
    }

    /**
     * Get all booked tickets for specified user. Tickets should be sorted by event date in descending order.
     *
     * @param user     User
     * @param pageSize Pagination param. Number of tickets to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @return List of Ticket objects.
     */
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        return ticketRepository.findAllByUser(user, pageable);
    }

    /**
     * Get all booked tickets for specified event. Tickets should be sorted in by user email in ascending order.
     *
     * @param event    Event
     * @param pageSize Pagination param. Number of tickets to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @return List of Ticket objects.
     */
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        return ticketRepository.findAllByEvent(event, pageable);
    }

    /**
     * Cancel ticket with a specified id.
     *
     * @param ticketId Ticket id.
     * @return Flag whether anything has been canceled.
     */
    public boolean cancelTicket(long ticketId) {
        if (ticketRepository.existsById(ticketId)) {
            ticketRepository.deleteById(ticketId);
            return true;
        }

        return false;
    }
}
