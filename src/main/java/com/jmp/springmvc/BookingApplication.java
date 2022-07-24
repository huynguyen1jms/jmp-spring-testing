package com.jmp.springmvc;

import com.jmp.springmvc.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;

@SpringBootApplication
public class BookingApplication {
    private static Logger logger = LoggerFactory.getLogger(BookingApplication.class);

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(BookingApplication.class, args);

//        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
//
//        //Send an user
//        System.out.println("Sending an user message.");
//        jmsTemplate.convertAndSend("userQueue",
//                new User(1, "huy", "huy@gmail.com"));
//
//        logger.info("Waiting for user and confirmation ...");
    }
}
