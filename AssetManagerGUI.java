import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

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

class User {
    String username;
    List<Asset> assets = new ArrayList<>();
    private final String filePath = "assets.txt";

    public User(String username) {
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
        User user = new User("mohamed");

        JFrame frame = new JFrame("Asset Manager");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel nameLabel = new JLabel("Asset Name:");
        JTextField nameField = new JTextField(15);

        JLabel valueLabel = new JLabel("Asset Value:");
        JTextField valueField = new JTextField(10);

        JButton addButton = new JButton("Add Asset");
        JButton removeButton = new JButton("Remove Selected");
        JButton editButton = new JButton("Edit Selected");

        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> assetList = new JList<>(listModel);
        assetList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // تحميل الأصول من المستخدم
        for (Asset asset : user.getAssets()) {
            listModel.addElement(asset.toString());
        }

        // عند اختيار عنصر من القائمة، يتم تعبئة الحقول تلقائياً
        assetList.addListSelectionListener(e -> {
            int index = assetList.getSelectedIndex();
            if (index >= 0) {
                Asset selected = user.getAssets().get(index);
                nameField.setText(selected.name);
                valueField.setText(String.valueOf(selected.value));
            }
        });

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

        JPanel inputPanel = new JPanel();
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(valueLabel);
        inputPanel.add(valueField);
        inputPanel.add(addButton);
        inputPanel.add(editButton);
        inputPanel.add(removeButton);

        frame.getContentPane().add(inputPanel, BorderLayout.NORTH);
        frame.getContentPane().add(new JScrollPane(assetList), BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
