import requests
import json

base_url = 'http://192.168.1.211:8081/api/rate-products/add-classes'

payload = [
    {
        "classCode": "XXAR",
        "rateProductNumber": "MI2",
        "rateSetCode": "GDLA01/US", 
        "dayRate": 36.44,
        "weekRate": 255.06,
        "monthRate": 1093.13,
        "xDayRate": 36.44,
        "hourRate": 12.15,
        "mileRate": 0.0
    },
    {
        "classCode": "MCAR",
        "rateProductNumber": "MI2",
        "rateSetCode": "GDLA01/US", 
        "dayRate": 17.71,
        "weekRate": 106.25,
        "monthRate": 478.13,
        "xDayRate": 17.71,
        "hourRate": 5.90,
        "mileRate": 0.0
    },
    {
        "classCode": "MDAR",
        "rateProductNumber": "MI2",
        "rateSetCode": "GDLA01/US", 
        "dayRate": 17.71,
        "weekRate": 106.25,
        "monthRate": 478.13,
        "xDayRate": 17.71,
        "hourRate": 5.90,
        "mileRate": 0.0
    },
    {
        "classCode": "ECAR",
        "rateProductNumber": "MI2",
        "rateSetCode": "GDLA01/US", 
        "dayRate": 28.65,
        "weekRate": 200.52,
        "monthRate": 859.38,
        "xDayRate": 28.65,
        "hourRate": 9.55,
        "mileRate": 0.0
    },
    {
        "classCode": "ECMR",
        "rateProductNumber": "MI2",
        "rateSetCode": "GDLA01/US", 
        "dayRate": 28.65,
        "weekRate": 200.52,
        "monthRate": 859.38,
        "xDayRate": 28.65,
        "hourRate": 9.55,
        "mileRate": 0.0
    },
    {
        "classCode": "EDAR",
        "rateProductNumber": "MI2",
        "rateSetCode": "GDLA01/US", 
        "dayRate": 28.65,
        "weekRate": 200.52,
        "monthRate": 859.38,
        "xDayRate": 25.65,
        "hourRate": 9.55,
        "mileRate": 0.0
    },
    {
        "classCode": "EDMR",
        "rateProductNumber": "MI2",
        "rateSetCode": "GDLA01/US", 
        "dayRate": 28.65,
        "weekRate": 200.52,
        "monthRate": 859.38,
        "xDayRate": 28.65,
        "hourRate": 9.55,
        "mileRate": 0.0
    },
    {
        "classCode": "CCAR",
        "rateProductNumber": "MI2",
        "rateSetCode": "GDLA01/US", 
        "dayRate": 29.65,
        "weekRate": 207.52,
        "monthRate": 889.38,
        "xDayRate": 29.65,
        "hourRate": 9.55,
        "mileRate": 0.0
    },
    {
        "classCode": "CCMR",
        "rateProductNumber": "MI2",
        "rateSetCode": "GDLA01/US", 
        "dayRate": 29.65,
        "weekRate": 207.52,
        "monthRate": 839.38,
        "xDayRate": 29.65,
        "hourRate": 9.55,
        "mileRate": 0.0
    },
    {
        "classCode": "CDMR",
        "rateProductNumber": "MI2",
        "rateSetCode": "GDLA01/US", 
        "dayRate": 28.65,
        "weekRate": 200.52,
        "monthRate": 859.38,
        "xDayRate": 28.65,
        "hourRate": 9.55,
        "mileRate": 0.0
    },
    {
        "classCode": "IBAR",
        "rateProductNumber": "MI2",
        "rateSetCode": "GDLA01/US", 
        "dayRate": 36.44,
        "weekRate": 255.06,
        "monthRate": 1093.13,
        "xDayRate": 36.44,
        "hourRate": 12.15,
        "mileRate": 0.0
    },
    {
        "classCode": "ICAR",
        "rateProductNumber": "MI2",
        "rateSetCode": "GDLA01/US", 
        "dayRate": 36.44,
        "weekRate": 255.06,
        "monthRate": 1093.13,
        "xDayRate": 36.44,
        "hourRate": 12.15,
        "mileRate": 0.0
    },
    {
        "classCode": "IDAR",
        "rateProductNumber": "MI2",
        "rateSetCode": "GDLA01/US", 
        "dayRate": 36.44,
        "weekRate": 255.06,
        "monthRate": 1093.13,
        "xDayRate": 36.44,
        "hourRate": 12.15,
        "mileRate": 0.0
    },
    {
        "classCode": "IDMR",
        "rateProductNumber": "MI2",
        "rateSetCode": "GDLA01/US", 
        "dayRate": 36.44,
        "weekRate": 255.06,
        "monthRate": 1093.13,
        "xDayRate": 36.44,
        "hourRate": 12.15,
        "mileRate": 0.0
    },
    {
        "classCode": "SCAR",
        "rateProductNumber": "MI2",
        "rateSetCode": "GDLA01/US", 
        "dayRate": 37.44,
        "weekRate": 262.77,
        "monthRate": 1100.13,
        "xDayRate": 37.44,
        "hourRate": 12.15,
        "mileRate": 0.0
    },
    {
        "classCode": "FCAR",
        "rateProductNumber": "MI2",
        "rateSetCode": "GDLA01/US", 
        "dayRate": 45.80,
        "weekRate": 320.60,
        "monthRate": 1374.00,
        "xDayRate": 45.80,
        "hourRate": 15.00,
        "mileRate": 0.0
    },
    {
        "classCode": "FCMR",
        "rateProductNumber": "MI2",
        "rateSetCode": "GDLA01/US", 
        "dayRate": 45.70,
        "weekRate": 320.50,
        "monthRate": 1373.90,
        "xDayRate": 45.70,
        "hourRate": 14.90,
        "mileRate": 0.0
    },
    {
        "classCode": "FDAR",
        "rateProductNumber": "MI2",
        "rateSetCode": "GDLA01/US", 
        "dayRate": 45.80,
        "weekRate": 320.60,
        "monthRate": 1374.00,
        "xDayRate": 45.80,
        "hourRate": 15.00,
        "mileRate": 0.0
    },
    {
        "classCode": "CFAR",
        "rateProductNumber": "MI2",
        "rateSetCode": "GDLA01/US", 
        "dayRate": 46.70,
        "weekRate": 326.90,
        "monthRate": 1401.00,
        "xDayRate": 46.70,
        "hourRate": 15.50,
        "mileRate": 0.0
    },
    {
        "classCode": "IFAR",
        "rateProductNumber": "MI2",
        "rateSetCode": "GDLA01/US", 
        "dayRate": 62.50,
        "weekRate": 437.50,
        "monthRate": 1875.00,
        "xDayRate": 62.50,
        "hourRate": 21.00,
        "mileRate": 0.0
    },
    {
        "classCode": "MVAR",
        "rateProductNumber": "MI2",
        "rateSetCode": "GDLA01/US", 
        "dayRate": 65.00,
        "weekRate": 455.00,
        "monthRate": 1950.00,
        "xDayRate": 65.00,
        "hourRate": 22.00,
        "mileRate": 0.0
    },
    {
        "classCode": "IVAR",
        "rateProductNumber": "MI2",
        "rateSetCode": "GDLA01/US", 
        "dayRate": 9999.00,
        "weekRate": 9999.00,
        "monthRate": 9999.00,
        "xDayRate": 9999.00,
        "hourRate": 9999.00,
        "mileRate": 0.0
    }
]

def upload_rates(payload):
    response = requests.post(base_url, headers={"Content-Type": "application/json"}, data=json.dumps(payload))
    return response

def main():
    response = upload_rates(payload)
    if response.status_code in [200, 201]:
        print(f"Status Code: {response.status_code}, Response: {response.status_code}")
    else:
        print(f"Failed to upload rates. Status Code: {response.status_code}, Response: {response.text}")

# Execute the script
if __name__ == "__main__":
    main()
