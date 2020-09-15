# checking-account-service
This microservice  is responsible for the checking account domain, including a ledger for its transactions.

## run
to run it, just execute the following command:

```
./gradlew run
```

on the command line. Alternatively, you can still run it through an IDE like IntelliJ.

## route calls examples

curl --location --request POST 'http://localhost:7000/account' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "example",
    "tax_id": "123456789"
}'

curl --location --request POST 'http://localhost:7000/account/{accout-id}/deposit' \
--header 'Content-Type: application/json' \
--data-raw '{
   "value": 35.0
}'

curl --location --request POST 'http://localhost:7000/account/{accout-id}/withdrawal' \
--header 'Content-Type: application/json' \
--data-raw '{
   "value": 35.0
}'

curl --location --request POST 'http://localhost:7000/account/{accout-id}/transfer' \
--header 'Content-Type: application/json' \
--data-raw '{
   "account_id": {another_account},
   "value": 35.0
}'


