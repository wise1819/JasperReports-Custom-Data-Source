import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRField;
import org.json.JSONArray;
import org.json.JSONObject;

//https://community.jaspersoft.com/wiki/how-create-and-use-jrdatasource-adapter
//TODO: Other authentication-String added, but the Mylin-Rest-Interface works differently since it relies on a JSESSIONID-cookie
public class ProjektronDataSource implements JRDataSource {
    public enum API{
        BOOKING, MYLIN
    }

    // TODO: The format needs to be hard coded. Use Gradle (there are compiling parameters or something like that that can parameterize the execution, creating two classes from one, with each having a different format hard coded)
    private API Api = API.BOOKING;
    private JSONArray data;
    private int index = -1;

    private ProjektronDataSource() {
        super();
        final String url = "https://fu-projekt.bcs-hosting.de/app/rest/timerecording/bookings?syncStateTimestamp=0&minDate=2018-10-29";

        final DataFetcher dataFetcher = new DataFetcher(API.BOOKING);
        BCSCredentials credentials = new BCSCredentials();
        dataFetcher.authenticate(credentials.asJSON(Api));
        data = dataFetcher.getData(url).getJSONArray("bookings");
    }

    @Override
    /**
     * this method tell to JasperReport if there are still records to read. If it returns true, there are other records, otherwise it must return false.
     */
    public boolean next() {
        index++;
        return index < data.length();
    }

    @Override
    /**
     * We already know that a record can have any number of fields. This method is called for every field in the report, and it must return a value for that field. The parameter received by this method is the field that needs to be valorized, and it contains the name of the field, the description and, in general, information about the field. Knowing this information could be useful to identify the field and return the appropriate data.
     */
    public Object getFieldValue(JRField jrField) {
        JSONObject booking = data.getJSONObject(index);
        return booking.get(jrField.getName());
    }

    public static ProjektronDataSource getDataSource() {
        return new ProjektronDataSource();
    }
}
