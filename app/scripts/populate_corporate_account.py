import requests
import json
from datetime import date

API_URL = "http://192.168.1.211:8081/api/corporate-accounts"

def create_corporate_account():
    # CorporateAccount object
    corporate_account = {
        "cdpId": "MYWEB1",
        "companyName": "Mobity Web",
        "corporateContract": {
            "contractNumber": "MYWEB1"
        },
        "sourceCode": "WPG",
        "searchable": False
    }

    # Convert to JSON
    payload = json.dumps(corporate_account)

    # Headers
    headers = {
        'Content-Type': 'application/json'
    }

    # Make the POST request
    response = requests.post(API_URL, headers=headers, data=payload)

    # Check the response
    if response.status_code == 200:
        print("Corporate account created successfully!")
        print("Response:", response.json())
    else:
        print("Failed to create corporate account.")
        print("Status code:", response.status_code)
        print("Response:", response.text)

def main():
    create_corporate_account()

if __name__ == "__main__":
    main()
