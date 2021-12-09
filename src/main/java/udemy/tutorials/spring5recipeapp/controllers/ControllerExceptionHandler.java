package udemy.tutorials.spring5recipeapp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {


  @ExceptionHandler(NumberFormatException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ModelAndView handleBadRequest(Exception e) {
    log.error("Handling number format exception: " + e.getMessage(), e);

    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("400error");
    modelAndView.addObject("exception", e);

    return modelAndView;
  }

}
