# Getting Started

#### User Registration Restful API
We would like you to develop a RESTful Web Service in Java. The goal of this exercise is to
get an idea of the code you would produce if you were working at Gamesys, therefore your
solution should be of production quality.

Requirements:
1. Must use Java.
2. Should use a build & dependency management tool e.g. Maven or Gradle.
3. We should be able to compile and run your tests e.g. mvn clean verify
4. Please include instructions for running the application.
#### Problem: Develop a Registration Service that implements a /register endpoint taking a JSON body.
#### Required data & validation:
* Username - alphanumeric, no spaces
* Password – min length 4, at least one upper case letter & number
* DoB (Date of Birth) - ISO 8601 format
* Payment Card Number – between 15 and 19 digits
#### Expected responses: 
* If the request body fails to conform to any of the basic validation checks return HTTP
Status code: 400
* Reject registrations if the user is under the age of 18 and return HTTP Status code: 403
* If the username has already been used reject the request and return HTTP Status code:
409
* A successful registration should return HTTP Status code: 201
#### Example request:
curl -X POST \
http://127.0.0.1:8080/register \
-H 'Content-Type: application/json' \
-d '{
"username": "NadezhdaKovrigina ",
"password": "Kovrigina1234 ",
"dob": "1985-05-25",
"paymentCardNumber": "135465748745154"
}'
#### Optional extra:
On start-up allow a list of blocked payment issuer identification numbers to be provided. The
issuer identification number (IIN) is the first 6 digits of the payment card’s number. If the IIN
is blocked registration should fail returning HTTP Status code: 406

# Solution 
Execution Instrucitons-  Following are the two ways to run "cashier-tech" either by Spring Boot or by Maven 

### 1. By Spring-Boot	
#### Optional extra: with IIN
	mvn spring-boot:run -Dspring-boot.run.arguments=--iin.blockedSet=456789,987654
#####Optional extra: without IIN	
	mvn spring-boot:run
### 2. By Maven
##### Build Jar 	
	mvn clean package
#### Execute Jar-  Optional extra: with IIN
	mvn spring-boot:run -Dspring-boot.run.arguments=--iin.blockedSet=456789,987654
####Execute Jar- Optional extra: without IIN	
	java -jar target/cashier-tech-1.0.0.RELEASE.jar

####Test with following cURL command:

 curl -i -X POST \
http://127.0.0.1:8080/register \
-H 'Content-Type: application/json' \
-d '{
"username": "Gourav",
"password": "Garg1234",
"dob": "1980-02-21",
"paymentCardNumber": "12345678912345678"
}'
