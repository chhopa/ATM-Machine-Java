package ATMMachine;

import javax.swing.*;
import java.awt.*;

public class ATMGUI extends JFrame {

    // ATM Logic Data
    private float balance = 0.0f; // Initial balance
    private final int PIN = 1234;
    private String currentTransactionType = "";

    // GUI Components
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private JPasswordField pinField;
    private JLabel balanceLabel;
    private JTextField amountField;
    private JLabel transactionLabel;

    public ATMGUI() {
        // --- 1. Frame Setup ---
        setTitle("ATM Machine");
        setSize(450, 350); // Set a window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        // --- 2. Main Panel with CardLayout ---
        // CardLayout allows us to switch between different panels (screens)
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // --- 3. Create Panels for each screen ---
        JPanel pinPanel = createPinPanel();
        JPanel menuPanel = createMenuPanel();
        JPanel balancePanel = createBalancePanel();
        JPanel transactionPanel = createTransactionPanel();

        // --- 4. Add panels to the main panel with unique names ---
        mainPanel.add(pinPanel, "PIN");
        mainPanel.add(menuPanel, "MENU");
        mainPanel.add(balancePanel, "BALANCE");
        mainPanel.add(transactionPanel, "TRANSACTION");

        // --- 5. Add the main panel to the frame ---
        add(mainPanel);
        cardLayout.show(mainPanel, "PIN"); // Start with the PIN screen
    }

    /**
     * Creates the initial screen for PIN entry.
     */
    private JPanel createPinPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding

        JLabel pinLabel = new JLabel("Enter your PIN:");
        pinLabel.setFont(new Font("Arial", Font.BOLD, 16));

        pinField = new JPasswordField(10);
        pinField.setFont(new Font("Arial", Font.PLAIN, 16));

        JButton enterButton = new JButton("Enter");
        enterButton.addActionListener(e -> checkPin());

        // Place components in a grid
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(pinLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(pinField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        panel.add(enterButton, gbc);

        return panel;
    }

    /**
     * Creates the main menu screen with four options.
     */
    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("ATM Menu", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        JButton balanceButton = new JButton("1. Check Account Balance");
        JButton withdrawButton = new JButton("2. Withdraw Money");
        JButton depositButton = new JButton("3. Deposit Money");
        JButton exitButton = new JButton("4. Exit");

        // Add actions to buttons
        balanceButton.addActionListener(e -> showBalanceScreen());
        withdrawButton.addActionListener(e -> showTransactionScreen("withdraw"));
        depositButton.addActionListener(e -> showTransactionScreen("deposit"));
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(balanceButton);
        buttonPanel.add(withdrawButton);
        buttonPanel.add(depositButton);
        buttonPanel.add(exitButton);

        panel.add(buttonPanel, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Creates the screen to display the current account balance.
     */
    private JPanel createBalancePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        balanceLabel = new JLabel("Your current balance is: $0.00");
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(balanceLabel, gbc);
        gbc.gridy = 1;
        panel.add(backButton, gbc);

        return panel;
    }

    /**
     * Creates a reusable screen for both deposits and withdrawals.
     */
    private JPanel createTransactionPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        transactionLabel = new JLabel("Enter amount to...");
        transactionLabel.setFont(new Font("Arial", Font.BOLD, 16));

        amountField = new JTextField(15);
        amountField.setFont(new Font("Arial", Font.PLAIN, 16));

        JButton submitButton = new JButton("Submit");
        JButton backButton = new JButton("Back to Menu");

        // Add actions to buttons
        submitButton.addActionListener(e -> performTransaction());
        backButton.addActionListener(e -> {
            amountField.setText("");
            cardLayout.show(mainPanel, "MENU");
        });

        // Layout components
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(transactionLabel, gbc);
        gbc.gridy = 1; gbc.gridwidth = 1;
        panel.add(new JLabel("Amount: $"), gbc);
        gbc.gridx = 1;
        panel.add(amountField, gbc);
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(submitButton, gbc);
        gbc.gridx = 1;
        panel.add(backButton, gbc);

        return panel;
    }

    // --- LOGIC METHODS ---

    /**
     * Checks if the entered PIN is correct.
     */
    private void checkPin() {
        try {
            int enteredPin = Integer.parseInt(new String(pinField.getPassword()));
            if (enteredPin == PIN) {
                cardLayout.show(mainPanel, "MENU"); // Switch to menu screen on success
            } else {
                JOptionPane.showMessageDialog(this, "Wrong PIN! Please try again.", "PIN Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid PIN format! Please enter numbers only.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
        pinField.setText(""); // Clear the PIN field after each attempt
    }

    /**
     * Updates the balance label and switches to the balance screen.
     */
    private void showBalanceScreen() {
        balanceLabel.setText(String.format("Your current balance is: $%.2f", balance));
        cardLayout.show(mainPanel, "BALANCE");
    }

    /**
     * Sets up the transaction screen for either withdrawal or deposit.
     * @param type The type of transaction ("withdraw" or "deposit").
     */
    private void showTransactionScreen(String type) {
        currentTransactionType = type;
        if (type.equals("withdraw")) {
            transactionLabel.setText("Enter amount to withdraw:");
        } else {
            transactionLabel.setText("Enter amount to deposit:");
        }
        amountField.setText(""); // Clear any previous amount
        cardLayout.show(mainPanel, "TRANSACTION");
    }

    /**
     * Processes the withdrawal or deposit based on the user's input.
     */
    private void performTransaction() {
        String amountText = amountField.getText();
        float amount;

        try {
            amount = Float.parseFloat(amountText);
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter a positive amount.", "Invalid Amount", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount! Please enter numbers only.", "Input Error", JOptionPane.ERROR_MESSAGE);
            amountField.setText("");
            return;
        }

        if (currentTransactionType.equals("withdraw")) {
            if (amount > balance) {
                JOptionPane.showMessageDialog(this, "Insufficient Funds.", "Withdrawal Error", JOptionPane.ERROR_MESSAGE);
            } else {
                balance -= amount;
                JOptionPane.showMessageDialog(this, String.format("Successfully withdrew $%.2f.", amount), "Success", JOptionPane.INFORMATION_MESSAGE);
                cardLayout.show(mainPanel, "MENU");
            }
        } else if (currentTransactionType.equals("deposit")) {
            balance += amount;
            JOptionPane.showMessageDialog(this, String.format("Successfully deposited $%.2f.", amount), "Success", JOptionPane.INFORMATION_MESSAGE);
            cardLayout.show(mainPanel, "MENU");
        }
    }

    /**
     * The main method to start the application.
     */
    public static void main(String[] args) {
        // Run the GUI on the Event Dispatch Thread (EDT) for thread safety
        SwingUtilities.invokeLater(() -> {
            ATMGUI atmGui = new ATMGUI();
            atmGui.setVisible(true);
        });
    }
}