import requests
import json

base_url = 'http://192.168.1.211:8081/api/options'

def create_option(option_data):
    response = requests.post(base_url, headers={"Content-Type": "application/json"}, data=json.dumps(option_data))
    return response

def main():
    options_list = [
        {
            "optionCode": "FC01",
            "shortDesc": "FC1 00T10P",
            "longDesc": "Fuel Charge 00%TotLoss 10%PartLoss",
            "typeFlag": "O",
            "glAccount": None,
            "echo": "N",
            "allowQty": "N",
            "insOnly": "N",
            "passThru": "Y",
            "rptAsRev": "Y",
            "webResVisible": "Y",
            "optSetCode": "PFC01",
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
            "webResVisible": "Y",
            "optSetCode": "PFC01"
        },
        {
            "optionCode": "BCP10",
            "shortDesc": "ClPrLs10%",
            "longDesc": "BuyDwn Coll Part Loss 10%",
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
            "optSetCode": "PFC01"
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
            "optSetCode": "PFC01"
        },
        {
            "optionCode": "BTP10",
            "shortDesc": "TfPrLs10%",
            "longDesc": "BuyDwn Theft Partial Loss 10%",
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
            "optSetCode": "PFC01"
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
            "optSetCode": "PFC01"
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
            "optSetCode": "PFC01"
        }
    ]

    for option in options_list:
        response = create_option(option)
        print(f"Status Code: {response.status_code}, Response: {response.status_code}")

if __name__ == "__main__":
    main()
