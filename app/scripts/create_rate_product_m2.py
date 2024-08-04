import requests
import json
import time

base_url = 'http://192.168.1.211:8081/api/rate-products'

def create_rate_product(rate_product_data):
    response = requests.post(base_url, headers={"Content-Type": "application/json"}, data=json.dumps(rate_product_data))
    return response

def get_currency(rate_set_code):
    if rate_set_code.endswith("/MX"):
        return "MXN"
    else:
        return "USD"

def get_incl_opt_set(rate_set_code):
    if rate_set_code == "":
        return None
    else:
        return {"code": "1"}

def create_rate_product_data(rate_set_code):
    return {
        "rateSet": {
            "rateSetCode": rate_set_code
        },
        "product": "MI2",
        "effPkupDate": "2024-07-24",
        "effPkupTime": "12:00",
        "mustPkupBefore": "18:00",
        "comment": "My Inc2",
        "rateType": "N",
        "inclCvg1": True,
        "inclCvg2": False,
        "inclCvg3": False,
        "inclCvg4": False,
        "milesMeth": None,
        "week": None,
        "extraWeek": None,
        "freeMilesHour": 0,
        "graceMinutes": 59,
        "chargeForGrace": True,
        "discountable": True,
        "editable": False,
        "minDaysForWeek": 27,
        "periodMaxDays": None,
        "daysPerMonth": 30,
        "commYn": None,
        "commCat": "Comm Cat",
        "inclOptSet": get_incl_opt_set(rate_set_code),
        "currency": get_currency(rate_set_code),
        "paidFreeDay": "*/*",
        "modDate": "2024-07-24T10:00:00.000+00:00",
        "modTime": 12.0,
        "modEmpl": "ASTJXS",
        "empl": "EMP456"
    }

def main():
    rate_set_codes = [
        "GDLA01/*", "GDLA01/MX", "GDLA01/US",
        "BJXA01/*", "BJXA01/MX", "BJXA01/US",
        "CUNA01/*", "CUNA01/MX", "CUNA01/US",
        "PVRA01/*", "PVRA01/MX", "PVRA01/US",
        "SJDA01/*", "SJDA01/MX", "SJDA01/US"
    ]

    rate_products_list = [create_rate_product_data(rate_set_code) for rate_set_code in rate_set_codes]

    for rate_product in rate_products_list:
        print(f"Creating rate product for rate set: {rate_product['rateSet']['rateSetCode']}")
        print(f"Request data: {json.dumps(rate_product, indent=2)}")
        response = create_rate_product(rate_product)
        print(f"Status Code: {response.status_code}, Response: {response.text}")
        if response.status_code != 200:
            print(f"Failed to create rate product for rate set: {rate_product['rateSet']['rateSetCode']}")
        # time.sleep(3)

if __name__ == "__main__":
    main()
