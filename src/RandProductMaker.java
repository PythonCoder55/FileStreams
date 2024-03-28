import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.lang.Double.parseDouble;

public class RandProductMaker {

    public static void main(String[] args) {
        ProductGeneratorSection section = new ProductGeneratorSection();
        section.setVisible(true);
    }

    public static class ProductGeneratorSection extends JFrame {
        JPanel mainPanel, titlePanel, inputPanel, buttonPanel;
        JLabel titleLabel, nameLabel, idLabel, costLabel, descriptionLabel, countLabel;
        JTextField nameField, idField, costField, descriptionField, countField;
        JButton addButton, quitButton;

        ActionListener quit = new QuitListener();
        ActionListener add = new AddListener();

        String productName, productID, productDescription, outputString;
        double productCost;
        int recordCount;

        ProductGeneratorSection() {
            setTitle("Random Product Maker");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension screenSize = toolkit.getScreenSize();
            int screenHeight = screenSize.height;
            int screenWidth = screenSize.width;
            setSize(3*(screenWidth / 4), 3*(screenHeight / 4));
            setLocationRelativeTo(null);
            setResizable(false);

            mainPanel = new JPanel();
            titlePanel = new JPanel();
            inputPanel = new JPanel();
            buttonPanel = new JPanel();


            titleLabel = new JLabel("Product Maker");
            titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));

            nameLabel = new JLabel("Product Name:");
            nameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));

            descriptionLabel = new JLabel("Description:");
            descriptionLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));

            idLabel = new JLabel("Product ID:");
            idLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));

            costLabel = new JLabel("Product Cost:");
            costLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));

            countLabel = new JLabel("Records Added:");
            countLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));


            nameField = new JTextField();
            nameField.setFont(new Font("Times New Roman", Font.BOLD, 15));

            descriptionField = new JTextField();
            descriptionField.setFont(new Font("Times New Roman", Font.BOLD, 15));

            idField = new JTextField();
            idField.setFont(new Font("Times New Roman", Font.BOLD, 15));

            costField = new JTextField();
            costField.setFont(new Font("Times New Roman", Font.BOLD, 15));

            countField = new JTextField();
            countField.setFont(new Font("Times New Roman", Font.BOLD, 15));


            addButton = new JButton("Add");
            addButton.setFont(new Font("Times New Roman", Font.PLAIN, 25));
            addButton.addActionListener(add);

            quitButton = new JButton("Quit");
            quitButton.setFont(new Font("Times New Roman", Font.PLAIN, 25));
            quitButton.addActionListener(quit);

            add(mainPanel);
            mainPanel.setLayout(new GridLayout(3,1));

            mainPanel.add(titlePanel);
            titlePanel.add(titleLabel);

            mainPanel.add(inputPanel);
            inputPanel.setLayout(new GridLayout(5,2));
            inputPanel.add(nameLabel);
            nameLabel.setHorizontalAlignment(JLabel.CENTER);
            inputPanel.add(nameField);
            inputPanel.add(descriptionLabel);
            descriptionLabel.setHorizontalAlignment(JLabel.CENTER);
            inputPanel.add(descriptionField);
            inputPanel.add(idLabel);
            idLabel.setHorizontalAlignment(JLabel.CENTER);
            inputPanel.add(idField);
            inputPanel.add(costLabel);
            costLabel.setHorizontalAlignment(JLabel.CENTER);
            inputPanel.add(costField);
            inputPanel.add(countLabel);
            countLabel.setHorizontalAlignment(JLabel.CENTER);
            inputPanel.add(countField);
            countField.setEditable(false);
            countField.setFont(new Font("Times New Roman", Font.BOLD, 15));
            countField.setText(String.valueOf(recordCount));

            mainPanel.add(buttonPanel);
            buttonPanel.add(addButton);
            buttonPanel.add(quitButton);
        }

        private class AddListener implements ActionListener {

            public void actionPerformed(ActionEvent AE) {
                if (!(nameField.getText().equals("")) &&
                        !(descriptionField.getText().equals("")) &&
                        !(idField.getText().equals("")) &&
                        !(costField.getText().equals(""))) {
                    if ((nameField.getText().length() <= 35) &&
                            (descriptionField.getText().length() <= 75) &&
                            (idField.getText().length() <= 6)) {
                        productName = nameField.getText();
                        productDescription = descriptionField.getText();
                        productID = idField.getText();
                        productCost = parseDouble(costField.getText());

                        outputString = String.format("\n%-6s %-35s %-75s  %.2f", productID, productName, productDescription, productCost);

                        File workingDirectory = new File(System.getProperty("user.dir"));
                        Path file = Paths.get(workingDirectory.getPath() + "\\ProductRecords.txt");
                        try {
                            RandomAccessFile outFile = new RandomAccessFile(file.toString(), "rw");
                            outFile.seek(outFile.length());
                            outFile.write(outputString.getBytes());
                            outFile.close();
                            JOptionPane.showMessageDialog(null, "Product Written To File!");
                        }
                        catch (FileNotFoundException e) {
                            System.out.println("File Not Found!");
                            e.printStackTrace();
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        nameField.setText("");
                        descriptionField.setText("");
                        idField.setText("");
                        costField.setText("");
                        recordCount++;
                        countField.setText(String.valueOf(recordCount));
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Check That The Information Entered Fits Within The Fields: \nName: 35 Characters\nDescription: 75 Characters\nID: 6 Characters");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Please Fill In All Fields!");
                }
            }
        }

        public static class QuitListener implements ActionListener {
            public void actionPerformed(ActionEvent AE) {
                System.exit(0);
            }
        }
    }
}
