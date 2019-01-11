import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import org.json.JSONArray;
import org.json.JSONObject;

public class ProjektronDataSource implements JRDataSource {
    private JSONArray data;
    private int index = -1;

    private ProjektronDataSource() {
        super();
        final DataFetcher dataFetcher = new DataFetcher();
        var authString = Util.AuthenticationReader.getUserAuthentication();
        dataFetcher.authenticate("{userLogin: \"<USERNAME>\", userPwd: \"<PASSWORD>\"}");
        data = dataFetcher.getData(null).getJSONArray("bookings");
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
