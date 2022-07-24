package unit;

import com.jmp.springmvc.dao.EventRepository;
import com.jmp.springmvc.dao.TicketRepository;
import com.jmp.springmvc.dao.UserAccountRepository;
import com.jmp.springmvc.dao.UserRepository;
import com.jmp.springmvc.model.Event;
import com.jmp.springmvc.model.Ticket;
import com.jmp.springmvc.model.User;
import com.jmp.springmvc.model.UserAccount;
import com.jmp.springmvc.service.TicketService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
public class TickerServiceTest {

    @TestConfiguration
    public static class TickerServiceTestConfig {
        @Bean
        TicketService ticketService() {
            return new TicketService();
        }
    }

    @Autowired
    private TicketService ticketService;

    @MockBean
    TicketRepository ticketRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    EventRepository eventRepository;

    @MockBean
    UserAccountRepository userAccountRepository;

    @Test
    public void bookTicketUserNotExist() {
        int place = 1;
        long userId = 1;
        long eventId = 1;
        Ticket.Category category = Ticket.Category.BAR;
        Ticket ticket = ticketService.bookTicket(userId, eventId, place, category);
        assertNull(ticket, "user is not exist");
    }

    @Test
    public void ticketPriceIsGreaterThanUserMoney() {
        int place = 1;
        Ticket bookedTicket = new Ticket();
        bookedTicket.setPlace(place);
        Mockito.when(ticketRepository.findAll()).thenReturn(Arrays.asList(bookedTicket));

        long userId = 1;
        User bookedUser = new User();
        bookedUser.setId(userId);

        UserAccount bookedUserAccount = new UserAccount();
        bookedUserAccount.setMoney(100);

        bookedUser.setUserAccount(bookedUserAccount);
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(bookedUser));

        long eventId = 1;
        Event event = new Event();
        event.setTicketPrice(200);
        Mockito.when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        Ticket.Category category = Ticket.Category.BAR;

        assertNull(ticketService.bookTicket(userId, eventId, place, category), "Ticket price is greater than user's money");
    }

    @Test
    public void placeAlreadyBeenBooked() {
        int place = 1;
        Ticket bookedTicket = new Ticket();
        bookedTicket.setPlace(place);
        bookedTicket.setPlace(place);
        Mockito.when(ticketRepository.findAll()).thenReturn(Arrays.asList(bookedTicket));

        long userId = 1;
        User bookedUser = new User();
        bookedUser.setId(userId);

        UserAccount bookedUserAccount = new UserAccount();
        bookedUserAccount.setMoney(200);

        bookedUser.setUserAccount(bookedUserAccount);
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(bookedUser));

        long eventId = 1;
        Event event = new Event();
        event.setTicketPrice(200);
        Mockito.when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        Ticket.Category category = Ticket.Category.BAR;

        assertThrows(IllegalStateException.class,
                () -> ticketService.bookTicket(userId, eventId, place, category)
                , "This place has already been booked");
    }

}