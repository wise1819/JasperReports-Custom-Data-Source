# JasperReports-Custom-Data-Source
A Java implementation of a custom data source for JasperReports to use in order to fetch data from Projektron's BCS server

## Building the project
### Enabling Authentication
Currently there are two ways of passing in user-credentials into the custom-data-source component implemented:
1. Put a file at `$HOME/.bcs` (e.g. `c:\users\name\.bcs` in windows) containing two lines `user=USERNAME` and `password=PASSWORD`
2. Put the BCS username and password into the environment variables `BCS_USER` and `BCS_PASSWORD` respectively

### Making the data source available in JasperSoft Studio
#### Defining the data adapter
1. Open the `Data Adapter Wizard` (creating a new adapter)
2. Select `Custom implementation of JRDataSource`
3. In the `Factory Class` text field, simply put `ProjektronDataSource`
4. In the field for the static method, input `getDataSource`
5. Not click the `Add` button and select the `.jar` file created earlier
6. Click `Finish` (you can also click `Test` before that)
#### Defining a data set
1. Create a new datasource, selecting the just created data adapter from the combo box
2. Right click the dataset/report root node and add the appropriate fields (for example `effortRegularTimeCosts` and `java.lang.String`)
3. To preview the data, click the `Data preview` tab at the bottom and then on `Refresh Preview Data`

