import requests
import json

base_url = 'http://192.168.1.211:8081/api/privilege-codes'

privilege_codes_list = [
    {"code": "20D", "description": "20 DISC EVEN CORP RATES", "applyToAllRates": True, "note": "", "premiumAccount": False},
    
    {"code": "IL0", 
     "description": "Incl10 TAX LRF LOCN", 
     "applyToAllRates": False, 
     "note": "", 
     "optionSetCodeString": "1",
     "premiumAccount": False}, 
    
    {"code": "IL2", "description": "Incl12 TAX, LOCN, LRF, LDW", "applyToAllRates": False, "note": "", "premiumAccount": False},
    {"code": "L0", "description": "Inc0 Priv Pring Code", "applyToAllRates": False, "note": "NO LDW", "premiumAccount": False},
    {"code": "L1", "description": "LDW 20Ttl 20Ptl Prv Prc Code", "applyToAllRates": False, "note": "LDW 20Ttl20Ptl", "premiumAccount": False},
    
    {"code": "L2", 
     "description": "LDW 10Ttl 10Ptl Prv Prc Code", 
     "applyToAllRates": False, 
     "note": "LDW 10Ttl10Ptl", 
     "premiumAccount": False},
     
    {"code": "PA", "description": "PREM ACCT EXEMPT OBOOKING, LOR", "applyToAllRates": False, "note": "", "premiumAccount": True},
    {"code": "DF", "description": "default_no_action", "applyToAllRates": False, "note": "default_no_action", "premiumAccount": True}
]

def create_privilege_code(privilege_code_data):
    headers = {"Content-Type": "application/json"}
    response = requests.post(base_url, headers=headers, data=json.dumps(privilege_code_data))
    return response

def main():
    for privilege_code in privilege_codes_list:
        response = create_privilege_code(privilege_code)
        if response.status_code in [200, 201]:
            try:
                print(f"Status Code: {response.status_code}, Response: {response.json()}")
            except requests.exceptions.JSONDecodeError:
                print(f"Status Code: {response.status_code}, Response: No JSON content")
        else:
            print(f"Failed to create privilege code. Status Code: {response.status_code}, Response: {response.text}")

if __name__ == "__main__":
    main()
