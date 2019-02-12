from locust import HttpLocust, TaskSet, task
import random
from datetime import datetime, timedelta

CURRENCY = ["ISK", "CAD", "MXN", "CHF", "AUD", "CNY", "GBP", "USD", "SEK", "NOK", "TRY", "IDR", "ZAR", "HRK", "EUR",
            "HKD", "ILS", "NZD", "MYR", "JPY", "CZK", "SGD", "RUB", "RON", "HUF", "BGN", "INR", "KRW", "DKK", "THB",
            "PHP", "PLN", "BRL"]


class UserBehavior(TaskSet):

    # Number of different calls:
    # 33
    @task
    def get_latest_tests(self):
        base = random.choice(CURRENCY)
        self.client.get(
            "/latest?base=%s" % base,
            name="Get the latest currency")

    # Number of different calls:
    # 33 * 33 * 10 = 10890
    @task
    def get_convert_tests(self):
        currencyTo = random.choice(CURRENCY)
        currencyFrom = random.choice(CURRENCY)
        amount = random.randint(0, 9)  # 0-9
        self.client.get(
            "/convert?fromamount=%i&to=%s&from=%s" % (amount, currencyTo, currencyFrom),
            name="Convert amount of currencyFrom to currencyTo")

    # Number of different calls:
    # 33 * 33 * 10 = 10890
    @task
    def get_average_tests(self):
        currencyTo = random.choice(CURRENCY)
        currencyFrom = random.choice(CURRENCY)
        startAt = datetime.today() - timedelta(days=10)
        startAt = startAt.date().isoformat()
        endAt = datetime.today() - timedelta(days=1)
        endAt = endAt.date().isoformat()
        self.client.get(
            "/average?from=%s&to=%s&start_at=%s&end_at=%s" % (currencyFrom, currencyTo, startAt, endAt),
            name="Get average currency value in the last 10 days")

    # Number of different calls:
    # 33 * 10 = 330
    @task
    def get_standarddeviation_tests(self):
        base = random.choice(CURRENCY)
        startAt = datetime.today() - timedelta(days=10)
        startAt = startAt.date().isoformat()
        endAt = datetime.today() - timedelta(days=1)
        endAt = endAt.date().isoformat()
        self.client.get(
            "/sd?base=%s&start_at=%s&end_at=%s" % (base, startAt, endAt),
            name="Get standard deviation value in the last 10 days")


class WebsiteUser(HttpLocust):
    task_set = UserBehavior
    # The Locust class(as well as HttpLocust since it’s a subclass)
    # also allows one to specify minimum and maximum wait time
    # in milliseconds—per simulated user—between the execution
    # of tasks (min_wait and max_wait) as well as other user behaviours.
    # By default the time is randomly chosen uniformly
    # between min_wait and max_wait.
    min_wait = 100
    max_wait = 1000
