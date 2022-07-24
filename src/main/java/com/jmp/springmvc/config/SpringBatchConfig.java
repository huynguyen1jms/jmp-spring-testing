package com.jmp.springmvc.config;

import com.jmp.springmvc.dao.EventRepository;
import com.jmp.springmvc.dao.TicketRepository;
import com.jmp.springmvc.dto.TicketDTO;
import com.jmp.springmvc.facade.BookingFacade;
import com.jmp.springmvc.model.Event;
import com.jmp.springmvc.model.User;
import com.jmp.springmvc.task.MyTask;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.io.FileNotFoundException;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    @Autowired
    private BookingFacade bookingFacade;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Bean
    public ItemReader<TicketDTO> itemReader() {
        Jaxb2Marshaller studentMarshaller = new Jaxb2Marshaller();
        studentMarshaller.setClassesToBeBound(TicketDTO.class);

        return new StaxEventItemReaderBuilder<TicketDTO>()
                .name("ticketReader")
                .resource(new ClassPathResource("tickets.xml"))
                .addFragmentRootElements("ticket")
                .unmarshaller(studentMarshaller)
                .build();
    }

    @Bean
    public Step stepOne() {
        return steps.get("stepOne")
                .tasklet(new MyTask(bookingFacade))
                .build();
    }

    //    @Scheduled(cron = "0 */1 * * * ?")
    @Bean
    public Job demoJob() throws FileNotFoundException {
        bookingFacade.preloadTickets();
        List<User> users = bookingFacade.getAllUser();
        System.out.println(users.size());
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@");
        List<Event> events = eventRepository.findAll();
        System.out.println(events.size());

        return jobs.get("demoJob")
                .incrementer(new RunIdIncrementer())
                .start(stepOne())
                .build();
    }
}