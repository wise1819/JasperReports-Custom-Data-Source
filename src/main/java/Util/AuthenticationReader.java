package Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AuthenticationReader {
    public enum API_TYPE {
        BOOKING, MYLIN
    }

    /**
     * TODO: What is the best way to get the credentials?
     * Tries to get user-credentials for a bcs-instance via 1. Text-File
     *
     * @param api the type of the API to form the JSON
     * @return a String that incoperates the user's credentials in the
     */
    public static String getUserAuthentication(API_TYPE api) {
        //Tries reading credentials from the file .bcs in the users home-dir
        String userHome = System.getProperty("user.home");
        Map<String, String> auth = readFromPasswordFile(userHome + "/.bcs");
        if (auth == null)
            auth = readFromEnviromentVariables();
        if (auth == null)
            return null;
        return constructJson(auth, api);
    }

    /**
     * TODO: Check how to do this for windows
     *
     * @param path path to the file that contains the username and password in the following format:
     *             user=<username>
     *             password=<password>
     * @return
     */
    private static Map<String, String> readFromPasswordFile(String path) {
        String line;
        Map<String, String> auth = new HashMap<>();
        try {

            File f = new File(path);
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                if (line.contains("user")) {
                    line = line.split("=")[1];
                    line = line.trim();
                    auth.put("user", line);
                } else if (line.contains("password")) {
                    line = line.split("=")[1];
                    line = line.trim();
                    auth.put("password", line);
                } else {
                    continue;
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            return null;
        }
        return auth;
    }


    private static Map<String, String> readFromEnviromentVariables() {
        Map<String,String> env, auth;
        auth = new HashMap<>();
        env = System.getenv();
        auth.put("user",env.get("bcs_user"));
        auth.put("password",env.get("bcs_password"));
        if (auth.size() == 2)
            return auth;
        else
            return null;

    }

    private static String constructJson(Map<String, String> auth, API_TYPE api) {
        if (api == API_TYPE.BOOKING) {
            return String.format("{userLogin:\"%s\", userPwd: \"%s\" }", auth.get("user"), auth.get("password"));
        }
        return null;
    }
}
