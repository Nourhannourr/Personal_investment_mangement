
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class User {
    String username;
    String ID;
    String email;
    String password;

    public User(String username, String password, String email,String ID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.ID=ID;
    }
}

public class SignUpLogin {
    private JFrame frame;
    private List<User> users = new ArrayList<>();

    public SignUpLogin() {
        loadUsers();
        showLoginPage();
    }





    private void showLoginPage() {
        frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titlePanel.add(titleLabel);
        frame.add(titlePanel, BorderLayout.NORTH);
    
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
    
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        formPanel.add(usernameLabel, gbc);
    
        gbc.gridx = 1;
        gbc.gridy = 0;
        JTextField usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 18));
        usernameField.setPreferredSize(new Dimension(250, 30));
        formPanel.add(usernameField, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 18));
        formPanel.add(passwordLabel, gbc);
    
        gbc.gridx = 1;
        gbc.gridy = 1;
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordField.setPreferredSize(new Dimension(250, 30));
        formPanel.add(passwordField, gbc);
    
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                for (User user : users) {
                    if (user.username.equals(username) && user.password.equals(password)) {
                        JOptionPane.showMessageDialog(null, "Login successful");
                        return;
                    }
                }
                JOptionPane.showMessageDialog(null, "Invalid username or password");
            }
        });
        loginButton.setBackground(Color.decode("#03A9F4"));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.setPreferredSize(new Dimension(120, 40));
    
        buttonPanel.add(loginButton);
    
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);
    
        JPanel signUpPanel = new JPanel();
        signUpPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        JLabel signUpLabel = new JLabel("Don't have an account?");
        signUpLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        signUpPanel.add(signUpLabel);
    
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                showSignUpPage();
            }
        });
        signUpButton.setBackground(Color.decode("#4CAF50"));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setFont(new Font("Arial", Font.BOLD, 14));
        signUpButton.setPreferredSize(new Dimension(90, 30));
        signUpPanel.add(signUpButton);
    
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(signUpPanel, gbc);
    
        frame.add(formPanel, BorderLayout.CENTER);
        frame.setSize(600, 550);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    

    private void showSignUpPage() {
        frame = new JFrame("Sign Up");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        JLabel titleLabel = new JLabel("Sign Up");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titlePanel.add(titleLabel);
        frame.add(titlePanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        

    gbc.gridx = 0;
    gbc.gridy = 0;
    JLabel usernameLabel = new JLabel("Username:");
    usernameLabel.setFont(new Font("Arial", Font.BOLD, 18));
    formPanel.add(usernameLabel, gbc);

    gbc.gridx = 1;
    gbc.gridy = 0;
    JTextField usernameField = new JTextField(20);
    usernameField.setFont(new Font("Arial", Font.PLAIN, 18));
    usernameField.setPreferredSize(new Dimension(250, 30));
    formPanel.add(usernameField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    JLabel IDLabel = new JLabel("ID:");
    IDLabel.setFont(new Font("Arial", Font.BOLD, 18));
    formPanel.add(IDLabel, gbc);

    gbc.gridx = 1;
    gbc.gridy = 1;
    JTextField IDField = new JTextField(20);
    IDField.setFont(new Font("Arial", Font.PLAIN, 18));
    IDField.setPreferredSize(new Dimension(250, 30));
    formPanel.add(IDField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    JLabel emailLabel = new JLabel("Email:");
    emailLabel.setFont(new Font("Arial", Font.BOLD, 18));
    formPanel.add(emailLabel, gbc);

    gbc.gridx = 1;
    gbc.gridy = 2;
    JTextField emailField = new JTextField(20);
    emailField.setFont(new Font("Arial", Font.PLAIN, 18));
    emailField.setPreferredSize(new Dimension(250, 30));
    formPanel.add(emailField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 3;
    JLabel passwordLabel = new JLabel("Password:");
    passwordLabel.setFont(new Font("Arial", Font.BOLD, 18));
    formPanel.add(passwordLabel, gbc);

    gbc.gridx = 1;
    gbc.gridy = 3;
    JPasswordField passwordField = new JPasswordField(20);
    passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
    passwordField.setPreferredSize(new Dimension(250, 30));
    formPanel.add(passwordField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 4;
    JLabel ConfirmpasswordLabel = new JLabel("Confirm Password:");
    ConfirmpasswordLabel.setFont(new Font("Arial", Font.BOLD, 18));
    formPanel.add(ConfirmpasswordLabel, gbc);

    gbc.gridx = 1;
    gbc.gridy = 4;
    JPasswordField ConfirmPasswordField = new JPasswordField(20);
    ConfirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 18));
    ConfirmPasswordField.setPreferredSize(new Dimension(250, 30));
    formPanel.add(ConfirmPasswordField, gbc);

    

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    JButton signUpButton = new JButton("Sign Up");

    signUpButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(ConfirmPasswordField.getPassword());
        String email = emailField.getText();
        String ID = IDField.getText();

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(null, "Passwords do not match");
            return;
        }
        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(null, "Invalid email address");
            return;
        }


        for (User user : users) {
            if (user.username.equals(username)) {
                JOptionPane.showMessageDialog(null, "Username already exists");
                return;
            }
        }

        User user = new User(username, password, email, ID);
        users.add(user);
        saveUsers();
        JOptionPane.showMessageDialog(null, "Sign up successful");
        frame.dispose();
        showLoginPage();
    }

    });
    
    signUpButton.setBackground(Color.decode("#4CAF50"));
    signUpButton.setForeground(Color.WHITE);
    signUpButton.setFont(new Font("Arial", Font.BOLD, 18));
    signUpButton.setPreferredSize(new Dimension(120, 40));

    
    buttonPanel.add(signUpButton);

    gbc.gridx = 0;
    gbc.gridy = 5;
    gbc.gridwidth = 2;
    formPanel.add(buttonPanel, gbc);

    frame.add(formPanel, BorderLayout.CENTER);
    frame.setSize(600, 550);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    }

    private void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    User user = new User(parts[0], parts[3], parts[2], parts[1]);
                    users.add(user);
                }
            }
        } catch (IOException e) {
            // Handle error
        }
    }    
    
    private void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt"))) {
            for (User user : users) {
                writer.write(user.username + "," + user.ID  + "," + user.email + "," + user.password );
                writer.newLine();
            }
        } catch (IOException e) {
            // Handle error
        }
    }

    public static void main(String[] args) {
        new SignUpLogin();
    }
    
}

    


