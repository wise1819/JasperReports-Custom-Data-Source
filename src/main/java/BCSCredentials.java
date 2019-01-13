import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

// ### HOW TO USE THIS AUTHENTICATION READER ###
// So far 2 Methods are supported
// 1. read from a file located at "~/.bcs" with a format like:
// "user=<username>
// "password=<password>
// 2. read from environment Variables with Variable-Names: "BCS_USER" and "BCS_PASSWORD"


class BCSCredentials {
    enum FORMAT {
        BOOKING, MYLIN
    }

    private String username;
    private String password;

    BCSCredentials() {
        if (readPasswordFile() || readEnvironmentVariables()) return;
        throw new RuntimeException("Could not load BCS Credentials");
    }

    /**
     * reads from the file located at ~/.bcs that contains the username and password in the following format:
     * user=<username>
     * password=<password>
     *
     * @return success
     */

    boolean readPasswordFile() {
        //Tries reading credentials from the file .bcs in the users home-dir
        String home = System.getProperty("user.home");
        Path path = Paths.get(home, ".bcs");

        try {
            Files.lines(path).forEach(line -> {
                String[] parts = line.split("=");
                switch (parts[0]) {
                    case "user":
                        this.username = parts[1];
                        break;
                    case "password":
                        this.password = parts[1];
                        break;
                }
            });
        } catch (IOException e) {
            return false;
        }
        return (username != null && password != null);
    }

    /**
     * Reads the User-Credentials from the System-Environment-Variables named "BCS_USER" and "BCS_PASSWORD"
     *
     * @return success
     */
    private boolean readEnvironmentVariables() {
        Map<String, String> env;
        env = System.getenv();
        username = env.get("BCS_USER");
        password = env.get("BCS_PASSWORD");
        return (username != null && password != null);
    }

    String asJSON(FORMAT format) {
        switch (format) {
            case BOOKING:
                return String.format("{userLogin:\"%s\",userPwd:\"%s\"}", username, password);
            case MYLIN:
                return String.format("{login:\"%s\",pwd:\"%s\"}", username, password);
            default:
                return null;
        }
    }
}
