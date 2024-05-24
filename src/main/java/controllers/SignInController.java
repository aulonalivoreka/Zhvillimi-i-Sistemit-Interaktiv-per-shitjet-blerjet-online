package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.dto.LoginUserDto;
import services.UserService;

public class SignInController {

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField pwdPassword;
    @FXML
    private Button loginButton;
    @FXML
    private Button cancelButton;
    @FXML
    private CheckBox showpassword;
    @FXML
    private TextField txtPassword;
    @FXML
    private Label loginMessageLabel;

    @FXML
    public void initialize() {
        showpassword.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                txtPassword.setText(pwdPassword.getText());
                txtPassword.setVisible(true);
                pwdPassword.setVisible(false);
            } else {
                pwdPassword.setText(txtPassword.getText());
                pwdPassword.setVisible(true);
                txtPassword.setVisible(false);
            }
        });
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = txtUsername.getText();
        String password = pwdPassword.getText();

        LoginUserDto loginUserDto = new LoginUserDto(username, password);
        boolean success = UserService.login(loginUserDto);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Login Successful!", "Welcome " + username);
            // Optionally redirect to the main application window
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();
        } else {
            loginMessageLabel.setText("Invalid username or password.");
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
