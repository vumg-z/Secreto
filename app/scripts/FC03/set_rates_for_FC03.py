import requests
import json

base_url = 'http://192.168.1.211:8081/api/options-rates'

def add_rate_to_option(rate_data):
    response = requests.post(f"{base_url}/add", headers={"Content-Type": "application/json"}, data=json.dumps(rate_data))
    return response

def main():
    rates = [
        {"optionCode": "FC03", "currency": "MXN", "primaryRate": 1002.5, "xDayRate": 1002.5, "pricingCode": "40", "privilegeCode": "L1"},
        {"optionCode": "FC03", "currency": "MXN", "primaryRate": 1258.0, "xDayRate": 1258.0, "pricingCode": "45", "privilegeCode": "L1"},
        {"optionCode": "FC03", "currency": "MXN", "primaryRate": 600.0, "xDayRate": 600.0, "pricingCode": "20", "privilegeCode": "L2"},
        {"optionCode": "FC03", "currency": "USD", "primaryRate": 36.0, "xDayRate": 36.05, "pricingCode": "20", "privilegeCode": "L2"},
        {"optionCode": "FC03", "currency": "MXN", "primaryRate": 600.0, "xDayRate": 600.0, "pricingCode": "21", "privilegeCode": "L2"},
        {"optionCode": "FC03", "currency": "USD", "primaryRate": 29.3, "xDayRate": 29.3, "pricingCode": "21", "privilegeCode": "L2"},
        {"optionCode": "FC03", "currency": "USD", "primaryRate": 25.0, "xDayRate": 25.0, "pricingCode": "25", "privilegeCode": "L2"},
        {"optionCode": "FC03", "currency": "USD", "primaryRate": 26.6, "xDayRate": 26.6, "pricingCode": "29", "privilegeCode": "L2"},
        {"optionCode": "FC03", "currency": "USD", "primaryRate": 26.8, "xDayRate": 26.8, "pricingCode": "40", "privilegeCode": "L2"},
        {"optionCode": "FC03", "currency": "USD", "primaryRate": 33.3, "xDayRate": 33.3, "pricingCode": "45", "privilegeCode": "L2"},
        
        {"optionCode": "FC03", "currency": "MXN", "primaryRate": 949.5, "xDayRate": 949.5, "pricingCode": "20", "privilegeCode": "DF"},
        {"optionCode": "FC03", "currency": "MXN", "primaryRate": 941.5, "xDayRate": 941.5, "pricingCode": "21", "privilegeCode": "DF"},
        {"optionCode": "FC03", "currency": "MXN", "primaryRate": 886.0, "xDayRate": 886.0, "pricingCode": "25", "privilegeCode": "DF"},
        {"optionCode": "FC03", "currency": "MXN", "primaryRate": 1001.5, "xDayRate": 1001.5, "pricingCode": "29", "privilegeCode": "DF"},
        {"optionCode": "FC03", "currency": "MXN", "primaryRate": 1002.5, "xDayRate": 1002.5, "pricingCode": "40", "privilegeCode": "DF"},
        {"optionCode": "FC03", "currency": "MXN", "primaryRate": 1258.0, "xDayRate": 1258.0, "pricingCode": "45", "privilegeCode": "DF"},
        
        {"optionCode": "FC03", "currency": "USD", "primaryRate": 49.2, "xDayRate": 49.2, "pricingCode": "20", "privilegeCode": "L0"},
        {"optionCode": "FC03", "currency": "USD", "primaryRate": 50.8, "xDayRate": 50.8, "pricingCode": "21", "privilegeCode": "L0"},
        {"optionCode": "FC03", "currency": "USD", "primaryRate": 52.5, "xDayRate": 52.5, "pricingCode": "25", "privilegeCode": "L0"},
        {"optionCode": "FC03", "currency": "USD", "primaryRate": 60.0, "xDayRate": 60.0, "pricingCode": "29", "privilegeCode": "L0"},
        {"optionCode": "FC03", "currency": "USD", "primaryRate": 60.8, "xDayRate": 60.8, "pricingCode": "40", "privilegeCode": "L0"},
        {"optionCode": "FC03", "currency": "USD", "primaryRate": 73.1, "xDayRate": 73.1, "pricingCode": "45", "privilegeCode": "L0"},
        {"optionCode": "FC03", "currency": "MXN", "primaryRate": 949.5, "xDayRate": 949.5, "pricingCode": "20", "privilegeCode": "L1"},
        {"optionCode": "FC03", "currency": "MXN", "primaryRate": 941.5, "xDayRate": 941.5, "pricingCode": "21", "privilegeCode": "L1"},
        {"optionCode": "FC03", "currency": "MXN", "primaryRate": 886.0, "xDayRate": 886.0, "pricingCode": "25", "privilegeCode": "L1"},
        {"optionCode": "FC03", "currency": "MXN", "primaryRate": 1001.5, "xDayRate": 1001.5, "pricingCode": "29", "privilegeCode": "L1"},
        {"optionCode": "FC03", "currency": "MXN", "primaryRate": 1002.5, "xDayRate": 1002.5, "pricingCode": "40", "privilegeCode": "L1"},
    ]

    for rate_data in rates:
        response = add_rate_to_option(rate_data)
        print(f"Status Code: {response.status_code}, Response: {response.text}")

if __name__ == "__main__":
    main()
