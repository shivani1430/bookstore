# Bookstore #

Spring Boot based implementation of a Bookstore.

## Requirments

* JDK 1.8
* Maven 3.2+
* IDE ( IntelliJ IDEA or Spring Tool Suite (STS))
* mongo

To run the application:

* start mongo server
* run the below command

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
--data-raw '{"isbn":"0987654321","title":"exp", "author":"kate"}'

### Search media coverage about a book, given its ISBN
curl --location --request GET 'http://localhost:8080/book/media_search?isbn=1234567890'

### Buy a book
curl --location --request POST 'http://localhost:8080/book/buy' \
--header 'Content-Type: application/json' \
--data-raw '{"bookId":"5f0d9ba5d78d8873e2d71d5a","transactionId":"0987654321","user":{"userId":"userId","email":"shivani@gmail.com","phoneNo":"0987654321","name":"shivani"}}'