import requests
import json

base_url = 'http://192.168.1.211:8081/api/options-rates'

def add_rate_to_option(rate_data):
    response = requests.post(f"{base_url}/add", headers={"Content-Type": "application/json"}, data=json.dumps(rate_data))
    return response

def main():
    # List of rates for LDW1
    data = [
        {"optionCode": "LDW1", "currency": "USD", "primaryRate": 15.600, "xdayRate": 15.60, "privilegeCode": "L0", "pricingCode": "DF"},
        {"optionCode": "LDW1", "currency": "USD", "primaryRate": 20.800, "xdayRate": 20.80, "privilegeCode": "L0", "pricingCode": "DF"},
        {"optionCode": "LDW1", "currency": "USD", "primaryRate": 26.000, "xdayRate": 26.00, "privilegeCode": "L0", "pricingCode": "DF"},
        {"optionCode": "LDW1", "currency": "USD", "primaryRate": 27.100, "xdayRate": 27.10, "privilegeCode": "L0", "pricingCode": "DF"},
        {"optionCode": "LDW1", "currency": "USD", "primaryRate": 37.500, "xdayRate": 37.50, "privilegeCode": "L0", "pricingCode": "DF"},
        {"optionCode": "LDW1", "currency": "MXN", "primaryRate": 0.010, "xdayRate": 0.01, "privilegeCode": "L1", "pricingCode": "DF"},
        {"optionCode": "LDW1", "currency": "MXN", "primaryRate": 0.010, "xdayRate": 0.01, "privilegeCode": "L1", "pricingCode": "DF"},
        {"optionCode": "LDW1", "currency": "MXN", "primaryRate": 0.010, "xdayRate": 0.01, "privilegeCode": "L1", "pricingCode": "DF"},
        {"optionCode": "LDW1", "currency": "MXN", "primaryRate": 0.010, "xdayRate": 0.01, "privilegeCode": "L1", "pricingCode": "DF"},
        {"optionCode": "LDW1", "currency": "MXN", "primaryRate": 0.010, "xdayRate": 0.01, "privilegeCode": "L1", "pricingCode": "DF"},
        {"optionCode": "LDW1", "currency": "USD", "primaryRate": 13.000, "xdayRate": 13.00, "privilegeCode": "L0", "pricingCode": "DF"},
        {"optionCode": "LDW1", "currency": "USD", "primaryRate": 15.600, "xdayRate": 15.60, "privilegeCode": "L0", "pricingCode": "DF"}
    ]

    for rate_data in data:
        response = add_rate_to_option(rate_data)
        print(f"Status Code: {response.status_code}, Response: {response.text}")

if __name__ == "__main__":
    main()
