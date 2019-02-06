# currencyConverter

Java REST application for currency conversion.


Task with "CLIENT" described functionalities/Use Cases is :

Using https://exchangeratesapi.io/ API as source of data create Exchange Rate Service that will offer following functionality:

- Will allow a South African Republic national to check how much he has to pay for INPUT amount of Norwegian Krone

- Allow an American to Calculate average price of Brazilian Real between 12/24/2017 and 12/27/2017

- Will display currency with highest stability in price quoted against Croatian Kuna for GIVEN period


Assumptions :

Current exchange rates served by exchangeratesapi.io are changed every 60 seconds

https://exchangeratesapi.io/ can serve up to 100 requests per second and your service needs to serve up to a 1000

You have up to 5 machines you can use to run necessary systems

Historical data doesn’t change

In your current setup you don’t have ability to create a persistent storage