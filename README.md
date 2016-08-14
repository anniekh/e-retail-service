# e-retail-service

A Simple REST web service using Spring Boot (Spring MVC) with an API that
supports the following:
Create a new product
* Create a new product
* Get a list of all products
* Get details about a product
* Able to update a product
* Able to set price points for different currencies for a product

A product has the following attributes:
* Product ID
* Name
* Description
* Tags(not required)
* One or more price points (only one per currency, with default currency being USD, other
currencies can be added via the API)

# How to use this API:
Load the project on your favourite editor, and compile the maven project or CD to the root directory of the project,
and execute the following command to produce the jar file:
_'mvn package'_

Once the build is completed successfully, execute the following command to start the Spring-Boot application:
_'mvn spring-boot:run'_

You should see that the application has started successfully.
You can now access the API via the localhost url:
_'http://localhost:8080'_

# Create a new product
To make a PUT request to create or update a product, the API end point is:
'http://localhost:8080/product'
The API will return HTTP status **Created** for new product and **OK** for updating existing product.
It accepts JSON as the request body.
Example request body:
_{
	"id": "1",
	"name": "blah",
	"description": "tool",
	"tag": "science",
	"pricePoints": [{
		"currency": "USD",
		"price": "390"
	}]
}_

cURL command to execute:
_curl -i -X PUT -H "Content-Type:application/json" http://localhost:8080/product -d '{json body here}'_

# Get a list of all products
To make a GET request to get a list of existing products, the API end point is:
_'http://localhost:8080/products'_

cURL command to execute:
_'curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/products'_

# Get details about a product
To make a GET request to the detail of an existing product, the API end point is:
_'http://localhost:8080/product/{id}'_
where {id} is the product ID.

cURL command to execute:
_'curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/product/1'_

# Able to set price points for different currencies for a product
To make a POST request to set the price of an existing product, the API end point is:
'http://localhost:8080/product/1/price'
Returns HTTP OK for successful update or NOT FOUND if product ID does not exists.
Accepts JSON as the request body.
Example JSON body:
_{
	"currency": "INR",
	"price": "2900"
}_

cURL command to execute:
_curl -i -X POST -H "Content-Type:application/json" http://localhost:8080/product/1/price -d '{json body here}'_