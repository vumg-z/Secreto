import requests
import json

base_url = 'http://192.168.1.211:8081/api/options'

def create_option(option_data):
    response = requests.post(base_url, headers={"Content-Type": "application/json"}, data=json.dumps(option_data))
    return response

def main():
    fees = [ 
        {
            "optionCode": "LOCN",
            "shortDesc": "Location Fee Short Desc",
            "longDesc": "Location Fee Long Desc",
            "typeFlag": "O",
            "glAccount": None,
            "echo": "N",
            "allowQty": "N",
            "insOnly": "N",
            "passThru": "Y",
            "rptAsRev": "Y",
            "webResVisible": "N",
            "linkedOpt": "Opt",
            "optSetCode": "1",
            "isFee": True,
            "applicableOptionsCodes": ["FC03", "FC02", "FC01"]
        },
        {
            "optionCode": "TAX",
            "shortDesc": "TAX FEE",
            "longDesc": "TAX FEE",
            "typeFlag": "T",
            "glAccount": None,
            "echo": "N",
            "allowQty": "N",
            "insOnly": "N",
            "passThru": "Y",
            "rptAsRev": "Y",
            "webResVisible": "N",
            "optSetCode": "1",
            "isFee": True,
            "applicableOptionsCodes": ["FC03", "FC02", "FC01"]

        },
        {
            "optionCode": "LRF",
            "shortDesc": "License Recovery Fee Fee Short Desc",
            "longDesc": "License Recovery Fee Fee Long Desc",
            "typeFlag": "O",
            "glAccount": None,
            "echo": "N",
            "allowQty": "N",
            "insOnly": "N",
            "passThru": "Y",
            "rptAsRev": "Y",
            "webResVisible": "N",
            "optSetCode": "1",
            "isFee": True,
            "applicableOptionsCodes": ["FC03", "FC02", "FC01"]

        }
    ]

    for fee in fees:
        response = create_option(fee)
        print(f"Status Code: {response.status_code}, Response: {response.json()}")

if __name__ == "__main__":
    main()
