#!/usr/bin/env bash

echo "Open browser and go to: http://127.0.0.1:8089/"

#locust -f locust_tests.py --host=http://localhost:8080
#locust -f locust_tests.py --host=http://currency.rtzvznrmps.eu-west-1.elasticbeanstalk.com

HOST=$1

if [ "$HOST" = "" ]; then
   HOST=http://localhost:8080
fi

echo "locust -f locust_tests.py --host=$HOST"

locust -f locust_tests.py --host=$HOST