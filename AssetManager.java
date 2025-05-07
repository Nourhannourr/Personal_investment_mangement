import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

class Asset {
    String name;
    double value;

    public Asset(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public String toString() {
        return name + " - $" + value;
    }

    public String toFileString() {
        return name + "," + value;
    }

    public static Asset fromFileString(String line) {
        String[] parts = line.split(",");
        if (parts.length == 2) {
            try {
                String name = parts[0];
                double value = Double.parseDouble(parts[1]);
                return new Asset(name, value);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }
}

class User1 {
    String username;
    List<Asset> assets = new ArrayList<>();
    private final String filePath = "assets.txt";

    public User1(String username) {
        this.username = username;
        loadAssetsFromFile();
    }

    public void addAsset(Asset asset) {
        assets.add(asset);
        saveAllAssetsToFile();
    }

    public void removeAsset(int index) {
        if (index >= 0 && index < assets.size()) {
            assets.remove(index);
            saveAllAssetsToFile();
        }
    }

    public void editAsset(int index, Asset newAsset) {
        if (index >= 0 && index < assets.size()) {
            assets.set(index, newAsset);
            saveAllAssetsToFile();
        }
    }

    public List<Asset> getAssets() {
        return assets;
    }

    private void saveAllAssetsToFile() {
        try (FileWriter fw = new FileWriter(filePath)) {
            for (Asset asset : assets) {
                fw.write(asset.toFileString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAssetsFromFile() {
        File file = new File(filePath);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Asset asset = Asset.fromFileString(line);
                if (asset != null) {
                    assets.add(asset);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class AssetManagerGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            User1 user = new User1("mohamed");

            JFrame frame = new JFrame("💼 Asset Manager");
            frame.setSize(600, 500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null); // center window

            // ---------- Main Panel ----------
            JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
            mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
            mainPanel.setBackground(new Color(245, 245, 250));

            // ---------- Title ----------
            JLabel titleLabel = new JLabel("Asset Manager", SwingConstants.CENTER);
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
            titleLabel.setForeground(new Color(60, 63, 65));
            mainPanel.add(titleLabel, BorderLayout.NORTH);

            // ---------- Input Panel ----------
            JPanel inputPanel = new JPanel(new GridBagLayout());
            inputPanel.setBackground(new Color(250, 250, 255));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(8, 8, 8, 8);
            gbc.anchor = GridBagConstraints.WEST;

            JLabel nameLabel = new JLabel("Asset Name:");
            nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            JTextField nameField = new JTextField(15);

            JLabel valueLabel = new JLabel("Asset Value:");
            valueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            JTextField valueField = new JTextField(10);

            gbc.gridx = 0; gbc.gridy = 0;
            inputPanel.add(nameLabel, gbc);
            gbc.gridx = 1;
            inputPanel.add(nameField, gbc);

            gbc.gridx = 0; gbc.gridy = 1;
            inputPanel.add(valueLabel, gbc);
            gbc.gridx = 1;
            inputPanel.add(valueField, gbc);

            mainPanel.add(inputPanel, BorderLayout.WEST);

            // ---------- List Panel ----------
            DefaultListModel<String> listModel = new DefaultListModel<>();
            JList<String> assetList = new JList<>(listModel);
            assetList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            assetList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane listScrollPane = new JScrollPane(assetList);
            listScrollPane.setPreferredSize(new Dimension(250, 300));
            mainPanel.add(listScrollPane, BorderLayout.CENTER);

            for (Asset asset : user.getAssets()) {
                listModel.addElement(asset.toString());
            }

            assetList.addListSelectionListener(e -> {
                int index = assetList.getSelectedIndex();
                if (index >= 0) {
                    Asset selected = user.getAssets().get(index);
                    nameField.setText(selected.name);
                    valueField.setText(String.valueOf(selected.value));
                }
            });

            // ---------- Button Panel ----------
            JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 0));
            JButton addButton = new JButton("Add");
            JButton editButton = new JButton("Edit");
            JButton removeButton = new JButton("Remove");
            JButton zakatButton = new JButton("Calculate Zakat");

            Font btnFont = new Font("Segoe UI", Font.BOLD, 14);
            addButton.setFont(btnFont);
            editButton.setFont(btnFont);
            removeButton.setFont(btnFont);

            addButton.setBackground(new Color(46, 204, 113));
            removeButton.setBackground(new Color(231, 76, 60));
            editButton.setBackground(new Color(52, 152, 219));

            addButton.setForeground(Color.WHITE);
            removeButton.setForeground(Color.WHITE);
            editButton.setForeground(Color.WHITE);

            buttonPanel.add(addButton);
            buttonPanel.add(editButton);
            buttonPanel.add(removeButton);

            mainPanel.add(buttonPanel, BorderLayout.SOUTH);

            zakatButton.setFont(btnFont);
            zakatButton.setBackground(new Color(172,100, 186));
            zakatButton.setForeground(Color.WHITE);
            JPanel extraButtonsPanel = new JPanel(new GridLayout(1, 3, 10, 0));
            extraButtonsPanel.add(zakatButton);
           
            mainPanel.add(extraButtonsPanel, BorderLayout.NORTH); // Or wherever fits your layout


            // ---------- Action Listeners ----------
            addButton.addActionListener(e -> {
                String name = nameField.getText();
                double value;

                try {
                    value = Double.parseDouble(valueField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Enter a valid number.");
                    return;
                }

                Asset asset = new Asset(name, value);
                user.addAsset(asset);
                listModel.addElement(asset.toString());

                nameField.setText("");
                valueField.setText("");
            });

            removeButton.addActionListener(e -> {
                int selectedIndex = assetList.getSelectedIndex();
                if (selectedIndex != -1) {
                    user.removeAsset(selectedIndex);
                    listModel.remove(selectedIndex);
                    nameField.setText("");
                    valueField.setText("");
                } else {
                    JOptionPane.showMessageDialog(frame, "Select an asset to remove.");
                }
            });

            editButton.addActionListener(e -> {
                int selectedIndex = assetList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String newName = nameField.getText();
                    double newValue;

                    try {
                        newValue = Double.parseDouble(valueField.getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Enter a valid number.");
                        return;
                    }

                    Asset updatedAsset = new Asset(newName, newValue);
                    user.editAsset(selectedIndex, updatedAsset);
                    listModel.set(selectedIndex, updatedAsset.toString());
                } else {
                    JOptionPane.showMessageDialog(frame, "Select an asset to edit.");
                }
            });
            zakatButton.addActionListener(e -> {
                double total = user.getAssets().stream().mapToDouble(a -> a.value).sum();
                double zakat = total * 0.025;
                JOptionPane.showMessageDialog(frame, String.format("Total Value: $%.2f\nZakat (2.5%%): $%.2f", total, zakat));
            });
            
            // ---------- Final ----------
            frame.setContentPane(mainPanel);
            frame.setVisible(true);
        });
    }
}
