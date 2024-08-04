import requests
import json

base_url = 'http://192.168.1.211:8081/api/locations/receive'

def create_default_location(location_data):
    response = requests.post(base_url, headers={"Content-Type": "application/json"}, data=json.dumps(location_data))
    return response

def main():
    default_location = {
        "location_number": "DEFLOC",
        "location_name": "Default Location",
        "address_line1": "System",
        "address_line2": "System",
        "address_line3": "System",
        "phone": "System",
        "profit_center_number": "001",
        "doFuelCalc": "Y",
        "holdingDrawer": "HD1",
        "autoVehicleSelect": "N",
        "checkOutFuel": "8",
        "validRentalLoc": "Y",
        "metroplex_location_id": "Default",
        "allowMultiLanguageRa": "Y",
        "allowWaitRas": "Y",
        "dispatchControl": "N"
    }

    response = create_default_location(default_location)
    if response.status_code == 200 or response.status_code == 201:
        try:
            print(f"Status Code: {response.status_code}, Response: {response.json()}")
        except requests.exceptions.JSONDecodeError:
            print(f"Status Code: {response.status_code}, Response: No JSON content")
    else:
        print(f"Failed to create default location. Status Code: {response.status_code}, Response: {response.text}")

if __name__ == "__main__":
    main()
