import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;

public class AccountConnector extends JFrame {

    public AccountConnector() {
        setTitle("Bank & Stock Account Connector");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // ==== Bank Account Tab ====
        JPanel bankPanel = createStyledPanel();
        bankPanel.add(createSectionLabel("Connect Your Bank Account"));
        String[] banks = {"Select Bank", "Chase", "Bank of America", "Wells Fargo"};
        
        JComboBox<String> bankDropdown = new JComboBox<>(banks);
        JTextField bankUsernameField = createTextField();
        JPasswordField bankPasswordField = new JPasswordField();
        JPasswordField bankConfirmPasswordField = new JPasswordField();
        JTextField cardField = createTextField();
        JTextField otpField = createTextField();
        JButton connectBankBtn = createButton("Connect Bank Account");

        bankPanel.add(createLabeledField("Bank:", bankDropdown));
        bankPanel.add(createLabeledField("Username:", bankUsernameField));
        bankPanel.add(createLabeledField("Password:", bankPasswordField));
        bankPanel.add(createLabeledField("Confirm Password:", bankConfirmPasswordField));
        bankPanel.add(createLabeledField("Card Number:", cardField));
        bankPanel.add(createLabeledField("OTP Code:", otpField));
        bankPanel.add(connectBankBtn);

        connectBankBtn.addActionListener(e -> {
            String bank = (String) bankDropdown.getSelectedItem();
            String username = bankUsernameField.getText().trim();
            String password = new String(bankPasswordField.getPassword()).trim();
            String confirmPassword = new String(bankConfirmPasswordField.getPassword()).trim();
            String card = cardField.getText().trim();
            String otp = otpField.getText().trim();

            if (bankDropdown.getSelectedIndex() == 0 || username.isEmpty() || password.isEmpty() || card.isEmpty() || otp.isEmpty()) {
                showError("Please fill in all fields.");
            } else if (!password.equals(confirmPassword)) {
                showError("Passwords do not match.");
            } else {
                saveToFile("bank_accounts.txt", "Bank: " + bank + ", Username: " + username + ", Password: " + password + ", Card: " + card + ", OTP: " + otp);
                showSuccess("Bank account linked and saved successfully!");
            }
        });

        // ==== Stock Account Tab ====
        JPanel stockPanel = createStyledPanel();
        stockPanel.add(createSectionLabel("Connect Your Stock Market Account"));
        String[] platforms = {"Select Platform", "Robinhood", "E*TRADE", "Fidelity"};
        JComboBox<String> platformDropdown = new JComboBox<>(platforms);
        JTextField usernameField = createTextField();
        JPasswordField passwordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();
        JTextField stockCardField = createTextField();
        JButton connectStockBtn = createButton("Connect Stock Account");

        stockPanel.add(createLabeledField("Platform:", platformDropdown));
        stockPanel.add(createLabeledField("Username:", usernameField));
        stockPanel.add(createLabeledField("Password:", passwordField));
        stockPanel.add(createLabeledField("Confirm Password:", confirmPasswordField));
        stockPanel.add(createLabeledField("Card Number:", stockCardField));
        stockPanel.add(connectStockBtn);

        connectStockBtn.addActionListener(e -> {
            String platform = (String) platformDropdown.getSelectedItem();
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String confirmPassword = new String(confirmPasswordField.getPassword()).trim();
            String card = stockCardField.getText().trim();
            
            if (platformDropdown.getSelectedIndex() == 0 || username.isEmpty() || password.isEmpty() || card.isEmpty()) {
                showError("Please fill in all fields.");
            } else if (!password.equals(confirmPassword)) {
                showError("Passwords do not match.");
            } else {
                saveToFile("stock_accounts.txt", "Platform: " + platform + ", Username: " + username + ", Password: " + password + ", Card: " + card);
                showSuccess("Stock account linked and saved successfully!");
            }
        });

        tabbedPane.addTab("Bank Account", bankPanel);
        tabbedPane.addTab("Stock Market Account", stockPanel);

        add(tabbedPane);
    }

    // ========== UI Helpers ==========
    private JPanel createStyledPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        return panel;
    }

    private JLabel createSectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        return label;
    }

    private JPanel createLabeledField(String labelText, JComponent inputField) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(120, 30));
        panel.add(label, BorderLayout.WEST);
        panel.add(inputField, BorderLayout.CENTER);
        return panel;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(200, 30));
        return field;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(200, 40));
        button.setMaximumSize(new Dimension(200, 40));
        button.setBackground(Color.LIGHT_GRAY);
        return button;
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void saveToFile(String filename, String content) {
        try (FileWriter fw = new FileWriter(filename, true)) {
            fw.write(content + System.lineSeparator());
        } catch (IOException e) {
            showError("Error saving to file: " + e.getMessage());
        }
    }

    // ========== Main ==========
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AccountConnector().setVisible(true));
    }
}
