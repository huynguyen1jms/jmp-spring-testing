package com.jmp.springmvc.dao;

import com.jmp.springmvc.model.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByTitle(String title, Pageable pageable);

    List<Event> findByDate(Date date, Pageable pageable);
}
