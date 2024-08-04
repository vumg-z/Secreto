import requests
import json

base_url = 'http://192.168.1.211:8081/api/class-codes'

# Pricing codes 
# CCAR, pricing code = 21
# CCMR, = 21
# CDAR = 21
# CDMN = 21
# CDMR = 21
# CFAN = 40
# CFAR = 40
# CWMN = 21
# CWMR = 21
# EBAN = 21
# EBAR = 21
# ECAN = 21
# ECAR = 21
# ECMN = 21
# ECMR = 21
# EDAN = 21
# EDAR = 21
# EDMN = 21
# EDMR = 21
# EFAN = 40 
# EFAR = 40
# FCAR = 29 
# FCMR = 29
# FDMR = 29 
# FVAR = 45
# IBAN = 25
# IBAR = 25
# ICAR = 25
# ICMR = 25
# IDAN = 25
# IDAR = 25
# IDMN = 25
# IDMR = 25
# IFAR = 45
# IPAR = 40
# IPMR = 40
# IVAR = 45 
# IVMR = 45
# IWMN = 25
# LCAR = 45
# MBCN = 20
# MCAR = 20
# MDAR = 20 
# MVAR = 45
# SCAR = 25
# XXAR = 21

def create_class_code(class_code_data):
    response = requests.post(base_url, headers={"Content-Type": "application/json"}, data=json.dumps(class_code_data))
    return response

def main():
    default_location_number = "GDLMY1"
    pricing_codes = {
        "CCAR": 21, "CCMR": 21, "CDAR": 21, "CDMN": 21, "CDMR": 21, "CFAN": 40,
        "CFAR": 40, "CWMN": 21, "CWMR": 21, "EBAN": 21, "EBAR": 21, "ECAN": 21,
        "ECAR": 21, "ECMN": 21, "ECMR": 21, "EDAN": 21, "EDAR": 21, "EDMN": 21,
        "EDMR": 21, "EFAN": 40, "EFAR": 40, "FCAR": 29, "FCMR": 29, "FDMR": 29,
        "FVAR": 45, "IBAN": 25, "IBAR": 25, "ICAR": 25, "ICMR": 25, "IDAN": 25,
        "IDAR": 25, "IDMN": 25, "IDMR": 25, "IFAR": 45, "IPAR": 40, "IPMR": 40,
        "IVAR": 45, "IVMR": 45, "IWMN": 25, "LCAR": 45, "MBCN": 20, "MCAR": 20,
        "MDAR": 20, "MVAR": 45, "SCAR": 25, "XXAR": 21, "FDAR": 29
    }

    class_codes_list = [
        {"classCode": "XXAR", "description": "Executive Car"},
        {"classCode": "MCAR", "description": "Mini Car"},
        {"classCode": "MDAR", "description": "Mid-Size Car"},
        {"classCode": "ECAR", "description": "Economy Car"},
        {"classCode": "ECMR", "description": "Economy Car Manual"},
        {"classCode": "EDAR", "description": "Economy Car Auto"},
        {"classCode": "EDMR", "description": "Economy Car Deluxe"},
        {"classCode": "CCAR", "description": "Compact Car"},
        {"classCode": "CCMR", "description": "Compact Car Manual"},
        {"classCode": "CDMR", "description": "Compact Car Deluxe"},
        {"classCode": "IBAR", "description": "Intermediate Car"},
        {"classCode": "ICAR", "description": "Intermediate Car Auto"},
        {"classCode": "IDAR", "description": "Intermediate Car Deluxe"},
        {"classCode": "IDMR", "description": "Intermediate Car Manual"},
        {"classCode": "SCAR", "description": "Standard Car"},
        {"classCode": "FCAR", "description": "Full Size Car"},
        {"classCode": "FCMR", "description": "Full Size Car Manual"},
        {"classCode": "FDAR", "description": "Full Size Car Auto"},
        {"classCode": "CFAR", "description": "Crossover SUV"},
        {"classCode": "IFAR", "description": "Intermediate SUV"},
        {"classCode": "MVAR", "description": "Minivan"},
        {"classCode": "IVAR", "description": "Luxury SUV"}
    ]

    for class_code in class_codes_list:
        pricing_code_id = pricing_codes.get(class_code["classCode"], None)
        if not pricing_code_id:
            continue  # Skip class codes without a pricing code

        class_code_data = {
            "classCode": class_code["classCode"],
            "description": class_code["description"],
            "location": {
                "location_number": default_location_number
            },
            "pricingCode": {
                "code": pricing_code_id
            }
        }
        response = create_class_code(class_code_data)
        if response.status_code in [200, 201]:
            try:
                print(f"Status Code: {response.status_code}, Response: {response.status_code}")
            except requests.exceptions.JSONDecodeError:
                print(f"Status Code: {response.status_code}, Response: No JSON content")
        else:
            print(f"Failed to create class code. Status Code: {response.status_code}, Response: {response.text}")

if __name__ == "__main__":
    main()
