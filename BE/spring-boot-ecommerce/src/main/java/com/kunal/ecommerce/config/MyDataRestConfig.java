package com.kunal.ecommerce.config;

import com.kunal.ecommerce.entity.Product;
import com.kunal.ecommerce.entity.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration //so that springboot picks up this class
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private EntityManager entityManager;

    @Autowired //for this ex, use of autowired is optional since we have only one constructor, but still leaving it to just to be explicit
    public MyDataRestConfig(EntityManager theEntityManager){ //injecting JPA Entity Manager
        entityManager = theEntityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

        HttpMethod[] unsupportedActions = {HttpMethod.PUT,HttpMethod.POST,HttpMethod.DELETE};
        config.getExposureConfiguration()
                .forDomainType(Product.class) //disabling for product Repo
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(unsupportedActions)) //for a singe item
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(unsupportedActions)); //for collection of items

        config.getExposureConfiguration()
                .forDomainType(ProductCategory.class) //disabling for product Repo
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(unsupportedActions)) //for a singe item
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(unsupportedActions)); //for collection of items

        // call an internal helper method
        exposeIds(config);
    }

    // expose entity ids
    private void exposeIds(RepositoryRestConfiguration config) {

        // - get a list of all entity classes from the entity manager
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        // - create an arraylist of the above entity types
        List<Class> entityClasses = new ArrayList<>();

        // - get the entity types for the entities
        for (EntityType tempEntityType : entities) {
            entityClasses.add(tempEntityType.getJavaType());
        }

        // - expose the entity ids for the array of entity/domain types
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}
