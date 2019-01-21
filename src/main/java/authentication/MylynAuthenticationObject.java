package authentication;

public class MylynAuthenticationObject {

	private final String sessionIdCookie;
	private final String xCsrfToken;
	private final String authenticationMessage;

	public MylynAuthenticationObject(String message) {
		sessionIdCookie = "";
		xCsrfToken = "";
		authenticationMessage = message;
	}

	public MylynAuthenticationObject(String xCsrfToken, String sessionIdCookie) {
		this.sessionIdCookie = sessionIdCookie;
		this.xCsrfToken = xCsrfToken;
		authenticationMessage = "";
	}

	public boolean isValid() {
		return !(sessionIdCookie.isEmpty() || xCsrfToken.isEmpty());
	}

	public String getSessionIdCookie() {
		return sessionIdCookie;
	}

	public String getxCsrfToken() {
		return xCsrfToken;
	}

	public String getAuthenticationMessage() {
		return authenticationMessage;
	}

}
