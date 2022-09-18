# order-payment-parser

This application parse JSON and CSV files. After parsing it prints in the console.

## Input
JSON and CSV files must only contain following fields
  Order ID : Integer
  amount : Double
  currency : String
  comment : String
  
## Build
Build Command is
mvn clean install

## Run
Run Command is
java -jar orders_parser.jar order1.csv order2.json
  
## Output
Output would be in console with following fields
  Order ID : Integer
  amount : Double
  currency : String
  comment : String
  fileName : String
  line : Integer
  result : String
  
 Example 
 {“id”:1,“orderId”:1,”amount”:100,”comment”:”order payment”,”filename”:”orders.csv”,”line”:1,”result”:”OK”}
