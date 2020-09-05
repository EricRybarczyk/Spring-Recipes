package dev.ericrybarczyk.springrecipes.controllers;


import dev.ericrybarczyk.springrecipes.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public abstract class BaseController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    // otherwise this method will return status 200 because method is more granular level than this annotation on the custom exception class
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception e) {
        log.error(getExceptionLogMessage(e));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", e);
        modelAndView.setViewName("errors/error404");
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormat(Exception e) {
        log.error(getExceptionLogMessage(e));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", e);
        modelAndView.setViewName("errors/error400");
        return modelAndView;
    }

    private String getExceptionLogMessage(Exception e) {
        return String.format("Handling %s: %s", e.getClass().getSimpleName(), e.getMessage());
    }
}
