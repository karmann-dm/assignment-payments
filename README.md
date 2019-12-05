## Disclaimer
All test scenarios with user cases you can find in file SCENARIOS_TESTS.txt in root folter.
## Test assignment
It contains simple implementation of payment system.
### Structure of api
#### User
User api designed just for logical relations with users and accounts
##### Endpoints
```
POST /user    creates user (see com.karmanno.payments.dto.CreateUserRequest)

GET /user     fetches all users
```
#### Currency
Currency api designed for managing currency list and its states
##### Endpoints
```
POST /currency creates currency (see om.karmanno.payments.dto.CreateCurrencyRequest)
```
#### Price
Price api designed for managing price of sell/buy for currencies
##### Endpoints
```
POST /price  creates currency (see com.karmanno.payments.dto.PriceRequest)

PUT /price   edits currency (see com.karmanno.payments.dto.PriceRequest)

GET /price?from=FROM&to=TO  fetches price FROM/TO
```
#### Account
Account api designed for managing users accounts.
##### Endpoints
```
POST /account creates account (see com.karmanno.payments.dto.CreateAccountRequest)

PUT /account puts money account (see com.karmanno.payments.dto.PutMoneyRequest)

GET /account?userId=USER fetches all accounts owned by USER
```
#### Payment
Payment api designed for provide a payment service by using of which user can perform his payments.
##### Endpoints
```
POST /payment (see com.karmanno.payments.dto.MakePaymentRequest)

GET /payment?account=ACCOUNT fetches all payments by account

GET /payment/ID fetches payment by ID
```


## Run instructions
The default port of server is 8080.
To run an application just do:
```
./mvnw exec:java
```
To run tests just do:
```
./mvnw test
```
