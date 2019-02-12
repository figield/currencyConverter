#!/usr/bin/env bash

echo "Open browser and go to: http://127.0.0.1:8089/"

locust -f locust_tests.py --host=http://localhost:8080

