# Project info
This is a simple application based on micro-services architecture implemented in Java using [Spring boot](https://spring.io/projects/spring-boot) and [MySQL Database](https://www.mysql.com/). Application employ [Maven](https://maven.apache.org/) building tool to compile each micro-service and [Docker Compose](https://docs.docker.com/compose/) to assemble services into fully-working, horizontally scalable backend server. The main feature of the app is to provide access to resources through humble REST API available under URL: `http://localhost:8080/v1/credits` after running the app.

# Building application
### Prerequisites:
 - [Docker Engine](https://docs.docker.com/install/overview/)
 - [Docker Compose](https://docs.docker.com/compose/install/)
 
 ### Running application
- After acquired necessary prerequisites open terminal and navigate to the directory into which you want to clone the project.
- Execute the following command: `git clone https://github.com/psobow/microservices-app`.
- After complete fetching data go into `microservices-app` directory with cloned project by command `cd microservices-app` and execute the following command `docker-compose up`.
- After a while, you should have a running server and listen on port 8080, ready-to-handle your requests.

# Application workflow
## Creating new credit
To create new credit we have to send POST request with JSON type request body to this URL `http://localhost:8080/v1/credits`.
  
#### Example request body content:
```
{
    "customerDto": 
    {
      "firstName": "Patryk",
      "pesel": "12345678901",
      "surname": "Sobow"
    },
    "productDto": 
    {
      "productName": "Car",
      "value": 50000
    },
    "creditDto": 
    {
      "creditName": "Car loan"
    }
}
```
#### Example server response:

`1` - which is ID newly created credit

  
#### Input restrictions:

- Each property can not be null or empty.
- **firstName**, **surname** can contain only letters.
- **pesel** must contain exactly eleven digits.
- **creditName** must be between 1 and 200 characters.

#### Example server response after post request with invalid input data
```
{
    "timestamp": "2019-08-26T05:18:00.365+0000",
    "status": 400,
    "error": "Bad Request",
    "errors": 
    [
        {
            "codes": 
            [
                "Pattern.creditDataDto.customerDto.firstName",
                "Pattern.customerDto.firstName",
                "Pattern.firstName",
                "Pattern.java.lang.String",
                "Pattern"
            ],
            "defaultMessage": "First name can contain only letters"",
            "objectName": "creditDataDto",
            "field": "customerDto.firstName",
            "rejectedValue": "Patryk1",
            "bindingFailure": false,
            "code": "Pattern"
        }
    ],
    "message": "Validation failed for object='creditDataDto'. Error count: 1",
    "path": "/v1/credits"
}
```
  

## Retrieving data from the database
To retrieve data from the database you should send GET request to this URL `http://localhost:8080/v1/credits`.

#### Example server response:

```
[
    {
        "customerDto": 
        {
            "creditId": 2,
            "firstName": "Patryk",
            "pesel": "12345678901",
            "surname": "Sobow"
        },
        "productDto": 
        {
            "creditId": 2,
            "productName": "Car",
            "value": 50000
        },
        "creditDto": 
        {
            "creditName": "Car loan"
        }
    }
]
```


## Deleting data from the database
To delete data from data base you should send DELETE request to this URL `http://localhost:8080/v1/credits/ID` where `ID` is path variable respondent to ID of credit which is going to be deleted.

#### example server response: 
none


# Architecture details
Inside the application, we may distinguish three independent micro-services such as credit-service, customer-service, and product-service each of them is responsible for managing certain data.

### Credit-service

The main part of the application. It's delegate tasks to other services and provides REST API for communication with the app from the outer world. May handle HTTP requests such as: 
- GET - Retrieving and compounding data from databases. Returning a list of the data of every credit stored in databases. 
- POST - Validate input and create new credit data. Delegate persisting in database certain parts of data to other services.
- DELETE - Delete credit with the given ID.

### Customer-service

May be requested only by credit-service, responsible for managing customer entity. It will persist data when receive POST request, delete data with given ID after receive DELETE request and return list of all customers which have been stored inside database after receive GET request.

### Product-service

May be requested only by credit-service, responsible for managing product entity. It will persist data when receive POST request, delete data with given ID after receive DELETE request and return list of all products which have been stored inside database after receive GET request.

<p align="center">
  <img src="https://github.com/psobow/microservices-app/blob/master/misc/architecture.png"/>
</p>

# Model data stored inside databases
#### Credit
- **long** id
- **String** creditName
#### Customer
- **long** id
- **String** firstName
- **String** surname
- **String** pesel
- **long** creditId
#### Product
- **long** id
- **String** productName
- **int** value
- **long** creditId
 
# Data-Transfer-Object - data returned and posted into the application
#### CreditDto
- **String** creditName

#### CustomerDto
- **String** firstName
- **String** surname
- **String** pesel
- **long** creditId

#### ProductDto
- **String** productName
- **int** value
- **long** creditId

# Application Testing
Due to lack of spare time and insufficient knowledge od Docker, I did not manage to implement a satisfactory amount of unit test suites as well as integration test suites. Instead of testing application with JUnit and Mockito library, I decided to use a convenient tool [Postman](https://www.getpostman.com/), which appeared to be the best possible way to test genuine application behavior. 

- You can get my neat postman-test-suite under this [link](https://www.getpostman.com/collections/155d9fd4b82b9288e689) 




