package com.example.prodcut.product.mapper;

import com.example.prodcut.product.Category;
import com.example.prodcut.product.Product;
import com.example.prodcut.product.dto.request.ProductRequest;
import com.example.prodcut.product.dto.response.ProductPurchaseResponse;
import com.example.prodcut.product.dto.response.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "category", expression = "java(mapCategory(request.categoryId()))")
    Product toEntity(ProductRequest request);

    default Category mapCategory(Integer categoryId) {
        if (categoryId == null) return null;
        return Category.builder().id(categoryId).build();
    }

    @Mapping(target = "availableQuantity", source = "availability")
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "categoryDescription", source = "category.description")
    ProductResponse toDto(Product product);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "quantity",source = "quantity")
    ProductPurchaseResponse toProductPurchaseResponse(Product product, double quantity);
}
