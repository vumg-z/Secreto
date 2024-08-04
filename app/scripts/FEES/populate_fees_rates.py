import requests
import json

base_url = 'http://192.168.1.211:8081/api/options-rates'

def add_rate_to_option(rate_data):
    response = requests.post(f"{base_url}/add", headers={"Content-Type": "application/json"}, data=json.dumps(rate_data))
    return response

def main():
    rates = [
        {"optionCode": "LOCN", "currency": "DF", "primaryRate": 10.0, "pricingCode": "DF", "privilegeCode": "DF"},
        {"optionCode": "TAX", "currency": "DF", "primaryRate": 16.0, "pricingCode": "DF", "privilegeCode": "DF"},
        {"optionCode": "LRF", "currency": "DF", "primaryRate": 3.0, "pricingCode": "DF", "privilegeCode": "DF"},
    ]

    for rate_data in rates:
        response = add_rate_to_option(rate_data)
        print(f"Status Code: {response.status_code}, Response: {response.text}")

if __name__ == "__main__":
    main()
