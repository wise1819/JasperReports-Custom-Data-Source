import java.util.List;

import org.json.JSONObject;

import authentication.Authenticator;
import authentication.BookingsAuthenticationObject;
import authentication.Credentials;
import bookings.BookingsFetcher;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRField;

public class ProjektronDataSource implements JRDataSource {
	private List<JSONObject> data;
	private int index = -1;
	private BookingsAuthenticationObject authObject;

	private ProjektronDataSource() {
		if (authObject == null || !authObject.isValid()) {
			Credentials c = Credentials.fetchCredentials();
			authObject = Authenticator.authenticateForBookings(c.username, c.password);
		}

		if (!authObject.isValid()) {
			throw new RuntimeException(authObject.getAuthenticationMessage());
		}

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
