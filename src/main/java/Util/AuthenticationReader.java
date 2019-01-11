package Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//TODO: Test on Windows

// ### HOW TO USE THIS AUTHENTICATION READER ###
// So far 2 Methods are supported
// 1. read from a file located at "~/.bcs" with a format like:
// "user=<username>
// "password=<password>
// 2. read from environment Variables with Variable-Names: "BCS_USER" and "BCS_PASSWORD"


public class AuthenticationReader {
    public enum API_TYPE {
        BOOKING, MYLIN
    }

    /**
     * Tries to get user-credentials for a bcs-instance via 1. Text-File
     *
     * @param api the type of the API to form the JSON
     * @return a String that incoperates the user's credentials in the
     */
    public static String getUserAuthentication(API_TYPE api) {
        Map<String, String> auth = readFromPasswordFile();
        if (auth == null)
            auth = readFromEnvironmentVariables();
        if (auth == null)
            return null;
        return constructJson(auth, api);
    }

    /**
     * reads from the file located at ~/.bcs that contains the username and password in the following format:
     * user=<username>
     * password=<password>
     *
     * @return
     */
    private static Map<String, String> readFromPasswordFile() {
        //Tries reading credentials from the file .bcs in the users home-dir
        String path = System.getProperty("user.home") + "/.bcs";

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

    /**
     * Reads the User-Credentials from the System-Environment-Variables named "BCS_USER" and "BCS_PASSWORD"
     *
     * @return
     */
    private static Map<String, String> readFromEnvironmentVariables() {
        Map<String, String> env, auth;
        auth = new HashMap<>();
        env = System.getenv();
        auth.put("user", env.get("BCS_USER"));
        auth.put("password", env.get("BCS_PASSWORD"));
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
