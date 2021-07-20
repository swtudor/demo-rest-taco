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
- /design
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
 
_to be implemented later_   
- /design/recent
    - GET: returns a list of most recent Taco objects

- /orders/current
  - GET: takes orderId, maybe also userId as params & returns the current Order and it's taco-list
  

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

- /registration 
    - POST: takes a valid User object and adds it to the user table
    ? possibly start a session

## JSON for requests