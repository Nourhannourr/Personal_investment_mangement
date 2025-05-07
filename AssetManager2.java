import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.awt.*;
import java.io.*;
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

class User {
    String username;
    List<Asset> assets;
    private final String filePath = "assets.txt";

    public User(String username) {
        this.username = username;
        assets = loadAssetsFromFile();
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

    private List<Asset> loadAssetsFromFile() {
        List<Asset> loadedAssets = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) return loadedAssets;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Asset asset = Asset.fromFileString(line);
                if (asset != null) {
                    loadedAssets.add(asset);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadedAssets;
    }

    public void generateAndExportReport(String format) {
        if (format.equalsIgnoreCase("PDF")) {
            generatePDFReport();
        } else if (format.equalsIgnoreCase("Excel")) {
            generateExcelReport();
        }
    }

    private void generatePDFReport() {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("assets_report.pdf"));
            document.open();

            Paragraph title = new Paragraph("Assets Report");
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(Chunk.NEWLINE);
            for (Asset asset : assets) {
                document.add(new Paragraph(asset.toString()));
            }

            document.close();
            JOptionPane.showMessageDialog(null, "PDF Report generated successfully.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error generating PDF report.");
            e.printStackTrace();
        }
    }

    private void generateExcelReport() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Assets Report");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Asset Name");
        headerRow.createCell(1).setCellValue("Asset Value");

        int rowNum = 1;
        for (Asset asset : assets) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(asset.name);
            row.createCell(1).setCellValue(asset.value);
        }

        try (FileOutputStream fileOut = new FileOutputStream("assets_report.xlsx")) {
            workbook.write(fileOut);
            workbook.close();
            JOptionPane.showMessageDialog(null, "Excel Report generated successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error generating Excel report.");
            e.printStackTrace();
        }
    }
}

public class AssetManagerGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            User user = new User("mohamed");

            JFrame frame = new JFrame("💼 Asset Manager");
            frame.setSize(600, 500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
            mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
            mainPanel.setBackground(new Color(245, 245, 250));

            JLabel titleLabel = new JLabel("Asset Manager", SwingConstants.CENTER);
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
            titleLabel.setForeground(new Color(60, 63, 65));
            mainPanel.add(titleLabel, BorderLayout.NORTH);

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

            JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 10, 0));
            JButton addButton = new JButton("Add");
            JButton editButton = new JButton("Edit");
            JButton removeButton = new JButton("Remove");
            JButton reportButton = new JButton("Get Report");
            JButton exportButton = new JButton("Export Report");

            Font btnFont = new Font("Segoe UI", Font.BOLD, 14);
            addButton.setFont(btnFont);
            editButton.setFont(btnFont);
            removeButton.setFont(btnFont);
            reportButton.setFont(btnFont);
            exportButton.setFont(btnFont);

            addButton.setBackground(new Color(46, 204, 113));
            removeButton.setBackground(new Color(231, 76, 60));
            editButton.setBackground(new Color(52, 152, 219));
            reportButton.setBackground(new Color(255, 165, 0));
            exportButton.setBackground(new Color(142, 68, 173));

            addButton.setForeground(Color.WHITE);
            removeButton.setForeground(Color.WHITE);
            editButton.setForeground(Color.WHITE);
            reportButton.setForeground(Color.WHITE);
            exportButton.setForeground(Color.WHITE);

            buttonPanel.add(addButton);
            buttonPanel.add(editButton);
            buttonPanel.add(removeButton);
            buttonPanel.add(reportButton);
            buttonPanel.add(exportButton);

            mainPanel.add(buttonPanel, BorderLayout.SOUTH);

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

            reportButton.addActionListener(e -> {
                // Generate report
                JOptionPane.showMessageDialog(frame, "Generating Report...");
            });

            exportButton.addActionListener(e -> {
                // Export report (PDF/Excel)
                String format = JOptionPane.showInputDialog(frame, "Enter format (PDF/Excel):");
                user.generateAndExportReport(format);
            });

            frame.setContentPane(mainPanel);
            frame.setVisible(true);
        });
    }
}
