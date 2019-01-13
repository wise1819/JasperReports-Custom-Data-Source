import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRField;
import org.json.JSONArray;
import org.json.JSONObject;

//TODO: Other authentication-String added, but the Mylin-Rest-Interface works differently since it relies on a JSESSIONID-cookie
public class ProjektronDataSource implements JRDataSource {
    private JSONArray data;
    private int index = -1;

    private ProjektronDataSource() {
        super();
        final String url = "https://fu-projekt.bcs-hosting.de/app/rest/timerecording/bookings?syncStateTimestamp=0&minDate=2018-10-29";

        final DataFetcher dataFetcher = new DataFetcher();
        BCSCredentials credentials = new BCSCredentials();
        // TODO: The format needs to be hard coded. Use Gradle (there are compiling parameters or something like that that can parameterize the execution, creating two classes from one, with each having a different format hard coded)
        dataFetcher.authenticate(credentials.asJSON(BCSCredentials.FORMAT.BOOKING));
        data = dataFetcher.getData(url).getJSONArray("bookings");
    }

    @Override
    public boolean next() {
        index++;
        return index < data.length();
    }

    @Override
    public Object getFieldValue(JRField jrField) {
        JSONObject booking = data.getJSONObject(index);
        return booking.get(jrField.getName());
    }

    public static ProjektronDataSource getDataSource() {
        return new ProjektronDataSource();
    }
}
