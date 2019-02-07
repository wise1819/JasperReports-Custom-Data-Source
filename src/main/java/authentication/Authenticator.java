package authentication;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Authenticator {

	public static final BookingsAuthenticationObject INVALID_AUTHENTICATIONOBJECT = new BookingsAuthenticationObject("",
			"");

	private static final String URL_BCS_BOOKINGS = "https://fu-projekt.bcs-hosting.de/";
	private static final String URL_BCS_TICKETS = "http://fuberlinws18.demo.projektron.de/";

	public static BookingsAuthenticationObject authenticateForBookings(String username, String password) {
		String urlBCS = URL_BCS_BOOKINGS + "app/rest/auth/login";

		HashMap<String, String> data = new HashMap<>();
		data.put("userLogin", username);
		data.put("userPwd", password);
		try {
			HttpResponse<String> authResponse = doPostRequest(urlBCS, data);
			if (authResponse.getStatus() == 200) {
				String mobileAppTokenCookie = getMobileAppTokenCookie(authResponse);
				String xCsrfToken = getXCsrfToken(authResponse);
				return new BookingsAuthenticationObject(mobileAppTokenCookie, xCsrfToken);
			} else {
				return new BookingsAuthenticationObject("ResponseCode was not 200 but: " + authResponse.getStatus()
						+ ". Authorization Response was: " + authResponse.getBody());
			}

		} catch (UnirestException e) {
			e.printStackTrace();
			return new BookingsAuthenticationObject("Exception happened during authorization: " + e.getMessage());
		}
	}

	public static MylynAuthenticationObject authenticateForMylynData(String username, String password) {

		String urlBCS = URL_BCS_TICKETS + "rest/auth/login";

		HashMap<String, String> data = new HashMap<>();
		data.put("login", username);
		data.put("pwd", password);

		try {
			HttpResponse<String> authResponse = doPostRequest(urlBCS, data);
			if (authResponse.getStatus() == 200) {
				String xCsrfToken = authResponse.getHeaders().get("X-CSRF-Token").get(0);
				List<String> setCookieHeader = authResponse.getHeaders().get("Set-Cookie");
				String sessionIdCookie = null;
				for (String cookiePart : setCookieHeader.get(0).split(";")) {
					if (cookiePart.contains("JSESSIONID")) {
						sessionIdCookie = cookiePart;
					}
				}
				return new MylynAuthenticationObject(xCsrfToken, sessionIdCookie);
			} else {
				return new MylynAuthenticationObject("ResponseCode was not 200 but: " + authResponse.getStatus()
						+ ". Authorization Response was: " + authResponse.getBody());
			}
		} catch (UnirestException e) {
			e.printStackTrace();
			return new MylynAuthenticationObject("Exception happened during authorization: " + e.getMessage());
		}

	}

	private static HttpResponse<String> doPostRequest(String url, HashMap<String, String> data)
			throws UnirestException {
		return Unirest.post(url).header("Content-Type", "application/json").header("cache-control", "no-cache")
				.header("Content-Encoding", "UTF-8").body(new JSONObject(data).toString()).asString();
	}

	private static String getXCsrfToken(HttpResponse<String> authResponse) {
		return authResponse.getHeaders().get("X-CSRF-Token").get(0);
	}

	private static String getMobileAppTokenCookie(HttpResponse<String> authResponse) {
		String cookies = authResponse.getHeaders().get("Set-Cookie").get(0);
		int beginIndex = cookies.indexOf("MobileAppToken");
		int endIndex = cookies.substring(beginIndex).indexOf(";");
		String mobileAppTokenCookie = cookies.substring(beginIndex, endIndex);
		return mobileAppTokenCookie;
	}

}
