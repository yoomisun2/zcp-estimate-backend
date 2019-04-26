package io.cloudzcp.estimate.exception;

import java.time.LocalDateTime;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
		ErrorEntity entity = new ErrorEntity();
		entity.setTimestamp(LocalDateTime.now());
		entity.setStatus(HttpStatus.NOT_FOUND.value());
		entity.setError("Resource Not Found"); //HttpStatus.NOT_FOUND.getReasonPhrase();
		entity.setMessage(ex.getLocalizedMessage());
		entity.setDetail("");
		
		String path  = (String) request.getAttribute("javax.servlet.error.request_uri", RequestAttributes.SCOPE_REQUEST);
		if(path == null) {
			path = (String) request.getAttribute("org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping", RequestAttributes.SCOPE_REQUEST);
		}
		entity.setPath(path == null ? "" : path);

		return handleExceptionInternal(ex, entity, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
}
