import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Main GUI class for the Gym Management System.
 * Provides a graphical interface for managing gym members including:
 * - Adding regular and premium members
 * - Activating and deactivating memberships
 * - Marking attendance
 * - Upgrading membership plans
 * - Calculating discounts for premium members
 * - Saving and loading member data from files
 * 
 * @author Bhumika Rayamajhi
 * @version 2.0
 */
public class GymGUI extends JFrame {
    // ArrayList to store members
    private ArrayList<GymMember> membersList;

    // Colors for UI styling
    private final Color primaryBlue = new Color(28, 57, 87);
    private final Color secondaryGreen = new Color(42, 84, 72);
    private final Color dangerRed = new Color(150, 40, 40);
    private final Color borderColor = new Color(58, 90, 116);
    private final Color backgroundColor = new Color(240, 242, 245);
    private final Color panelBackground = new Color(250, 251, 252);
    private final Color textColor = new Color(33, 37, 41);
    private final Color fieldBorderColor = new Color(206, 212, 218);
    private final Color fieldBackground = new Color(248, 249, 250);

    // Fonts for UI styling
    private final Font titleFont = new Font("Segoe UI Semibold", Font.PLAIN, 14);
    private final Font mainFont = new Font("Segoe UI", Font.PLAIN, 13);
    private final Font buttonFont = new Font("Segoe UI Semibold", Font.PLAIN, 12);
    private final Font headerFont = new Font("Segoe UI", Font.BOLD, 18);

    // Form Fields
    private JTextField idField, nameField, locationField, phoneField, emailField;
    private JTextField referralField, trainerField, amountToPayField, paidAmountField, removalReasonField;
    private JRadioButton maleButton, femaleButton;
    private ButtonGroup genderGroup;
    private JComboBox<String> planComboBox;

    // Date ComboBoxes
    private JComboBox<String> dobYearComboBox, dobMonthComboBox, dobDayComboBox;
    private JComboBox<String> msYearComboBox, msMonthComboBox, msDayComboBox;

    // Non-editable fields
    private JTextField premiumChargeField, discountField;

    // File paths for saving and loading data
    /**
     * Constant defining the file path for storing member data.
     * Used by both saveToFile() and readFromFile() methods.
     */
    private final String MEMBERS_FILE = "members.txt";

    /**
     * Constant for error dialog title.
     * Used to maintain consistent UI messaging.
     */
    private static final String ERROR_TITLE = "Error";

    /**
     * Constant for success dialog title.
     * Used to maintain consistent UI messaging.
     */
    private static final String SUCCESS_TITLE = "Success";

    /**
     * Constant for information dialog title.
     * Used to maintain consistent UI messaging.
     */
    private static final String INFO_TITLE = "Information";

    /**
     * Constant for member not found message.
     * Used to maintain consistent error messaging.
     */
    private static final String MEMBER_NOT_FOUND = "Member not found!";

    /**
     * Constant for invalid ID message.
     * Used to maintain consistent error messaging.
     */
    private static final String INVALID_ID = "Invalid ID!";

    // Remove the enum and replace with constants
    // private static final double BASIC_PRICE = 6500.0;
    // private static final double STANDARD_PRICE = 12500.0;
    // private static final double DELUXE_PRICE = 18500.0;
    // private static final double PREMIUM_PRICE = 50000.0;

    /**
     * Constructor for GymGUI.
     * Initializes the GUI and sets up the main window with all components.
     * Creates an empty ArrayList to store member objects.
     */
    public GymGUI() {
        super("IIC-Bhumika Fitness Gym Management");
        membersList = new ArrayList<>();
        setupGUI();
    }

    /**
     * Method to set up the GUI
     * Creates and arranges all UI components
     */
    private void setupGUI() {
        setLayout(null); // Using null layout
        getContentPane().setBackground(backgroundColor);

        // Add header panel
        JPanel headerPanel = createHeaderPanel();
        headerPanel.setBounds(0, 0, 1100, 50);
        add(headerPanel);

        // Add main content
        JPanel personalInfoPanel = createPersonalInfoPanel();
        personalInfoPanel.setBounds(20, 60, 520, 250);
        add(personalInfoPanel);

        JPanel additionalInfoPanel = createAdditionalInfoPanel();
        additionalInfoPanel.setBounds(560, 60, 520, 220);
        add(additionalInfoPanel);

        JPanel regularPaymentPanel = createRegularPaymentPanel();
        regularPaymentPanel.setBounds(20, 320, 520, 220);
        add(regularPaymentPanel);

        JPanel premiumPaymentPanel = createPremiumPaymentPanel();
        premiumPaymentPanel.setBounds(560, 320, 520, 220);
        add(premiumPaymentPanel);

        JPanel buttonPanel = createButtonPanel();
        buttonPanel.setBounds(20, 550, 1065, 150);
        add(buttonPanel);

        setSize(1100, 755);
        setMinimumSize(new Dimension(1000, 650));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Method to create the header panel
     * 
     * @return The header panel with title
     */
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(primaryBlue);

        JLabel titleLabel = new JLabel("IIC-Bhumika Fitness Gym Management");
        titleLabel.setFont(headerFont);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(25, 12, 400, 25);
        panel.add(titleLabel);

        return panel;
    }

    /**
     * Method to create the personal information panel
     * Contains fields for basic member information
     * 
     * @return The personal information panel
     */
    private JPanel createPersonalInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(panelBackground);
        panel.setBorder(createStyledBorder("Personal Information"));

        // Create fields
        idField = createStyledField("Enter Member ID");
        nameField = createStyledField("Enter Full Name");
        locationField = createStyledField("Enter Location");
        phoneField = createStyledField("Enter Phone Number");
        emailField = createStyledField("Enter Email");

        // Add labels and fields
        JLabel idLabel = new JLabel("Member ID:");
        idLabel.setFont(mainFont);
        idLabel.setBounds(20, 30, 120, 25);
        panel.add(idLabel);
        idField.setBounds(150, 30, 350, 35);
        panel.add(idField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(mainFont);
        nameLabel.setBounds(20, 70, 120, 25);
        panel.add(nameLabel);
        nameField.setBounds(150, 70, 350, 35);
        panel.add(nameField);

        JLabel locationLabel = new JLabel("Location:");
        locationLabel.setFont(mainFont);
        locationLabel.setBounds(20, 110, 120, 25);
        panel.add(locationLabel);
        locationField.setBounds(150, 110, 350, 35);
        panel.add(locationField);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setFont(mainFont);
        phoneLabel.setBounds(20, 150, 120, 25);
        panel.add(phoneLabel);
        phoneField.setBounds(150, 150, 350, 35);
        panel.add(phoneField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(mainFont);
        emailLabel.setBounds(20, 190, 120, 25);
        panel.add(emailLabel);
        emailField.setBounds(150, 190, 350, 35);
        panel.add(emailField);

        return panel;
    }

    /**
     * Method to create the additional information panel
     * Contains fields for additional member information
     * 
     * @return The additional information panel
     */
    private JPanel createAdditionalInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(panelBackground);
        panel.setBorder(createStyledBorder("Additional Information"));

        createDateComboBoxes();

        // Gender section
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(mainFont);
        genderLabel.setBounds(20, 30, 120, 25);
        panel.add(genderLabel);

        maleButton = new JRadioButton("Male");
        femaleButton = new JRadioButton("Female");
        genderGroup = new ButtonGroup();

        maleButton.setFont(mainFont);
        maleButton.setBackground(panelBackground);
        femaleButton.setFont(mainFont);
        femaleButton.setBackground(panelBackground);

        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);

        maleButton.setBounds(150, 30, 80, 35);
        femaleButton.setBounds(250, 30, 80, 35);

        panel.add(maleButton);
        panel.add(femaleButton);

        // Date of Birth section
        JLabel dobLabel = new JLabel("Date of Birth:");
        dobLabel.setFont(mainFont);
        dobLabel.setBounds(20, 70, 120, 35);
        panel.add(dobLabel);

        dobYearComboBox.setBounds(150, 70, 80, 30);
        panel.add(dobYearComboBox);

        JLabel dobSep1 = new JLabel("-");
        dobSep1.setBounds(235, 70, 10, 30);
        panel.add(dobSep1);

        dobMonthComboBox.setBounds(250, 70, 100, 30);
        panel.add(dobMonthComboBox);

        JLabel dobSep2 = new JLabel("-");
        dobSep2.setBounds(355, 70, 10, 30);
        panel.add(dobSep2);

        dobDayComboBox.setBounds(370, 70, 80, 30);
        panel.add(dobDayComboBox);

        // Membership Start Date section
        JLabel msLabel = new JLabel("Start Date:");
        msLabel.setFont(mainFont);
        msLabel.setBounds(20, 110, 120, 35);
        panel.add(msLabel);

        msYearComboBox.setBounds(150, 110, 80, 30);
        panel.add(msYearComboBox);

        JLabel msSep1 = new JLabel("-");
        msSep1.setBounds(235, 110, 10, 30);
        panel.add(msSep1);

        msMonthComboBox.setBounds(250, 110, 100, 30);
        panel.add(msMonthComboBox);

        JLabel msSep2 = new JLabel("-");
        msSep2.setBounds(355, 110, 10, 30);
        panel.add(msSep2);

        msDayComboBox.setBounds(370, 110, 80, 30);
        panel.add(msDayComboBox);

        return panel;
    }

    /**
     * Method to create date combo boxes
     * Creates combo boxes for year, month, and day selection
     */
    private void createDateComboBoxes() {
        String[] years = new String[50];
        String[] months = {"January", "February", "March", "April", "May", "June", 
                "July", "August", "September", "October", "November", "December"};
        String[] days = new String[31];

        int currentYear = 2025; // Hardcoded for simplicity
        for (int i = 0; i < 50; i++) {
            years[i] = String.valueOf(currentYear - 49 + i);
        }
        for (int i = 0; i < 31; i++) {
            days[i] = String.format("%02d", i + 1);
        }

        dobYearComboBox = new JComboBox<>(years);
        dobMonthComboBox = new JComboBox<>(months);
        dobDayComboBox = new JComboBox<>(days);

        msYearComboBox = new JComboBox<>(years);
        msMonthComboBox = new JComboBox<>(months);
        msDayComboBox = new JComboBox<>(days);

        msYearComboBox.setSelectedItem("2025"); // Default to current year
    }

    /**
     * Method to create the regular payment panel
     * Contains fields for regular member payment
     * 
     * @return The regular payment panel
     */
    private JPanel createRegularPaymentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(panelBackground);
        panel.setBorder(createStyledBorder("Regular Payment"));

        // Plan selection
        planComboBox = new JComboBox<>(new String[]{
                "Basic (Rs. " + RegularMember.BASIC_PRICE + ")", 
                "Standard (Rs. " + RegularMember.STANDARD_PRICE + ")", 
                "Deluxe (Rs. " + RegularMember.DELUXE_PRICE + ")"
            });

        JLabel planLabel = new JLabel("Plan:");
        planLabel.setFont(mainFont);
        planLabel.setBounds(20, 30, 120, 25);
        panel.add(planLabel);
        planComboBox.setBounds(150, 30, 350, 35);
        panel.add(planComboBox);

        // Amount to Pay field
        amountToPayField = createStyledField(String.valueOf(RegularMember.BASIC_PRICE));
        amountToPayField.setForeground(textColor);

        JLabel amountLabel = new JLabel("Amount to Pay:");
        amountLabel.setFont(mainFont);
        amountLabel.setBounds(20, 70, 120, 25);
        panel.add(amountLabel);
        amountToPayField.setBounds(150, 70, 350, 35);
        panel.add(amountToPayField);

        // Referral field
        referralField = createStyledField("Enter Referral Code");

        JLabel referralLabel = new JLabel("Referral Source:");
        referralLabel.setFont(mainFont);
        referralLabel.setBounds(20, 110, 120, 25);
        panel.add(referralLabel);
        referralField.setBounds(150, 110, 350, 35);
        panel.add(referralField);

        // Removal reason field
        removalReasonField = createStyledField("Enter Removal Reason");

        JLabel removalLabel = new JLabel("Removal Reason:");
        removalLabel.setFont(mainFont);
        removalLabel.setBounds(20, 150, 120, 25);
        panel.add(removalLabel);
        removalReasonField.setBounds(150, 150, 350, 35);
        panel.add(removalReasonField);

        // Add action listener to update amount field when plan changes
        planComboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedPlan = planComboBox.getSelectedItem().toString();
                    // Extract just the plan name (remove price part)
                    String planName = selectedPlan.split(" ")[0].toLowerCase();

                    // Get the price based on the plan name
                    double price = RegularMember.BASIC_PRICE; // Default basic plan price

                    if (planName.equals("standard")) {
                        price = RegularMember.STANDARD_PRICE;
                    } else if (planName.equals("deluxe")) {
                        price = RegularMember.DELUXE_PRICE;
                    }

                    amountToPayField.setText(String.valueOf(price));
                    amountToPayField.setForeground(textColor);
                }
            });

        return panel;
    }

    /**
     * Method to create the premium payment panel
     * Contains fields for premium member payment
     * 
     * @return The premium payment panel
     */
    private JPanel createPremiumPaymentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(panelBackground);
        panel.setBorder(createStyledBorder("Premium Payment"));

        // Trainer field
        trainerField = createStyledField("Enter Trainer Name");

        JLabel trainerLabel = new JLabel("Trainer:");
        trainerLabel.setFont(mainFont);
        trainerLabel.setBounds(20, 30, 120, 25);
        panel.add(trainerLabel);
        trainerField.setBounds(150, 30, 350, 35);
        panel.add(trainerField);

        // Premium charge field (non-editable)
        premiumChargeField = new JTextField(String.valueOf(PremiumMember.PREMIUM_CHARGE));
        premiumChargeField.setEditable(false);
        premiumChargeField.setBackground(fieldBackground);
        premiumChargeField.setForeground(textColor);
        premiumChargeField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(fieldBorderColor),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
        premiumChargeField.setFont(mainFont);

        JLabel premiumChargeLabel = new JLabel("Premium Charge:");
        premiumChargeLabel.setFont(mainFont);
        premiumChargeLabel.setBounds(20, 70, 120, 25);
        panel.add(premiumChargeLabel);
        premiumChargeField.setBounds(150, 70, 350, 35);
        panel.add(premiumChargeField);

        // Paid amount field
        paidAmountField = createStyledField("Enter Paid Amount");

        JLabel paidLabel = new JLabel("Paid Amount:");
        paidLabel.setFont(mainFont);
        paidLabel.setBounds(20, 110, 120, 25);
        panel.add(paidLabel);
        paidAmountField.setBounds(150, 110, 350, 35);
        panel.add(paidAmountField);

        // Discount field
        discountField = createReadOnlyField("0.00");

        JLabel discountLabel = new JLabel("Discount:");
        discountLabel.setFont(mainFont);
        discountLabel.setBounds(20, 150, 120, 25);
        panel.add(discountLabel);
        discountField.setBounds(150, 150, 350, 30);
        panel.add(discountField);

        return panel;
    }

    /**
     * Method to create the button panel
     * Contains all action buttons
     * 
     * @return The button panel
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(backgroundColor);
        panel.setBorder(createStyledBorder("Actions"));

        // Create buttons
        JButton addRegularButton = createStyledButton("Add Regular", primaryBlue);
        JButton addPremiumButton = createStyledButton("Add Premium", primaryBlue);
        JButton activateButton = createStyledButton("Activate", primaryBlue);
        JButton deactivateButton = createStyledButton("Deactivate", dangerRed);
        JButton markAttendanceButton = createStyledButton("Mark Attendance", secondaryGreen);
        JButton upgradePlanButton = createStyledButton("Upgrade Plan", secondaryGreen);
        JButton revertRegularButton = createStyledButton("Revert Regular", dangerRed);

        JButton calculateDiscButton = createStyledButton("Calculate Discount", secondaryGreen);
        JButton revertPremiumButton = createStyledButton("Revert Premium", dangerRed);
        JButton payDueButton = createStyledButton("Pay Due", primaryBlue);
        JButton saveToFileButton = createStyledButton("Save to file", primaryBlue);
        JButton readFromFileButton = createStyledButton("Read from file", secondaryGreen);
        JButton displayButton = createStyledButton("Display", secondaryGreen);
        JButton clearButton = createStyledButton("Clear", dangerRed);

        // Position buttons - first row
        int buttonWidth = 140;
        int buttonHeight = 40;
        int hGap = 10;
        int vGap = 10;
        int startX = 15;
        int startY = 35;

        addRegularButton.setBounds(startX, startY, buttonWidth, buttonHeight);
        addPremiumButton.setBounds(startX + buttonWidth + hGap, startY, buttonWidth, buttonHeight);
        activateButton.setBounds(startX + (buttonWidth + hGap) * 2, startY, buttonWidth, buttonHeight);
        deactivateButton.setBounds(startX + (buttonWidth + hGap) * 3, startY, buttonWidth, buttonHeight);
        markAttendanceButton.setBounds(startX + (buttonWidth + hGap) * 4, startY, buttonWidth, buttonHeight);
        upgradePlanButton.setBounds(startX + (buttonWidth + hGap) * 5, startY, buttonWidth, buttonHeight);
        revertRegularButton.setBounds(startX + (buttonWidth + hGap) * 6, startY, buttonWidth, buttonHeight);

        // Position buttons - second row
        int secondRowY = startY + buttonHeight + vGap;

        calculateDiscButton.setBounds(startX, secondRowY, buttonWidth, buttonHeight);
        revertPremiumButton.setBounds(startX + buttonWidth + hGap, secondRowY, buttonWidth, buttonHeight);
        payDueButton.setBounds(startX + (buttonWidth + hGap) * 2, secondRowY, buttonWidth, buttonHeight);
        saveToFileButton.setBounds(startX + (buttonWidth + hGap) * 3, secondRowY, buttonWidth, buttonHeight);
        readFromFileButton.setBounds(startX + (buttonWidth + hGap) * 4, secondRowY, buttonWidth, buttonHeight);
        displayButton.setBounds(startX + (buttonWidth + hGap) * 5, secondRowY, buttonWidth, buttonHeight);
        clearButton.setBounds(startX + (buttonWidth + hGap) * 6, secondRowY, buttonWidth, buttonHeight);

        // Add action listeners using anonymous inner classes instead of lambda expressions
        addRegularButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRegularMember();
            }
        });
        
        addPremiumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPremiumMember();
            }
        });
        
        activateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activateMembership();
            }
        });
        
        deactivateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deactivateMembership();
            }
        });
        
        markAttendanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                markAttendance();
            }
        });
        
        upgradePlanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                upgradePlan();
            }
        });
        
        revertRegularButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                revertRegularMember();
            }
        });
        
        calculateDiscButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateDiscount();
            }
        });
        
        revertPremiumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                revertPremiumMember();
            }
        });
        
        payDueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                payDue();
            }
        });
        
        saveToFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveToFile();
            }
        });
        
        readFromFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readFromFile();
            }
        });
        
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayMembers();
            }
        });
        
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        // Add buttons to panel
        panel.add(addRegularButton);
        panel.add(addPremiumButton);
        panel.add(activateButton);
        panel.add(deactivateButton);
        panel.add(markAttendanceButton);
        panel.add(upgradePlanButton);
        panel.add(revertRegularButton);

        panel.add(calculateDiscButton);
        panel.add(revertPremiumButton);
        panel.add(payDueButton);
        panel.add(saveToFileButton);
        panel.add(readFromFileButton);
        panel.add(displayButton);
        panel.add(clearButton);

        return panel;
    }

    /**
     * Method to validate email format manually without regex.
     * Checks for proper email structure including:
     * - Presence of @ symbol in correct position
     * - Domain with at least one dot
     * - Username part before @
     * - Valid top-level domain after last dot
     * 
     * @param email The email to validate
     * @return True if email is valid, false otherwise
     */
    private boolean isValidEmail(String email) {
        // Basic email validation without regex
        if (email == null) {
            return false;
        }

        // Check for @ symbol
        int atIndex = email.indexOf('@');
        if (atIndex <= 0 || atIndex == email.length() - 1) {
            return false;
        }

        // Check for . in domain part
        int dotIndex = email.lastIndexOf('.');
        if (dotIndex <= atIndex + 1 || dotIndex == email.length() - 1) {
            return false;
        }

        // Check username part (before @)
        String username = email.substring(0, atIndex);
        if (username.isEmpty()) {
            return false;
        }

        // Check domain part (after @)
        String domain = email.substring(atIndex + 1);
        if (domain.isEmpty() || !domain.contains(".")) {
            return false;
        }

        // Check top-level domain (after last .)
        String tld = email.substring(dotIndex + 1);
        if (tld.length() < 2) {
            return false;
        }

        return true;
    }

    /**
     * Method to validate phone number format manually without regex.
     * Checks if the number is 10 digits and starts with 97 or 98.
     * Removes any spaces, hyphens, or dots before validation.
     * 
     * @param phone The phone number to validate
     * @return True if phone number is valid, false otherwise
     */
    private boolean isValidPhone(String phone) {
        if (phone == null) {
            return false;
        }

        // Remove any spaces, hyphens, or dots
        String cleanedPhone = phone.replaceAll("[-. ]", "");

        // Check if the cleaned phone number contains only digits
        for (int i = 0; i < cleanedPhone.length(); i++) {
            if (!Character.isDigit(cleanedPhone.charAt(i))) {
                return false;
            }
        }

        // Check if the length is exactly 10 digits
        if (cleanedPhone.length() != 10) {
            return false;
        }

        // Check if the number starts with 97 or 98
        String prefix = cleanedPhone.substring(0, 2);
        return prefix.equals("97") || prefix.equals("98");
    }

    /**
     * Method to add a regular member.
     * Validates input and creates a new RegularMember object.
     * Performs validation on all fields including email and phone format.
     * Checks for duplicate member IDs before adding.
     * Displays success message upon successful addition.
     * 
     * @throws NumberFormatException If ID is not a valid integer
     */
    private void addRegularMember() {
        try {
            // Get values from fields
            int id = Integer.parseInt(idField.getText().trim());
            String name = nameField.getText().trim();

            // Check for empty fields
            if (name.isEmpty() || locationField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all required fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate email and phone
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();

            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Invalid email format!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isValidPhone(phone)) {
                JOptionPane.showMessageDialog(this, "Invalid phone number format!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (memberExists(id)) {
                JOptionPane.showMessageDialog(this, "Member ID already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String location = locationField.getText().trim();
            String gender = maleButton.isSelected() ? "Male" : "Female";
            String dob = getSelectedDate(dobYearComboBox, dobMonthComboBox, dobDayComboBox);
            String startDate = getSelectedDate(msYearComboBox, msMonthComboBox, msDayComboBox);
            String referral = referralField.getText().trim();
            if (referral.equals("Enter Referral Code")) {
                referral = "None";
            }

            RegularMember member = new RegularMember(id, name, location, phone, email, gender, dob, startDate, referral);
            membersList.add(member);

            JOptionPane.showMessageDialog(this, "Regular member added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID number!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Method to add a premium member.
     * Validates input and creates a new PremiumMember object.
     * Requires additional trainer information.
     * Performs validation on all fields including email and phone format.
     * Checks for duplicate member IDs before adding.
     * Displays success message upon successful addition.
     * 
     * @throws NumberFormatException If ID is not a valid integer
     */
    private void addPremiumMember() {
        try {
            if (!validateRequiredFields() || trainerField.getText().trim().isEmpty() || 
            trainerField.getText().equals("Enter Trainer Name")) {
                JOptionPane.showMessageDialog(this, "Enter all fields including trainer!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate email and phone
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();

            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Invalid email format!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isValidPhone(phone)) {
                JOptionPane.showMessageDialog(this, "Invalid phone number format!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int id = Integer.parseInt(idField.getText().trim());
            if (memberExists(id)) {
                JOptionPane.showMessageDialog(this, "Member ID already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String name = nameField.getText().trim();
            String location = locationField.getText().trim();
            String gender = maleButton.isSelected() ? "Male" : "Female";
            String dob = getSelectedDate(dobYearComboBox, dobMonthComboBox, dobDayComboBox);
            String startDate = getSelectedDate(msYearComboBox, msMonthComboBox, msDayComboBox);
            String trainer = trainerField.getText().trim();

            PremiumMember member = new PremiumMember(id, name, location, phone, email, gender, dob, startDate, trainer);
            membersList.add(member);

            JOptionPane.showMessageDialog(this, "Premium member added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID number!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Method to activate a member's membership.
     * Prompts for member ID and changes their status to active.
     * Validates that the member exists before activation.
     * Displays appropriate messages for success or failure.
     * 
     * @throws NumberFormatException If ID is not a valid integer
     */
    private void activateMembership() {
        String idStr = JOptionPane.showInputDialog(this, "Enter Member ID to activate:");
        if (idStr == null || idStr.trim().isEmpty()) return;

        try {
            int id = Integer.parseInt(idStr.trim());
            GymMember member = findMemberById(id);
            if (member == null) {
                JOptionPane.showMessageDialog(this, "Member not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (member.getActiveStatus()) {
                JOptionPane.showMessageDialog(this, "Already active!", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            member.activateMembership();
            JOptionPane.showMessageDialog(this, "Membership activated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Method to deactivate a member's membership.
     * Prompts for member ID and changes their status to inactive.
     * Validates that the member exists before deactivation.
     * Displays appropriate messages for success or failure.
     * 
     * @throws NumberFormatException If ID is not a valid integer
     */
    private void deactivateMembership() {
        String idStr = JOptionPane.showInputDialog(this, "Enter Member ID to deactivate:");
        if (idStr == null || idStr.trim().isEmpty()) return;

        try {
            int id = Integer.parseInt(idStr.trim());
            GymMember member = findMemberById(id);
            if (member == null) {
                JOptionPane.showMessageDialog(this, "Member not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!member.getActiveStatus()) {
                JOptionPane.showMessageDialog(this, "Already inactive!", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            member.deactivateMembership();
            JOptionPane.showMessageDialog(this, "Membership deactivated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Method to mark attendance
     * Marks attendance for a member with the given ID from dialog
     */
    private void markAttendance() {
        String idStr = JOptionPane.showInputDialog(this, "Enter Member ID to mark attendance:");
        if (idStr == null || idStr.trim().isEmpty()) return;

        try {
            int id = Integer.parseInt(idStr.trim());
            GymMember member = findMemberById(id);
            if (member == null) {
                JOptionPane.showMessageDialog(this, "Member not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!member.getActiveStatus()) {
                JOptionPane.showMessageDialog(this, "Member not active!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            member.markAttendance();

            // Check if regular member is eligible for upgrade after marking attendance
            if (member instanceof RegularMember) {
                RegularMember regMember = (RegularMember) member;
                if (regMember.getAttendance() >= regMember.getAttendanceLimit() && !regMember.getIsEligibleForUpgrade()) {
                    // Use reflection to set the eligibility flag since direct setter isn't working
                    try {
                        java.lang.reflect.Field field = RegularMember.class.getDeclaredField("isEligibleForUpgrade");
                        field.setAccessible(true);
                        field.set(regMember, true);
                    } catch (Exception e) {
                        System.err.println("Failed to set upgrade eligibility: " + e.getMessage());
                    }
                    JOptionPane.showMessageDialog(this, 
                        "Attendance marked successfully!\nMember is now eligible for plan upgrade!", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }

            JOptionPane.showMessageDialog(this, "Attendance marked successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Method to upgrade plan for a regular member
     * Upgrades the plan if the member is eligible
     */
    private void upgradePlan() {
        String idStr = JOptionPane.showInputDialog(this, "Enter Member ID to upgrade plan:");
        if (idStr == null || idStr.trim().isEmpty()) return;

        try {
            int id = Integer.parseInt(idStr.trim());
            GymMember member = findMemberById(id);
            if (member == null) {
                JOptionPane.showMessageDialog(this, "Member not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!(member instanceof RegularMember)) {
                JOptionPane.showMessageDialog(this, "Not a regular member!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            RegularMember regMember = (RegularMember) member;

            if (!regMember.getIsEligibleForUpgrade()) {
                JOptionPane.showMessageDialog(this, 
                    "Not eligible for upgrade. Need at least " + regMember.getAttendanceLimit() + " attendances.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Show plan selection dialog
            String[] plans = {"Basic", "Standard", "Deluxe"};
            String selectedPlan = (String) JOptionPane.showInputDialog(
                    this, 
                    "Select new plan:", 
                    "Upgrade Plan", 
                    JOptionPane.QUESTION_MESSAGE, 
                    null, 
                    plans, 
                    plans[0]
                );

            if (selectedPlan == null) return;

            String result = regMember.upgradePlan(selectedPlan.toLowerCase());
            JOptionPane.showMessageDialog(this, result, "Plan Upgrade", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Method to calculate discount for premium member
     * Calculates discount if full payment has been made
     */
    private void calculateDiscount() {
        String idStr = JOptionPane.showInputDialog(this, "Enter Member ID to calculate discount:");
        if (idStr == null || idStr.trim().isEmpty()) return;

        try {
            int id = Integer.parseInt(idStr.trim());
            GymMember member = findMemberById(id);
            if (member == null) {
                JOptionPane.showMessageDialog(this, "Member not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!(member instanceof PremiumMember)) {
                JOptionPane.showMessageDialog(this, "Not a premium member!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            PremiumMember premMember = (PremiumMember) member;

            if (!premMember.isFullPayment()) {
                JOptionPane.showMessageDialog(this, 
                    "Cannot calculate discount. Full payment not made.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            premMember.calculateDiscount();
            JOptionPane.showMessageDialog(this, 
                "Discount calculated: Rs. " + premMember.getDiscountAmount(), 
                "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Method to pay due amount for premium member
     * Processes payment for a premium member
     */
    private void payDue() {
        String idStr = JOptionPane.showInputDialog(this, "Enter Member ID to pay due amount:");
        if (idStr == null || idStr.trim().isEmpty()) return;

        try {
            int id = Integer.parseInt(idStr.trim());
            GymMember member = findMemberById(id);
            if (member == null) {
                JOptionPane.showMessageDialog(this, "Member not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!(member instanceof PremiumMember)) {
                JOptionPane.showMessageDialog(this, "Not a premium member!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            PremiumMember premMember = (PremiumMember) member;

            if (premMember.isFullPayment()) {
                JOptionPane.showMessageDialog(this, "Payment is already complete!", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String amountStr = JOptionPane.showInputDialog(this, 
                    "Enter amount to pay (Remaining: Rs. " + 
                    (premMember.getPremiumCharge() - premMember.getPaidAmount()) + "):");

            if (amountStr == null || amountStr.trim().isEmpty()) return;

            try {
                double amount = Double.parseDouble(amountStr.trim());
                String result = premMember.payDueAmount(amount);
                JOptionPane.showMessageDialog(this, result, "Payment", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid amount!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Method to revert regular member
     * Reverts a regular member with the given ID from dialog
     */
    private void revertRegularMember() {
        String idStr = JOptionPane.showInputDialog(this, "Enter Member ID to revert (Regular):");
        if (idStr == null || idStr.trim().isEmpty()) return;

        try {
            int id = Integer.parseInt(idStr.trim());
            GymMember member = findMemberById(id);
            if (member == null) {
                JOptionPane.showMessageDialog(this, "Member not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!(member instanceof RegularMember)) {
                JOptionPane.showMessageDialog(this, "Not a regular member!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            RegularMember regMember = (RegularMember) member;
            String reason = removalReasonField.getText().trim().isEmpty() || 
                removalReasonField.getText().equals("Enter Removal Reason") ? 
                    "No reason" : removalReasonField.getText().trim();
            regMember.revertRegularMember(reason);
            JOptionPane.showMessageDialog(this, "Regular member reverted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Method to revert premium member
     * Reverts a premium member with the given ID from dialog
     */
    private void revertPremiumMember() {
        String idStr = JOptionPane.showInputDialog(this, "Enter Member ID to revert (Premium):");
        if (idStr == null || idStr.trim().isEmpty()) return;

        try {
            int id = Integer.parseInt(idStr.trim());
            GymMember member = findMemberById(id);
            if (member == null) {
                JOptionPane.showMessageDialog(this, "Member not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!(member instanceof PremiumMember)) {
                JOptionPane.showMessageDialog(this, "Not a premium member!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            PremiumMember premMember = (PremiumMember) member;
            premMember.revertPremiumMember();
            JOptionPane.showMessageDialog(this, "Premium member reverted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Method to save members to file.
     * Saves all members' details to a text file with formatted columns.
     * Includes member ID, name, location, phone, email, membership details,
     * attendance, loyalty points, active status, and payment information.
     * 
     * @throws IOException If an error occurs during file writing
     */
    private void saveToFile() {
        if (membersList == null || membersList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No members to save!", ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Use try-with-resources for automatic resource management
        try (FileWriter fw = new FileWriter(new File(MEMBERS_FILE));
        BufferedWriter bw = new BufferedWriter(fw)) {

            // Write header
            bw.write(String.format("%-5s %-15s %-15s %-15s %-25s %-20s %-10s %-10s %-10s %-15s %-10s %-15s %-15s %-15s\n", 
                    "ID", "Name", "Location", "Phone", "Email", "Membership Start Date", 
                    "Plan", "Price", "Attendance", "Loyalty Points", "Active Status", 
                    "Full Payment", "Discount Amount", "Net Amount Paid"));

            // Write member details
            for (GymMember member : membersList) {
                if (member == null) continue;

                String plan = "";
                String price = "";
                String fullPayment = "N/A";
                String discountAmount = "N/A";
                String netAmountPaid = "N/A";

                if (member instanceof RegularMember) {
                    RegularMember regMember = (RegularMember) member;
                    plan = regMember.getPlan();
                    price = String.valueOf(regMember.getPrice());
                } else if (member instanceof PremiumMember) {
                    PremiumMember premMember = (PremiumMember) member;
                    plan = "Premium";
                    price = String.valueOf(premMember.getPremiumCharge());
                    fullPayment = premMember.isFullPayment() ? "Yes" : "No";
                    discountAmount = String.valueOf(premMember.getDiscountAmount());
                    netAmountPaid = String.valueOf(premMember.getPaidAmount());
                }

                bw.write(String.format("%-5d %-15s %-15s %-15s %-25s %-20s %-10s %-10s %-10d %-15.2f %-10s %-15s %-15s %-15s\n",
                        member.getId(), member.getName(), member.getLocation(), member.getPhone(),
                        member.getEmail(), member.getMembershipStartDate(), plan, price,
                        member.getAttendance(), member.getLoyaltyPoints(),
                        member.getActiveStatus() ? "Active" : "Inactive", fullPayment,
                        discountAmount, netAmountPaid));
            }

            JOptionPane.showMessageDialog(this, "Member details saved to file successfully!", SUCCESS_TITLE, JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Error saving file: " + e.getMessage(), 
                ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
            // Log the exception
            System.err.println("File save error: " + e);
            e.printStackTrace();
        }
    }

    /**
     * Method to read members from file.
     * Reads all members' details from a text file and reconstructs member objects.
     * Displays the file contents in a scrollable window.
     * Clears the current member list before loading from file.
     * 
     * @throws IOException If an error occurs during file reading
     * @throws NumberFormatException If numeric data in the file is invalid
     */
    
    private void readFromFile() {
        File file = new File(MEMBERS_FILE);
        if (!file.exists()) {
            JOptionPane.showMessageDialog(this, "File does not exist!", ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            // Skip header line
            String line = br.readLine();
            if (line == null) {
                JOptionPane.showMessageDialog(this, "File is empty!", ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Clear current list
            membersList.clear();
            
            // Create StringBuilder for display
            StringBuilder displayText = new StringBuilder();
            displayText.append(line + "\n"); // Add the header line
            
            int membersLoaded = 0;
            String INFO_TITLE = "Information"; // Define this if not already defined
            
            while ((line = br.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) continue;
                
                // Add the line to display
                displayText.append(line + "\n");
                
                try {
                    // Check if line is long enough to parse
                    if (line.length() < 100) {
                        System.out.println("Line too short: " + line);
                        continue;
                    }
                    
                    // Parse the line using substring
                    int id = Integer.parseInt(line.substring(0, 5).trim());
                    String name = line.substring(5, 20).trim();
                    String location = line.substring(20, 35).trim();
                    String phone = line.substring(35, 50).trim();
                    String email = line.substring(50, 75).trim();
                    String startDate = line.substring(75, 95).trim();
                    String plan = line.substring(95, 105).trim();
                    
                    // Default values
                    int attendance = 0;
                    double loyaltyPoints = 0.0;
                    boolean activeStatus = false;
                    
                    // Try to parse additional fields if they exist
                    if (line.length() >= 125) {
                        try {
                            String attendanceStr = line.substring(115, 125).trim();
                            // Remove any non-digit characters before parsing
                            attendanceStr = attendanceStr.replaceAll("[^0-9]", "");
                            if (!attendanceStr.isEmpty()) {
                                attendance = Integer.parseInt(attendanceStr);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Error parsing attendance: " + line.substring(115, 125));
                        }
                    }
                    
                    if (line.length() >= 140) {
                        try {
                            loyaltyPoints = Double.parseDouble(line.substring(125, 140).trim());
                        } catch (NumberFormatException e) {
                            System.out.println("Error parsing loyalty points: " + line.substring(125, 140));
                        }
                    }
                    
                    if (line.length() >= 150) {
                        activeStatus = line.substring(140, 150).trim().equals("Active");
                    }
                    
                    // Create appropriate member object based on plan
                    if (plan.equalsIgnoreCase("Premium")) {
                        // Create a new PremiumMember
                        String trainer = "Default Trainer";
                        PremiumMember member = new PremiumMember(id, name, location, phone, email, 
                                                               "Male", "1990-01-01", startDate, trainer);
                        
                        // Set active status
                        if (activeStatus) member.activateMembership();
                        
                        // Set attendance
                        for (int i = 0; i < attendance; i++) {
                            member.markAttendance();
                        }
                        
                        membersList.add(member);
                        membersLoaded++;
                    } else {
                        // Create a new RegularMember
                        String referral = "Default";
                        
                        RegularMember member = new RegularMember(id, name, location, phone, email, 
                                                               "Male", "1990-01-01", startDate, referral);
                        
                        // Handle plan
                        if (!plan.equalsIgnoreCase("basic")) {
                            // Mark attendance to make eligible for upgrade
                            for (int i = 0; i < 30; i++) {
                                member.markAttendance();
                            }
                            // Upgrade the plan
                            member.upgradePlan(plan);
                        }
                        
                        // Set active status
                        if (activeStatus) member.activateMembership();
                        
                        // Reset and set correct attendance
                        member.resetMember();
                        if (activeStatus) member.activateMembership();
                        for (int i = 0; i < attendance; i++) {
                            member.markAttendance();
                        }
                        
                        membersList.add(member);
                        membersLoaded++;
                    }
                } catch (Exception e) {
                    System.out.println("Error processing line: " + line);
                    System.out.println("Error: " + e.getMessage());
                }
            }
            
            // Only show the display frame if members were loaded
            if (membersLoaded > 0) {
                // Display the file contents
                JTextArea textArea = new JTextArea(displayText.toString());
                textArea.setEditable(false);
                textArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Use monospaced font for alignment
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(800, 400));
                
                JFrame frame = new JFrame("Member Details from File");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.getContentPane().add(scrollPane);
                frame.pack();
                frame.setLocationRelativeTo(this);
                
                JOptionPane.showMessageDialog(this, 
                    membersLoaded + " members imported from file successfully!", 
                    SUCCESS_TITLE, JOptionPane.INFORMATION_MESSAGE);
                
                frame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "No valid members found in file!", 
                    INFO_TITLE, JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading from file: " + e.getMessage(), ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
        }
    }
             

    /**
     * Method to display members
     * Creates a new frame to display all members
     */
    private void displayMembers() {
        if (membersList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No members to display!", ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create a text area to display member information
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        // Create a scroll pane for the text area
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(800, 500));
        
        // Build the display text
        StringBuilder displayText = new StringBuilder();
        displayText.append("MEMBER LIST\n");
        displayText.append("===========================================================\n\n");
        
        for (GymMember member : membersList) {
            displayText.append("Member ID: ").append(member.getId()).append("\n");
            displayText.append("Name: ").append(member.getName()).append("\n");
            displayText.append("Location: ").append(member.getLocation()).append("\n");
            displayText.append("Phone: ").append(member.getPhone()).append("\n");
            displayText.append("Email: ").append(member.getEmail()).append("\n");
            displayText.append("Gender: ").append(member.getGender()).append("\n");
            displayText.append("DOB: ").append(member.getDOB()).append("\n");
            displayText.append("Membership Start: ").append(member.getMembershipStartDate()).append("\n");
            displayText.append("Attendance: ").append(member.getAttendance()).append("\n");
            displayText.append("Loyalty Points: ").append(member.getLoyaltyPoints()).append("\n");
            displayText.append("Status: ").append(member.getActiveStatus() ? "Active" : "Inactive").append("\n");
            
            if (member instanceof RegularMember) {
                RegularMember regMember = (RegularMember) member;
                displayText.append("Type: Regular\n");
                displayText.append("Plan: ").append(regMember.getPlan()).append("\n");
                displayText.append("Price: Rs. ").append(regMember.getPrice()).append("\n");
                displayText.append("Referral: ").append(regMember.getReferralSource()).append("\n");
                displayText.append("Eligible for Upgrade: ").append(regMember.getIsEligibleForUpgrade() ? "Yes" : "No").append("\n");
            } else if (member instanceof PremiumMember) {
                PremiumMember premMember = (PremiumMember) member;
                displayText.append("Type: Premium\n");
                displayText.append("Trainer: ").append(premMember.getPersonalTrainer()).append("\n");
                displayText.append("Premium Charge: Rs. ").append(premMember.getPremiumCharge()).append("\n");
                displayText.append("Discount Amount: Rs. ").append(premMember.getDiscountAmount()).append("\n");
                displayText.append("Net Amount Paid: Rs. ").append(premMember.getPaidAmount()).append("\n");
                displayText.append("Payment Status: ").append(premMember.isFullPayment() ? "Complete" : "Incomplete").append("\n");
            }
            
            displayText.append("\n-----------------------------------\n\n");
        }
        
        textArea.setText(displayText.toString());
        
        // Create a frame to display the members
        JFrame frame = new JFrame("Member Details - Total: " + membersList.size());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(scrollPane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Method to clear fields
     * Clears all form fields
     */
    private void clearFields() {
        idField.setText("Enter Member ID");
        nameField.setText("Enter Full Name");
        locationField.setText("Enter Location");
        phoneField.setText("Enter Phone Number");
        emailField.setText("Enter Email");
        referralField.setText("Enter Referral Code");
        trainerField.setText("Enter Trainer Name");
        amountToPayField.setText("6500.0"); // Default to Basic plan price
        paidAmountField.setText("Enter Paid Amount");
        removalReasonField.setText("Enter Removal Reason");

        for (JTextField field : new JTextField[]{idField, nameField, locationField, phoneField, emailField,
            referralField, trainerField, paidAmountField, removalReasonField}) {
            field.setForeground(Color.GRAY);
        }
        amountToPayField.setForeground(textColor);

        genderGroup.clearSelection();
        planComboBox.setSelectedIndex(0);
        dobYearComboBox.setSelectedIndex(0);
        dobMonthComboBox.setSelectedIndex(0);
        dobDayComboBox.setSelectedIndex(0);
        msYearComboBox.setSelectedItem("2025");
        msMonthComboBox.setSelectedIndex(0);
        msDayComboBox.setSelectedIndex(0);
        discountField.setText("0.00");
    }

    /**
     * Method to validate required fields.
     * Checks if all required fields are filled with valid data.
     * Validates ID, name, location, phone, email, and gender selection.
     * 
     * @return True if all required fields are valid, false otherwise
     */
    private boolean validateRequiredFields() {
        // Check if required fields are empty or contain only placeholder text
        if (idField.getText().trim().isEmpty() || idField.getText().equals("Enter Member ID") ||
        nameField.getText().trim().isEmpty() || nameField.getText().equals("Enter Full Name") ||
        locationField.getText().trim().isEmpty() || locationField.getText().equals("Enter Location") ||
        phoneField.getText().trim().isEmpty() || phoneField.getText().equals("Enter Phone Number") ||
        emailField.getText().trim().isEmpty() || emailField.getText().equals("Enter Email")) {

            JOptionPane.showMessageDialog(this, "Please fill all required fields", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate email format
        if (!isValidEmail(emailField.getText().trim())) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate phone format
        if (!isValidPhone(phoneField.getText().trim())) {
            JOptionPane.showMessageDialog(this, "Please enter a valid phone number", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate ID is a number
        try {
            Integer.parseInt(idField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    /**
     * Method to check if a member exists
     * 
     * @param id The member ID
     * @return True if the member exists, false otherwise
     */
    private boolean memberExists(int id) {
        if (membersList == null) {
            return false;
        }

        for (GymMember member : membersList) {
            if (member != null && member.getId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to find a member by ID
     * 
     * @param id The member ID
     * @return The member, or null if not found
     */
    private GymMember findMemberById(int id) {
        if (membersList == null) {
            return null;
        }

        for (GymMember member : membersList) {
            if (member != null && member.getId() == id) {
                return member;
            }
        }
        return null;
    }

    /**
     * Method to get the selected date from combo boxes
     * 
     * @param yearBox The year combo box
     * @param monthBox The month combo box
     * @param dayBox The day combo box
     * @return The selected date as a string (YYYY-MM-DD)
     */
    private String getSelectedDate(JComboBox<String> yearBox, JComboBox<String> monthBox, JComboBox<String> dayBox) {
        String year = (String) yearBox.getSelectedItem();
        String day = (String) dayBox.getSelectedItem();

        // Check for null selections
        if (year == null || day == null) {
            return "2023-01-01"; // Default date if something is not selected
        }

        // Convert month name to number
        int monthNum = monthBox.getSelectedIndex() + 1;
        String monthStr = String.format("%02d", monthNum);
        String dayStr = String.format("%02d", Integer.parseInt(day));

        return year + "-" + monthStr + "-" + dayStr;
    }

    /**
     * Method to create a styled text field with placeholder text.
     * Sets up focus listeners to handle placeholder text behavior.
     * Applies consistent styling including fonts, colors, and borders.
     * 
     * @param placeholder The placeholder text to display when field is empty
     * @return A styled JTextField with placeholder behavior
     */
    private JTextField createStyledField(String placeholder) {
        JTextField field = new JTextField(placeholder);
        field.setFont(mainFont);
        field.setForeground(Color.GRAY);
        field.setBackground(fieldBackground);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(fieldBorderColor, 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
            ));

        field.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent evt) {
                    if (field.getText().equals(placeholder)) {
                        field.setText("");
                        field.setForeground(textColor);
                    }
                }

                public void focusLost(FocusEvent evt) {
                    if (field.getText().isEmpty()) {
                        field.setText(placeholder);
                        field.setForeground(Color.GRAY);
                    }
                }
            });
        return field;
    }

    /**
     * Method to create a read-only text field.
     * Creates a non-editable field with distinct styling.
     * 
     * @param text The text to display in the field
     * @return A styled, non-editable JTextField
     */
    private JTextField createReadOnlyField(String text) {
        JTextField field = new JTextField(text);
        field.setEditable(false);
        field.setFont(mainFont);
        field.setBackground(fieldBackground);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(fieldBorderColor, 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
            ));
        return field;
    }

    /**
     * Method to create a styled border with title.
     * Creates a consistent border style for all panels.
     * 
     * @param title The title to display in the border
     * @return A TitledBorder with consistent styling
     */
    private Border createStyledBorder(String title) {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(borderColor, 1),
                title,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                titleFont,
                borderColor
            ),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        );
    }

    /**
     * Method to create a styled button
     * 
     * @param text The button text
     * @param color The button color
     * @return The styled button
     */
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(buttonFont);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);

        button.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) { button.setBackground(color.darker()); }

                public void mouseExited(MouseEvent evt) { button.setBackground(color); }
            });
        return button;
    }

    /**
     * Main method - entry point for the application.
     * Creates an instance of GymGUI and makes it visible.
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
                    new GymGUI().setVisible(true);
    }

    /**
     * Validates a date string in the format YYYY-MM-DD
     * 
     * @param dateStr The date string to validate
     * @return True if the date is valid, false otherwise
     */
    private boolean isValidDate(String dateStr) {
        // Basic format validation
        if (dateStr == null || !dateStr.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return false;
        }

        try {
            // Extract components
            int year = Integer.parseInt(dateStr.substring(0, 4));
            int month = Integer.parseInt(dateStr.substring(5, 7));
            int day = Integer.parseInt(dateStr.substring(8, 10));

            // Basic range check
            if (year < 1900 || year > 2100 || month < 1 || month > 12 || day < 1 || day > 31) {
                return false;
            }

            // Check days in month
            if (month == 2) {
                // February - check for leap year
                boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
                if (day > (isLeapYear ? 29 : 28)) {
                    return false;
                }
            } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                // April, June, September, November have 30 days
                if (day > 30) {
                    return false;
                }
            }

            return true;
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            return false;
        }
    }
}



