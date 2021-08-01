# Fullstack-Ecommerce-Project
Used Spring Data JPA(Persist data in SQL stores with Java Persistence API using Spring Data and Hibernate), Lombok, Rest Repositories(Exposing Spring Data repositories over REST via Spring Data REST) and MYSQL Driver dependencies.

The dialect specifies the type of database used in hibernate so that hibernate generate appropriate type of SQL statements. For connecting any hibernate application with the database, it is required to provide the configuration of SQL dialect.

Release 1.0.0<br/>
Make REST APIs Read Only. So onlt GET requests are allowe else you will get 405 Method Not Allowed.<br/>
Possible Solutions:<br>
1. option 1: Don't use Spring Rest
    1.1 Manually create your own @RestController
    1.2 Manually define methods for GET accesss(@GetMapping)
    1.3 But we lost the Spring Data Rest support for paging, sorting,etc
    ```
    /api
    {
  "_links" : {
    "productCategory" : {
      "href" : "http://localhost:8080/api/product-category{?page,size,sort}",
      "templated" : true
    },
    "products" : {
      "href" : "http://localhost:8080/api/products{?page,size,sort}",
      "templated" : true
    },
    "profile" : {
      "href" : "http://localhost:8080/api/profile"
    }
  }
}
    ```


2. Option 2: Use Spring Data REST
    2.1 Configure to disbale required HTTP methods

Spring Data Rest and Spring Data JPA supports query methods. Spring will construct a query based on method naming conventions. Ex findBy, readBy, queryBy,etc<br>
Page<Product> findByCategoryId(@RequestParam("id") Long id, Pageable pageable); ===> spring will build and execute a query SELECT * FROM PRODUCT WHERE CATEGORY_ID=?;<br>
If you don't want the query bbuilt by spring, you can use @Query annotation to build your own custom query for REST repo method. Data REST automitcally exposes endpts for query methods: /search/queryMethodName


