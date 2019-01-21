package mylyn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;

import authentication.Authenticator;
import authentication.MylynAuthenticationObject;

public class MylynTicketFetcher {

	private static final String BCS_SERVER = "http://fuberlinws18.demo.projektron.de/";
	private static final String TICKETS_PATH = "/rest/mylyn/tickets/all";

	/**
	 * 
	 * @param auth object that holds all authentication details for session with the
	 *             server.
	 * @return {@link List} of {@link JSONObject}
	 */
	public static List<JSONObject> fetchAllTicketsForAccount(MylynAuthenticationObject auth) {
		String url = BCS_SERVER + TICKETS_PATH;
		System.out.println(url);
		try {
			HttpResponse<JsonNode> response = getRequestWithAuthObject(auth, url);

			List<JSONObject> tickets = new ArrayList<JSONObject>();
			System.out.println(response.getBody());
			JSONArray ticketsJsonArray = response.getBody().getArray();
			for (int i = 0; i < ticketsJsonArray.length(); i++) {
				tickets.add(ticketsJsonArray.getJSONObject(i));
			}
			return tickets;
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Collections.emptyList();
	}

	private static HttpResponse<JsonNode> getRequestWithAuthObject(MylynAuthenticationObject auth, String url)
			throws UnirestException {
		GetRequest request = Unirest.get(url).header("Cookie", auth.getSessionIdCookie())
				.header("X-CSRF-Token", auth.getxCsrfToken()).header("cache-control", "no-cache")
				.header("Connection", "keep-alive");

		request.getHeaders().forEach((k, v) -> System.out.println(k + ": " + v));
		return request.asJson();
	}

	public static void main(String[] args) {
		MylynAuthenticationObject authenticateForMylynData = Authenticator.authenticateForMylynData("Admin", "Admin");
		if (!authenticateForMylynData.isValid()) {
			System.out.println("Authentication did not work");
			System.out.println(authenticateForMylynData.getAuthenticationMessage());
			return;
		}
		List<JSONObject> allTickets = MylynTicketFetcher.fetchAllTicketsForAccount(authenticateForMylynData);
		allTickets.forEach(System.out::println);
	}

}
