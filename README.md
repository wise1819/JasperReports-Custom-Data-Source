# JasperReports-Custom-Data-Source
A Java implementation of a custom data source for JasperReports to use in order to fetch data from Projektron's BCS server

## Build the project
To build the custom data source, enter your credentials in the `ProjektronDataSource` constructor and run `gradle shadowJar`. This will (among other things) create a `build/libs/` folder with a single `.jar` file in it. This file contains the data adapter and all of its dependencies.
## Enable Authentication
There are three ways of passing in user-credentials into the custom-data-source component
1. Put the corresponding JSON-String in the source code and compile
2. Put a file at $HOME/.bcs with two lines containing either user=USERNAME or password=PASSWORD
3. Put the credentials in your enviroment variables BCS_USER and BCS_PASSWORD

## Making the data source available in JasperSoft Studio
### Defining the data adapter
1. Open the `Data Adapter Wizard` (creating a new adapter)
2. Select `Custom implementation of JRDataSource`
3. In the `Factory Class` text field, simply put `ProjektronDataSource`
4. In the field for the static method, input `getDataSource`
5. Not click the `Add` button and select the `.jar` file created earlier
6. Click `Finish` (you can also click `Test` before that)
### Defining a data set
1. Create a new datasource, selecting the just created data adapter from the combo box
2. Right click the dataset/report root node and add the appropriate fields (for example `effortRegularTimeCosts` and `java.lang.String`)
3. To preview the data, click the `Data preview` tab at the bottom and then on `Refresh Preview Data`

