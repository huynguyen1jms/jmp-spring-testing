package integration;

import com.jmp.springmvc.BookingApplication;
import com.jmp.springmvc.facade.BookingFacade;
import com.jmp.springmvc.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@ContextConfiguration(classes = BookingApplication.class)
@SpringBootTest
public class IntegrationTestJms {

    @Autowired
    private BookingFacade bookingFacade;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Test
    public void testJmsCreateUser() throws Exception {

        String email = "huy" + Math.random() + "@gmail.com";
        User user = new User();
        user.setName("huy");
        user.setEmail(email);
        jmsTemplate.convertAndSend("userQueue", user);
        User userReceive;
        do {
            Thread.sleep(2000);
            userReceive = bookingFacade.getUserByEmail(email);
        } while (userReceive == null);

        assertEquals(userReceive.getName(), user.getName());
    }

}