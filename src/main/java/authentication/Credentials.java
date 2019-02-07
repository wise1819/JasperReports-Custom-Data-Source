package authentication;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Credentials {
	public String username;
	public String password;

	public Credentials(String uname, String passwd) {
		username = uname;
		password = passwd;
	}

	public static Credentials fetchCredentials() {
		try {
			File credentialsFile = new java.io.File("." + File.separator + "projektronCredentials");
			if (!credentialsFile.exists()) {
				credentialsFile.createNewFile();
				JTextField userNameField = new JTextField();
				JPasswordField passwordField = new JPasswordField();
				Object[] message = { "Name", userNameField, "Passwort", passwordField };

				JOptionPane pane = new JOptionPane(message, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
				pane.createDialog(null, "Credentials").setVisible(true);

				String username = userNameField.getText();
				String password = new String(passwordField.getPassword());

				if (credentialsFile.canWrite()) {
					try (PrintWriter writer = new PrintWriter(credentialsFile)) {
						writer.println("username:" + username);
						writer.println("password:" + password);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return new Credentials(username, password);
			} else {
				List<String> allLines = Files.readAllLines(credentialsFile.toPath());
				String username = "", password = "";
				for (String line : allLines) {
					if (line.startsWith("username")) {
						username = line.split(":")[1];
					} else {
						password = line.split(":")[1];
					}
				}
				return new Credentials(username, password);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}