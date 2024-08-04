import requests

# Define the endpoint URL
endpoint_url = 'http://192.168.1.211:8081/api/options'


requests_payload = [
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
        "dueReport": 0.0,
        "duePenalty": 0.0,
        "expireDate": "2025-12-31",
        "optSetCode": None
    },
    {
        "optionCode": "BABY",
        "shortDesc": "BabySeat",
        "longDesc": "Silla de Bebe",
        "typeFlag": "O",
        "glAccount": None,
        "echo": "Y",
        "allowQty": "Y",
        "insOnly": "N",
        "passThru": "N",
        "rptAsRev": "N",
        "webResVisible": "Y",
        "expireDate": "2025-12-31",
        "optSetCode": None
    },
    {
        "optionCode": "XTPLA",
        "shortDesc": "XtndTplAlt",
        "longDesc": " Xtend TPLA 1.5Mill MXN ",
        "typeFlag": "O",
        "glAccount": None,
        "echo": "Y",
        "allowQty": "N",
        "insOnly": "N",
        "passThru": "N",
        "rptAsRev": "Y",
        "webResVisible": "Y",
        "expireDate": "2025-12-31",
        "optSetCode": None
    },
    {
        "optionCode": "XTPLB",
        "shortDesc": "XtndTplBaj",
        "longDesc": "Xtend TPLB 1.5Mill MXN",
        "typeFlag": "O",
        "glAccount": None,
        "echo": "Y",
        "allowQty": "N",
        "insOnly": "N",
        "passThru": "N",
        "rptAsRev": "Y",
        "webResVisible": "Y",
        "expireDate": "2025-12-31",
        "optSetCode": None
    },
    {
        "optionCode": "BCP02",
        "shortDesc": "BuyDwn Col Part Loss 02%",
        "longDesc": "BuyDwn Col Part Loss 02%",
        "typeFlag": "O",
        "glAccount": None,
        "echo": "Y",
        "allowQty": "N",
        "insOnly": "N",
        "passThru": "N",
        "rptAsRev": "Y",
        "webResVisible": "Y",
        "expireDate": "2025-12-31",
        "optSetCode": None
    },
    {
        "optionCode": "BTT20",
        "shortDesc": "BuyDwn Thft Totl Loss 20%",
        "longDesc": "BuyDwn Thft Totl Loss 20%",
        "typeFlag": "O",
        "glAccount": None,
        "echo": "Y",
        "allowQty": "N",
        "insOnly": "N",
        "passThru": "N",
        "rptAsRev": "Y",
        "webResVisible": "Y",
        "expireDate": "2025-12-31",
        "optSetCode": None
    },
    {
        "optionCode": "BTT10",
        "shortDesc": "BuyDwn Thft Totl Loss 10%",
        "longDesc": "BuyDwn Thft Totl Loss 10%",
        "typeFlag": "O",
        "glAccount": None,
        "echo": "Y",
        "allowQty": "N",
        "insOnly": "N",
        "passThru": "N",
        "rptAsRev": "Y",
        "webResVisible": "Y",
        "expireDate": "2025-12-31",
        "optSetCode": None
    },
    {
        "optionCode": "BTT00",
        "shortDesc": "BuyD n Thft Totl Loss 00%",
        "longDesc": "BuyD n Thft Totl Loss 00%",
        "typeFlag": "O",
        "glAccount": None,
        "echo": "Y",
        "allowQty": "N",
        "insOnly": "N",
        "passThru": "N",
        "rptAsRev": "Y",
        "webResVisible": "Y",
        "expireDate": "2025-12-31",
        "optSetCode": None
    },
    {
        "optionCode": "BTP10",
        "shortDesc": "BuyDwn Thft Part Loss 10%",
        "longDesc": "BuyDwn Thft Part Loss 10%",
        "typeFlag": "O",
        "glAccount": None,
        "echo": "Y",
        "allowQty": "N",
        "insOnly": "N",
        "passThru": "N",
        "rptAsRev": "Y",
        "webResVisible": "Y",
        "expireDate": "2025-12-31",
        "optSetCode": None
    },
    {
        "optionCode": "BTP05",
        "shortDesc": "BuyDwn Thft Part Loss 05%",
        "longDesc": "BuyDwn Thft Part Loss 05%",
        "typeFlag": "O",
        "glAccount": None,
        "echo": "Y",
        "allowQty": "N",
        "insOnly": "N",
        "passThru": "N",
        "rptAsRev": "Y",
        "webResVisible": "Y",
        "expireDate": "2025-12-31",
        "optSetCode": None
    },
    {
        "optionCode": "BTP00",
        "shortDesc": "BuyDwn Theft Partial Loss 00%",
        "longDesc": "BuyDwn Theft Partial Loss 00%",
        "typeFlag": "O",
        "glAccount": None,
        "echo": "Y",
        "allowQty": "N",
        "insOnly": "N",
        "passThru": "N",
        "rptAsRev": "Y",
        "webResVisible": "Y",
        "expireDate": "2025-12-31",
        "optSetCode": None
    },
    {
        "optionCode": "BCT20",
        "shortDesc": "BuyDwn Coll Totl Loss 20%",
        "longDesc": "BuyDwn Coll Totl Loss 20%",
        "typeFlag": "O",
        "glAccount": None,
        "echo": "Y",
        "allowQty": "N",
        "insOnly": "N",
        "passThru": "N",
        "rptAsRev": "Y",
        "webResVisible": "Y",
        "expireDate": "2025-12-31",
        "optSetCode": None
    },
    {
        "optionCode": "BCT10",
        "shortDesc": "BuyDwn Coll Totl Loss 10%",
        "longDesc": "BuyDwn Coll Totl Loss 10%",
        "typeFlag": "O",
        "glAccount": None,
        "echo": "Y",
        "allowQty": "N",
        "insOnly": "N",
        "passThru": "N",
        "rptAsRev": "Y",
        "webResVisible": "Y",
        "expireDate": "2025-12-31",
        "optSetCode": None
    },
    {
        "optionCode": "BCT00",
        "shortDesc": "BuyDwn Coll Totl Loss 00%",
        "longDesc": "BuyDwn Coll Totl Loss 00%",
        "typeFlag": "O",
        "glAccount": None,
        "echo": "Y",
        "allowQty": "N",
        "insOnly": "N",
        "passThru": "N",
        "rptAsRev": "Y",
        "webResVisible": "Y",
        "expireDate": "2025-12-31",
        "optSetCode": None
    },
    {
        "optionCode": "BCP20",
        "shortDesc": "BuyDwn Coll Part Loss 20%",
        "longDesc": "BuyDwn Coll Part Loss 20%",
        "typeFlag": "O",
        "glAccount": None,
        "echo": "Y",
        "allowQty": "N",
        "insOnly": "N",
        "passThru": "N",
        "rptAsRev": "Y",
        "webResVisible": "Y",
        "expireDate": "2025-12-31",
        "optSetCode": None
    },
    {
        "optionCode": "BCP10",
        "shortDesc": "BuyDwn Coll Part Loss 10%",
        "longDesc": "BuyDwn Coll Part Loss 10%",
        "typeFlag": "O",
        "glAccount": None,
        "echo": "Y",
        "allowQty": "N",
        "insOnly": "N",
        "passThru": "N",
        "rptAsRev": "Y",
        "webResVisible": "Y",
        "expireDate": "2025-12-31",
        "optSetCode": None
    },
    {
        "optionCode": "BCP05",
        "shortDesc": "BuyDwn Coll Part Loss 05%",
        "longDesc": "BuyDwn Coll Part Loss 05%",
        "typeFlag": "O",
        "glAccount": None,
        "echo": "Y",
        "allowQty": "N",
        "insOnly": "N",
        "passThru": "N",
        "rptAsRev": "Y",
        "webResVisible": "Y",
        "expireDate": "2025-12-31",
        "optSetCode": None
    },
    {
        "optionCode": "BCP00",
        "shortDesc": "BuyDwn Coll Part Loss 00%",
        "longDesc": "BuyDwn Coll Part Loss 00%",
        "typeFlag": "O",
        "glAccount": None,
        "echo": "Y",
        "allowQty": "N",
        "insOnly": "N",
        "passThru": "N",
        "rptAsRev": "Y",
        "webResVisible": "Y",
        "expireDate": "2025-12-31",
        "optSetCode": None
    }
]



# Function to send a POST request
def send_post_request(url, payload):
    response = requests.post(url, json=payload)
    return response.status_code, response.json()

def main():
    # Send each request sequentially
    for payload in requests_payload:
        status_code, response_json = send_post_request(endpoint_url, payload)
        print(f'Status Code: {status_code}, Response: {response_json}')

if __name__ == "__main__":
    main()
