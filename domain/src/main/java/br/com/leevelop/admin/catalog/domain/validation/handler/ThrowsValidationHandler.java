package br.com.leevelop.admin.catalog.domain.validation.handler;

import br.com.leevelop.admin.catalog.domain.exceptions.DomainException;
import br.com.leevelop.admin.catalog.domain.validation.Error;
import br.com.leevelop.admin.catalog.domain.validation.ValidationHandler;

import java.util.List;

public class ThrowsValidationHandler implements ValidationHandler {

    @Override
    public ValidationHandler append(final Error anError) {
        throw DomainException.with(anError);
    }

    @Override
    public ValidationHandler append(ValidationHandler validationHandler) {
        throw DomainException.with(validationHandler.getErrors());
    }

    @Override
    public ValidationHandler validate(final Validation aValidation) {
        try {
            aValidation.validate();
        } catch (final Exception ex) {
            throw DomainException.with(new Error(ex.getMessage()));
        }

        return this;
    }

    @Override
    public boolean hasErrors() {
        return ValidationHandler.super.hasErrors();
    }

    @Override
    public List<Error> getErrors() {
        return List.of();
    }
}
