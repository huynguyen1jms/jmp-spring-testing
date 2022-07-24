package com.jmp.springmvc.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ApplicationExceptionHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception exception) {
        LOGGER.info("Global exception handler!!!");
        LOGGER.info(exception.getMessage());
        ModelAndView modelAndView = new ModelAndView("error");
        return modelAndView;
    }
}
