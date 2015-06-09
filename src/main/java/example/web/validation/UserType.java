package example.web.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {UserType.RequiredValidator.class})
public @interface UserType {
    Class<?>[] groups() default {};

    String message() default "userTypeはUSERかADMINです";

    Class<? extends UserType>[] payload() default {};

    class RequiredValidator implements ConstraintValidator<UserType, String> {
        @Override
        public void initialize(UserType period) {
        }

        @Override
        public boolean isValid(String str, ConstraintValidatorContext context) {
            return str == null || str.matches("^(ADMIN|USER)$");
        }
    }
}
