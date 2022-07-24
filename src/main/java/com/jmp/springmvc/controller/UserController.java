package com.jmp.springmvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jmp.springmvc.facade.BookingFacade;
import com.jmp.springmvc.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@RequestMapping(value = "/user")
@Controller
public class UserController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookingFacade bookingFacade;

    @Autowired
    private ObjectWriter objectWriter;

    @Autowired
    private JmsTemplate jmsTemplate;

    @RequestMapping(value = "/get-by-id", method = RequestMethod.GET)
    public ResponseEntity<String> getUsersById(@RequestParam(name = "id") long id) throws JsonProcessingException {

        User user = bookingFacade.getUserById(id);
        List<User> users = new ArrayList<>();
        if (user == null) {
            LOGGER.info("user is empty");
        } else {
            users.add(user);
        }
        String jsonObject = objectWriter.writeValueAsString(users);
        return ResponseEntity.ok().body(jsonObject);
    }

    @RequestMapping(value = "/get-by-email", method = RequestMethod.GET)
    public ResponseEntity<String> getUsersByEmail(@RequestParam(name = "email") String email) throws JsonProcessingException {

        User user = bookingFacade.getUserByEmail(email);
        List<User> users = new ArrayList<>();
        if (user == null) {
            LOGGER.info("user is empty");
        } else {
            users.add(user);
        }

        String jsonObject = objectWriter.writeValueAsString(users);
        return ResponseEntity.ok().body(jsonObject);
    }

    @RequestMapping(value = "/get-by-name", method = RequestMethod.GET)
    public ResponseEntity<String> getUsersByName(@RequestParam(name = "name") String name,
                                                 @RequestParam(name = "pageSize") int pageSize,
                                                 @RequestParam(name = "pageNum") int pageNum) throws JsonProcessingException {

        List<User> users = bookingFacade.getUsersByName(name, pageSize, pageNum);
        if (users == null || users.isEmpty()) {
            LOGGER.info("user is empty");
        }

        String jsonObject = objectWriter.writeValueAsString(users);
        return ResponseEntity.ok().body(jsonObject);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<String> createUser(@RequestBody User user) throws JsonProcessingException {
//        user = bookingFacade.createUser(user);

        String jsonObject = objectWriter.writeValueAsString(user);
        LOGGER.info(jsonObject);
        jmsTemplate.convertAndSend("userQueue", user);
        return ResponseEntity.ok().body("Creating user...");
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        bookingFacade.updateUser(user);
        return ResponseEntity.ok().body("User is updated");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@RequestBody User user) {
        bookingFacade.deleteUser(user.getId());

        return ResponseEntity.ok().body("User is deleted");
    }

}