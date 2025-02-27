# StayEase
StayEase API Documentation

Overview

StayEase is a hotel booking system that allows users to register, log in, create hotels, update hotels, delete hotels, and book or cancel hotel reservations.

Base URL

http://localhost:30080

Authentication

User Registration

Endpoint:

POST /auth/register

Body (JSON):

{
  "email": "Ram1@example.com",
  "password": "Ram1@example.com",
  "first_name": "Ram",
  "last_name": "Ram1",
  "role": "CUSTOMER",
  "hotel": null
}

User Login

Endpoint:

POST /auth/login

Body (JSON):

{
  "email": "Ram1@example.com",
  "password": "Ram1@example.com"
}

Hotel Management

Create a Hotel

Endpoint:

POST /hotels/createHotel

Authorization: Bearer Token
Body (JSON):

{
  "name": "Hotel Nirma",
  "location": "New York",
  "description": "A luxurious 5-star hotel in the heart of the city.",
  "available_rooms": 50
}

Update a Hotel

Endpoint:

PUT /hotels/updateHotel?hotelId=1

Authorization: Bearer Token
Query Params:

hotelId=1

Body (JSON):

{
  "name": "Hotel Nirma",
  "location": "New York City",
  "description": "A luxurious 5-star hotel in the heart of the city.",
  "available_rooms": 100
}

Delete a Hotel

Endpoint:

DELETE /hotels/delete/1

Authorization: Bearer Token

Booking System

Book a Hotel

Endpoint:

POST /hotels/book

Authorization: Bearer Token
Body (JSON):

{
  "userId": 4,
  "hotelsId": 2
}

Cancel a Booking

Endpoint:

POST /hotels/cancel

Authorization: Bearer Token
Body (JSON):

{
  "userId": 4,
  "hotelsId": 2
}

Steps to Run JAR Files

Build the JAR File:

If using Maven:

mvn clean package

If using Gradle:

gradle build

Navigate to the Target/Build Directory:

cd target  # For Maven
cd build/libs  # For Gradle

Run the JAR File:

java -jar stayease.jar

(Replace stayease.jar with the actual JAR file name generated during the build process.)

Verify the Application is Running:

Open a browser and navigate to:

http://localhost:30080

Check API endpoints using Postman or cURL.

Notes

All API calls requiring authentication use Bearer Token.

Ensure correct JSON format in the request body.

Update and delete operations require valid hotelId or userId parameters.

Future updates may include advanced booking features and user role management.
