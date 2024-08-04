import requests

base_url = 'http://192.168.1.211:8081/api/valid-type-codes'

valid_type_codes = [
    {
        "typeCode": "FC",
        "description": "FULLY COMP INSURED",
        "note1": "Note 1",
        "note2": "Note 2",
        "note3": "Note 3",
        "note4": "Note 4",
        "postingCode": "A",
        "chauffeured": "N",
        "reqIns": "N",
        "raPrintLibraryNumber": "97"
    },
    {
        "typeCode": "CC",
        "description": "CREDIT CARD INSURED",
        "note1": "Note 1",
        "note2": "Note 2",
        "note3": "Note 3",
        "note4": "Note 4",
        "postingCode": "B",
        "chauffeured": "N",
        "reqIns": "Y",
        "raPrintLibraryNumber": "98"
    },
    {
        "typeCode": "CO",
        "description": "COMPANY OWN INSURED",
        "note1": "Note 1",
        "note2": "Note 2",
        "note3": "Note 3",
        "note4": "Note 4",
        "postingCode": "C",
        "chauffeured": "Y",
        "reqIns": "N",
        "raPrintLibraryNumber": "99"
    }
]

# Function to create valid type codes
def create_valid_type_code(valid_type_code):
    response = requests.post(base_url, json=valid_type_code)
    if response.status_code == 200:
        print(f'Successfully created valid type code: {valid_type_code["typeCode"]}')
    else:
        print(f'Failed to create valid type code: {valid_type_code["typeCode"]}')
        print(f'Status Code: {response.status_code}, Response: {response.text}')

def main():
    for code in valid_type_codes:
        create_valid_type_code(code)

if __name__ == "__main__":
    main()