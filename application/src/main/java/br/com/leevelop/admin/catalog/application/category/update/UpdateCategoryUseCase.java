package br.com.leevelop.admin.catalog.application.category.update;

import br.com.leevelop.admin.catalog.application.UseCase;
import br.com.leevelop.admin.catalog.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class UpdateCategoryUseCase
        extends UseCase<UpdateCategoryCommand, Either<Notification, UpdateCategoryOutput>> {
}
