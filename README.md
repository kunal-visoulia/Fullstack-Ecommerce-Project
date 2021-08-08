# Fullstack-Ecommerce-Project
STEPS:
1. setup db tables(create-user.sql(creates a new user),create-products.sql(creates a schema, and inside that schema 2 tables product and procuct_category))

2. start.spring.io spring-boot-starter-data-jpa(Spring Data JPA(dependency name in start.spring.io)), spring-boot-starter-data-rest(Rest Repositiories), mysql-connector-java(MySQL Driver), lombok(for getter and setters and other boilerplate code).<br>Created application.properties file with required db config.<br> Created JPA Entities for db tables.<br> Created REST APIs with Spring Data JPA repositories(interfaces in DAO) and  Spring Data REST.Used Spring Data JPA(Persist data in SQL stores with Java Persistence API using Spring Data and Hibernate), Lombok, Rest Repositories(Exposing Spring Data repositories over REST via Spring Data REST) and MYSQL Driver dependencies. <br>The dialect specifies the type of database used in hibernate so that hibernate generate appropriate type of SQL statements. For connecting any hibernate application with the database, it is required to provide the configuration of SQL dialect.<br>In computer software, a data access object (DAO) is a pattern that provides an abstract interface to some type of database or other persistence mechanism. By mapping application calls to the persistence layer, the DAO provides some specific data operations without exposing details of the database.The Data Access Object (DAO) pattern is a structural pattern that allows us to isolate the application/business layer from the persistence layer (usually a relational database, but it could be any other persistence mechanism) using an abstract API.The functionality of this API is to hide from the application all the complexities involved in performing CRUD operations in the underlying storage mechanism. This permits both layers to evolve separately without knowing anything about each other.

3. RELEASE 1.0: created product-list componenet to get lists of product via a service. creqate a Product class to represent Product[] coming from BE.

```
Release 1.0.0<br/>
Make REST APIs Read Only. So onlt GET requests are allowe else you will get 405 Method Not Allowed.<br/>
Possible Solutions:<br>
1. option 1: Don't use Spring Rest
    1.1 Manually create your own @RestController
    1.2 Manually define methods for GET accesss(@GetMapping)
    1.3 But we lost the Spring Data Rest support for paging, sorting,etc

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

2. Option 2: Use Spring Data REST
    2.1 Configure to disbale required HTTP methods
```

4. RELEASE 2.0: Used HTML template from starter files and integrated the shop view into angular. installed boostrap and font-awesome locally via npm.

5. Added Search for product by category(not related to search bar, these are links in the sidenav, when you click on them, main grid will show products of that category). Route: /category/id or /category(in this case use deafult id). Product List componenet(simple table in release 1.0 but now a whole grid of products) gets product for given categry from BE(the BE returned products regardless of category so added new query method findByCategoryId to REST repo and Spring Data REST automatically expose end pts for query methods under api/products/search/querymethodname?paramlist). Currently i was just hardcoding the ids(category names and ids are static) in UI in sdienav(routerlink="category/1", etc). 
```
Spring Data Rest and Spring Data JPA supports query methods. Spring will construct a query based on method naming conventions. Ex findBy, readBy, queryBy,etc

Page<Product> findByCategoryId(@RequestParam("id") Long id, Pageable pageable); ===> spring will build and execute a query SELECT * FROM PRODUCT WHERE CATEGORY_ID=?;

If you don't want the query bbuilt by spring, you can use @Query annotation to build your own custom query for REST repo method. 
```

6. Made modification in BE to support getting list of categories(names and ids and other info) via REST apis. Expose Entity ids(by updating spring data rest cinfig and using JPA Entity Manager), create class ProductCategory in FE, create new Menu componenent for categoriy list in  sidenav.

```
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
}
There is no entity id at the productcategory level,but, enitity id is embedded in the hateos links. But no easy access, Requires parsing url string. Not ideal. I want the api response to include categoryid and Category Name at category Level only for dyanmic listing of categories in FE.
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
```

7. Added functionality for search products by keyword(search bar). Added new Search method(findByNameContaining) in BE, created new search component,new api method for search, added route for searching(search/:keyword), ProductList componenet still handles  the search results(logic and view reuse for listing products)

8. Created Product details componenet and added api call to get specific product with id.

9. Added Pagination using ng-Bootstrap. added pagination support to ProductService(calls findBycategoryid with page and size parameters). update interface in product.service to map the recieved pagination info.. FLOW: we set new poprties in product list comp that we need to enable pagination, we make api call to get product list for given category id and other pagiantion info, we update the product list data as well as the pagination properties in the componenet.

```
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
    "size" : 10, //items per page(default is 20 for Spring Data REST)
    "totalElements" : 100,
    "totalPages" : 10,
    "number" : 0 //page number; for spring pagenumbers are 0 based, while for ng-bootssrap it is 1 based
  }
}

For pagiantion we need implementation on both BE and FE, but Spring data rest manages that for us through
concepts of page and size. In FE, we use ng-boostrap for pagination. We jsut need to map the above
pagination metadata in FE pagination implementation.
```

10. Added pagination for keyword search. add new api call method to search products and send pagination params, actually everything we did above just for handling pagination in searchmode in productlist component.

11. Created shopping cart status componenet and related code. addtocart buttons data stored in service(Subject) which then propagates changes to cartstatus component to update quantity and bill.

12. Created card details component that just recieves info ffrom cart service and shows cart data wiht increment quantity, decrement quantity and remove item buttons.

13. Checkout Form: Reactive Form(Form control: individual control that tracks the value and validation status; FormGroup: a collection of controls, can create nested groups; Formbuilder:to build the form)<br>Populate countries and States from BE api. Added new tables Country(id(pk),code,name) and State(id(pk),name,country_id(fk references country id)). Added new corresponding JPA Entities and repositories.<br> For State repository, added custom query method findByCountryCode(table:country,column:code) because i want to find states by country code and not just all states. Also added config to make apis /country and /state readonly(becuase there is no need for updation).ran the script for countries and states. api/countries returned list of countries and in each country was its assocaited states object. but we didnot want states so we used @JsonIgnore(Reproducing this scenario: Create Entities for Country and State==> create repository interface only for country==> country contains a List<states> so if you got to /api/countire rn, it will ahve the list of all states embedded into each country==>resolve by adding@jsonignore to country entity List<states>).<br> added luv2shopformservice for credit card expiry date and yesrs suggestions(logic here to show months based on selected year).

INSERT INTO country VALUES 
(1,'BR','Brazil'),
(2,'CA','Canada'),
(3,'DE','Germany'),
(4,'IN','India'),
(5,'TR','Turkey'),
(6,'US','United States');

INSERT INTO state VALUES 
(1,'Acre',1),
(2,'Alagoas',1),
(3,'Amapá',1),
(4,'Amazonas',1),
...,
(57,'Andhra Pradesh',4),
(58,'Arunachal Pradesh',4),
(59,'Assam',4),

though we did @jsonignore which only removes list of states from returned json data, but still in DB avery country contains a list of associated states
```
@OneToMany(mappedBy = "country") 
@JsonIgnore 
private List<State> states;
```
so it finds country with given code and returns the lsit of states. but how is this query method present in States repo???


14. Validation for Checkout Page using new FormControls. We also needed to define getter methods to access these customer info FormControls in the HTMl template to get the actual status, list of errors,etc. ex in  template we do, firstName.invalid to check if validation failed. We only dispaly validation error if user has interacted with the form. When the user changes field value, the control is marked as dirty and when the field loses focus, the control is marked as touched.

15. custom Validator Rule: our current form validation for customer info passes if you only use whitespaces to fill firstname and lastname. Created new custom validator class, notOnlyWhiteSpace

16. Added validation for credit card, billing and shipping info section in checkout page

17. Update Cart totals on checkout page: the checkout componenet will subscribe(subscribe to subject in cartservice) to events from CartServcie. However, since checkout componenet is instantiated later in the application, it will miss out on previous messages nad thus cart totals will be 0. So we neeed to get a replay of the missed messages from subject. So we can use ReplaySubject. But actually we only need the last event/msg(last computed total of items in cart) and not any msgs before that. So we use behaviuor subject. it has a notion of current value and stores the latest msg/event and send that to new subs.

18. Saving the order to BE: We wnat to save our cart orders in BE. we made our custom coded Checkout Contoller and custome coded Checkout Service but why not Spring Data Rest. because its only good for baisc crud but its not best for processing the order using custom business logic ==> generate custom tracking number, save order in db and so on; as it is very limited in terms of customization.

![plot](./img/1.png)
We have our ORDERS basically for the given order that we're saving in the db. a CUSTOMER can have 0 to many ORDERS. 2 lines between orders and address mean we have 2 adresses: billig and shipping. ORDERS has a collection of ORDER_ITEMs. 

![plot](./img/2.png)

We will also need to create a data tranfer object Purchase to send data from FE to BE.<br>
![plot](./img/3.png)
![plot](./img/4.png)

BE architecture: CheckoutController==>CheckoutService==>Spring Data JPA Repo(Customer Repository)==>DB

ran the db script #5



POSTMAN /checkout post api data
```
{
   "customer":{
      "firstName":"kunal",
      "lastName":"vtest2",
      "email":"vtest2@vtest2.com"
   },
   "shippingAddress":{
      "street":"vtest2s",
      "city":"vtest2s",
      "state":"tindas",
      "country":"Canada",
      "zipCode":"vtest2s"
   },
   "billingAddress":{
      "street":"vtest2b",
      "city":"vtest2b",
      "state":"tindab",
      "country":"Canada",
      "zipCode":"vtest2b"
      },
   "order":{
      "totalPrice":36.98,
      "totalQuantity":2
   },
   "orderItems":[
      {
         "imageUrl":"assets/images/products/coffeemugs/coffeemug-luv2code-1000.png",
         "quantity":1,
         "unitPrice":18.99,
         "productId":26
      },
      {
         "imageUrl":"assets/images/products/mousepads/mousepad-luv2code-1000.png",
         "quantity":1,
         "unitPrice":17.99,
         "productId":51
      }
   ]
}

response:
{
    "orderTrackingNumber": "f8b0a5f6-2aab-46d6-8d42-c480668c4d3b"
}

in address table
id,    city,    country,   state,    street,   zip_code
'5', 'vtest2b', 'Canada', 'tindab', 'vtest2b', 'vtest2b'
'6', 'vtest2s', 'Canada', 'tindas', 'vtest2s', 'vtest2s'

in customer table
id, first_name, last_name, email
'3', 'kunal', 'vtest2', 'vtest2@vtest2.com'

in orderitems
id,                                       image_url,              quantity, unit_price, order_id, product_id //orderitems associated with orders via orderid
'5', 'assets/images/products/coffeemugs/coffeemug-luv2code-1000.png', '1', '18.99',          '3', '26'
'6', 'assets/images/products/mousepads/mousepad-luv2code-1000.png', '1', '17.99',             '3', '51'

in orders
id, order_tracking_number, total_price, total_quantity, billing_address_id, customer_id, shipping_address_id, status, date_created, last_updated
'3', 'f8b0a5f6-2aab-46d6-8d42-c480668c4d3b', '36.98', '2', '5', '3', '6', NULL, '2021-08-07 21:08:07.812000', '2021-08-07 21:08:07.812000'

```