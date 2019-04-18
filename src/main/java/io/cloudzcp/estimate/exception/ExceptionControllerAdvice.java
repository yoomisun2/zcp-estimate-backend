package io.cloudzcp.estimate.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(value = {EntityNotFoundException.class})
	protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
		ErrorEntity entity = new ErrorEntity("Resource Not Found", ex.getLocalizedMessage(), HttpStatus.NOT_FOUND.value());
		return handleExceptionInternal(ex, entity, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

}
