package br.com.leevelop.admin.catalog.infrastructure.category.presenters;


import br.com.leevelop.admin.catalog.application.category.retrieve.get.CategoryOutput;
import br.com.leevelop.admin.catalog.application.category.retrieve.list.CategoryListOutput;
import br.com.leevelop.admin.catalog.infrastructure.category.models.CategoryListResponse;
import br.com.leevelop.admin.catalog.infrastructure.category.models.CategoryResponse;

public interface  CategoryApiPresenter {

    static CategoryResponse present(final CategoryOutput output) {
        return new CategoryResponse(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }

    static CategoryListResponse present(final CategoryListOutput output) {
        return new CategoryListResponse(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.deletedAt()
        );
    }

}
