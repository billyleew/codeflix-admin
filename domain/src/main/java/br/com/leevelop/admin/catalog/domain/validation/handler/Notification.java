package br.com.leevelop.admin.catalog.domain.validation.handler;

import br.com.leevelop.admin.catalog.domain.exceptions.DomainException;
import br.com.leevelop.admin.catalog.domain.validation.Error;
import br.com.leevelop.admin.catalog.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.List;

public class Notification implements ValidationHandler {

    private final List<Error> errors;

    private Notification(final List<Error> errors) {
        this.errors = errors;
    }

    public static Notification create() {
        return new Notification(new ArrayList<>());
    }

    public static Notification create(final Error anError) {
        return new Notification(new ArrayList<>()).append(anError);
    }

    public static Notification create(final Throwable t) {
        return create(new Error(t.getMessage()));
    }

    @Override
    public List<Error> getErrors() {
        return this.errors;
    }

    @Override
    public Notification append(final Error anError) {
        errors.add(anError);
        return this;
    }

    @Override
    public Notification append(ValidationHandler validationHandler) {
        this.errors.addAll(validationHandler.getErrors());
        return this;
    }

    @Override
    public Notification validate(Validation aValidation) {
        try {
            aValidation.validate();
        } catch (final DomainException ex) {
            this.errors.addAll(ex.getErrors());
        } catch (final Throwable t) {
            this.errors.add(new Error(t.getMessage()));
        }
        return this;
    }
}
