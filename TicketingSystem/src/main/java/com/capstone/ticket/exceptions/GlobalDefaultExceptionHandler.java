package com.capstone.ticket.exceptions;

//@ControllerAdvice
//class GlobalDefaultExceptionHandler {
//    public static final String DEFAULT_ERROR_VIEW = "error";
//
//
//    @ResponseStatus(HttpStatus.CONFLICT)  // 409
//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public void handleConflict() {
//        // Nothing to do
//    }
//
//    @ExceptionHandler(value = Exception.class)
//    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
//        // If the exception is annotated with @ResponseStatus rethrow it and let
//        // the framework handle it - like the OrderNotFoundException example
//        // at the start of this post.
//        // AnnotationUtils is a Spring Framework utility class.
//        if (AnnotationUtils.findAnnotation
//                (e.getClass(), ResponseStatus.class) != null)
//            throw e;
//
//        // Otherwise setup and send the user to a default error-view.
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("exception", e);
//        mav.addObject("url", req.getRequestURL());
//        mav.setViewName(DEFAULT_ERROR_VIEW);
//        return mav;
//    }
//}

import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalDefaultExceptionHandler {
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleGenericNotFoundException(NotFoundException e) {
        CustomErrorResponse error = new CustomErrorResponse("NOT_FOUND_ERROR", e.getMessage());

        error.setTimestamp(LocalDateTime.now());

        error.setStatus((HttpStatus.NOT_FOUND.value()));

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}


