# Taco App
## What does our taco app do?
- registers/creates users
- logs users in & creating a session
- creates taco 
- add taco to order-list of tacos for that order
- takes ordering info

## what do we want the taco app to do? Future functionality
- see most recent taco creations (from other users)
- see status of current order: placed, cooking, delivering, arrived
- see contents of current order

## what is the data in our taco app?
- ingredients
- users
- taco (made of ingredients)
- orders (made of taco & users)

## endpoints
- /tacos
  - POST: takes a validated Taco object
      - saves Taco object to SQL table (TACO)
      - adds Taco object to the current order
  
  Request Body:
  ```json
  {
    "ingredientIds": ["MISA", "RTSA", "FAJT", "IMPB"],
    "name": "sally-taco"
  }
  ```

- /orders
  POST: takes a valid Order object and 
    -save that Order object to our TACO_ORDERS table
  
- /orders/{orderId}
  
  PUT: takes a valid Order object, finds by path param ID and updates the order

Request body (same for POST /order & PUT /order/{orderId}):
  ```json
{
    "name": "Sally Taco",
    "street": "123 Street",
    "city": "City",
    "state": "MO",
    "zip": "12345",
    "ccNumber": "1111222233334444",
    "ccExpiration": "12/22",
    "ccCVV": "111",
    "userId": "1",
    "tacoIds": [
        1
    ]
}
  ```

- /authenticate
    POST: takes a valid username and password, returns a JWT token
  
** Save the JWT token and [add to every header](https://learning.postman.com/docs/sending-requests/authorization/#bearer-token) a key: Authorization with the value: Bearer _paste token value here_

Request Body to authenticate:   

```json
{
"username":"spectacular",
"password": "supersecure"
}
```

- /register 
    POST: takes a valid User object and adds it to the user table
  
Request Body for registering a new user  

```json
{
  "username":"spectacular",
  "password":"supersecure",
  "fullname": "Sparkles Spectacular",
  "street": "1234 Street",
  "city": "Midwestern City",
  "state": "MO",
  "zip": "12345",
  "phoneNumber": "3141234567"
}
```

##_to be implemented later_##
- /tacos/recent
  - GET: returns a list of most recent Taco objects
- /orders/current
  - GET: takes orderId, maybe also userId as params & returns the current Order and it's taco-list
