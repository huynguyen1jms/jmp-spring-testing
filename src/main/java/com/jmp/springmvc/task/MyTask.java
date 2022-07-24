package com.jmp.springmvc.task;

import com.jmp.springmvc.facade.BookingFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class MyTask implements Tasklet {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private BookingFacade bookingFacade;

    public MyTask(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        LOGGER.info("Execute MyTask-------------");
        bookingFacade.preloadTickets();
        return RepeatStatus.FINISHED;
    }
}