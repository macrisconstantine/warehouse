# Warehouse
Simple Shipment Navigation Application

##  Description
This application is a simple tool designed to assist me as a warehouse manager. The app helps me keep track of packing progress and general statistics for yearly shipments of literature pallets.

## How to download
Simply download the WarehouseM12.jar file in the repo and it should run immediately! 
#### In case the ```.jar``` file does not run (JNI error or Java Exception)
- Check to make sure it is being opened with the appropriate JDK platform
- Check to make sure you have the right Java version 

## How to use
The application opens to a login page, and since you won't yet have a user, you will have to tab over to "Register as New User" to make a user.
### Make a user
- Fill in the necessary fields.
- Press "Register", and return to login on success.
- Log in with your username and password. 

Your user data will be automatically be recorded and saved in a local file upon registration.
### Navigating the Shipments
Upon login you will find a 2022 shipment with default values. 
- Press "New Shipment" to add a new shipment of default pallet and box sizes.
- Adjust the values to represent the real shipment's attributes and press "Create Shipment".
- Log packing progress by submitting the number of book, bag, or leaflet pallets that have been packed--use the spinner in case you want to log multiple packed pallets at once.
- Use the left and right arrows to navigate between shipments.
- To create a new shipment with custom box and pallet attributes, press "Edit Pallets"--before creating the new shipment--to change the pallet attributes.
- Use the spinner and search button to find pallet info according to ID (default spinner value is always the ID of the pallet added into the warehouse most recently).
- If an irreversable mistake is made with any shipment info, simply drop the shipment, and create a new one with the corrected values.
- When useful, use the reset and finish shipment buttons as shortcuts to avoid unecessary manual labor.
- Press "Shipment Details" to see details of the currently displayed shipment.
- Press "General Statistics" to see cumulative warehouse statistics.
