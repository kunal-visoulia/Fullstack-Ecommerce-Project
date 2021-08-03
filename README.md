# Fullstack-Ecommerce-Project
STEPS:
1. setup db tables 

...

2. Checkout Form: Populate countries and States from BE api. Added new tables Country(id(pk),code,name) and State(id(pk),name,country_id(fk references country id)). Added new corresponding JPA Entities and repositories. For State repository, added custom query method findByCountryCode because i want to find states by country code and not just all states. Also added config to make apis /country and /state readonly(becuase there is no need for updation).ran the script for countrie snad states. ap/countries returned list of countries and in each country was its assocaited states objec. but we didnot want states so we used @JsonIgnore(Reproducing this scenario: Create Entities for Country and State==> create repository interface only for country==> country contains a List<states> so if you got to /api/countire rn, it will ahve the list of all states embedded into each country==>resolve by adding@jsonignore to country entity List<states>).

3. Validation for Checkout Page using new FormControls. We also needed to define getter methods to access these customer info FormControls in the HTMl template to get the actual status, list of errors,etc. ex int template we do, firstName.invalid to check if validation failed. We only dispaly validation error if user has interacted with the form. When the user changes field value, the control is marked as dirty and when the field loses focus, the control is marked as touched.

4. custom Validator Rule: our current form validation for customer info passes if you only use whitespaces to fill firstname and lastname. Created new custom validator class, notOnlyWhiteSpace

5. Added validation for credit card, billing and shipping info section in checkout page

6. Update Cart totals on checkout page: the checkout componenet will subscribe(subscribe to subject in cartservice) to events from CartServcie. However, since checkout componenet is instantiated later in the application, it will miss out on previous messages nad thus cart totals will be 0. So we neeed to get a replay of the missed messages from subject. So we can use ReplaySubject. But actually we only need the last event/msg(last computed total of items in cart) and not any msgs before that. So we use behaviuor subject. it has a notion of current value and stores the latest msg/event and send that to new subs.

7. Saving the order to BE: We wnat to save our cart orders in BE. we made our custom Checkout Contoller andCheckout Service but why not Spring Data Rest. because its only good for baisc crud but its not best for processing the order using custom business logic ==> generate custom tracking number, save order in db and so on; as it is very limited in terms of customization.

![plot](./img/1.png)
![plot](./img/2.png)

We will also need to create a data tranfer object Purchase to send data from FE to BE. ran the db script #5
BE architecture: CheckoutController==>CheckoutService==>Spring Data JPA Repo(Customer Repository)==>DB



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

In computer software, a data access object (DAO) is a pattern that provides an abstract interface to some type of database or other persistence mechanism. By mapping application calls to the persistence layer, the DAO provides some specific data operations without exposing details of the database.The Data Access Object (DAO) pattern is a structural pattern that allows us to isolate the application/business layer from the persistence layer (usually a relational database, but it could be any other persistence mechanism) using an abstract API.The functionality of this API is to hide from the application all the complexities involved in performing CRUD operations in the underlying storage mechanism. This permits both layers to evolve separately without knowing anything about each other.

By default, Spring Data REST does not expose entity ids. We need entity ids. We need enity  ids for: Get a list of product categories by id, master/detail view to get a given product by id. http://localhost:8080/api/product-category<br>
{
  "_embedded" : {
    "productCategory" : [ {
      "categoryName" : "Books",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/api/product-category/1" //hateos link
        },
        "productCategory" : {
          "href" : "http://localhost:8080/api/product-category/1"
        },
        "products" : {
          "href" : "http://localhost:8080/api/product-category/1/products"
        }
      }
    },...
}<br>
There is no entity id at the productcategory level,but, enitity id is embedded in the hateos links. But no easy access, Requires parsing url string. Not ideal. I want the api response to include categoryid and Category Name at category Level only for dyanimc listing of categories in FE.<br>
{
  "_embedded" : {
    "productCategory" : [ {
      "id" : 1, // here
      "categoryName" : "Books",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/api/product-category/1"
        },
        "productCategory" : {
          "href" : "http://localhost:8080/api/product-category/1"
        },
        "products" : {
          "href" : "http://localhost:8080/api/product-category/1/products"
        }
      }
    },...}


SpringData Rest also send Pagination metadata http://localhost:8080/api/products?pages=0&size=10<br>
{
  "_embedded" : {
    "products" : [ {
      "id" : 1,
      "sku" : "BOOK-TECH-1000",
      "name" : "Crash Course in Python",
      "description" : "Learn Python at your own pace. The author explains how the technology works in easy-to-understand language. This book includes working examples that you can apply to your own projects. Purchase the book and get started today!",
      "unitPrice" : 14.99,
      "imageUrl" : "assets/images/products/books/book-luv2code-1000.png",
      "active" : true,
      "unitsInStock" : 100,
      "dateCreated" : "2021-08-01T02:45:21.000+00:00",
      "lastUpdated" : null,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/api/products/1"
        },
        "product" : {
          "href" : "http://localhost:8080/api/products/1"
        },
        "category" : {
          "href" : "http://localhost:8080/api/products/1/category"
        }
      }
    }, ...,
  "page" : {
    "size" : 10,
    "totalElements" : 100,
    "totalPages" : 10,
    "number" : 0 //for spring pagenumbers are 0 based, while for ng-bootssrap it is 1 based
  }
}

For pagiantion we need implelkemntation on both BE and FE, but Spring data rest manages that for us throguh concepts of page and size. In FE, we use ng-boostrap for pagination. We jsut need to map the above pagination metadata in FE pagination implementation.


