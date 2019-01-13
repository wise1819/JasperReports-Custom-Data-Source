import com.mashape.unirest.http.*;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

import java.util.List;

class DataFetcher {

    private ProjektronDataSource.API Api;
    private String Cookie;
    private String X_CSRF_Token;
    private String JsessionID;

    public DataFetcher(ProjektronDataSource.API api) {
        this.Api = api;
    }

    /**
     * @param credentialsString
     * @return success
     */
    boolean authenticate(String credentialsString) {
        //Rest Auth-Endpoint is different for both APIs
        final String base_url = "https://fu-projekt.bcs-hosting.de";
        String url;
        if (this.Api == ProjektronDataSource.API.BOOKING)
            url = base_url + "/app/rest/auth/login";
        else
            url = base_url + "/rest/auth/login";

        final HttpResponse<JsonNode> authenticationResponse;

        try {
            authenticationResponse = Unirest.post(url)
                    .header("Content-Type", "application/json")
                    .body(credentialsString)
                    .asJson();
        } catch (UnirestException e) {
            return false;
        }

        if (authenticationResponse.getStatus() != 200) {
            return false;
        }

        if (this.Api == ProjektronDataSource.API.BOOKING) {
            final Headers headers = authenticationResponse.getHeaders();
            Cookie = headers.getFirst("Cookie");
            X_CSRF_Token = headers.getFirst("X-Csrf-Token");
            return true;
        } else {
            final Headers headers = authenticationResponse.getHeaders();
            X_CSRF_Token = headers.getFirst("X-Csrf-Token");
            List<String> cookies = authenticationResponse.getHeaders().get("set-cookie");
            this.JsessionID = cookies.get(0).split(";")[0].substring(11);
            return true;
        }
    }

    JSONObject getData(String url) {
        try {

            if (this.Api == ProjektronDataSource.API.BOOKING) {
                final HttpResponse<JsonNode> dataResponse;

                dataResponse = Unirest.get(url)
                        .header("Cookie", Cookie)
                        .header("X-Csrf-Token", X_CSRF_Token)
                        .asJson();
                return dataResponse.getBody().getObject();
            }

            // TODO: Extend to other Mylin-Endpoints; URL fixed so far
            else {
                return Unirest.get(url).header("X-CSRF-Token", this.X_CSRF_Token).header("cache-control", "no-cache").header("JSESSIONID", this.JsessionID).asJson().getBody().getObject();
            }

        } catch (UnirestException e) {
            return null;
        }
    }
}