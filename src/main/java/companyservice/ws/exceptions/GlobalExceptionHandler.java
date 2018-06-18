package companyservice.ws.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import companyservice.ws.exceptions.errorresponse.ApiError;
import companyservice.ws.exceptions.errorresponse.ApiErrorMeta;
import companyservice.ws.exceptions.errorresponse.ApiResponseCodes;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { GenericException.class })
    protected ResponseEntity<ApiError> handleGenericException(RuntimeException ex, WebRequest request) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
							 .body(new ApiError(new ApiErrorMeta(ex.getMessage())));
    }
	
	@ExceptionHandler(value = { ResourceNotFoundException.class })
    protected ResponseEntity<ApiError> handleResourceNotFoundException(RuntimeException ex, WebRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
							 .body(new ApiError(new ApiErrorMeta(ex.getMessage())));
    }
	
	@ExceptionHandler(value = { ValidationException.class })
	protected ResponseEntity<ApiError> handleValidationException(ValidationException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							 .body(new ApiError(new ApiErrorMeta(ApiResponseCodes.VALIDATION_ERROR.getCode(),
									 							 ApiResponseCodes.VALIDATION_ERROR.name(),
							 						   			 ApiResponseCodes.VALIDATION_ERROR.getInfo()), ex.getErrors().asMap()));
	}
	
}
