import requests
import json

base_url = 'http://192.168.1.211:8081/api/options'

def create_option(option_data):
    response = requests.post(base_url, headers={"Content-Type": "application/json"}, data=json.dumps(option_data))
    return response

# LDW2   BCP10     01-01-20            08-04-20 ASTJXS  08-04-20 ASTJXS        
# LDW2   BCT10     01-01-20            08-04-20 ASTJXS  08-04-20 ASTJXS        
# LDW2   BTP10     01-01-20            08-04-20 ASTJXS  08-04-20 ASTJXS        
# LDW2   BTT10     01-01-20            08-04-20 ASTJXS  08-04-20 ASTJXS

def main():
    options_list = [
        {
            "optionCode": "LDW2",
            "shortDesc": "LW2 10T10P",
            "longDesc": " LDW 10%Tt Loss 10%Ptl Loss",
            "typeFlag": "O",
            "glAccount": None,
            "echo": "N",
            "allowQty": "N",
            "insOnly": "N",
            "passThru": "Y",
            "rptAsRev": "Y",
            "webResVisible": "Y",
            "optSetCode": "LDW2",
            "bundle": True
        },
        {
            "optionCode": "BCP10",
            "shortDesc": "AccsrTheft",
            "longDesc": "Robo de Autopartes",
            "typeFlag": "C",
            "glAccount": None,
            "echo": "N",
            "allowQty": "N",
            "insOnly": "N",
            "passThru": "Y",
            "rptAsRev": "Y",
            "webResVisible": "N",
            "modifiedEmployee": "Employee",
            "dayOfWeekPricing": "Pricing",
            "optSetCode": "LDW2"
        },
        {
            "optionCode": "BCT10",
            "shortDesc": "ClPrLs00%",
            "longDesc": "BuyDwn Coll Part Loss 00%",
            "typeFlag": "C",
            "glAccount": None,
            "echo": "N",
            "allowQty": "N",
            "rptAsRev": "Y",
            "webResVisible": "N",
            "dueReport": 0.0,
            "duePenalty": 0.0,
            "expireDate": "2025-12-31",
            "insInvPysCls": "Cls",
            "assetByUnit": "Unit",
            "effBlkRmvTyp": "Type",
            "startDate": "2024-01-01",
            "restEbdsAuthOpt": "Auth",
            "modifiedDate": "2024-07-23",
            "modifiedEmployee": "Employee",
            "optSetCode": "LDW2"
        },
        {
            "optionCode": "BTT10",
            "shortDesc": "ClTtLs00%",
            "longDesc": "BuyDwn Coll Totl Loss 00%",
            "typeFlag": "C",
            "glAccount": None,
            "echo": "N",
            "allowQty": "N",
            "rptAsRev": "Y",
            "webResVisible": "N",
            "dueReport": 0.0,
            "duePenalty": 0.0,
            "expireDate": "2025-12-31",
            "insInvPysCls": "Cls",
            "assetByUnit": "Unit",
            "effBlkRmvTyp": "Type",
            "startDate": "2024-01-01",
            "restEbdsAuthOpt": "Auth",
            "modifiedDate": "2024-07-23",
            "modifiedEmployee": "Employee",
            "optSetCode": "LDW2"
        }
    ]

    for option in options_list:
        response = create_option(option)
        print(f"Status Code: {response.status_code}, Response: {response.status_code}")

if __name__ == "__main__":
    main()
