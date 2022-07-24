package com.jmp.springmvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jmp.springmvc.facade.BookingFacade;
import com.jmp.springmvc.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequestMapping(value = "/event")
@Controller
public class EventController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookingFacade bookingFacade;

    @Autowired
    private ObjectWriter objectWriter;

    @RequestMapping(value = "/get-by-id", method = RequestMethod.GET)
    public ResponseEntity<String> getEventsById(@RequestParam(name = "id") long id) throws JsonProcessingException {

        Event event = bookingFacade.getEventById(id);
        if (event == null) {
            LOGGER.info("event is empty");
        }

        String jsonObject = objectWriter.writeValueAsString(event);
        return ResponseEntity.ok().body(jsonObject);
    }

    @RequestMapping(value = "/get-by-title", method = RequestMethod.GET)
    public ResponseEntity<String> getEventsByEmail(@RequestParam(name = "title") String title,
                                                   @RequestParam(name = "pageSize") int pageSize,
                                                   @RequestParam(name = "pageNum") int pageNum) throws JsonProcessingException {

        List<Event> events = bookingFacade.getEventsByTitle(title, pageSize, pageNum);
        if (events == null || events.isEmpty()) {
            LOGGER.info("event is empty");
        }

        String jsonObject = objectWriter.writeValueAsString(events);
        return ResponseEntity.ok().body(jsonObject);
    }

    @RequestMapping(value = "/get-by-date", method = RequestMethod.GET)
    public ResponseEntity<String> getEventsForDay(@RequestParam(name = "date") String inputDate,
                                                  @RequestParam(name = "pageSize") int pageSize,
                                                  @RequestParam(name = "pageNum") int pageNum) throws ParseException, JsonProcessingException {

        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(inputDate);

        List<Event> events = bookingFacade.getEventsForDay(date, pageSize, pageNum);
        if (events == null || events.isEmpty()) {
            System.out.println("event is empty");
        }

        String jsonObject = objectWriter.writeValueAsString(events);
        return ResponseEntity.ok().body(jsonObject);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<String> createEvent(@RequestBody Event event) throws JsonProcessingException {
        event = bookingFacade.createEvent(event);

        String jsonObject = objectWriter.writeValueAsString(event);
        return ResponseEntity.ok().body(jsonObject);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<String> updateEvent(@RequestBody Event event) throws JsonProcessingException {
        event = bookingFacade.updateEvent(event);

        String jsonObject = objectWriter.writeValueAsString(event);
        return ResponseEntity.ok().body(jsonObject);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteEvent(@RequestBody Event event) throws JsonProcessingException {
        bookingFacade.deleteEvent(event.getId());

        String jsonObject = objectWriter.writeValueAsString(event);
        return ResponseEntity.ok().body(jsonObject);
    }
}
