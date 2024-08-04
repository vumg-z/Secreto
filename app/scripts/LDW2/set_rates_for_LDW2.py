import requests
import json

base_url = 'http://192.168.1.211:8081/api/options-rates'

def add_rate_to_option(rate_data):
    response = requests.post(f"{base_url}/add", headers={"Content-Type": "application/json"}, data=json.dumps(rate_data))
    return response

def main():
    # List of rates for LDW2
    data = [
        # USD L0
        {"optionCode": "LDW2", "currency": "USD", "primaryRate": 21.500, "xdayRate": 21.50, "privilegeCode": "L0", "pricingCode": "21"},
        {"optionCode": "LDW2", "currency": "USD", "primaryRate": 27.500, "xdayRate": 27.50, "privilegeCode": "L0", "pricingCode": "25"},
        {"optionCode": "LDW2", "currency": "USD", "primaryRate": 33.400, "xdayRate": 33.40, "privilegeCode": "L0", "pricingCode": "29"},
        {"optionCode": "LDW2", "currency": "USD", "primaryRate": 34.050, "xdayRate": 34.05, "privilegeCode": "L0", "pricingCode": "40"},
        {"optionCode": "LDW2", "currency": "USD", "primaryRate": 39.800, "xdayRate": 39.80, "privilegeCode": "L0", "pricingCode": "45"},
        # MXN L1
        {"optionCode": "LDW2", "currency": "MXN", "primaryRate": 86.000, "xdayRate": 86.00, "privilegeCode": "L1", "pricingCode": "20"},
        {"optionCode": "LDW2", "currency": "MXN", "primaryRate": 238.500, "xdayRate": 238.50, "privilegeCode": "L1", "pricingCode": "21"},
        {"optionCode": "LDW2", "currency": "MXN", "primaryRate": 286.000, "xdayRate": 286.00, "privilegeCode": "L1", "pricingCode": "25"},
        {"optionCode": "LDW2", "currency": "MXN", "primaryRate": 363.000, "xdayRate": 363.00, "privilegeCode": "L1", "pricingCode": "29"},
        {"optionCode": "LDW2", "currency": "MXN", "primaryRate": 358.500, "xdayRate": 358.50, "privilegeCode": "L1", "pricingCode": "40"},
        {"optionCode": "LDW2", "currency": "MXN", "primaryRate": 458.000, "xdayRate": 458.00, "privilegeCode": "L1", "pricingCode": "45"},
        # USD L2
        {"optionCode": "LDW2", "currency": "USD", "primaryRate": 0.010, "xdayRate": 0.01, "privilegeCode": "L2", "pricingCode": "20"},
        {"optionCode": "LDW2", "currency": "USD", "primaryRate": 0.010, "xdayRate": 0.01, "privilegeCode": "L2", "pricingCode": "21"},
        {"optionCode": "LDW2", "currency": "USD", "primaryRate": 0.010, "xdayRate": 0.01, "privilegeCode": "L2", "pricingCode": "25"},
        {"optionCode": "LDW2", "currency": "USD", "primaryRate": 0.010, "xdayRate": 0.01, "privilegeCode": "L2", "pricingCode": "29"},
        {"optionCode": "LDW2", "currency": "USD", "primaryRate": 0.010, "xdayRate": 0.01, "privilegeCode": "L2", "pricingCode": "40"},
        {"optionCode": "LDW2", "currency": "USD", "primaryRate": 0.010, "xdayRate": 0.01, "privilegeCode": "L2", "pricingCode": "45"},
        # MXN (no privilege code)
        {"optionCode": "LDW2", "currency": "MXN", "primaryRate": 86.000, "xdayRate": 86.00, "privilegeCode": "DF", "pricingCode": "20"},
        {"optionCode": "LDW2", "currency": "MXN", "primaryRate": 238.500, "xdayRate": 238.50, "privilegeCode": "DF", "pricingCode": "21"},
        {"optionCode": "LDW2", "currency": "MXN", "primaryRate": 286.000, "xdayRate": 286.00, "privilegeCode": "DF", "pricingCode": "25"},
        {"optionCode": "LDW2", "currency": "MXN", "primaryRate": 363.000, "xdayRate": 363.00, "privilegeCode": "DF", "pricingCode": "29"},
        {"optionCode": "LDW2", "currency": "MXN", "primaryRate": 358.500, "xdayRate": 358.50, "privilegeCode": "DF", "pricingCode": "40"},
        {"optionCode": "LDW2", "currency": "MXN", "primaryRate": 458.000, "xdayRate": 458.00, "privilegeCode": "DF", "pricingCode": "45"},
        # USD L0 (repeat from above for completeness)
        {"optionCode": "LDW2", "currency": "USD", "primaryRate": 13.200, "xdayRate": 13.20, "privilegeCode": "L0", "pricingCode": "20"},
        {"optionCode": "LDW2", "currency": "USD", "primaryRate": 21.500, "xdayRate": 21.50, "privilegeCode": "L0", "pricingCode": "21"}
    ]

    for rate_data in data:
        response = add_rate_to_option(rate_data)
        print(f"Status Code: {response.status_code}, Response: {response.text}")

if __name__ == "__main__":
    main()
