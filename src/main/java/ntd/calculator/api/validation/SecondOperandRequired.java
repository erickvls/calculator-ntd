package ntd.calculator.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SecondOperandValidator.class)
public @interface SecondOperandRequired {
    String message() default "Second operand is required for this operation type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}