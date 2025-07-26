package br.com.leevelop.admin.catalog.infrastructure.api.controllers;

import br.com.leevelop.admin.catalog.application.category.create.CreateCategoryCommand;
import br.com.leevelop.admin.catalog.application.category.create.CreateCategoryOutput;
import br.com.leevelop.admin.catalog.application.category.create.CreateCategoryUseCase;
import br.com.leevelop.admin.catalog.application.category.delete.DeleteCategoryUseCase;
import br.com.leevelop.admin.catalog.application.category.retrieve.get.GetCategoryByIdUseCase;
import br.com.leevelop.admin.catalog.application.category.retrieve.list.ListCategoriesUseCase;
import br.com.leevelop.admin.catalog.application.category.update.UpdateCategoryCommand;
import br.com.leevelop.admin.catalog.application.category.update.UpdateCategoryOutput;
import br.com.leevelop.admin.catalog.application.category.update.UpdateCategoryUseCase;
import br.com.leevelop.admin.catalog.domain.category.CategorySearchQuery;
import br.com.leevelop.admin.catalog.domain.pagination.Pagination;
import br.com.leevelop.admin.catalog.domain.validation.handler.Notification;
import br.com.leevelop.admin.catalog.infrastructure.api.CategoryAPI;
import br.com.leevelop.admin.catalog.infrastructure.category.models.CategoryListResponse;
import br.com.leevelop.admin.catalog.infrastructure.category.models.CategoryResponse;
import br.com.leevelop.admin.catalog.infrastructure.category.models.CreateCategoryRequest;
import br.com.leevelop.admin.catalog.infrastructure.category.models.UpdateCategoryRequest;
import br.com.leevelop.admin.catalog.infrastructure.category.presenters.CategoryApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

@RestController
public class CategoryController implements CategoryAPI {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;
    private final GetCategoryByIdUseCase getCategoryByIdUseCase;
    private final ListCategoriesUseCase listCategoriesUseCase;

    public CategoryController(
            final CreateCategoryUseCase createCategoryUseCase,
            final UpdateCategoryUseCase updateCategoryUseCase,
            final DeleteCategoryUseCase deleteCategoryUseCase,
            final GetCategoryByIdUseCase getCategoryByIdUseCase,
            final ListCategoriesUseCase listCategoriesUseCase
    ) {
        this.createCategoryUseCase = Objects.requireNonNull(createCategoryUseCase);
        this.updateCategoryUseCase = Objects.requireNonNull(updateCategoryUseCase);
        this.deleteCategoryUseCase = Objects.requireNonNull(deleteCategoryUseCase);
        this.getCategoryByIdUseCase = Objects.requireNonNull(getCategoryByIdUseCase);
        this.listCategoriesUseCase = Objects.requireNonNull(listCategoriesUseCase);
    }

    @Override
    public ResponseEntity<?> createCategory(final CreateCategoryRequest input) {
        final var aCommand = CreateCategoryCommand.with(
                input.name(),
                input.description(),
                input.active() != null ? input.active() : true
        );

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<CreateCategoryOutput, ResponseEntity<?>> onSuccess = output ->
                ResponseEntity.created(URI.create("/categories/" + output.id())).body(output);

        return this.createCategoryUseCase.execute(aCommand)
                .fold(onError, onSuccess);
    }

    @Override
    public Pagination<CategoryListResponse> listCategories(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction) {
        return listCategoriesUseCase.execute(
                new CategorySearchQuery(page, perPage, search, sort, direction))
                .map(CategoryApiPresenter::present);
    }

    @Override
    public CategoryResponse getById(String id) {
        return CategoryApiPresenter.present(this.getCategoryByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateById(String id, UpdateCategoryRequest input) {
        final var aCommand = UpdateCategoryCommand.with(
                id,
                input.name(),
                input.description(),
                input.active() != null ? input.active() : true
        );

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<UpdateCategoryOutput, ResponseEntity<?>> onSuccess = ResponseEntity::ok;

        return this.updateCategoryUseCase.execute(aCommand)
                .fold(onError, onSuccess);
    }

    @Override
    public void deleteById(String anId) {
        this.deleteCategoryUseCase.execute(anId);
    }
}
