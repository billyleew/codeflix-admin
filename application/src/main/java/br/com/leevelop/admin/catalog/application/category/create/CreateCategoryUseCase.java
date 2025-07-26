package br.com.leevelop.admin.catalog.application.category.create;

import br.com.leevelop.admin.catalog.application.UseCase;
import br.com.leevelop.admin.catalog.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateCategoryUseCase extends
        UseCase<CreateCategoryCommand, Either<Notification, CreateCategoryOutput>> {
}
