package com.kunal.ecommerce.config;

import com.kunal.ecommerce.entity.Product;
import com.kunal.ecommerce.entity.ProductCategory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration //so that springboot picks up this class
public class MyDataRestConfig implements RepositoryRestConfigurer {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

        HttpMethod[] unsupportedActions = {HttpMethod.PUT,HttpMethod.POST,HttpMethod.DELETE}
        config.getExposureConfiguration()
                .forDomainType(Product.class) //disabling for product Repo
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(unsupportedActions)) //for a singe item
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(unsupportedActions)); //for collection of items

        config.getExposureConfiguration()
                .forDomainType(ProductCategory.class) //disabling for product Repo
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(unsupportedActions)) //for a singe item
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(unsupportedActions)); //for collection of items

    }
}
