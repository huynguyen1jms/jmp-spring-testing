package com.jmp.springmvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jmp.springmvc.dto.TicketRequest;
import com.jmp.springmvc.facade.BookingFacade;
import com.jmp.springmvc.model.Event;
import com.jmp.springmvc.model.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping(value = "/ticket")
@Controller
public class TicketController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookingFacade bookingFacade;

    @Autowired
    private ObjectWriter objectWriter;

    @RequestMapping(value = "/book", method = RequestMethod.POST)
    public ResponseEntity<String> bookTicket(@RequestBody TicketRequest ticketDTO) {


        Ticket ticket = bookingFacade.bookTicket(ticketDTO.getUserId(), ticketDTO.getEventId(), ticketDTO.getPlace(), ticketDTO.getCategory());
        String result;
        if (ticket == null) {
            result = "ERROR";
        } else {
            result = "OK";
        }

        return ResponseEntity.ok().body(result);
    }

    @RequestMapping(value = "/get-booked-by-user", method = RequestMethod.GET)
    public ResponseEntity<String> getBookedByUser(@RequestParam(name = "title") String title,
                                                  @RequestParam(name = "pageSize") int pageSize,
                                                  @RequestParam(name = "pageNum") int pageNum) throws JsonProcessingException {

        List<Event> events = bookingFacade.getEventsByTitle(title, pageSize, pageNum);
        if (events == null || events.isEmpty()) {
            LOGGER.info("event is empty");
        }

        String jsonObject = objectWriter.writeValueAsString(events);
        return ResponseEntity.ok().body(jsonObject);
    }

    @RequestMapping(value = "/get-booked-by-event", method = RequestMethod.GET)
    public ResponseEntity<String> getBookedByEvent(@RequestParam(name = "title") String title,
                                                   @RequestParam(name = "pageSize") int pageSize,
                                                   @RequestParam(name = "pageNum") int pageNum) throws JsonProcessingException {

        List<Event> events = bookingFacade.getEventsByTitle(title, pageSize, pageNum);
        if (events == null || events.isEmpty()) {
            LOGGER.info("event is empty");
        }

        String jsonObject = objectWriter.writeValueAsString(events);
        return ResponseEntity.ok().body(jsonObject);
    }

}
