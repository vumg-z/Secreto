import requests
import json

base_url = 'http://192.168.1.211:8081/api/options'

def create_option(option_data):
    response = requests.post(base_url, headers={"Content-Type": "application/json"}, data=json.dumps(option_data))
    return response

def main():
    options_list = [
        {
            "optionCode": "FC03",
            "shortDesc": "FCover 00%Ttl 00%Ptl",
            "longDesc": "FCover 00%Ttl 00%Ptl",
            "typeFlag": "O",
            "glAccount": None,
            "echo": "N",
            "allowQty": "N",
            "insOnly": "N",
            "passThru": "Y",
            "rptAsRev": "Y",
            "webResVisible": "Y",
            "dueReport": 0.0,
            "duePenalty": 0.0,
            "expireDate": "2025-12-31",
            "insInvPysCls": "Cls",
            "assetByUnit": "Unit",
            "effBlkRmvTyp": "Type",
            "startDate": "2024-01-01",
            "linkedOpt": "Opt",
            "restEbdsAuthOpt": "Auth",
            "blk1wyMilesSeq": 1,
            "modifiedTime": 10.0,
            "optSetCode": "PFC03",
            "bundle": True
        },
        {
            "optionCode": "ACCTF",
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
            "optSetCode": "PFC03"
        },
        {
            "optionCode": "BCP00",
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
            "optSetCode": "PFC03"
        },
        {
            "optionCode": "BCT00",
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
            "optSetCode": "PFC03"
        },
        {
            "optionCode": "BTP00",
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
            "optSetCode": "PFC03"
        },
        {
            "optionCode": "BTT00",
            "shortDesc": "TfTtLs00%",
            "longDesc": "BuyDwn Theft Total Loss 00%",
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
            "optSetCode": "PFC03"
        },
        {
            "optionCode": "XTPLB",
            "shortDesc": "XtndTplBaj",
            "longDesc": "Xtend TPLB 1.5Mill MXN",
            "typeFlag": "O",
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
            "optSetCode": "PFC03"
        }
    ]

    for option in options_list:
        response = create_option(option)
        print(f"Status Code: {response.status_code}, Response: {response.status_code}")

if __name__ == "__main__":
    main()
