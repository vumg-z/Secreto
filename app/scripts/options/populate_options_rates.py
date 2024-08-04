import requests
import json

base_url = 'http://192.168.1.211:8081/api/options-rates'

def add_rate_to_option(rate_data):
    response = requests.post(f"{base_url}/add", headers={"Content-Type": "application/json"}, data=json.dumps(rate_data))
    return response

def main():
    # Expanded list of rates including new options
    data = [
        {"optionCode": "ACCTF", "currency": "default", "primaryRate": 200.000, "xdayRate": 200.00, "privilegeCode": "DF", "pricingCode": "DF"},
        {"optionCode": "ACCTF", "currency": "MXN", "primaryRate": 200.000, "xdayRate": 200.00, "privilegeCode": "DF", "pricingCode": "DF"},
        {"optionCode": "ACCTF", "currency": "USD", "primaryRate": 10.000, "xdayRate": 10.00, "privilegeCode": "DF", "pricingCode": "DF"},
        {"optionCode": "ACCTF", "currency": "MXN", "primaryRate": 200.000, "xdayRate": 200.00, "privilegeCode": "L0", "pricingCode": "DF"},
        {"optionCode": "ACCTF", "currency": "USD", "primaryRate": 10.000, "xdayRate": 10.00, "privilegeCode": "L0", "pricingCode": "DF"},
        {"optionCode": "ACCTF", "currency": "MXN", "primaryRate": 200.000, "xdayRate": 200.00, "privilegeCode": "L1", "pricingCode": "DF"},
        {"optionCode": "ACCTF", "currency": "USD", "primaryRate": 10.000, "xdayRate": 10.00, "privilegeCode": "L1", "pricingCode": "DF"},
        {"optionCode": "ACCTF", "currency": "MXN", "primaryRate": 200.000, "xdayRate": 200.00, "privilegeCode": "L2", "pricingCode": "DF"},
        {"optionCode": "ACCTF", "currency": "USD", "primaryRate": 10.000, "xdayRate": 10.00, "privilegeCode": "L2", "pricingCode": "DF"},
        
        {"optionCode": "BCP02", "currency": "USD", "primaryRate": 999.00, "xdayRate": 0.00, "privilegeCode": "DF", "pricingCode": "DF"},
        {"optionCode": "BTT20", "currency": "USD", "primaryRate": 999.00, "xdayRate": 0.00, "privilegeCode": "DF", "pricingCode": "DF"},
        {"optionCode": "BTT10", "currency": "USD", "primaryRate": 999.00, "xdayRate": 0.00, "privilegeCode": "DF", "pricingCode": "DF"},
        {"optionCode": "BTT00", "currency": "USD", "primaryRate": 999.00, "xdayRate": 0.00, "privilegeCode": "DF", "pricingCode": "DF"},
        {"optionCode": "BTP10", "currency": "USD", "primaryRate": 999.00, "xdayRate": 0.00, "privilegeCode": "DF", "pricingCode": "DF"},
        {"optionCode": "BTP05", "currency": "USD", "primaryRate": 999.00, "xdayRate": 0.00, "privilegeCode": "DF", "pricingCode": "DF"},
        {"optionCode": "BTP00", "currency": "USD", "primaryRate": 999.00, "xdayRate": 0.00, "privilegeCode": "DF", "pricingCode": "DF"},
        {"optionCode": "BCT20", "currency": "USD", "primaryRate": 999.00, "xdayRate": 0.00, "privilegeCode": "DF", "pricingCode": "DF"},
        {"optionCode": "BCT10", "currency": "USD", "primaryRate": 999.00, "xdayRate": 0.00, "privilegeCode": "DF", "pricingCode": "DF"},
        {"optionCode": "BCT00", "currency": "USD", "primaryRate": 999.00, "xdayRate": 0.00, "privilegeCode": "DF", "pricingCode": "DF"},
        {"optionCode": "BCP20", "currency": "USD", "primaryRate": 999.00, "xdayRate": 0.00, "privilegeCode": "DF", "pricingCode": "DF"},
        {"optionCode": "BCP10", "currency": "USD", "primaryRate": 999.00, "xdayRate": 0.00, "privilegeCode": "DF", "pricingCode": "DF"},
        {"optionCode": "BCP05", "currency": "USD", "primaryRate": 999.00, "xdayRate": 0.00, "privilegeCode": "DF", "pricingCode": "DF"},
        {"optionCode": "BCP00", "currency": "USD", "primaryRate": 999.00, "xdayRate": 0.00, "privilegeCode": "DF", "pricingCode": "DF"},
        
        {"optionCode": "BCP02", "currency": "MXN", "primaryRate": 999.00, "xdayRate": 0.00, "privilegeCode": "DF", "pricingCode": "DF"},
        {"optionCode": "BTT20", "currency": "MXN", "primaryRate": 999.00, "xdayRate": 0.00, "privilegeCode": "DF", "pricingCode": "DF"},
        {"optionCode": "BTT10", "currency": "MXN", "primaryRate": 999.00, "xdayRate": 0.00, "privilegeCode": "DF", "pricingCode": "DF"},
        {"optionCode": "BTT00", "currency": "MXN", "primaryRate": 999.00, "xdayRate": 0.00, "privilegeCode": "DF", "pricingCode": "DF"},
        {"optionCode": "BTP10", "currency": "MXN", "primaryRate": 999.00, "xdayRate": 0.00, "privilegeCode": "DF", "pricingCode": "DF"},
        {"optionCode": "BTP05", "currency": "MXN", "primaryRate": 999.00, "xdayRate": 0.00, "privilegeCode": "DF", "pricingCode": "DF"},
        {"optionCode": "BTP00", "currency": "MXN", "primaryRate": 999.00, "xdayRate": 0.00, "privilegeCode": "DF", "pricingCode": "DF"},
        {"optionCode": "BCT20", "currency": "MXN", "primaryRate": 999.00, "xdayRate": 0.00, "privilegeCode": "DF", "pricingCode": "DF"},
        {"optionCode": "BCT10", "currency": "MXN", "primaryRate": 999.00, "xdayRate": 0.00, "privilegeCode": "DF", "pricingCode": "DF"},
        {"optionCode": "BCT00", "currency": "MXN", "primaryRate": 999.00, "xdayRate": 0.00, "privilegeCode": "DF", "pricingCode": "DF"},
        {"optionCode": "BCP20", "currency": "MXN", "primaryRate": 999.00, "xdayRate": 0.00, "privilegeCode": "DF", "pricingCode": "DF"},
        {"optionCode": "BCP10", "currency": "MXN", "primaryRate": 999.00, "xdayRate": 0.00, "privilegeCode": "DF", "pricingCode": "DF"},
        {"optionCode": "BCP05", "currency": "MXN", "primaryRate": 999.00, "xdayRate": 0.00, "privilegeCode": "DF", "pricingCode": "DF"},
        {"optionCode": "BCP00", "currency": "MXN", "primaryRate": 999.00, "xdayRate": 0.00, "privilegeCode": "DF", "pricingCode": "DF"},
        
        {"optionCode": "BABY", "currency": "MXN", "primaryRate": 90.000, "xdayRate": 90.00, "privilegeCode": "DF", "pricingCode": "DF"},
        {"optionCode": "BABY", "currency": "USD", "primaryRate": 5.000, "xdayRate": 5.00, "privilegeCode": "DF", "pricingCode": "DF"},
        
        {"optionCode": "XTPLA", "currency": "MXN", "primaryRate": 600.000, "xdayRate": 600.00, "privilegeCode": "DF", "pricingCode": "DF"},
        {"optionCode": "XTPLA", "currency": "USD", "primaryRate": 30.000, "xdayRate": 30.00, "privilegeCode": "DF", "pricingCode": "DF"},
        {"optionCode": "XTPLB", "currency": "MXN", "primaryRate": 300.000, "xdayRate": 300.00, "privilegeCode": "DF", "pricingCode": "DF"},
        {"optionCode": "XTPLB", "currency": "USD", "primaryRate": 15.000, "xdayRate": 15.00, "privilegeCode": "DF", "pricingCode": "DF"},
        {"optionCode": "XTPLB", "currency": "MXN", "primaryRate": 300.000, "xdayRate": 300.00, "privilegeCode": "L0", "pricingCode": "DF"},
        {"optionCode": "XTPLB", "currency": "USD", "primaryRate": 15.000, "xdayRate": 15.00, "privilegeCode": "L0", "pricingCode": "DF"},
        {"optionCode": "XTPLB", "currency": "MXN", "primaryRate": 300.000, "xdayRate": 300.00, "privilegeCode": "L1", "pricingCode": "DF"},
        {"optionCode": "XTPLB", "currency": "USD", "primaryRate": 15.000, "xdayRate": 15.00, "privilegeCode": "L1", "pricingCode": "DF"},
        {"optionCode": "XTPLB", "currency": "MXN", "primaryRate": 300.000, "xdayRate": 300.00, "privilegeCode": "L2", "pricingCode": "DF"},
        {"optionCode": "XTPLB", "currency": "USD", "primaryRate": 15.000, "xdayRate": 15.00, "privilegeCode": "L2", "pricingCode": "DF"},
     
    ]

    for rate_data in data:
        response = add_rate_to_option(rate_data)
        print(f"Status Code: {response.status_code}, Response: {response.text}")

if __name__ == "__main__":
    main()
