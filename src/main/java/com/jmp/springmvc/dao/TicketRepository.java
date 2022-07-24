package com.jmp.springmvc.dao;

import com.jmp.springmvc.model.Event;
import com.jmp.springmvc.model.Ticket;
import com.jmp.springmvc.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByUser(User user, Pageable pageable);

    List<Ticket> findAllByEvent(Event event, Pageable pageable);
}
