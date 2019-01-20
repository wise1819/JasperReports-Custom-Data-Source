import java.util.List;

import org.json.JSONObject;

import authentication.AuthenticationObject;
import authentication.Authenticator;
import bookings.BookingsFetcher;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRField;

public class ProjektronDataSource implements JRDataSource {
	private List<JSONObject> data;
	private int index = -1;

	private ProjektronDataSource() {
		AuthenticationObject authObject = Authenticator.authenticate("<USERNAME>", "<PASSWORD>");

		data = BookingsFetcher.fetchAllBookingsForAccount(authObject);
	}

	@Override
	public boolean next() {
		index++;
		return index < data.size();
	}

	@Override
	public Object getFieldValue(JRField jrField) {
		JSONObject booking = data.get(index);
		if (jrField.getName().equals("effortExpense")) {
			System.err.println(booking.get("effortExpense"));
		}
		if (booking.has(jrField.getName())) {
			return booking.get(jrField.getName());
		} else {
			return new JSONObject();
		}
	}

	public static ProjektronDataSource getDataSource() {
		return new ProjektronDataSource();
	}
}
