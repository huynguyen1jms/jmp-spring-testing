package com.jmp.springmvc.jms;

import com.jmp.springmvc.facade.BookingFacade;
import com.jmp.springmvc.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class UserReceiver{

    private Logger logger = LoggerFactory.getLogger(UserReceiver.class);
    private static AtomicInteger id = new AtomicInteger();

    @Autowired
    private BookingFacade bookingFacade;

    @Autowired
    ConfirmationSender confirmationSender;


    @JmsListener(destination = "userQueue", containerFactory = "connectionFactory")
    public void receiveMessage(User receivedUser, Message message) {
        logger.info(" >> Original received message: " + message);
        logger.info(" >> Received user: " + receivedUser);
        bookingFacade.createUser(receivedUser);

        confirmationSender.sendMessage(new Confirmation(id.incrementAndGet(), "User "
                + receivedUser.getEmail() + " received."));

    }

}