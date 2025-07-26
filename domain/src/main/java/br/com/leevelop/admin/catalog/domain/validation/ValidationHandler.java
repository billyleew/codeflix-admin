package br.com.leevelop.admin.catalog.domain.validation;

import java.util.List;

public interface ValidationHandler {

    List<Error> getErrors();

    ValidationHandler append(Error anError);

    ValidationHandler append(ValidationHandler validationHandler);

    ValidationHandler validate(Validation aValidation);

    default boolean hasErrors() {
        return getErrors() != null && !getErrors().isEmpty();
    }

    default Error firstError() {
        if (getErrors() != null && !getErrors().isEmpty()) {
            return getErrors().getFirst();
        } else {
            return null;
        }
    }

    interface Validation {
        void validate();
    }
}
