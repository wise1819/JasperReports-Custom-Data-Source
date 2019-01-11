import com.mashape.unirest.http.*;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

class DataFetcher {
    private String Cookie;
    private String X_CSRF_Token;

    /**
     *
     * @param credentialsString
     * @return
     */
    boolean authenticate(String credentialsString) {
        final String url = "https://fu-projekt.bcs-hosting.de/app/rest/auth/login";
        final HttpResponse<JsonNode> authentificationResponse;

        try {
            authentificationResponse = Unirest.post(url)
                    .header("Content-Type", "application/json")
                    .body(credentialsString)
                    .asJson();
        } catch (UnirestException e) {
            return false;
        }

        if (authentificationResponse.getStatus() != 200) {
            return false;
        }

        final Headers headers = authentificationResponse.getHeaders();
        Cookie = headers.getFirst("Cookie");
        X_CSRF_Token = headers.getFirst("X-Csrf-Token");

        return true;
    }

    JSONObject getData(String url) {
        if (url == null) url = "https://fu-projekt.bcs-hosting.de/app/rest/timerecording/bookings";

        final HttpResponse<JsonNode> dataResponse;

        try {
            dataResponse = Unirest.get(url)
                    .header("Cookie", Cookie)
                    .header("X-Csrf-Token", X_CSRF_Token)
                    .asJson();
        } catch (UnirestException e) {
            return null;
        }

        return dataResponse.getBody().getObject();
    }
}