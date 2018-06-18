package companyservice.ws.aspects;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

import companyservice.ws.annotations.WithValidator;
import companyservice.ws.exceptions.GenericException;
import companyservice.ws.exceptions.ValidationException;
import companyservice.ws.validators.ValidatableResource;
import companyservice.ws.validators.Validator;

@Aspect
@Component
public class WithValidatorAspect {

	@Autowired
	ApplicationContext appContext;
	
	@Before("@annotation(companyservice.ws.annotations.WithValidator)")
	public Object logExecutionTime(JoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
	    Method method = signature.getMethod();
	    WithValidator withValidatorAnnotation = method.getAnnotation(WithValidator.class);
		
	    Class<? extends Validator>[] validators = withValidatorAnnotation.value();
		
	    if (validators.length < 1) {
	    	throw new GenericException("Missing validators");
	    }
	    
	    Object[] args = joinPoint.getArgs();
	    
	    if (args.length < 1) {
	    	throw new GenericException("Missing validatable arguments");
	    }
	    
	    List<ValidatableResource> validatableResources = new ArrayList<ValidatableResource>();
	    
	    for (Object arg : args) {
	    	if (arg instanceof ValidatableResource) {
	    		validatableResources.add((ValidatableResource) arg);
	    	}
	    }

	    if (validatableResources.isEmpty()) {
	    	throw new GenericException("Non validatable arguments");
	    }
	    
	    Multimap<String, String> errors = LinkedHashMultimap.create();
	    
	    for (Class<? extends Validator> validator : validators) {
	    	Validator v = (Validator) appContext.getBean(validator.getSimpleName());
	    	for (ValidatableResource resource : validatableResources) {
		    	errors.putAll(v.validate(resource));
	    	}
	    }
	    
	    if (!errors.isEmpty()) {
	    	throw new ValidationException(errors);
	    }
	    
		return joinPoint.getThis();
	}
	
}
