import java.util.List;

import org.json.JSONObject;

import authentication.Authenticator;
import authentication.Credentials;
import authentication.MylynAuthenticationObject;
import mylyn.MylynTicketFetcher;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public final class MylinDataSource implements JRDataSource {

	private MylynAuthenticationObject authObject;
	private List<JSONObject> data;
	private int index = -1;

	private MylinDataSource() {

		if (authObject == null || !authObject.isValid()) {
			Credentials c = Credentials.fetchCredentials();
			authObject = Authenticator.authenticateForMylynData(c.username, c.password);
		}

		if (!authObject.isValid()) {
			throw new RuntimeException(authObject.getAuthenticationMessage());
		}

		data = MylynTicketFetcher.fetchAllTicketsForAccount(authObject);

	}

	@Override
	public boolean next() {
		index++;
		return index < data.size();
	}

	@Override
	public Object getFieldValue(JRField jrField) throws JRException {
		JSONObject ticket = data.get(index);
		if (ticket.has(jrField.getName())) {
			return ticket.get(jrField.getName());
		} else {
			return new JSONObject();
		}
	}

	public static MylinDataSource getDataSource() {
		return new MylinDataSource();
	}

}
