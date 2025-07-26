package br.com.leevelop.admin.catalog.application.category.retrieve.list;

import br.com.leevelop.admin.catalog.application.UseCase;
import br.com.leevelop.admin.catalog.domain.category.CategorySearchQuery;
import br.com.leevelop.admin.catalog.domain.pagination.Pagination;

public abstract class ListCategoriesUseCase
        extends UseCase<CategorySearchQuery, Pagination<CategoryListOutput>> {
}
