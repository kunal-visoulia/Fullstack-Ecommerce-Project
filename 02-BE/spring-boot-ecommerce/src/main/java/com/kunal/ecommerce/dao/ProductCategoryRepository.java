package com.kunal.ecommerce.dao;

import com.kunal.ecommerce.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "productCategory", path="product-category")
//first apram is the name of json Entry and 2nd param is the actual path reference
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Long> {
}
