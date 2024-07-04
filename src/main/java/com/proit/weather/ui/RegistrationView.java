package com.proit.weather.ui;

import com.proit.weather.backend.entity.User;
import com.proit.weather.backend.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.Set;

@Route("register")
@AnonymousAllowed
public class RegistrationView extends VerticalLayout {

    public RegistrationView(UserService userService) {
        TextField usernameField = new TextField("Username");
        PasswordField passwordField = new PasswordField("Password");
        PasswordField confirmPasswordField = new PasswordField("Confirm Password");
        Button registerButton = new Button("Register");

        registerButton.addClickListener(e -> {
            String username = usernameField.getValue();
            String password = passwordField.getValue();
            String confirmPassword = confirmPasswordField.getValue();

            if (!password.equals(confirmPassword)) {
                Notification.show("Passwords do not match", 3000, Notification.Position.MIDDLE);
                return;
            }

            try {
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user.setRoles(Set.of("ROLE_USER"));
                userService.createUser(user);
                Notification.show("Registration successful", 3000, Notification.Position.MIDDLE);
                getUI().ifPresent(ui -> ui.navigate("login"));
            } catch (Exception ex) {
                Notification.show("Registration failed: " + ex.getMessage(), 3000, Notification.Position.MIDDLE);
            }
        });

        FormLayout formLayout = new FormLayout();
        formLayout.add(usernameField, passwordField, confirmPasswordField, registerButton);

        add(new H1("Register"), formLayout);
    }

}
