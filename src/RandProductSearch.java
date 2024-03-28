import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RandProductSearch {

    public static void main(String[] args) {
        ProductSearchSection section = new ProductSearchSection();
        section.setVisible(true);
    }

    public static class ProductSearchSection extends JFrame {
        JPanel mainPanel, titlePanel, inputPanel, displayPanel, buttonPanel;
        JLabel titleLabel, inputLabel;
        JTextField inputTextField;
        JTextArea displayTextArea;
        JScrollPane displayScroll;
        JButton searchButton, quitButton;
        ActionListener quit = new QuitListener();
        ActionListener search = new SearchListener();
        String userInput, displayOutput;

        ProductSearchSection() {
            setTitle("Product Search");
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension screenSize = toolkit.getScreenSize();
            int screenHeight = screenSize.height;
            int screenWidth = screenSize.width;
            setSize(3*(screenWidth / 4), 3*(screenHeight / 4));
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            mainPanel = new JPanel();
            titlePanel = new JPanel();
            inputPanel = new JPanel();
            displayPanel = new JPanel();
            buttonPanel = new JPanel();
            titleLabel = new JLabel("Random Product Search");
            titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
            inputLabel = new JLabel("Product Search :");
            inputLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
            inputTextField = new JTextField();
            displayTextArea = new JTextArea(6, 75);
            displayScroll = new JScrollPane(displayTextArea);

            searchButton = new JButton("Search");
            searchButton.setFont(new Font("Times New Roman", Font.PLAIN, 25));
            searchButton.addActionListener(search);
            quitButton = new JButton("Quit");
            quitButton.setFont(new Font("Times New Roman", Font.PLAIN, 25));
            quitButton.addActionListener(quit);

            add(mainPanel);
            mainPanel.setLayout(new GridLayout(4, 1, 50, 50));

            mainPanel.add(titlePanel);
            titlePanel.add(titleLabel);
            titleLabel.setHorizontalAlignment(JLabel.CENTER);

            mainPanel.add(inputPanel);
            inputPanel.setLayout(new GridLayout(2, 1, 150, 0));
            inputPanel.add(inputLabel);
            inputPanel.add(inputTextField);
            inputTextField.setFont(new Font("Times New Roman", Font.PLAIN, 20));

            mainPanel.add(displayPanel);
            displayPanel.add(displayScroll);
            displayTextArea.setFont(new Font("Times New Roman", Font.BOLD, 15));

            mainPanel.add(buttonPanel);
            buttonPanel.add(searchButton);
            buttonPanel.add(quitButton);
        }

        private class SearchListener implements ActionListener {
            public void actionPerformed(ActionEvent AE) {
                displayTextArea.setText("");
                userInput = inputTextField.getText();
                File workingDirectory = new File(System.getProperty("user.dir"));
                Path file = Paths.get(workingDirectory.getPath() + "\\ProductRecords.txt");
                try {
                    RandomAccessFile inFile = new RandomAccessFile(file.toString(), "r");
                    while ((displayOutput = inFile.readLine()) != null) {
                        if (displayOutput.contains(userInput)) {
                            displayTextArea.append(displayOutput + "\n");
                        }
                    }
                } catch (FileNotFoundException ex) {
                    System.out.println("File Not Found!");
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        private class QuitListener implements ActionListener {
            public void actionPerformed(ActionEvent AE) {
                System.exit(0);
            }
        }
    }
}
