import requests
import json

base_url = 'http://192.168.1.211:8081/api/options-rates'

def add_rate_to_option(rate_data):
    response = requests.post(f"{base_url}/add", headers={"Content-Type": "application/json"}, data=json.dumps(rate_data))
    return response

def main():
    rates = [
        {"optionCode": "FC02", "currency": "USD", "primaryRate": 23.5, "xDayRate": 23.5, "pricingCode": "20", "privilegeCode": "L2"},
        {"optionCode": "FC02", "currency": "USD", "primaryRate": 16.8, "xDayRate": 16.8, "pricingCode": "21", "privilegeCode": "L2"},
        {"optionCode": "FC02", "currency": "USD", "primaryRate": 12.5, "xDayRate": 12.5, "pricingCode": "25", "privilegeCode": "L2"},
        {"optionCode": "FC02", "currency": "USD", "primaryRate": 14.1, "xDayRate": 14.1, "pricingCode": "29", "privilegeCode": "L2"},
        {"optionCode": "FC02", "currency": "USD", "primaryRate": 14.3, "xDayRate": 14.3, "pricingCode": "40", "privilegeCode": "L2"},
        {"optionCode": "FC02", "currency": "USD", "primaryRate": 16.7, "xDayRate": 16.7, "pricingCode": "45", "privilegeCode": "L2"},
        
        {"optionCode": "FC02", "currency": "USD", "primaryRate": 36.7, "xDayRate": 36.7, "pricingCode": "20", "privilegeCode": "L0"},
        {"optionCode": "FC02", "currency": "USD", "primaryRate": 38.3, "xDayRate": 38.3, "pricingCode": "21", "privilegeCode": "L0"},
        {"optionCode": "FC02", "currency": "USD", "primaryRate": 40.0, "xDayRate": 40.0, "pricingCode": "25", "privilegeCode": "L0"},
        {"optionCode": "FC02", "currency": "USD", "primaryRate": 47.5, "xDayRate": 47.5, "pricingCode": "29", "privilegeCode": "L0"},
        {"optionCode": "FC02", "currency": "USD", "primaryRate": 48.3, "xDayRate": 48.3, "pricingCode": "40", "privilegeCode": "L0"},
        {"optionCode": "FC02", "currency": "USD", "primaryRate": 56.4, "xDayRate": 56.4, "pricingCode": "45", "privilegeCode": "L0"},
        
        {"optionCode": "FC02", "currency": "MXN", "primaryRate": 649.5, "xDayRate": 649.5, "pricingCode": "20", "privilegeCode": "L1"},
        {"optionCode": "FC02", "currency": "MXN", "primaryRate": 641.5, "xDayRate": 641.5, "pricingCode": "21", "privilegeCode": "L1"},
        {"optionCode": "FC02", "currency": "MXN", "primaryRate": 686.0, "xDayRate": 686.0, "pricingCode": "25", "privilegeCode": "L1"},
        {"optionCode": "FC02", "currency": "MXN", "primaryRate": 701.5, "xDayRate": 701.5, "pricingCode": "29", "privilegeCode": "L1"},
        {"optionCode": "FC02", "currency": "MXN", "primaryRate": 702.0, "xDayRate": 702.0, "pricingCode": "40", "privilegeCode": "L1"},
        {"optionCode": "FC02", "currency": "MXN", "primaryRate": 858.0, "xDayRate": 858.0, "pricingCode": "45", "privilegeCode": "L1"},
        
        {"optionCode": "FC02", "currency": "MXN", "primaryRate": 649.5, "xDayRate": 649.5, "pricingCode": "20", "privilegeCode": "DF"},
        {"optionCode": "FC02", "currency": "USD", "primaryRate": 36.7, "xDayRate": 36.7, "pricingCode": "20", "privilegeCode": "DF"},
        {"optionCode": "FC02", "currency": "MXN", "primaryRate": 641.5, "xDayRate": 641.5, "pricingCode": "21", "privilegeCode": "DF"},
        {"optionCode": "FC02", "currency": "MXN", "primaryRate": 586.0, "xDayRate": 586.0, "pricingCode": "25", "privilegeCode": "DF"},
        {"optionCode": "FC02", "currency": "MXN", "primaryRate": 701.5, "xDayRate": 701.5, "pricingCode": "29", "privilegeCode": "DF"},
        {"optionCode": "FC02", "currency": "MXN", "primaryRate": 702.0, "xDayRate": 702.0, "pricingCode": "40", "privilegeCode": "DF"},
        {"optionCode": "FC02", "currency": "MXN", "primaryRate": 858.0, "xDayRate": 858.0, "pricingCode": "45", "privilegeCode": "DF"},
    ]

    for rate_data in rates:
        response = add_rate_to_option(rate_data)
        print(f"Status Code: {response.status_code}, Response: {response.text}")

if __name__ == "__main__":
    main()
