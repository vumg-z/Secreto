import requests
import json

base_url = 'http://192.168.1.211:8081/api/options'

def create_option(option_data):
    response = requests.post(base_url, headers={"Content-Type": "application/json"}, data=json.dumps(option_data))
    return response

def main():
    options_list = [
        # {
        #     "optionCode": "LOCN",
        #     "optSetCode": "1"
        # },
        # {
        #     "optionCode": "TAX",
        #     "optSetCode": "1"
        # },
        # {
        #     "optionCode": "LRF",
        #     "optSetCode": "1"
        # },
        # {
        #     "optionCode": "LDW",
        #     "optSetCode": None
        # },
        
    ]

    for option in options_list:
        response = create_option(option)
        print(f"Status Code: {response.status_code}, Response: {response.json()}")

if __name__ == "__main__":
    main()
