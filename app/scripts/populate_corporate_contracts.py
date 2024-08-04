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
    corporate_contracts = [
        {
            "contractNumber": "GU2",
            "rateProduct": {
                "product": "ERC"
            },
            "privilegeCodes": [
                {"code": "L2"},
                {"code": "PA"},
                {"code": "IL0"}
            ]
        },
        {
            "contractNumber": "LG390",
            "rateProduct": {
                "product": "PRM"
            },
            "privilegeCodes": [
                {"code": "PA"},
                {"code": "L2"}, 
                {"code": "IL0"}
            ]
        },
        {
            "contractNumber": "MM1",
            "rateProduct": {
                "product": "MI1"
            },
            "privilegeCodes": [
                {"code": "L1"},
                {"code": "PA"}
            ]
        },
        {
            "contractNumber": "MW20",
            "rateProduct": {
                "product": "MI2"
            },
            "privilegeCodes": [
                {"code": "L2"},
                {"code": "IL0"},
                {"code": "PA"},
                {"code": "20D"}
            ]
        },
        {
            "contractNumber": "MWB20D",
            "rateProduct": {
                "product": "MI2"
            },
            "privilegeCodes": [
                {"code": "L2"},
                {"code": "IL0PA"}
            ],
            "dailyDiscPct": 20,
            "wklyDiscPct": 20
        },
        {
            "contractNumber": "MYW20D",
            "rateProduct": {
                "product": "MI2"
            },
            "privilegeCodes": [
                {"code": "IL0L2"},
                {"code": "PA"}
            ],
            "dailyDiscPct": 20,
            "wklyDiscPct": 20,
            "raDiscAmt": 10.00
        },
        {
            "contractNumber": "MYWEB1",
            "rateProduct": {
                "product": "MI2"
            },
            "privilegeCodes": [
                {"code": "L2"},
                {"code": "IL0"}
            ]
        },
        {
            "contractNumber": "MYWLK1",
            "rateProduct": {
                "product": "MI1"
            },
            "privilegeCodes": [
                {"code": "L1"}
            ]
        },
        {
            "contractNumber": "PI1",
            "rateProduct": {
                "product": "PIL1"
            },
            "privilegeCodes": [
                {"code": "L2"},
                {"code": "IL0PA"}
            ]
        },
        {
            "contractNumber": "PRM",
            "rateProduct": {
                "product": "PRM"
            },
            "privilegeCodes": [
                {"code": "PA"},
                {"code": "L2"},
                {"code": "IL0"}
            ]
        },
        {
            "contractNumber": "RI0",
            "rateProduct": {
                "product": "ERC"
            },
            "privilegeCodes": [
                {"code": "L0"},
                {"code": "PA"},
                {"code": "IL0"}
            ]
        },
        {
            "contractNumber": "RI2",
            "rateProduct": {
                "product": "ERC"
            },
            "privilegeCodes": [
                {"code": "L2"},
                {"code": "PA"},
                {"code": "IL2"}
            ]
        },
        {
            "contractNumber": "RM0",
            "rateProduct": {
                "product": "ERCM"
            },
            "privilegeCodes": [
                {"code": "L0"},
                {"code": "PA"},
                {"code": "IL0"}
            ]
        },
        {
            "contractNumber": "RM2",
            "rateProduct": {
                "product": "ERCM"
            },
            "privilegeCodes": [
                {"code": "L2"},
                {"code": "PA"},
                {"code": "IL2"}
            ]
        },
        {
            "contractNumber": "RU0",
            "rateProduct": {
                "product": "ERC"
            },
            "privilegeCodes": [
                {"code": "L0"},
                {"code": "PA"},
                {"code": "IL0"}
            ]
        },
        {
            "contractNumber": "RU2",
            "rateProduct": {
                "product": "ERC"
            },
            "privilegeCodes": [
                {"code": "L2"},
                {"code": "PA"},
                {"code": "IL2"}
            ]
        },
        {
            "contractNumber": "WKP",
            "rateProduct": {
                "product": "MI1"
            },
            "privilegeCodes": [
                {"code": "L1"},
                {"code": "PA"}
            ]
        },
        {
            "contractNumber": "WLK1",
            "rateProduct": {
                "product": "MI1"
            },
            "privilegeCodes": [
                {"code": "L1"}
            ]
        }
    ]

    for contract in corporate_contracts:
        response = create_corporate_contract(contract)
        if response.status_code in [200, 201]:
            try:
                json_response = response.json()
                print(f"Status Code: {response.status_code}, Response: {json_response}")
            except requests.exceptions.JSONDecodeError:
                print(f"Status Code: {response.status_code}, Response: No JSON content")
            except RecursionError:
                print(f"Status Code: {response.status_code}, Response: Recursion error encountered while decoding JSON")
        else:
            print(f"Failed to create corporate contract {contract['contractNumber']}. Status Code: {response.status_code}, Response: {response.text}")

if __name__ == "__main__":
    main()
