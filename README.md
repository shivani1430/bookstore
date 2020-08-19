# Bookstore #

Spring Boot based implementation of a Bookstore.

![High level design](/docs/hflow.png "High level design")

## Book store Zoom in

![Book Store UML](/docs/lflow.png "Book Store UML")

## Requirments

* JDK 1.8
* Maven 3.2+
* IDE ( IntelliJ IDEA or Spring Tool Suite (STS))
* mongo

To run the application:

* start mongo server
* run the below command for install dependencies
* run the application

```
  mvn clean install
```

### Add a Book to the store
curl --location --request POST 'http://localhost:8080/book/add' \
--header 'Content-Type: application/json' \
--data-raw '{
    "isbn": "6789054321",
    "title": "minus",
    "author" :"author",
    "price": {
        "amount": 20,
        "currency": "INR"
    }
}'

### Search book based on ISBN/Title/Author
curl --location --request POST 'http://localhost:8080/book/search' \
--header 'Content-Type: application/json' \
--data-raw '{"isbn":"0987654321"}'

### Search media coverage about a book, given its ISBN
curl --location --request GET 'http://localhost:8080/book/media_search?isbn=0987654321'

### Buy a book
curl --location --request POST 'http://localhost:8080/book/buy' \
        --header 'Content-Type: application/json' \
        --data-raw '{
        "userId": "userId",
        "orderItems": [
        {
        "bookId": "5f3ca5bc2115a84e9c11a074",
        "quantity": 2
        },
        {
        "bookId": "5f3ca59f2115a84e9c11a073",
        "quantity": 1
        }
        ],
        "totalAmount": {
        "amount": 40,
        "currency": "INR"
        }
        }'