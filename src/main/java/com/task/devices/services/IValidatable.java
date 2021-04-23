package com.task.devices.services;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public interface IValidatable {
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * The method provides class validation with the standard framework – JSR-380 (Bean Validation 2.0).
     * It ensures that the fields of a class meet specific criteria, using annotations.
     *
     * @throws AppException if target class is not valid.
     */
    default void validate() {
        Set<ConstraintViolation<IValidatable>> constraint = validator.validate(this);

        if (!constraint.isEmpty()) {
            StringBuilder msg = new StringBuilder(this.getClass()
                    .getSimpleName())
                    .append(" has validation violations: ");

            for (ConstraintViolation<IValidatable> violation : constraint) {
                msg.append(violation.getPropertyPath())
                        .append(" ")
                        .append(violation.getMessage())
                        .append(" ")
                        .append(violation.getInvalidValue())
                        .append("\n");
            }
            throw new ValidationException(msg.toString());
        }
    }
}
