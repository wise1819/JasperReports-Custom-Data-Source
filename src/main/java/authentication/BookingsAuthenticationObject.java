package authentication;

public class BookingsAuthenticationObject {

	private final String mobileAppTokenCookie;
	private final String xCsrfToken;
	private final String authenticationMessage;

	public BookingsAuthenticationObject(String mobileAppTokenCookie, String xCsrfToken) {
		this.mobileAppTokenCookie = mobileAppTokenCookie;
		this.xCsrfToken = xCsrfToken;
		this.authenticationMessage = "";
	}

	public BookingsAuthenticationObject(String message) {
		this.mobileAppTokenCookie = "";
		this.xCsrfToken = "";
		this.authenticationMessage = message;
	}

	public String getMobileAppTokenCookie() {
		return mobileAppTokenCookie;
	}

	public String getxCsrfToken() {
		return xCsrfToken;
	}

	public boolean isValid() {
		return !(mobileAppTokenCookie.isEmpty() || xCsrfToken.isEmpty());
	}

	public String getAuthenticationMessage() {
		return authenticationMessage;
	}
}
