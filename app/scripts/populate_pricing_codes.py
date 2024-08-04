import requests
import json

base_url = 'http://192.168.1.211:8081/api/pricing-codes'

def create_rate_product(rate_product_data):
    try:
        response = requests.post(base_url, headers={"Content-Type": "application/json"}, data=json.dumps(rate_product_data))
        response.raise_for_status()  # Raises an HTTPError if the response code was unsuccessful
        return response
    except requests.exceptions.HTTPError as http_err:
        print(f"HTTP error occurred: {http_err} - {response.text}")  # Print the HTTP error and response text
    except Exception as err:
        print(f"Other error occurred: {err}")  # Print any other error
    return None

def main():
    rate_products_list = [
        {
            "code": "15",
            "description": "CARVALUE < $150K MXN",
            "ldwRate": 0,
            "noLdwResp": 99999999,
            "noLdwAge1": 0,
            "noLdwResp1": 0,
            "noLdwAge2": 0,
            "noLdwResp2": 0,
            "inclLdwResp": 0,
            "cvg2Value": 0,
            "cvg3Value": 0,
            "cvg4Value": 0
        },
        {
            "code": "20",
            "description": "MCXX <$200K MXN",
            "ldwRate": 0,
            "noLdwResp": 99999999,
            "noLdwAge1": 100,
            "noLdwResp1": 100,
            "noLdwAge2": 100,
            "noLdwResp2": 100,
            "inclLdwResp": 0,
            "cvg2Value": 0,
            "cvg3Value": 0,
            "cvg4Value": 0
        },
        {
            "code": "21",
            "description": "ECON & COMPACT",
            "ldwRate": 0,
            "noLdwResp": 99999999,
            "noLdwAge1": 0,
            "noLdwResp1": 0,
            "noLdwAge2": 0,
            "noLdwResp2": 0,
            "inclLdwResp": 0,
            "cvg2Value": 0,
            "cvg3Value": 0,
            "cvg4Value": 0
        },
        {
            "code": "25",
            "description": "MID & STD CAR",
            "ldwRate": 0,
            "noLdwResp": 99999999,
            "noLdwAge1": 0,
            "noLdwResp1": 0,
            "noLdwAge2": 0,
            "noLdwResp2": 0,
            "inclLdwResp": 0,
            "cvg2Value": 0,
            "cvg3Value": 0,
            "cvg4Value": 0
        },
        {
            "code": "29",
            "description": "FSIZE CAR",
            "ldwRate": 0,
            "noLdwResp": 99999999,
            "noLdwAge1": 0,
            "noLdwResp1": 0,
            "noLdwAge2": 0,
            "noLdwResp2": 0,
            "inclLdwResp": 0,
            "cvg2Value": 0,
            "cvg3Value": 0,
            "cvg4Value": 0
        },
        {
            "code": "40",
            "description": "Mini, Eco & Cmpt SUV",
            "ldwRate": 0,
            "noLdwResp": 99999999,
            "noLdwAge1": 0,
            "noLdwResp1": 0,
            "noLdwAge2": 0,
            "noLdwResp2": 0,
            "inclLdwResp": 0,
            "cvg2Value": 0,
            "cvg3Value": 0,
            "cvg4Value": 0
        },
        {
            "code": "45",
            "description": "IFAR and UP",
            "ldwRate": 0,
            "noLdwResp": 99999999,
            "noLdwAge1": 0,
            "noLdwResp1": 0,
            "noLdwAge2": 0,
            "noLdwResp2": 0,
            "inclLdwResp": 0,
            "cvg2Value": 0,
            "cvg3Value": 0,
            "cvg4Value": 0
        },
        {
            "code": "4C",
            "description": "10LO5TNMDW FSIZE-SUV",
            "ldwRate": 0,
            "noLdwResp": 30000,
            "noLdwAge1": 0,
            "noLdwResp1": 0,
            "noLdwAge2": 0,
            "noLdwResp2": 0,
            "inclLdwResp": 0,
            "cvg2Value": 0,
            "cvg3Value": 0,
            "cvg4Value": 0
        },
         {
            "code": "DF",
            "description": "Default",
            "ldwRate": 0,
            "noLdwResp": 30000,
            "noLdwAge1": 0,
            "noLdwResp1": 0,
            "noLdwAge2": 0,
            "noLdwResp2": 0,
            "inclLdwResp": 0,
            "cvg2Value": 0,
            "cvg3Value": 0,
            "cvg4Value": 0
        }
    ]

    for rate_product in rate_products_list:
        response = create_rate_product(rate_product)
        if response:
            print(f"Status Code: {response.status_code}, Response: {response.json()}")
        else:
            print("Failed to create rate product")

if __name__ == "__main__":
    main()
