package companyservice.ws.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import companyservice.ws.validators.Validator;

@Retention(RUNTIME)
@Target({ METHOD })
public @interface WithValidator {

	Class<? extends Validator>[] value() default {};
	
}
