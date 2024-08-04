import requests
import json

base_url = 'http://192.168.1.211:8081/api/options-rates'

def add_rate_to_option(rate_data):
    response = requests.post(f"{base_url}/add", headers={"Content-Type": "application/json"}, data=json.dumps(rate_data))
    return response

def main():
    rates = [
        {"optionCode": "FC01", "currency": "MXN", "primaryRate": 580.0, "weeklyRate": 580.0, "pricingCode": "29", "privilegeCode": "L0"},
        {"optionCode": "FC01", "currency": "USD", "primaryRate": 29.0, "weeklyRate": 29.0, "pricingCode": "20", "privilegeCode": "L0"},
        {"optionCode": "FC01", "currency": "MXN", "primaryRate": 580.0, "weeklyRate": 580.0, "pricingCode": "40", "privilegeCode": "L0"},
        {"optionCode": "FC01", "currency": "USD", "primaryRate": 29.0, "weeklyRate": 29.0, "pricingCode": "40", "privilegeCode": "L0"},
        {"optionCode": "FC01", "currency": "MXN", "primaryRate": 640.0, "weeklyRate": 640.0, "pricingCode": "20", "privilegeCode": "L0"},
        {"optionCode": "FC01", "currency": "USD", "primaryRate": 32.0, "weeklyRate": 32.0, "pricingCode": "20", "privilegeCode": "L0"},
        {"optionCode": "FC01", "currency": "MXN", "primaryRate": 309.5, "weeklyRate": 309.5, "pricingCode": "20", "privilegeCode": "L1"},
        {"optionCode": "FC01", "currency": "USD", "primaryRate": 15.45, "weeklyRate": 15.45, "pricingCode": "20", "privilegeCode": "L1"},
        {"optionCode": "FC01", "currency": "MXN", "primaryRate": 380.0, "weeklyRate": 380.0, "pricingCode": "21", "privilegeCode": "L1"},
        {"optionCode": "FC01", "currency": "USD", "primaryRate": 19.0, "weeklyRate": 19.0, "pricingCode": "21", "privilegeCode": "L1"},
        {"optionCode": "FC01", "currency": "MXN", "primaryRate": 446.0, "weeklyRate": 446.0, "pricingCode": "25", "privilegeCode": "L1"},
        {"optionCode": "FC01", "currency": "USD", "primaryRate": 22.3, "weeklyRate": 22.3, "pricingCode": "25", "privilegeCode": "L1"},
        {"optionCode": "FC01", "currency": "MXN", "primaryRate": 502.0, "weeklyRate": 502.0, "pricingCode": "29", "privilegeCode": "L1"},
        {"optionCode": "FC01", "currency": "USD", "primaryRate": 25.0, "weeklyRate": 25.0, "pricingCode": "29", "privilegeCode": "L1"},
        {"optionCode": "FC01", "currency": "MXN", "primaryRate": 502.5, "weeklyRate": 502.5, "pricingCode": "40", "privilegeCode": "L1"},
        {"optionCode": "FC01", "currency": "USD", "primaryRate": 25.0, "weeklyRate": 25.0, "pricingCode": "40", "privilegeCode": "L1"},
        {"optionCode": "FC01", "currency": "MXN", "primaryRate": 580.0, "weeklyRate": 580.0, "pricingCode": "45", "privilegeCode": "L1"},
        {"optionCode": "FC01", "currency": "USD", "primaryRate": 29.0, "weeklyRate": 29.0, "pricingCode": "45", "privilegeCode": "L1"},
        {"optionCode": "FC01", "currency": "MXN", "primaryRate": 100.0, "weeklyRate": 100.0, "pricingCode": "20", "privilegeCode": "L2"},
        {"optionCode": "FC01", "currency": "USD", "primaryRate": 5.0, "weeklyRate": 5.0, "pricingCode": "20", "privilegeCode": "L2"},
        {"optionCode": "FC01", "currency": "MXN", "primaryRate": 100.0, "weeklyRate": 100.0, "pricingCode": "21", "privilegeCode": "L2"},
        {"optionCode": "FC01", "currency": "USD", "primaryRate": 5.0, "weeklyRate": 5.0, "pricingCode": "21", "privilegeCode": "L2"},
        {"optionCode": "FC01", "currency": "MXN", "primaryRate": 160.5, "weeklyRate": 160.5, "pricingCode": "25", "privilegeCode": "L2"},
        {"optionCode": "FC01", "currency": "USD", "primaryRate": 8.0, "weeklyRate": 8.0, "pricingCode": "25", "privilegeCode": "L2"},
        {"optionCode": "FC01", "currency": "MXN", "primaryRate": 220.0, "weeklyRate": 220.0, "pricingCode": "29", "privilegeCode": "L2"},
        {"optionCode": "FC01", "currency": "USD", "primaryRate": 11.0, "weeklyRate": 11.0, "pricingCode": "29", "privilegeCode": "L2"},
        {"optionCode": "FC01", "currency": "MXN", "primaryRate": 220.0, "weeklyRate": 220.0, "pricingCode": "40", "privilegeCode": "L2"},
        {"optionCode": "FC01", "currency": "USD", "primaryRate": 11.0, "weeklyRate": 11.0, "pricingCode": "40", "privilegeCode": "L2"},
        {"optionCode": "FC01", "currency": "MXN", "primaryRate": 280.0, "weeklyRate": 280.0, "pricingCode": "45", "privilegeCode": "L2"},
        {"optionCode": "FC01", "currency": "USD", "primaryRate": 14.0, "weeklyRate": 14.0, "pricingCode": "45", "privilegeCode": "L2"}
    ]

    for rate_data in rates:
        response = add_rate_to_option(rate_data)
        print(f"Status Code: {response.status_code}, Response: {response.text}")

if __name__ == "__main__":
    main()
