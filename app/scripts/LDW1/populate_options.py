import requests
import json

base_url = 'http://192.168.1.211:8081/api/options'

def create_option(option_data):
    response = requests.post(base_url, headers={"Content-Type": "application/json"}, data=json.dumps(option_data))
    return response


#  LDW1   BCP20     01-01-20            16-05-20 ASTJXS  16-05-20 ASTJXS        
#  LDW1   BCT20     01-01-20            16-05-20 ASTJXS  16-05-20 ASTJXS        
#  LDW1   BTP20     01-01-20            16-05-20 ASTJXS  16-05-20 ASTJXS        
#  LDW1   BTT20     01-01-20            16-05-20 ASTJXS  16-05-20 ASTJXS        

def main():
    options_list = [
        {
            "optionCode": "LDW1",
            "shortDesc": "",
            "longDesc": "",
            "typeFlag": "O",
            "glAccount": None,
            "echo": "N",
            "allowQty": "N",
            "insOnly": "N",
            "passThru": "Y",
            "rptAsRev": "Y",
            "webResVisible": "N",
            "optSetCode": "LDW1",
            "bundle": True
        },
        {
            "optionCode": "BCP20",
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
            "optSetCode": "LDW1"
        },
        {
            "optionCode": "BCT20",
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
            "optSetCode": "LDW1"
        },
        {
            "optionCode": "BTP20",
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
            "optSetCode": "LDW1"
        },
        {
            "optionCode": "BTT20",
            "shortDesc": "TfPrLs00%",
            "longDesc": "BuyDwn Theft Partial Loss 00%",
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
            "optSetCode": "LDW1"
        }
    ]

    for option in options_list:
        response = create_option(option)
        print(f"Status Code: {response.status_code}, Response: {response.status_code}")

if __name__ == "__main__":
    main()
