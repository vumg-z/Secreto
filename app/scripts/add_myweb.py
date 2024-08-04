import requests
import json
import sys

# Increase the recursion limit
sys.setrecursionlimit(2000)

base_url = 'http://192.168.1.211:8081/api/corporate-contracts'

def create_corporate_contract(corporate_contract_data):
    response = requests.post(base_url, headers={"Content-Type": "application/json"}, data=json.dumps(corporate_contract_data))
    return response

def main():
    corporate_contract_data = {
    "contractNumber": "MYWEB1",
    "rateProduct": "MI2",
    "privilegeCodes": [
        {
            "code": "L2"
        },
        {
            "code": "IL0"
        }
    ]
}

    response = create_corporate_contract(corporate_contract_data)
    if response.status_code in [200, 201]:
        try:
            json_response = response.json()
            print(f"Status Code: {response.status_code}, Response: {json_response}")
        except requests.exceptions.JSONDecodeError:
            print(f"Status Code: {response.status_code}, Response: No JSON content")
        except RecursionError:
            print(f"Status Code: {response.status_code}, Response: Recursion error encountered while decoding JSON")
    else:
        print(f"Failed to create corporate contract. Status Code: {response.status_code}, Response: {response.text}")

if __name__ == "__main__":
    main()
