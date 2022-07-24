package com.jmp.springmvc.service;

import com.jmp.springmvc.dao.EventRepository;
import com.jmp.springmvc.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EventService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EventRepository eventRepository;

    public Event getEventById(long eventId) {
        Event event = eventRepository.getById(eventId);
        if (event != null) {
            LOGGER.info("Found event with id = {}", eventId);
        }
        return event;
    }

    /**
     * Get list of events by matching title. Title is matched using 'contains' approach.
     * In case nothing was found, empty list is returned.
     *
     * @param title    Event title or it's part.
     * @param pageSize Pagination param. Number of events to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @return List of events.
     */
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        return eventRepository.findByTitle(title, pageable);
    }

    /**
     * Get list of events for specified day.
     * In case nothing was found, empty list is returned.
     *
     * @param day      Date object from which day information is extracted.
     * @param pageSize Pagination param. Number of events to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @return List of events.
     */
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        return eventRepository.findByDate(day, pageable);
    }

    /**
     * Creates new event. Event id should be auto-generated.
     *
     * @param event Event data.
     * @return Created Event object.
     */
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    /**
     * Updates event using given data.
     *
     * @param event Event data for update. Should have id set.
     * @return Updated Event object.
     */
    public Event updateEvent(Event event) {
        return eventRepository.save(event);
    }

    /**
     * Deletes event by its id.
     *
     * @param eventId Event id.
     * @return Flag that shows whether event has been deleted.
     */
    public boolean deleteEvent(long eventId) {
        if (eventRepository.existsById(eventId)) {
            eventRepository.deleteById(eventId);
            return true;
        }
        return false;
    }
}
