package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Budget;
import model.Event;
import model.EventLog;
import model.Expense;
import model.ExpenseList;
import model.Income;
import model.IncomeList;
import model.MoneyItem;
import persistance.JsonReader;
import persistance.JsonWriter;

@ExcludeFromJacocoGeneratedReport
// Graphic User Interface for Budgeting Application
public class BudgetingAppGUI {
    private static Font GENERIC_PAGE_TITLE_FONT = new Font("Elephant", Font.PLAIN, 100);
    private static Font BUDGET_MANAGER_PAGE_TITLE_FONT = new Font("Elephant", Font.PLAIN, 50);
    private static Font PAGE_BUTTON_FONT = new Font("Elephant", Font.PLAIN, 50);
    private static Font DONE_BUTTON_FONT = new Font("Elephant", Font.PLAIN, 20);
    private static Font NAV_BUTTON_FONT = new Font("Elephant", Font.PLAIN, 15);

    private static Color PALE_GREEN = new Color(191, 255, 196);
    private static Color LIGHT_BLUE = new Color(0, 192, 255);
    private static Color BRIGHT_BLUE = new Color(0, 196, 255);
    private static Color DARK_BLUE = new Color(0, 0, 255);
    private static Color BRIGHT_YELLOW = new Color(255, 243, 0);
    private static Color DARK_YELLOW = new Color(173, 168, 0);
    private static Color LIGHT_RED = new Color(255, 100, 80);
    private static Color DARK_RED = new Color(255, 0, 0);
    private static Color BACKGROUND_COLOUR = PALE_GREEN;

    // Income Category Colours (gift & other are also Expense categories)
    private static Color WORK_CATEGORY_COLOUR = new Color(255, 155, 0); 
    private static Color GOVERNMENTBENEFIT_CATEGORY_COLOUR = new Color(191, 0, 0);  
    private static Color GIFT_CATEGORY_COLOUR = new Color(214, 207, 8); 
    private static Color OTHER_CATEGORY_COLOUR = new Color(255, 158, 246);  
    // Expense Category Colours (excluding gift & other already established above)
    private static Color FOOD_CATEGORY_COLOUR = new Color(171, 101, 0);  
    private static Color ENTERTAINMENT_CATEGORY_COLOUR = new Color(78, 0, 130);  
    private static Color PERSONALCARE_CATEGORY_COLOUR = new Color(127, 229, 255);  
    private static Color DONATION_CATEGORY_COLOUR = new Color(4, 143, 0);  
    private static Color TRANSPORTATION_CATEGORY_COLOUR = new Color(11, 151, 255);  
    private static Color INSURANCE_CATEGORY_COLOUR = new Color(255, 0, 0);  
    private static Color MEDICAL_CATEGORY_COLOUR = new Color(146, 255, 153);  
    private static Color TRAVEL_CATEGORY_COLOUR = new Color(255, 75, 0);  
    private static Color UTILITIES_CATEGORY_COLOUR = new Color(0, 255, 180); 
    private static Color HOUSING_CATEGORY_COLOUR = new Color(0, 2, 130); 
    private static Color EDUCATION_CATEGORY_COLOUR = new Color(204, 142, 138); 
    private static Color MEMBERSHIP_CATEGORY_COLOUR = new Color(175, 0, 255); 
    private static Color SAVINGS_CATEGORY_COLOUR = new Color(255, 0, 178);

    private static Border BUTTON_NORMAL_BORDER = BorderFactory.createLineBorder(Color.BLACK, 5);
    private static Border BUTTON_HOVER_BORDER = BorderFactory.createLineBorder(Color.BLACK, 5);

    private static int WINDOW_WIDTH = 1920;
    private static int WINDOW_HEIGHT = 1010;
    private static int NAV_BUTTON_HEIGHT = 35;
    private static int DISTRIBUTION_WINDOW_WIDTH = 700;
    private static int DISTRIBUTION_WINDOW_HEIGHT = 700;
    private static int DISTRIBUTION_CONTAINER_WIDTH = DISTRIBUTION_WINDOW_WIDTH - 22;
    private static int DISTRIBUTION_CONTAINER_HEIGHT = DISTRIBUTION_WINDOW_HEIGHT - 100 - 50;
    private static int DISTRIBUTION_BAR_WIDTH = DISTRIBUTION_CONTAINER_WIDTH - 70;
    private static int DISTRIBUTION_BAR_HEIGHT = 25;
    private static Dimension NAV_BUTTON_DIMENSIONS = new Dimension(WINDOW_WIDTH / 6 - 6, NAV_BUTTON_HEIGHT);
    private static Dimension BUDGET_DIMENSIONS = new Dimension(WINDOW_WIDTH - 100, WINDOW_HEIGHT - 200);
    private static Dimension DISTRIBUTION_CONTAINER_DIMENSIONS = new Dimension(DISTRIBUTION_CONTAINER_WIDTH, 
            DISTRIBUTION_CONTAINER_HEIGHT);
    private static Dimension DISTRIBUTION_BAR_DIMENSIONS = new Dimension(DISTRIBUTION_BAR_WIDTH, 
            DISTRIBUTION_BAR_HEIGHT);
    private static Dimension DIRECTORY_DIMENSIONS = new Dimension(DISTRIBUTION_BAR_WIDTH, 
            DISTRIBUTION_CONTAINER_HEIGHT - DISTRIBUTION_BAR_HEIGHT - 65);

    private Budget budget;
    private JsonReader reader;
    private JsonWriter writer;

    // EFFECTS: runs the budgeting app
    public BudgetingAppGUI() {
        runGui();
    }

    // EFFECTS: processes input from user
    public void runGui() {
        displayWelcomePage();
    }

    // EFFECTS: displays the welcome page for the budgeting app
    public void displayWelcomePage() {
        SpringLayout layout = new SpringLayout();
        JFrame welcomePage = makePage(layout);

        JLabel appLabel = makeAppLabel(welcomePage);
        JPanel title = makeWelcomeTitle(welcomePage);
        JTextArea welcomeMessage = makeWelcomeMessage(welcomePage);
        JButton startButton = makeStartButton(welcomePage);
        welcomePage.getRootPane().setDefaultButton(startButton);

        layout.putConstraint(SpringLayout.NORTH, title, 175, SpringLayout.SOUTH, appLabel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, title, 0, 
                SpringLayout.HORIZONTAL_CENTER, welcomePage.getContentPane());
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, welcomeMessage, 0, 
                SpringLayout.HORIZONTAL_CENTER, welcomePage.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, welcomeMessage, 5, SpringLayout.SOUTH, title);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, startButton, 0, 
                SpringLayout.HORIZONTAL_CENTER, welcomePage.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, startButton, 175, SpringLayout.SOUTH, welcomeMessage);
        welcomePage.setVisible(true);
    }

    // EFFECTS: returns a blank page for the app using the given layout
    public JFrame makePage(SpringLayout layout) {
        JFrame blankPage = new JFrame("Everyday MoneySaver's Budgeting App");
        blankPage.setExtendedState(JFrame.MAXIMIZED_BOTH);
        blankPage.setLayout(layout);
        blankPage.getContentPane().setBackground(BACKGROUND_COLOUR);

        return blankPage;
    }

    // MODIFIES: page
    // EFFECTS: returns the EveryDay Money Saver's app label 
    //          and adds it to the given page
    public JLabel makeAppLabel(JFrame page) {
        ImageIcon appIcon = new ImageIcon("resources/Images/EDM.png");
        JLabel appLabel = new JLabel(appIcon);
        page.add(appLabel);

        return appLabel;
    } 

    // MODIFIES: page
    // EFFECTS: returns the EveryDay Money Saver's welcome title
    //          and adds it to the given page
    public JPanel makeWelcomeTitle(JFrame page) {
        JPanel container = new JPanel();
        container.setBackground(BACKGROUND_COLOUR);
        JLabel title = new JLabel(" Welcome!");
        ImageIcon waveImg = new ImageIcon("resources/Images/wave.png");
        title.setFont(GENERIC_PAGE_TITLE_FONT);
        container.add(title);
        container.add(new JLabel(waveImg));
        page.add(container);

        return container;
    }

    // MODIFIES: page
    // EFFECTS: returns the EveryDay Money Saver's welcome message
    //          and adds it to the given page
    public JTextArea makeWelcomeMessage(JFrame page) {
        JTextArea message = new JTextArea("Welcome to the Everyday Money"  
                + "Saver's Budgeting App! With this app you can create a monthly budget, "
                + "manage your money, and save up for the future!");
        message.setBackground(BACKGROUND_COLOUR);
        message.setBorder(null);
        message.setFont(new Font("Elephant", Font.PLAIN, 20));
        message.setEditable(false);
        page.add(message);

        return message;
    }

    // MODIFIES: page
    // EFFECTS: returns a start button and adds it to the given page
    public JButton makeStartButton(JFrame page) {
        JButton start = new JButton("Start");
        start.setFont(PAGE_BUTTON_FONT);
        start.setBackground(new Color(0, 255, 51));
        start.setPreferredSize(new Dimension(275, 75));
        start.setBorder(BUTTON_NORMAL_BORDER);
        initializeStartHover(start);
        initializeStartClick(start, page);
        page.add(start);

        return start;
    }

    // MODIFIES: button
    // EFFECTS: adds a MouseListener to the given button that makes the border and text
    //          change color to white and the background color to dark green upon hover, 
    //          and the border and text back to black and the background color back to 
    //          bright green when not hovered
    public void initializeStartHover(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void mouseEntered(MouseEvent event) {
                button.setForeground(Color.WHITE);
                button.setBorder(BUTTON_HOVER_BORDER);
                button.setBackground(new Color(0, 133, 6));
            }

            @Override
            public void mouseExited(MouseEvent event) {
                button.setForeground(Color.BLACK);
                button.setBorder(BUTTON_NORMAL_BORDER);
                button.setBackground(new Color(0, 255, 51));
            }
        });
    }

    // MODIFIES: button, page
    // EFFECTS: adds an ActionListener to the given button that disables the visibility of
    //          page and runs displayBudgetGetterPage()
    public void initializeStartClick(JButton button, JFrame page) {
        button.addActionListener(new ActionListener() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void actionPerformed(ActionEvent event) {
                page.setVisible(false);
                displayBudgetGetterPage();
            }
        });
    }

    // EFFECTS: displays the budget getter page for the app
    public void displayBudgetGetterPage() {
        SpringLayout layout = new SpringLayout();
        JFrame budgetGetterPage = makePage(layout);
        JLabel appLabel = makeAppLabel(budgetGetterPage);
        JLabel budgetGetterTitle = makeBudgetGetterTitle(budgetGetterPage);
        JPanel buttons = makeBudgetGetterButtons(budgetGetterPage);
        buttons.setBackground(BACKGROUND_COLOUR);

        layout.putConstraint(SpringLayout.NORTH, budgetGetterTitle, 200, SpringLayout.SOUTH, appLabel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, budgetGetterTitle, 0, 
                SpringLayout.HORIZONTAL_CENTER, budgetGetterPage.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, buttons, 75, SpringLayout.SOUTH, budgetGetterTitle);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, buttons, 0, 
                SpringLayout.HORIZONTAL_CENTER, budgetGetterPage.getContentPane());
        budgetGetterPage.setVisible(true);
    }

    // MODIFIES: page
    // EFFECTS: returns the budget getter page title and adds it to the given page
    public JLabel makeBudgetGetterTitle(JFrame page) {
        JLabel title = new JLabel("You Need a Budget!");
        title.setFont(GENERIC_PAGE_TITLE_FONT);
        page.add(title);

        return title;
    }

    // MODIFIES: page
    // EFFECTS: returns the buttons panel for the budget getter page and adds it to 
    //          the given page
    public JPanel makeBudgetGetterButtons(JFrame page) {
        JPanel buttons = new JPanel();
        makeCreateBudgetButton(buttons, page);
        makeLoadBudgetButton(buttons, page);
        page.add(buttons);

        return buttons;
    }

    // MODIFIES: panel
    // EFFECTS: returns the budget creator button and adds it to the given panel
    public JButton makeCreateBudgetButton(JPanel panel, JFrame page) {
        JButton create = new JButton("Create a Budget");
        create.setFont(PAGE_BUTTON_FONT);
        create.setBackground(new Color(0, 255, 51));
        create.setPreferredSize(new Dimension(500, 75));
        create.setBorder(BUTTON_NORMAL_BORDER);
        initializeCreateHover(create);
        initializeCreateClick(create, page);
        panel.add(create);

        return create;
    }

    // MODIFIES: panel
    // EFFECTS: returns the budget loader button and adds it to the given panel
    public JButton makeLoadBudgetButton(JPanel panel, JFrame page) {
        JButton load = new JButton("Load a Budget");
        load.setFont(PAGE_BUTTON_FONT);
        load.setBackground(new Color(255, 243, 0));
        load.setPreferredSize(new Dimension(500, 75));
        load.setBorder(BUTTON_NORMAL_BORDER);
        initializeLoadHover(load);
        initializeLoadBudgetButtonClick(load, page);
        panel.add(load);

        return load;
    }

    // MODIFIES: button
    // EFFECTS: adds a MouseListener to the given button that makes the border and text
    //          change color to white and the background color to dark yellow upon hover, 
    //          and the border and text back to black and the background color back to 
    //          bright yellow when not hovered
    public void initializeLoadHover(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void mouseEntered(MouseEvent event) {
                button.setForeground(Color.WHITE);
                button.setBorder(BUTTON_HOVER_BORDER);
                button.setBackground(DARK_YELLOW);
            }

            @Override
            public void mouseExited(MouseEvent event) {
                button.setForeground(Color.BLACK);
                button.setBorder(BUTTON_NORMAL_BORDER);
                button.setBackground(BRIGHT_YELLOW);
            }
        });
    }

    // MODIFIES: button
    // EFFECTS: if there is no saved data/files, initiates runNoSavedBudgetsPopUp()
    //          otherwise initiates runSaveBudgetPopUp()  
    public void initializeLoadBudgetButtonClick(AbstractButton button, JFrame page) {
        File directory = new File("./data");
        File[] files = directory.listFiles();

        button.addActionListener(new ActionListener() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void actionPerformed(ActionEvent event) {
                if (files.length == 0) {
                    runNoSavedBudgetsPopUp();
                } else {
                    runLoadBudgetPopUp(page);
                }
            }
        });
    }

    // EFFECTS: displays the no saved budgets pop up window
    public void runNoSavedBudgetsPopUp() {
        JFrame noSavedBudgetPopUp = makePopUp("No Saved Budgets!", 700, 250);
        SpringLayout layout = new SpringLayout();
        noSavedBudgetPopUp.setLayout(layout);
        noSavedBudgetPopUp.setLocationRelativeTo(noSavedBudgetPopUp);
        JTextField message = makeNoSavedBudgetsMessage(noSavedBudgetPopUp);
        JButton okButton = makeOkButton(noSavedBudgetPopUp);
        noSavedBudgetPopUp.getRootPane().setDefaultButton(okButton);

        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, message, 0, 
                SpringLayout.HORIZONTAL_CENTER, noSavedBudgetPopUp.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, message, 50, SpringLayout.NORTH, noSavedBudgetPopUp.getContentPane());
        layout.putConstraint(SpringLayout.EAST, okButton, 0, SpringLayout.EAST, noSavedBudgetPopUp.getContentPane());
        layout.putConstraint(SpringLayout.SOUTH, okButton, 0, SpringLayout.SOUTH, noSavedBudgetPopUp.getContentPane());

        noSavedBudgetPopUp.setVisible(true);
    }

    // MODIFIES: popUp
    // EFFECTS: returns the no saved budgets message and adds it to the given popUp
    public JTextField makeNoSavedBudgetsMessage(JFrame popUp) {
        JTextField message = new JTextField("Sorry, you don't have any saved budgets yet!");
        message.setEditable(false);
        message.setBorder(null);
        message.setFont(NAV_BUTTON_FONT);
        popUp.add(message);

        return message;
    }
    
    // EFFECTS: displays the budget load pop up window
    public void runLoadBudgetPopUp(JFrame page) {
        JFrame loadBudgetPopUp = makePopUp("Load a Budget!", 1000, 500);
        SpringLayout layout = new SpringLayout();
        loadBudgetPopUp.setLayout(layout);
        loadBudgetPopUp.setLocationRelativeTo(loadBudgetPopUp);
        JPanel loadNamePrompt = makeBudgetLoadNamePrompt(loadBudgetPopUp);
        JComboBox loadNameField = (JComboBox) loadNamePrompt.getComponent(1);
        JButton doneButton = makeDoneButton(loadBudgetPopUp);
        loadBudgetPopUp.getRootPane().setDefaultButton(doneButton);
        initializeLoadBudgetDoneClick(loadNameField, doneButton, loadBudgetPopUp, page);

        layout.putConstraint(SpringLayout.SOUTH, doneButton, 0, SpringLayout.SOUTH, loadBudgetPopUp.getContentPane());
        layout.putConstraint(SpringLayout.EAST, doneButton, 0, SpringLayout.EAST, loadBudgetPopUp.getContentPane());

        loadBudgetPopUp.setVisible(true);
    }

    // MODIFIES: button, popUp
    // EFFECTS: loads the state of the saved budget indicated by the given loadNameInput to this GUI's budget, 
    //          disables the visibility of both the given popUp and page
    public void initializeLoadBudgetDoneClick(JComboBox loadNameInput, JButton button, JFrame popUp, JFrame page) {
        button.addActionListener(new ActionListener() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void actionPerformed(ActionEvent event) {
                String fileName = "./data/" + loadNameInput.getSelectedItem().toString();
                reader = new JsonReader(fileName);
                try {
                    budget = reader.read();
                    popUp.setVisible(false);
                    page.setVisible(false);
                    displayBudgetManagerPage();
                } catch (IOException e) {
                    System.out.println("\u001B[1m\u001B[31mError: Unable to read from file\u001B[0m");
                }
            }
        });
    }

    // MODIFIES: popUp
    // EFFECTS: adds a prompt and a corresponding textbox asking the user to
    //          select the desired saved budget's name, returns a panel for this, 
    //          and adds it to the given popUp
    public JPanel makeBudgetLoadNamePrompt(JFrame popUp) {
        JPanel prompt = new JPanel();
        JLabel promptText = new JLabel("Please select a saved budget to load:");
        promptText.setFont(new Font("Elephant", Font.PLAIN, 20));
        prompt.add(promptText);

        File directory = new File("./data");
        String[] fileNames = directory.list();
        JComboBox promptResponse = new JComboBox(fileNames);
        promptResponse.setSelectedIndex(0);
        promptResponse.setFont(new Font("Elephant", Font.PLAIN, 20));
        promptResponse.setPreferredSize(new Dimension(600, 30));
        prompt.add(promptResponse);

        popUp.add(prompt);

        return prompt;
    }

    // MODIFIES: button
    // EFFECTS: adds a MouseListener to the given button that makes the border and text
    //          change color to white and the background color to dark green upon hover, 
    //          and the border and text back to black and the background color back to 
    //          bright green when not hovered
    public void initializeCreateHover(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void mouseEntered(MouseEvent event) {
                button.setForeground(Color.WHITE);
                button.setBorder(BUTTON_HOVER_BORDER);
                button.setBackground(new Color(0, 133, 6));
            }

            @Override
            public void mouseExited(MouseEvent event) {
                button.setForeground(Color.BLACK);
                button.setBorder(BUTTON_NORMAL_BORDER);
                button.setBackground(new Color(0, 255, 51));
            }
        });
    }

    // MODIFIES: button
    // EFFECTS: adds an ActionListener to the given button that initiates 
    //          runCreateBudgetPopUp()
    public void initializeCreateClick(JButton button, JFrame page) {
        button.addActionListener(new ActionListener() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void actionPerformed(ActionEvent event) {
                runCreateBudgetPopUp(page);
            }
        });
    }

    // EFFECTS: returns the pop up window template
    public JFrame makePopUp(String title, int width, int height) {
        JFrame popUp = new JFrame(title);
        popUp.setSize(width, height);

        return popUp;
    }

    // EFFECTS: displays the budget creator pop up window
    public void runCreateBudgetPopUp(JFrame page) {
        JFrame createBudgetPopUp = makePopUp("Create a Budget!", 700, 450);
        SpringLayout layout = new SpringLayout();
        createBudgetPopUp.setLayout(layout);
        createBudgetPopUp.setLocationRelativeTo(createBudgetPopUp);
        JPanel namePrompt = makeBudgetNamePrompt(createBudgetPopUp);
        JPanel monthPrompt = makeBudgetMonthPrompt(createBudgetPopUp);
        JPanel yearPrompt = makeBudgetYearPrompt(createBudgetPopUp);
        JTextField nameField = (JTextField) namePrompt.getComponent(1);
        JTextField monthField = (JTextField) monthPrompt.getComponent(1);
        JTextField yearField = (JTextField) yearPrompt.getComponent(1);
        JButton doneButton = makeDoneButton(createBudgetPopUp);
        createBudgetPopUp.getRootPane().setDefaultButton(doneButton);
        initializeCreateBudgetDoneClick(nameField, monthField, yearField,
                doneButton, createBudgetPopUp, page);

        layout.putConstraint(SpringLayout.NORTH, monthPrompt, 20, SpringLayout.SOUTH, namePrompt);
        layout.putConstraint(SpringLayout.NORTH, yearPrompt, 20, SpringLayout.SOUTH, monthPrompt);
        layout.putConstraint(SpringLayout.SOUTH, doneButton, 0, SpringLayout.SOUTH, createBudgetPopUp.getContentPane());
        layout.putConstraint(SpringLayout.EAST, doneButton, 0, SpringLayout.EAST, createBudgetPopUp.getContentPane());

        createBudgetPopUp.setVisible(true);
    }

    // MODIFIES: popUp
    // EFFECTS: adds a prompt and a corresponding textbox asking the user to
    //          enter their name, returns a panel for this, and adds it to 
    //          the given popUp
    public JPanel makeBudgetNamePrompt(JFrame popUp) {
        JPanel prompt = new JPanel();
        JLabel promptText = new JLabel("Please enter your name:");
        promptText.setFont(new Font("Elephant", Font.PLAIN, 20));
        prompt.add(promptText);

        JTextField promptResponse = new JTextField();
        promptResponse.setEditable(true);
        promptResponse.setFont(new Font("Elephant", Font.PLAIN, 20));
        promptResponse.setPreferredSize(new Dimension(300, 30));
        prompt.add(promptResponse);

        popUp.add(prompt);

        return prompt;
    }

    // MODIFIES: popUp
    // EFFECTS: adds a prompt and a corresponding textbox asking the user to
    //          enter the month for the budget, returns a panel for this, 
    //          and adds it to the given popUp
    public JPanel makeBudgetMonthPrompt(JFrame popUp) {
        JPanel prompt = new JPanel();
        JLabel promptText = new JLabel("Please enter the month:");
        promptText.setFont(new Font("Elephant", Font.PLAIN, 20));
        prompt.add(promptText);

        JTextField promptResponse = new JTextField();
        promptResponse.setEditable(true);
        promptResponse.setFont(new Font("Elephant", Font.PLAIN, 20));
        promptResponse.setPreferredSize(new Dimension(300, 30));
        prompt.add(promptResponse);

        popUp.add(prompt);

        return prompt;
    }

    // MODIFIES: popUp
    // EFFECTS: adds a prompt and a corresponding textbox asking the user to
    //          enter the year for the budget, returns a panel for this, 
    //          and adds it to the given popUp
    public JPanel makeBudgetYearPrompt(JFrame popUp) {
        JPanel prompt = new JPanel();
        JLabel promptText = new JLabel("Please enter the year:");
        promptText.setFont(new Font("Elephant", Font.PLAIN, 20));
        prompt.add(promptText);

        JTextField promptResponse = new JTextField();
        promptResponse.setEditable(true);
        promptResponse.setFont(new Font("Elephant", Font.PLAIN, 20));
        promptResponse.setPreferredSize(new Dimension(300, 30));
        prompt.add(promptResponse);

        popUp.add(prompt);

        return prompt;
    }

    // MODIFIES: popUp
    // EFFECTS: returns a done button with a hover response initialized but NOT a
    //          click response and adds it to the given popUp
    public JButton makeDoneButton(JFrame popUp) {
        JButton button = new JButton("Done");
        button.setPreferredSize(new Dimension(100, 50));
        button.setFont(DONE_BUTTON_FONT);
        button.setBackground(BRIGHT_BLUE);
        initializeBrightBlueButtonHover(button);
        popUp.add(button);

        return button;
    }

    // MODIFIES: button
    // EFFECTS: adds a MouseListener to the given button that makes the border and text
    //          change color to white and the background color to dark blue upon hover, 
    //          and the border and text back to black and the background color back to 
    //          bright blue when not hovered
    public void initializeBrightBlueButtonHover(AbstractButton button) {
        button.addMouseListener(new MouseAdapter() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void mouseEntered(MouseEvent event) {
                button.setForeground(Color.WHITE);
                button.setBackground(DARK_BLUE);
            }

            @Override
            public void mouseExited(MouseEvent event) {
                button.setForeground(Color.BLACK);
                button.setBackground(BRIGHT_BLUE);
            }
        });
    }

    // MODIFIES: button
    // EFFECTS: adds a MouseListener to the given button that makes the border and text
    //          change color to white and the background color to dark blue upon hover, 
    //          and the border and text back to black and the background color back to 
    //          light blue when not hovered
    public void initializeLightBlueButtonHover(AbstractButton button) {
        button.addMouseListener(new MouseAdapter() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void mouseEntered(MouseEvent event) {
                button.setForeground(Color.WHITE);
                button.setBackground(DARK_BLUE);
            }

            @Override
            public void mouseExited(MouseEvent event) {
                button.setForeground(Color.BLACK);
                button.setBackground(LIGHT_BLUE);
            }
        });
    }

    // MODIFIES: button
    // EFFECTS: adds a MouseListener to the given button that makes the border and text
    //          change color to white and the background color to dark yellow upon hover, 
    //          and the border and text back to black and the background color back to 
    //          bright yellow when not hovered
    public void initializeYellowButtonHover(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void mouseEntered(MouseEvent event) {
                button.setForeground(Color.WHITE);
                button.setBackground(DARK_YELLOW);
            }

            @Override
            public void mouseExited(MouseEvent event) {
                button.setForeground(Color.BLACK);
                button.setBackground(BRIGHT_YELLOW);
            }
        });
    }

    // MODIFIES: button
    // EFFECTS: adds a MouseListener to the given button that makes the border and text
    //          change color to white and the background color to dark red upon hover, 
    //          and the border and text back to black and the background color back to 
    //          bright red when not hovered
    public void initializeRedButtonHover(AbstractButton button) {
        button.addMouseListener(new MouseAdapter() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void mouseEntered(MouseEvent event) {
                button.setForeground(Color.WHITE);
                button.setBackground(DARK_RED);
            }

            @Override
            public void mouseExited(MouseEvent event) {
                button.setForeground(Color.BLACK);
                button.setBackground(LIGHT_RED);
            }
        });
    }

    // MODIFIES: this, button, popUp, & page
    // EFFECTS: sets the name, month, & year fields of the budget in this GUI
    //          the given name, month, & year, disables the visibility of popUp & page,
    //          and runs displayBudgetManagerPage()  
    public void initializeCreateBudgetDoneClick(JTextField nameInput, JTextField monthInput, 
            JTextField yearInput, JButton button, JFrame popUp, JFrame page) {
        button.addActionListener(new ActionListener() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void actionPerformed(ActionEvent event) {
                String name = nameInput.getText();
                String month = monthInput.getText();
                int year = Integer.parseInt(yearInput.getText());
                budget = new Budget(name, month, year);
                popUp.setVisible(false);
                page.setVisible(false);
                displayBudgetManagerPage();
            }
        });
    }

    // EFFFECTS: displays the budget manager page for the app
    public void displayBudgetManagerPage() {
        SpringLayout layout = new SpringLayout();
        JFrame budgetManagerPage = makePage(layout);
        JLabel appLabel = makeAppLabel(budgetManagerPage);
        JMenuBar menuBar = makeMenuBar(budgetManagerPage);
        JLabel title = makeBudgetManagerTitle(budgetManagerPage);
        JScrollPane budgetScroller = makeBudgetScroller(budgetManagerPage);

        layout.putConstraint(SpringLayout.NORTH, menuBar, 10, SpringLayout.SOUTH, appLabel);
        layout.putConstraint(SpringLayout.NORTH, title, 10, SpringLayout.SOUTH, menuBar);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, menuBar, 0, 
                SpringLayout.HORIZONTAL_CENTER, budgetManagerPage.getContentPane());
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, title, 0, 
                SpringLayout.HORIZONTAL_CENTER, budgetManagerPage.getContentPane());
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, budgetScroller, 0, 
                SpringLayout.HORIZONTAL_CENTER, budgetManagerPage.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, budgetScroller, 10, SpringLayout.SOUTH, title);

        budgetManagerPage.setVisible(true);
    }

    // MODIFIES: page
    // EFFECTS: adds the menu bar to the given page
    public JMenuBar makeMenuBar(JFrame page) {
        JMenuBar menuBar = new JMenuBar();
        makeFileTab(menuBar, page);
        makeAddTab(menuBar, page);
        makeModifyTab(menuBar, page);
        makeRemoveTab(menuBar, page);
        makeToolsTab(menuBar);
        makeMenuTab(menuBar, page);
        page.add(menuBar);

        return menuBar;
    }

    // MODIFIES: menuBar
    // EFFECTS: adds the file tab to the given menuBar
    public void makeFileTab(JMenuBar menuBar, JFrame page) {
        JMenu fileTab = new JMenu("File");
        fileTab.setOpaque(true);
        fileTab.setBackground(BRIGHT_BLUE);
        fileTab.setPreferredSize(NAV_BUTTON_DIMENSIONS);
        fileTab.setFont(NAV_BUTTON_FONT);
        initializeLightBlueButtonHover(fileTab);
        makeLoadNavButton(fileTab, page);
        makeSaveNavButton(fileTab);
        menuBar.add(fileTab);
    }

    // MODIFIES: tab
    // EFFECTS: adds the load nav button to the given tab
    public void makeLoadNavButton(JMenu tab, JFrame page) {
        JMenuItem loadButton = new JMenuItem("Load");
        loadButton.setPreferredSize(NAV_BUTTON_DIMENSIONS);
        loadButton.setFont(NAV_BUTTON_FONT);
        loadButton.setOpaque(true);
        loadButton.setBackground(LIGHT_BLUE);
        loadButton.setBorder(null);
        initializeLightBlueButtonHover(loadButton);
        initializeLoadBudgetButtonClick(loadButton, page);

        tab.add(loadButton);
    }

    // MODIFIES: tab
    // EFFECTS: adds the save nav button to the given tab
    public void makeSaveNavButton(JMenu tab) {
        JMenuItem saveButton = new JMenuItem("Save");
        saveButton.setPreferredSize(NAV_BUTTON_DIMENSIONS);
        saveButton.setFont(NAV_BUTTON_FONT);
        saveButton.setOpaque(true);
        saveButton.setBackground(LIGHT_BLUE);
        saveButton.setBorder(null);
        initializeLightBlueButtonHover(saveButton);
        initializeSaveBudgetButtonClick(saveButton);

        tab.add(saveButton);
    }

    // MODIFIES: menuBar
    // EFFECTS: adds the add tab to the given menuBar
    public void makeAddTab(JMenuBar menuBar, JFrame page) {
        JMenu addTab = new JMenu("Add");
        addTab.setOpaque(true);
        addTab.setBackground(BRIGHT_BLUE);
        addTab.setPreferredSize(NAV_BUTTON_DIMENSIONS);
        addTab.setFont(NAV_BUTTON_FONT);
        initializeLightBlueButtonHover(addTab);
        makeAddIncomeNavButton(addTab, page);
        makeAddExpenseNavButton(addTab, page);
        menuBar.add(addTab);
    }

    // MODIFIES: tab
    // EFFECTS: adds the add income nav button to the given tab
    public void makeAddIncomeNavButton(JMenu tab, JFrame page) {
        JMenuItem incomeButton = new JMenuItem("Income");
        incomeButton.setPreferredSize(NAV_BUTTON_DIMENSIONS);
        incomeButton.setFont(NAV_BUTTON_FONT);
        incomeButton.setOpaque(true);
        incomeButton.setBackground(LIGHT_BLUE);
        incomeButton.setBorder(null);
        initializeLightBlueButtonHover(incomeButton);
        initializeAddIncomeButtonClick(incomeButton, page);

        tab.add(incomeButton);
    }

    // MODIFIES: tab
    // EFFECTS: adds the add expense nav button to the given tab
    public void makeAddExpenseNavButton(JMenu tab, JFrame page) {
        JMenuItem expenseButton = new JMenuItem("Expense");
        expenseButton.setPreferredSize(NAV_BUTTON_DIMENSIONS);
        expenseButton.setFont(NAV_BUTTON_FONT);
        expenseButton.setOpaque(true);
        expenseButton.setBackground(LIGHT_BLUE);
        expenseButton.setBorder(null);
        initializeLightBlueButtonHover(expenseButton);
        initializeAddExpenseButtonClick(expenseButton, page);

        tab.add(expenseButton);
    }

    // MODIFIES: menuBar
    // EFFECTS: adds the modify tab to the given menuBar
    public void makeModifyTab(JMenuBar menuBar, JFrame page) {
        JMenu modifyTab = new JMenu("Modify");
        modifyTab.setOpaque(true);
        modifyTab.setBackground(BRIGHT_BLUE);
        modifyTab.setPreferredSize(NAV_BUTTON_DIMENSIONS);
        modifyTab.setFont(NAV_BUTTON_FONT);
        initializeLightBlueButtonHover(modifyTab);
        makeModifyBudgetNavButton(modifyTab, page);
        makeModifyIncomeNavButton(modifyTab, page);
        makeModifyExpenseNavButton(modifyTab, page);
        menuBar.add(modifyTab);
    }

    // MODIFIES: tab
    // EFFECTS: adds the modify budget nav button to the given tab
    public void makeModifyBudgetNavButton(JMenu tab, JFrame page) {
        JMenuItem budgetButton = new JMenuItem("Budget");
        budgetButton.setPreferredSize(NAV_BUTTON_DIMENSIONS);
        budgetButton.setFont(NAV_BUTTON_FONT);
        budgetButton.setOpaque(true);
        budgetButton.setBackground(LIGHT_BLUE);
        budgetButton.setBorder(null);
        initializeLightBlueButtonHover(budgetButton);
        initializeModifyBudgetButtonClick(budgetButton, page);

        tab.add(budgetButton);
    }

    // MODIFIES: tab
    // EFFECTS: adds the modify income nav button to the given tab
    public void makeModifyIncomeNavButton(JMenu tab, JFrame page) {
        JMenuItem incomeButton = new JMenuItem("Income");
        incomeButton.setPreferredSize(NAV_BUTTON_DIMENSIONS);
        incomeButton.setFont(NAV_BUTTON_FONT);
        incomeButton.setOpaque(true);
        incomeButton.setBackground(LIGHT_BLUE);
        incomeButton.setBorder(null);
        initializeLightBlueButtonHover(incomeButton);
        initializeModifyIncomeButtonClick(incomeButton, page);

        tab.add(incomeButton);
    }

    // MODIFIES: tab
    // EFFECTS: adds the modify expense nav button to the given tab
    public void makeModifyExpenseNavButton(JMenu tab, JFrame page) {
        JMenuItem expenseButton = new JMenuItem("Expense");
        expenseButton.setPreferredSize(NAV_BUTTON_DIMENSIONS);
        expenseButton.setFont(NAV_BUTTON_FONT);
        expenseButton.setOpaque(true);
        expenseButton.setBackground(LIGHT_BLUE);
        expenseButton.setBorder(null);
        initializeLightBlueButtonHover(expenseButton);
        initializeModifyExpenseButtonClick(expenseButton, page);

        tab.add(expenseButton);
    }

    // MODIFIES: menuBar
    // EFFECTS: adds the remove tab to the given menuBar
    public void makeRemoveTab(JMenuBar menuBar, JFrame page) {
        JMenu removeTab = new JMenu("Remove");
        removeTab.setOpaque(true);
        removeTab.setBackground(BRIGHT_BLUE);
        removeTab.setPreferredSize(NAV_BUTTON_DIMENSIONS);
        removeTab.setFont(NAV_BUTTON_FONT);
        initializeLightBlueButtonHover(removeTab);
        makeRemoveIncomeNavButton(removeTab, page);
        makeRemoveExpenseNavButton(removeTab, page);
        menuBar.add(removeTab);
    }

    // MODIFIES: tab
    // EFFECTS: adds the remove income nav button to the given tab
    public void makeRemoveIncomeNavButton(JMenu tab, JFrame page) {
        JMenuItem incomeButton = new JMenuItem("Income");
        incomeButton.setPreferredSize(NAV_BUTTON_DIMENSIONS);
        incomeButton.setFont(NAV_BUTTON_FONT);
        incomeButton.setOpaque(true);
        incomeButton.setBackground(LIGHT_BLUE);
        incomeButton.setBorder(null);
        initializeLightBlueButtonHover(incomeButton);
        initializeRemoveIncomeButtonClick(incomeButton, page);

        tab.add(incomeButton);
    }

    // MODIFIES: tab
    // EFFECTS: adds the remove expense nav button to the given tab
    public void makeRemoveExpenseNavButton(JMenu tab, JFrame page) {
        JMenuItem expenseButton = new JMenuItem("Expense");
        expenseButton.setPreferredSize(NAV_BUTTON_DIMENSIONS);
        expenseButton.setFont(NAV_BUTTON_FONT);
        expenseButton.setOpaque(true);
        expenseButton.setBackground(LIGHT_BLUE);
        expenseButton.setBorder(null);
        initializeLightBlueButtonHover(expenseButton);
        initializeRemoveExpenseButtonClick(expenseButton, page);

        tab.add(expenseButton);
    }

    // MODIFIES: menuBar
    // EFFECTS: adds the tools tab to the given menuBar
    public void makeToolsTab(JMenuBar menuBar) {
        JMenu toolsTab = new JMenu("Tools");
        toolsTab.setOpaque(true);
        toolsTab.setBackground(BRIGHT_BLUE);
        toolsTab.setPreferredSize(NAV_BUTTON_DIMENSIONS);
        toolsTab.setFont(NAV_BUTTON_FONT);
        initializeLightBlueButtonHover(toolsTab);
        makeProjectionNavButton(toolsTab);
        makeDistributionsNavButton(toolsTab);
        makeIndividualTaxNavButton(toolsTab);
        makeOverallTaxNavButton(toolsTab);
        menuBar.add(toolsTab);
    }

    // MODIFIES: tab
    // EFFECTS: adds the projection nav button to the given tab
    public void makeProjectionNavButton(JMenu tab) {
        JMenuItem projectionButton = new JMenuItem("Projection");
        projectionButton.setPreferredSize(NAV_BUTTON_DIMENSIONS);
        projectionButton.setFont(NAV_BUTTON_FONT);
        projectionButton.setOpaque(true);
        projectionButton.setBackground(LIGHT_BLUE);
        projectionButton.setBorder(null);
        initializeLightBlueButtonHover(projectionButton);
        initializeProjectionButtonClick(projectionButton);

        tab.add(projectionButton);
    }

    // MODIFIES: tab
    // EFFECTS: adds the distributions nav button to the given tab
    public void makeDistributionsNavButton(JMenu tab) {
        JMenuItem distributionsButton = new JMenuItem("Distributions");
        distributionsButton.setPreferredSize(NAV_BUTTON_DIMENSIONS);
        distributionsButton.setFont(NAV_BUTTON_FONT);
        distributionsButton.setOpaque(true);
        distributionsButton.setBackground(LIGHT_BLUE);
        distributionsButton.setBorder(null);
        initializeLightBlueButtonHover(distributionsButton);
        initializeDistributionsButtonClick(distributionsButton);

        tab.add(distributionsButton);
    }

    // MODIFIES: tab
    // EFFECTS: adds the individual tax nav button to the given tab
    public void makeIndividualTaxNavButton(JMenu tab) {
        JMenuItem individualTaxButton = new JMenuItem("Individual Tax");
        individualTaxButton.setPreferredSize(NAV_BUTTON_DIMENSIONS);
        individualTaxButton.setFont(NAV_BUTTON_FONT);
        individualTaxButton.setOpaque(true);
        individualTaxButton.setBackground(LIGHT_BLUE);
        individualTaxButton.setBorder(null);
        initializeLightBlueButtonHover(individualTaxButton);
        initializeIndividualTaxButtonClick(individualTaxButton);

        tab.add(individualTaxButton);
    }

    // MODIFIES: tab
    // EFFECTS: adds the overall tax nav button to the given tab
    public void makeOverallTaxNavButton(JMenu tab) {
        JMenuItem overallTaxButton = new JMenuItem("Overall Tax");
        overallTaxButton.setPreferredSize(NAV_BUTTON_DIMENSIONS);
        overallTaxButton.setFont(NAV_BUTTON_FONT);
        overallTaxButton.setOpaque(true);
        overallTaxButton.setBackground(LIGHT_BLUE);
        overallTaxButton.setBorder(null);
        initializeLightBlueButtonHover(overallTaxButton);
        initializeOverallTaxButtonClick(overallTaxButton);

        tab.add(overallTaxButton);
    }

    // MODIFIES: menuBar
    // EFFECTS: adds the menu tab to the given menuBar
    public void makeMenuTab(JMenuBar menuBar, JFrame page) {
        JMenu menuTab = new JMenu("Menu");
        menuTab.setOpaque(true);
        menuTab.setBackground(LIGHT_RED);
        menuTab.setPreferredSize(NAV_BUTTON_DIMENSIONS);
        menuTab.setFont(NAV_BUTTON_FONT);
        initializeRedButtonHover(menuTab);
        makeMenuNavButton(menuTab, page);
        makeQuitNavButton(menuTab);
        menuBar.add(menuTab);
    }

    // MODIFIES: tab
    // EFFECTS: adds the menu nav button to the given tab
    public void makeMenuNavButton(JMenu tab, JFrame page) {
        JMenuItem menuButton = new JMenuItem("Return to Menu");
        menuButton.setPreferredSize(NAV_BUTTON_DIMENSIONS);
        menuButton.setFont(NAV_BUTTON_FONT);
        menuButton.setOpaque(true);
        menuButton.setBackground(LIGHT_RED);
        menuButton.setBorder(null);
        initializeRedButtonHover(menuButton);
        initializeMenuClick(menuButton, page);

        tab.add(menuButton);
    }

    // MODIFIES: tab
    // EFFECTS: adds the quit nav button to the given tab
    public void makeQuitNavButton(JMenu tab) {
        JMenuItem quitButton = new JMenuItem("Quit");
        quitButton.setPreferredSize(NAV_BUTTON_DIMENSIONS);
        quitButton.setFont(NAV_BUTTON_FONT);
        quitButton.setOpaque(true);
        quitButton.setBackground(LIGHT_RED);
        quitButton.setBorder(null);
        initializeRedButtonHover(quitButton);
        initializeQuitClick(quitButton);

        tab.add(quitButton);
    }

    // MODIFIES: button
    // EFFECTS: adds an ActionListener to the given button that initiates runSaveBudgetPopUp()  
    public void initializeSaveBudgetButtonClick(AbstractButton button) {
        button.addActionListener(new ActionListener() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void actionPerformed(ActionEvent event) {
                runSaveBudgetPopUp();
            }
        });
    }
    
    // EFFECTS: displays the budget save pop up window
    public void runSaveBudgetPopUp() {
        JFrame saveBudgetPopUp = makePopUp("Create a Budget!", 700, 250);
        SpringLayout layout = new SpringLayout();
        saveBudgetPopUp.setLayout(layout);
        saveBudgetPopUp.setLocationRelativeTo(saveBudgetPopUp);
        JPanel saveNamePrompt = makeBudgetSaveNamePrompt(saveBudgetPopUp);
        JTextField saveNameField = (JTextField) saveNamePrompt.getComponent(1);
        JButton doneButton = makeDoneButton(saveBudgetPopUp);
        saveBudgetPopUp.getRootPane().setDefaultButton(doneButton);
        initializeSaveBudgetDoneClick(saveNameField, doneButton, saveBudgetPopUp);

        layout.putConstraint(SpringLayout.SOUTH, doneButton, 0, SpringLayout.SOUTH, saveBudgetPopUp.getContentPane());
        layout.putConstraint(SpringLayout.EAST, doneButton, 0, SpringLayout.EAST, saveBudgetPopUp.getContentPane());

        saveBudgetPopUp.setVisible(true);
    }

    // MODIFIES: button, popUp
    // EFFECTS: adds an ActionListener to the given button that saves the current state 
    //          of this GUI's budget based on the given saveNameField
    public void initializeSaveBudgetDoneClick(JTextField saveNameField, JButton button, JFrame popUp) {
        button.addActionListener(new ActionListener() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void actionPerformed(ActionEvent event) {
                String saveName = saveNameField.getText();
                popUp.setVisible(false);
                writer = new JsonWriter("./data/" + saveName + ".json");
                try {
                    writer.open();
                    writer.write(budget);
                    writer.close();
                } catch (IOException e) {
                    System.out.println("\u001B[1m\u001B[31mError: Unable to write to file\u001B[0m");
                }       
            }
        });
    }

    // MODIFIES: popUp
    // EFFECTS: adds a prompt and a corresponding textbox asking the user to
    //          enter the budget save name, returns a panel for this, 
    //          and adds it to the given popUp
    public JPanel makeBudgetSaveNamePrompt(JFrame popUp) {
        JPanel prompt = new JPanel();
        JLabel promptText = new JLabel("Please enter the save name:");
        promptText.setFont(new Font("Elephant", Font.PLAIN, 20));
        prompt.add(promptText);

        JTextField promptResponse = new JTextField();
        promptResponse.setEditable(true);
        promptResponse.setFont(new Font("Elephant", Font.PLAIN, 20));
        promptResponse.setPreferredSize(new Dimension(300, 30));
        prompt.add(promptResponse);

        popUp.add(prompt);

        return prompt;
    }

    // MODIFIES: button
    // EFFECTS: adds an ActionListener to the given button that initiates runAddIncomePopUp()  
    public void initializeAddIncomeButtonClick(AbstractButton button, JFrame page) {
        button.addActionListener(new ActionListener() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void actionPerformed(ActionEvent event) {
                runAddIncomePopUp(page);
            }
        });
    }

    // MODIFIES: budget
    // EFFECTS: displays the add income pop up
    public void runAddIncomePopUp(JFrame page) {
        JFrame addIncomePopUp = makePopUp("Add Income!", 700, 450);
        SpringLayout layout = new SpringLayout();
        addIncomePopUp.setLayout(layout);
        addIncomePopUp.setLocationRelativeTo(addIncomePopUp);
        JPanel descriptionPrompt = makeDescriptionPrompt(addIncomePopUp);
        JPanel categoryPrompt = makeIncomeCategoryPrompt(addIncomePopUp);
        JPanel frequencyPrompt = makeFrequencyPrompt(addIncomePopUp);
        JPanel valuePrompt = makeValuePrompt(addIncomePopUp);
        JTextField descriptionField = (JTextField) descriptionPrompt.getComponent(1);
        JComboBox categoryField = (JComboBox) categoryPrompt.getComponent(1);
        JComboBox frequencyField = (JComboBox) frequencyPrompt.getComponent(1);
        JTextField valueField = (JTextField) valuePrompt.getComponent(1);
        JButton doneButton = makeDoneButton(addIncomePopUp);
        initializeAddIncomeDoneClick(descriptionField, categoryField, frequencyField,
                   valueField, doneButton, addIncomePopUp, page);
        addIncomePopUp.getRootPane().setDefaultButton(doneButton);

        layout.putConstraint(SpringLayout.NORTH, categoryPrompt, 20, SpringLayout.SOUTH, descriptionPrompt);
        layout.putConstraint(SpringLayout.NORTH, frequencyPrompt, 20, SpringLayout.SOUTH, categoryPrompt);
        layout.putConstraint(SpringLayout.NORTH, valuePrompt, 20, SpringLayout.SOUTH, frequencyPrompt);
        layout.putConstraint(SpringLayout.SOUTH, doneButton, 0, SpringLayout.SOUTH, addIncomePopUp.getContentPane());
        layout.putConstraint(SpringLayout.EAST, doneButton, 0, SpringLayout.EAST, addIncomePopUp.getContentPane());

        addIncomePopUp.setVisible(true);
    }    

    // MODIFIES: popUp
    // EFFECTS: adds a prompt and a corresponding textbox asking the user to
    //          enter the money item's description, returns a panel for this, 
    //          and adds it to the given popUp
    public JPanel makeDescriptionPrompt(JFrame popUp) {
        JPanel prompt = new JPanel();
        JLabel promptText = new JLabel("Please enter a description:");
        promptText.setFont(new Font("Elephant", Font.PLAIN, 20));
        prompt.add(promptText);

        JTextField promptResponse = new JTextField();
        promptResponse.setEditable(true);
        promptResponse.setFont(new Font("Elephant", Font.PLAIN, 20));
        promptResponse.setPreferredSize(new Dimension(300, 30));
        prompt.add(promptResponse);

        popUp.add(prompt);

        return prompt;
    }

    // MODIFIES: popUp
    // EFFECTS: adds a prompt and a corresponding textbox asking the user to
    //          select the income's category, returns a panel for this, 
    //          and adds it to the given popUp
    public JPanel makeIncomeCategoryPrompt(JFrame popUp) {
        JPanel prompt = new JPanel();
        JLabel promptText = new JLabel("Please select a category:");
        promptText.setFont(new Font("Elephant", Font.PLAIN, 20));
        prompt.add(promptText);

        String[] categories = Income.getCategories();
        JComboBox promptResponse = new JComboBox(categories);
        promptResponse.setSelectedIndex(0);
        promptResponse.setFont(new Font("Elephant", Font.PLAIN, 20));
        promptResponse.setPreferredSize(new Dimension(300, 30));
        prompt.add(promptResponse);

        popUp.add(prompt);

        return prompt;
    }

    // MODIFIES: popUp
    // EFFECTS: adds a prompt and a corresponding textbox asking the user to
    //          select the money item's frequency, returns a panel for this, 
    //          and adds it to the given popUp
    public JPanel makeFrequencyPrompt(JFrame popUp) {
        JPanel prompt = new JPanel();
        JLabel promptText = new JLabel("Please select a frequency:");
        promptText.setFont(new Font("Elephant", Font.PLAIN, 20));
        prompt.add(promptText);

        String[] frequencies = MoneyItem.getFrequencies();
        JComboBox promptResponse = new JComboBox(frequencies);
        promptResponse.setSelectedIndex(0);
        promptResponse.setFont(new Font("Elephant", Font.PLAIN, 20));
        promptResponse.setPreferredSize(new Dimension(300, 30));
        prompt.add(promptResponse);

        popUp.add(prompt);

        return prompt;
    }

    // MODIFIES: popUp
    // EFFECTS: adds a prompt and a corresponding textbox asking the user to
    //          enter the money item's value, returns a panel for this, 
    //          and adds it to the given popUp
    public JPanel makeValuePrompt(JFrame popUp) {
        JPanel prompt = new JPanel();
        JLabel promptText = new JLabel("Please enter a value: $");
        promptText.setFont(new Font("Elephant", Font.PLAIN, 20));
        prompt.add(promptText);

        JTextField promptResponse = new JTextField();
        promptResponse.setEditable(true);
        promptResponse.setFont(new Font("Elephant", Font.PLAIN, 20));
        promptResponse.setPreferredSize(new Dimension(300, 30));
        prompt.add(promptResponse);

        popUp.add(prompt);

        return prompt;
    }

    // MODIFIES: this, button, popUp, & page
    // EFFECTS: adds an ActionListener to the given button that adds a new Income item to the budget in this GUI, 
    //          initializing fields with the given inputs, disables the visibility of popUp & page, 
    //          and runs displayBudgetManagerPage()  
    public void initializeAddIncomeDoneClick(JTextField descriptionInput, JComboBox categoryInput,
            JComboBox frequencyInput, JTextField valueInput, JButton button, JFrame popUp, JFrame page) {
        button.addActionListener(new ActionListener() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void actionPerformed(ActionEvent event) {
                String description = descriptionInput.getText();
                String category = categoryInput.getSelectedItem().toString();
                String frequency = frequencyInput.getSelectedItem().toString();
                double value = Double.parseDouble(valueInput.getText());
                budget.addIncome(new Income(value, description, category, frequency));

                popUp.setVisible(false);
                page.setVisible(false);
                displayBudgetManagerPage();
            }
        });
    }

    // MODIFIES: button
    // EFFECTS: adds an ActionListener to the given button that initiates runAddExpensePopUp()  
    public void initializeAddExpenseButtonClick(AbstractButton button, JFrame page) {
        button.addActionListener(new ActionListener() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void actionPerformed(ActionEvent event) {
                runAddExpensePopUp(page);
            }
        });
    }

    // MODIFIES: budget
    // EFFECTS: displays the add expense pop up
    public void runAddExpensePopUp(JFrame page) {
        JFrame addExpensePopUp = makePopUp("Add Expense!", 700, 450);
        SpringLayout layout = new SpringLayout();
        addExpensePopUp.setLayout(layout);
        addExpensePopUp.setLocationRelativeTo(addExpensePopUp);
        JPanel descriptionPrompt = makeDescriptionPrompt(addExpensePopUp);
        JPanel categoryPrompt = makeExpenseCategoryPrompt(addExpensePopUp);
        JPanel frequencyPrompt = makeFrequencyPrompt(addExpensePopUp);
        JPanel valuePrompt = makeValuePrompt(addExpensePopUp);
        JTextField descriptionField = (JTextField) descriptionPrompt.getComponent(1);
        JComboBox categoryField = (JComboBox) categoryPrompt.getComponent(1);
        JComboBox frequencyField = (JComboBox) frequencyPrompt.getComponent(1);
        JTextField valueField = (JTextField) valuePrompt.getComponent(1);
        JButton doneButton = makeDoneButton(addExpensePopUp);
        initializeAddExpenseDoneClick(descriptionField, categoryField, frequencyField,
                   valueField, doneButton, addExpensePopUp, page);
        addExpensePopUp.getRootPane().setDefaultButton(doneButton);

        layout.putConstraint(SpringLayout.NORTH, categoryPrompt, 20, SpringLayout.SOUTH, descriptionPrompt);
        layout.putConstraint(SpringLayout.NORTH, frequencyPrompt, 20, SpringLayout.SOUTH, categoryPrompt);
        layout.putConstraint(SpringLayout.NORTH, valuePrompt, 20, SpringLayout.SOUTH, frequencyPrompt);
        layout.putConstraint(SpringLayout.SOUTH, doneButton, 0, SpringLayout.SOUTH, addExpensePopUp.getContentPane());
        layout.putConstraint(SpringLayout.EAST, doneButton, 0, SpringLayout.EAST, addExpensePopUp.getContentPane());

        addExpensePopUp.setVisible(true);
    }    

    // MODIFIES: popUp
    // EFFECTS: adds a prompt and a corresponding textbox asking the user to
    //          select the expense's category, returns a panel for this, 
    //          and adds it to the given popUp
    public JPanel makeExpenseCategoryPrompt(JFrame popUp) {
        JPanel prompt = new JPanel();
        JLabel promptText = new JLabel("Please select a category:");
        promptText.setFont(new Font("Elephant", Font.PLAIN, 20));
        prompt.add(promptText);

        String[] categories = Expense.getCategories();
        JComboBox promptResponse = new JComboBox(categories);
        promptResponse.setSelectedIndex(0);
        promptResponse.setFont(new Font("Elephant", Font.PLAIN, 20));
        promptResponse.setPreferredSize(new Dimension(300, 30));
        prompt.add(promptResponse);

        popUp.add(prompt);

        return prompt;
    }

    // MODIFIES: this, button, popUp, & page
    // EFFECTS: adds an ActionListener to the given button that adds a new Expense item to the budget in this GUI, 
    //          initializing fields with the given inputs, disables the visibility of popUp & page, 
    //          and runs displayBudgetManagerPage()  
    public void initializeAddExpenseDoneClick(JTextField descriptionInput, JComboBox categoryInput,
            JComboBox frequencyInput, JTextField valueInput, JButton button, JFrame popUp, JFrame page) {
        button.addActionListener(new ActionListener() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void actionPerformed(ActionEvent event) {
                String description = descriptionInput.getText();
                String category = categoryInput.getSelectedItem().toString();
                String frequency = frequencyInput.getSelectedItem().toString();
                double value = Double.parseDouble(valueInput.getText());
                budget.addExpense(new Expense(value, description, category, frequency));

                popUp.setVisible(false);
                page.setVisible(false);
                displayBudgetManagerPage();
            }
        });
    }

    // MODIFIES: button
    // EFFECTS: adds an ActionListener to the given button that initiates runModifyBudgetPopUp()  
    public void initializeModifyBudgetButtonClick(AbstractButton button, JFrame page) {
        button.addActionListener(new ActionListener() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void actionPerformed(ActionEvent event) {
                runModifyBudgetPopUp(page);
            }
        });
    }

    // MODIFIES: budget
    // EFFECTS: runs the modify budget pop up page
    public void runModifyBudgetPopUp(JFrame page) {
        JFrame modifyBudgetPopUp = makePopUp("Modify Budget!", 700, 450);
        SpringLayout layout = new SpringLayout();
        modifyBudgetPopUp.setLayout(layout);
        modifyBudgetPopUp.setLocationRelativeTo(modifyBudgetPopUp);
        JPanel namePrompt = makeModifyBudgetNamePrompt(modifyBudgetPopUp);
        JPanel monthPrompt = makeModifyBudgetMonthPrompt(modifyBudgetPopUp);
        JPanel yearPrompt = makeModifyBudgetYearPrompt(modifyBudgetPopUp);
        JTextField nameField = (JTextField) namePrompt.getComponent(1);
        JTextField monthField = (JTextField) monthPrompt.getComponent(1);
        JTextField yearField = (JTextField) yearPrompt.getComponent(1);
        JButton doneButton = makeDoneButton(modifyBudgetPopUp);
        modifyBudgetPopUp.getRootPane().setDefaultButton(doneButton);
        initializeModifyBudgetDoneClick(nameField, monthField, yearField, doneButton, modifyBudgetPopUp, page);

        layout.putConstraint(SpringLayout.NORTH, monthPrompt, 20, SpringLayout.SOUTH, namePrompt);
        layout.putConstraint(SpringLayout.NORTH, yearPrompt, 20, SpringLayout.SOUTH, monthPrompt);
        layout.putConstraint(SpringLayout.SOUTH, doneButton, 0, SpringLayout.SOUTH, modifyBudgetPopUp.getContentPane());
        layout.putConstraint(SpringLayout.EAST, doneButton, 0, SpringLayout.EAST, modifyBudgetPopUp.getContentPane());

        modifyBudgetPopUp.setVisible(true);
    }    

    // MODIFIES: popUp
    // EFFECTS: adds a prompt and a corresponding textbox asking the user to
    //          enter their new name, returns a panel for this, and adds it to 
    //          the given popUp
    public JPanel makeModifyBudgetNamePrompt(JFrame popUp) {
        JPanel prompt = new JPanel();
        JLabel promptText = new JLabel("Modify Budget Name:");
        promptText.setFont(new Font("Elephant", Font.PLAIN, 20));
        prompt.add(promptText);

        JTextField promptResponse = new JTextField(budget.getName());
        promptResponse.setEditable(true);
        promptResponse.setFont(new Font("Elephant", Font.PLAIN, 20));
        promptResponse.setPreferredSize(new Dimension(300, 30));
        prompt.add(promptResponse);

        popUp.add(prompt);

        return prompt;
    }

    // MODIFIES: popUp
    // EFFECTS: adds a prompt and a corresponding textbox asking the user to
    //          enter the new month for the budget, returns a panel for this, 
    //          and adds it to the given popUp
    public JPanel makeModifyBudgetMonthPrompt(JFrame popUp) {
        JPanel prompt = new JPanel();
        JLabel promptText = new JLabel("Modify Budget Month:");
        promptText.setFont(new Font("Elephant", Font.PLAIN, 20));
        prompt.add(promptText);

        JTextField promptResponse = new JTextField(budget.getMonth());
        promptResponse.setEditable(true);
        promptResponse.setFont(new Font("Elephant", Font.PLAIN, 20));
        promptResponse.setPreferredSize(new Dimension(300, 30));
        prompt.add(promptResponse);

        popUp.add(prompt);

        return prompt;
    }

    // MODIFIES: popUp
    // EFFECTS: adds a prompt and a corresponding textbox asking the user to
    //          enter the new year for the budget, returns a panel for this, 
    //          and adds it to the given popUp
    public JPanel makeModifyBudgetYearPrompt(JFrame popUp) {
        JPanel prompt = new JPanel();
        JLabel promptText = new JLabel("Modify Budget Year:");
        promptText.setFont(new Font("Elephant", Font.PLAIN, 20));
        prompt.add(promptText);

        JTextField promptResponse = new JTextField("" + budget.getYear());
        promptResponse.setEditable(true);
        promptResponse.setFont(new Font("Elephant", Font.PLAIN, 20));
        promptResponse.setPreferredSize(new Dimension(300, 30));
        prompt.add(promptResponse);

        popUp.add(prompt);

        return prompt;
    }

    // MODIFIES: this, button, popUp, & page
    // EFFECTS: adds an ActionListener to the given button that sets the name, month, & year fields 
    //          of the budget in this GUI using the given name, month, & year, disables the visibility of popUp,
    //          and reloads page
    public void initializeModifyBudgetDoneClick(JTextField nameInput, JTextField monthInput, 
            JTextField yearInput, JButton button, JFrame popUp, JFrame page) {
        button.addActionListener(new ActionListener() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void actionPerformed(ActionEvent event) {
                String name = nameInput.getText();
                String month = monthInput.getText();
                int year = Integer.parseInt(yearInput.getText());
                budget.setName(name);
                budget.setMonth(month);
                budget.setYear(year);
                popUp.setVisible(false);
                page.setVisible(false);
                displayBudgetManagerPage();
            }
        });
    }

    // MODIFIES: button
    // EFFECTS: adds an ActionListener to the given button that initiates runModifyIncomePopUp()  
    public void initializeModifyIncomeButtonClick(AbstractButton button, JFrame page) {
        button.addActionListener(new ActionListener() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void actionPerformed(ActionEvent event) {
                if (budget.getIncomeList().getSize() == 0) {
                    runNoIncomePopUp();
                } else {
                    runModifyIncomePopUp(page);
                }
            }
        });
    }

    // EFFECTS: displays the no income pop up window
    public void runNoIncomePopUp() {
        JFrame noIncomePopUp = makePopUp("No Income Items!", 700, 250);
        SpringLayout layout = new SpringLayout();
        noIncomePopUp.setLayout(layout);
        noIncomePopUp.setLocationRelativeTo(noIncomePopUp);
        JTextField message = makeNoIncomeMessage(noIncomePopUp);
        JButton okButton = makeOkButton(noIncomePopUp);
        noIncomePopUp.getRootPane().setDefaultButton(okButton);

        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, message, 0, 
                SpringLayout.HORIZONTAL_CENTER, noIncomePopUp.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, message, 50, SpringLayout.NORTH, noIncomePopUp.getContentPane());
        layout.putConstraint(SpringLayout.EAST, okButton, 0, SpringLayout.EAST, noIncomePopUp.getContentPane());
        layout.putConstraint(SpringLayout.SOUTH, okButton, 0, SpringLayout.SOUTH, noIncomePopUp.getContentPane());

        noIncomePopUp.setVisible(true);
    }

    // MODIFIES: popUp
    // EFFECTS: returns the no income message and adds it to the given popUp
    public JTextField makeNoIncomeMessage(JFrame popUp) {
        JTextField message = new JTextField("Sorry, you don't have any Income items yet!");
        message.setEditable(false);
        message.setBorder(null);
        message.setFont(NAV_BUTTON_FONT);
        popUp.add(message);

        return message;
    }

    // MODIFIES: popUp
    // EFFECTS: returns an ok button with mouse hover & click responses initialized
    public JButton makeOkButton(JFrame popUp) {
        JButton button = new JButton("Done");
        button.setPreferredSize(new Dimension(100, 50));
        button.setFont(DONE_BUTTON_FONT);
        button.setBackground(BRIGHT_BLUE);
        initializeBrightBlueButtonHover(button);
        initializeOkClick(button, popUp);
        popUp.add(button);

        return button;
    }

    // MODIFIES: button
    // EFFECTS: adds an ActionListener to the given button that will
    //          disable the visibility of the given popUp frame on mouseclick
    public void initializeOkClick(JButton button, JFrame popUp) {
        button.addActionListener(new ActionListener() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void actionPerformed(ActionEvent event) {
                popUp.setVisible(false);
            }
        });
    }

    @SuppressWarnings("methodlength")
    // EFFECTS: runs the modify income popUp
    public void runModifyIncomePopUp(JFrame page) {
        JFrame modifyIncomePopUp = makePopUp("Modify Income!", 700, 450);
        SpringLayout layout = new SpringLayout();
        modifyIncomePopUp.setLayout(layout);
        modifyIncomePopUp.setLocationRelativeTo(modifyIncomePopUp);
        JPanel incomeSelectorPrompt = makeIncomeSelectorPrompt(modifyIncomePopUp);
        JPanel descriptionPrompt = makeModifyDescriptionPrompt(modifyIncomePopUp);
        JPanel categoryPrompt = makeModifyIncomeCategoryPrompt(modifyIncomePopUp);
        JPanel frequencyPrompt = makeModifyFrequencyPrompt(modifyIncomePopUp);
        JPanel valuePrompt = makeModifyValuePrompt(modifyIncomePopUp);
        JComboBox incomeField = (JComboBox) incomeSelectorPrompt.getComponent(1);
        JTextField descriptionField = (JTextField) descriptionPrompt.getComponent(1);
        JComboBox categoryField = (JComboBox) categoryPrompt.getComponent(1);
        JComboBox frequencyField = (JComboBox) frequencyPrompt.getComponent(1);
        JTextField valueField = (JTextField) valuePrompt.getComponent(1);
        JButton doneButton = makeDoneButton(modifyIncomePopUp);
        initializeModifyIncomeDoneClick(incomeField, descriptionField, categoryField, frequencyField,
                    valueField, doneButton, modifyIncomePopUp, page);
        modifyIncomePopUp.getRootPane().setDefaultButton(doneButton);
        layout.putConstraint(SpringLayout.NORTH, descriptionPrompt, 20, SpringLayout.SOUTH, incomeSelectorPrompt);
        layout.putConstraint(SpringLayout.NORTH, categoryPrompt, 20, SpringLayout.SOUTH, descriptionPrompt);
        layout.putConstraint(SpringLayout.NORTH, frequencyPrompt, 20, SpringLayout.SOUTH, categoryPrompt);
        layout.putConstraint(SpringLayout.NORTH, valuePrompt, 20, SpringLayout.SOUTH, frequencyPrompt);
        layout.putConstraint(SpringLayout.SOUTH, doneButton, 0, SpringLayout.SOUTH, modifyIncomePopUp.getContentPane());
        layout.putConstraint(SpringLayout.EAST, doneButton, 0, SpringLayout.EAST, modifyIncomePopUp.getContentPane());
        modifyIncomePopUp.setVisible(true);
    }    

    // MODIFIES: popUp
    // EFFECTS: adds a prompt and a corresponding textbox asking the user to
    //          select an income from the budget's IncomeList, returns a panel for this, 
    //          and adds it to the given popUp
    public JPanel makeIncomeSelectorPrompt(JFrame popUp) {
        JPanel prompt = new JPanel();
        JLabel promptText = new JLabel("Please select an Income:");
        promptText.setFont(new Font("Elephant", Font.PLAIN, 20));
        prompt.add(promptText);

        HashMap<String, Integer> incomeMap = budget.getIncomeList().getIncomeDescriptions();
        Object[] incomeList = incomeMap.keySet().toArray();
        JComboBox promptResponse = new JComboBox(incomeList);
        promptResponse.setSelectedIndex(0);
        promptResponse.setFont(new Font("Elephant", Font.PLAIN, 20));
        promptResponse.setPreferredSize(new Dimension(300, 30));
        prompt.add(promptResponse);

        popUp.add(prompt);

        return prompt;
    }

    // MODIFIES: popUp
    // EFFECTS: adds a prompt and a corresponding textbox asking the user to
    //          enter the money item's new description, returns a panel for this, 
    //          and adds it to the given popUp
    public JPanel makeModifyDescriptionPrompt(JFrame popUp) {
        JPanel prompt = new JPanel();
        JLabel promptText = new JLabel("Modify Description:");
        promptText.setFont(new Font("Elephant", Font.PLAIN, 20));
        prompt.add(promptText);

        JTextField promptResponse = new JTextField();
        promptResponse.setEditable(true);
        promptResponse.setFont(new Font("Elephant", Font.PLAIN, 20));
        promptResponse.setPreferredSize(new Dimension(300, 30));
        prompt.add(promptResponse);

        popUp.add(prompt);

        return prompt;
    }

    // MODIFIES: popUp
    // EFFECTS: adds a prompt and a corresponding textbox asking the user to
    //          select the income's new category, returns a panel for this, 
    //          and adds it to the given popUp
    public JPanel makeModifyIncomeCategoryPrompt(JFrame popUp) {
        JPanel prompt = new JPanel();
        JLabel promptText = new JLabel("Modify Category:");
        promptText.setFont(new Font("Elephant", Font.PLAIN, 20));
        prompt.add(promptText);

        String[] categories = Income.getCategories();
        JComboBox promptResponse = new JComboBox(categories);
        promptResponse.setSelectedIndex(0);
        promptResponse.setFont(new Font("Elephant", Font.PLAIN, 20));
        promptResponse.setPreferredSize(new Dimension(300, 30));
        prompt.add(promptResponse);

        popUp.add(prompt);

        return prompt;
    }

    // MODIFIES: popUp
    // EFFECTS: adds a prompt and a corresponding textbox asking the user to
    //          select the money item's new frequency, returns a panel for this, 
    //          and adds it to the given popUp
    public JPanel makeModifyFrequencyPrompt(JFrame popUp) {
        JPanel prompt = new JPanel();
        JLabel promptText = new JLabel("Modify Frequency:");
        promptText.setFont(new Font("Elephant", Font.PLAIN, 20));
        prompt.add(promptText);

        String[] frequencies = MoneyItem.getFrequencies();
        JComboBox promptResponse = new JComboBox(frequencies);
        promptResponse.setSelectedIndex(0);
        promptResponse.setFont(new Font("Elephant", Font.PLAIN, 20));
        promptResponse.setPreferredSize(new Dimension(300, 30));
        prompt.add(promptResponse);

        popUp.add(prompt);

        return prompt;
    }

    // MODIFIES: popUp
    // EFFECTS: adds a prompt and a corresponding textbox asking the user to
    //          enter the money item's new value, returns a panel for this, 
    //          and adds it to the given popUp
    public JPanel makeModifyValuePrompt(JFrame popUp) {
        JPanel prompt = new JPanel();
        JLabel promptText = new JLabel("Modify Value: $");
        promptText.setFont(new Font("Elephant", Font.PLAIN, 20));
        prompt.add(promptText);

        JTextField promptResponse = new JTextField();
        promptResponse.setEditable(true);
        promptResponse.setFont(new Font("Elephant", Font.PLAIN, 20));
        promptResponse.setPreferredSize(new Dimension(300, 30));
        prompt.add(promptResponse);

        popUp.add(prompt);

        return prompt;
    }

    // MODIFIES: this, button, popUp, & page
    // EFFECTS: adds an ActionListener to the given button that sets all the fields 
    //          of the indicated Income item in this GUI's budget using the given inputs, 
    //          disables the visibility of popUp & page, and runs displayBudgetManagerPage()  
    public void initializeModifyIncomeDoneClick(JComboBox incomeInput, JTextField descriptionInput, 
            JComboBox categoryInput, JComboBox frequencyInput, JTextField valueInput, JButton button, 
            JFrame popUp, JFrame page) {
        button.addActionListener(new ActionListener() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void actionPerformed(ActionEvent event) {
                String oldDescription = incomeInput.getSelectedItem().toString();
                String newDescription = descriptionInput.getText();
                String category = categoryInput.getSelectedItem().toString();
                String frequency = frequencyInput.getSelectedItem().toString();
                double value = Double.parseDouble(valueInput.getText());
                HashMap<String, Integer> incomeList = budget.getIncomeList().getIncomeDescriptions();
                Income item = budget.getIncomeList().getItem(incomeList.get(oldDescription));
                item.setDescription(newDescription);
                item.setCategory(category);
                item.setFrequency(frequency);
                item.setValue(value);

                popUp.setVisible(false);
                page.setVisible(false);
                displayBudgetManagerPage();
            }
        });
    }

    // MODIFIES: button
    // EFFECTS: adds an ActionListener to the given button that initiates runModifyExpensePopUp()  
    public void initializeModifyExpenseButtonClick(AbstractButton button, JFrame page) {
        button.addActionListener(new ActionListener() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void actionPerformed(ActionEvent event) {
                if (budget.getExpenseList().getSize() == 0) {
                    runNoExpensePopUp(page);
                } else {
                    runModifyExpensePopUp(page);
                }
            }
        });
    }

    // EFFECTS: displays the no expense pop up window
    public void runNoExpensePopUp(JFrame page) {
        JFrame noExpensePopUp = makePopUp("No Expense Items!", 700, 250);
        SpringLayout layout = new SpringLayout();
        noExpensePopUp.setLayout(layout);
        noExpensePopUp.setLocationRelativeTo(noExpensePopUp);
        JTextField message = makeNoExpenseMessage(noExpensePopUp);
        JButton okButton = makeOkButton(noExpensePopUp);
        noExpensePopUp.getRootPane().setDefaultButton(okButton);

        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, message, 0, 
                SpringLayout.HORIZONTAL_CENTER, noExpensePopUp.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, message, 50, SpringLayout.NORTH, noExpensePopUp.getContentPane());
        layout.putConstraint(SpringLayout.EAST, okButton, 0, SpringLayout.EAST, noExpensePopUp.getContentPane());
        layout.putConstraint(SpringLayout.SOUTH, okButton, 0, SpringLayout.SOUTH, noExpensePopUp.getContentPane());

        noExpensePopUp.setVisible(true);
    }

    // MODIFIES: popUp
    // EFFECTS: returns the no expense message and adds it to the given popUp
    public JTextField makeNoExpenseMessage(JFrame popUp) {
        JTextField message = new JTextField("Sorry, you don't have any Expense items yet!");
        message.setEditable(false);
        message.setBorder(null);
        message.setFont(NAV_BUTTON_FONT);
        popUp.add(message);

        return message;
    }

    @SuppressWarnings("methodlength")
    // EFFECTS: displays the modify expense pop up 
    public void runModifyExpensePopUp(JFrame page) {
        JFrame modifyExpensePopUp = makePopUp("Modify Expense!", 700, 450);
        SpringLayout layout = new SpringLayout();
        modifyExpensePopUp.setLayout(layout);
        modifyExpensePopUp.setLocationRelativeTo(modifyExpensePopUp);
        JPanel expenseSelectorPrompt = makeExpenseSelectorPrompt(modifyExpensePopUp);
        JPanel descriptionPrompt = makeModifyDescriptionPrompt(modifyExpensePopUp);
        JPanel categoryPrompt = makeModifyExpenseCategoryPrompt(modifyExpensePopUp);
        JPanel frequencyPrompt = makeModifyFrequencyPrompt(modifyExpensePopUp);
        JPanel valuePrompt = makeModifyValuePrompt(modifyExpensePopUp);
        JComboBox expenseField = (JComboBox) expenseSelectorPrompt.getComponent(1);
        JTextField descriptionField = (JTextField) descriptionPrompt.getComponent(1);
        JComboBox categoryField = (JComboBox) categoryPrompt.getComponent(1);
        JComboBox frequencyField = (JComboBox) frequencyPrompt.getComponent(1);
        JTextField valueField = (JTextField) valuePrompt.getComponent(1);
        JButton doneButton = makeDoneButton(modifyExpensePopUp);
        initializeModifyExpenseDoneClick(expenseField, descriptionField, categoryField, frequencyField,
                    valueField, doneButton, modifyExpensePopUp, page);
        modifyExpensePopUp.getRootPane().setDefaultButton(doneButton);
        layout.putConstraint(SpringLayout.NORTH, descriptionPrompt, 20, SpringLayout.SOUTH, expenseSelectorPrompt);
        layout.putConstraint(SpringLayout.NORTH, categoryPrompt, 20, SpringLayout.SOUTH, descriptionPrompt);
        layout.putConstraint(SpringLayout.NORTH, frequencyPrompt, 20, SpringLayout.SOUTH, categoryPrompt);
        layout.putConstraint(SpringLayout.NORTH, valuePrompt, 20, SpringLayout.SOUTH, frequencyPrompt);
        layout.putConstraint(SpringLayout.SOUTH, doneButton, 0, 
                SpringLayout.SOUTH, modifyExpensePopUp.getContentPane());
        layout.putConstraint(SpringLayout.EAST, doneButton, 0, SpringLayout.EAST, modifyExpensePopUp.getContentPane());
        modifyExpensePopUp.setVisible(true);
    }    

    // MODIFIES: popUp
    // EFFECTS: adds a prompt and a corresponding textbox asking the user to
    //          select an expense from the budget's ExpenseList, returns a panel for this, 
    //          and adds it to the given popUp
    public JPanel makeExpenseSelectorPrompt(JFrame popUp) {
        JPanel prompt = new JPanel();
        JLabel promptText = new JLabel("Please select an Expense:");
        promptText.setFont(new Font("Elephant", Font.PLAIN, 20));
        prompt.add(promptText);

        HashMap<String, Integer> expenseMap = budget.getExpenseList().getExpenseDescriptions();
        Object[] expenseList = expenseMap.keySet().toArray();
        JComboBox promptResponse = new JComboBox(expenseList);
        promptResponse.setSelectedIndex(0);
        promptResponse.setFont(new Font("Elephant", Font.PLAIN, 20));
        promptResponse.setPreferredSize(new Dimension(300, 30));
        prompt.add(promptResponse);

        popUp.add(prompt);

        return prompt;
    }

    // MODIFIES: popUp
    // EFFECTS: adds a prompt and a corresponding textbox asking the user to
    //          select the expense's new category, returns a panel for this, 
    //          and adds it to the given popUp
    public JPanel makeModifyExpenseCategoryPrompt(JFrame popUp) {
        JPanel prompt = new JPanel();
        JLabel promptText = new JLabel("Modify Category:");
        promptText.setFont(new Font("Elephant", Font.PLAIN, 20));
        prompt.add(promptText);

        String[] categories = Expense.getCategories();
        JComboBox promptResponse = new JComboBox(categories);
        promptResponse.setSelectedIndex(0);
        promptResponse.setFont(new Font("Elephant", Font.PLAIN, 20));
        promptResponse.setPreferredSize(new Dimension(300, 30));
        prompt.add(promptResponse);

        popUp.add(prompt);

        return prompt;
    }

    // MODIFIES: this, button, popUp, & page
    // EFFECTS: adds an ActionListener to the given button that sets all the fields 
    //          of the indicated Expense item in this GUI's budget using the given inputs, 
    //          disables the visibility of popUp & page, and runs displayBudgetManagerPage()  
    public void initializeModifyExpenseDoneClick(JComboBox expenseInput, JTextField descriptionInput, 
            JComboBox categoryInput, JComboBox frequencyInput, JTextField valueInput, JButton button, 
            JFrame popUp, JFrame page) {
        button.addActionListener(new ActionListener() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void actionPerformed(ActionEvent event) {
                String oldDescription = expenseInput.getSelectedItem().toString();
                String newDescription = descriptionInput.getText();
                String category = categoryInput.getSelectedItem().toString();
                String frequency = frequencyInput.getSelectedItem().toString();
                double value = Double.parseDouble(valueInput.getText());
                HashMap<String, Integer> expenseList = budget.getExpenseList().getExpenseDescriptions();
                Expense item = budget.getExpenseList().getItem(expenseList.get(oldDescription));
                item.setDescription(newDescription);
                item.setCategory(category);
                item.setFrequency(frequency);
                item.setValue(value);

                popUp.setVisible(false);
                page.setVisible(false);
                displayBudgetManagerPage();
            }
        });
    }

    // MODIFIES: button
    // EFFECTS: adds an ActionListener to the given button that initiates runRemoveIncomePopUp()  
    public void initializeRemoveIncomeButtonClick(AbstractButton button, JFrame page) {
        button.addActionListener(new ActionListener() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void actionPerformed(ActionEvent event) {
                if (budget.getIncomeList().getSize() == 0) {
                    runNoIncomePopUp();
                } else {
                    runRemoveIncomePopUp(page);
                }
            }
        });
    }

    // EFFECTS: displays the remove income pop up 
    public void runRemoveIncomePopUp(JFrame page) {
        JFrame removeIncomePopUp = makePopUp("Remove Income!", 700, 450);
        SpringLayout layout = new SpringLayout();
        removeIncomePopUp.setLayout(layout);
        removeIncomePopUp.setLocationRelativeTo(removeIncomePopUp);
        JPanel incomeSelectorPrompt = makeIncomeSelectorPrompt(removeIncomePopUp);
        JComboBox incomeField = (JComboBox) incomeSelectorPrompt.getComponent(1);
        JButton doneButton = makeDoneButton(removeIncomePopUp);
        initializeRemoveIncomeDoneClick(incomeField, doneButton, removeIncomePopUp, page);
        removeIncomePopUp.getRootPane().setDefaultButton(doneButton);

        layout.putConstraint(SpringLayout.SOUTH, doneButton, 0, SpringLayout.SOUTH, removeIncomePopUp.getContentPane());
        layout.putConstraint(SpringLayout.EAST, doneButton, 0, SpringLayout.EAST, removeIncomePopUp.getContentPane());

        removeIncomePopUp.setVisible(true);
    }    

    // MODIFIES: this, button, popUp, & page
    // EFFECTS: adds an ActionListener to the given button that removes the indicated Income item in this GUI's budget
    //          using the given inputs, disables the visibility of popUp & page,
    //          and runs displayBudgetManagerPage()  
    public void initializeRemoveIncomeDoneClick(JComboBox incomeInput, JButton button, JFrame popUp, JFrame page) {
        button.addActionListener(new ActionListener() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void actionPerformed(ActionEvent event) {
                String incomeName = incomeInput.getSelectedItem().toString();
                HashMap<String, Integer> incomeList = budget.getIncomeList().getIncomeDescriptions();
                int incomeIndex = incomeList.get(incomeName);
                budget.removeIncome(incomeIndex);

                popUp.setVisible(false);
                page.setVisible(false);
                displayBudgetManagerPage();
            }
        });
    }

    // MODIFIES: button
    // EFFECTS: adds an ActionListener to the given button that initiates runRemoveExpensePopUp()  
    public void initializeRemoveExpenseButtonClick(AbstractButton button, JFrame page) {
        button.addActionListener(new ActionListener() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void actionPerformed(ActionEvent event) {
                if (budget.getExpenseList().getSize() == 0) {
                    runNoExpensePopUp(page);
                } else {
                    runRemoveExpensePopUp(page);
                }
            }
        });
    }

    // EFFECTS: displays the remove expense pop up 
    public void runRemoveExpensePopUp(JFrame page) {
        JFrame removeExpensePopUp = makePopUp("Remove Expense!", 700, 450);
        SpringLayout layout = new SpringLayout();
        removeExpensePopUp.setLayout(layout);
        removeExpensePopUp.setLocationRelativeTo(removeExpensePopUp);
        JPanel expenseSelectorPrompt = makeExpenseSelectorPrompt(removeExpensePopUp);
        JComboBox expenseField = (JComboBox) expenseSelectorPrompt.getComponent(1);
        JButton doneButton = makeDoneButton(removeExpensePopUp);
        initializeRemoveExpenseDoneClick(expenseField, doneButton, removeExpensePopUp, page);
        removeExpensePopUp.getRootPane().setDefaultButton(doneButton);

        layout.putConstraint(SpringLayout.SOUTH, doneButton, 0, 
                SpringLayout.SOUTH, removeExpensePopUp.getContentPane());
        layout.putConstraint(SpringLayout.EAST, doneButton, 0, SpringLayout.EAST, removeExpensePopUp.getContentPane());

        removeExpensePopUp.setVisible(true);
    }    

    // MODIFIES: this, button, popUp, & page
    // EFFECTS: adds an ActionListener to the given button that removes the indicated Expense item in this GUI's budget
    //          using the given inputs, disables the visibility of popUp & page,
    //          and runs displayBudgetManagerPage()  
    public void initializeRemoveExpenseDoneClick(JComboBox expenseInput, JButton button, JFrame popUp, JFrame page) {
        button.addActionListener(new ActionListener() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void actionPerformed(ActionEvent event) {
                String expenseName = expenseInput.getSelectedItem().toString();
                HashMap<String, Integer> expenseList = budget.getExpenseList().getExpenseDescriptions();
                int expenseIndex = expenseList.get(expenseName);
                budget.removeExpense(expenseIndex);

                popUp.setVisible(false);
                page.setVisible(false);
                displayBudgetManagerPage();
            }
        });
    }

    // MODIFIES: button
    // EFFECTS: adds an ActionListener to the given button that initiates runProjectionPopUp()  
    public void initializeProjectionButtonClick(AbstractButton button) {
        button.addActionListener(new ActionListener() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void actionPerformed(ActionEvent event) {
                runProjectionPopUp();
            }
        });
    }

    // EFFECTS: displays the projection pop up 
    public void runProjectionPopUp() {
        JFrame projectionPopUp = makePopUp("Make a Projection!", 700, 450);
        SpringLayout layout = new SpringLayout();
        projectionPopUp.setLayout(layout);
        projectionPopUp.setLocationRelativeTo(projectionPopUp);
        JPanel savingsPrompt = makeSavingsPrompt(projectionPopUp);
        JPanel projectionItemNamePrompt = makeProjectionItemNamePrompt(projectionPopUp);
        JPanel projectionItemValuePrompt = makeProjectionItemValuePrompt(projectionPopUp);
        JTextField savingsField = (JTextField) savingsPrompt.getComponent(1);
        JTextField itemNameField = (JTextField) projectionItemNamePrompt.getComponent(1);
        JTextField itemValueField = (JTextField) projectionItemValuePrompt.getComponent(1);
        JButton doneButton = makeDoneButton(projectionPopUp);
        initializeProjectionDoneClick(savingsField, itemNameField, itemValueField, doneButton, projectionPopUp);
        projectionPopUp.getRootPane().setDefaultButton(doneButton);

        layout.putConstraint(SpringLayout.NORTH, projectionItemNamePrompt, 20, SpringLayout.SOUTH, savingsPrompt);
        layout.putConstraint(SpringLayout.NORTH, projectionItemValuePrompt, 20, 
                SpringLayout.SOUTH, projectionItemNamePrompt);
        layout.putConstraint(SpringLayout.SOUTH, doneButton, 0, SpringLayout.SOUTH, projectionPopUp.getContentPane());
        layout.putConstraint(SpringLayout.EAST, doneButton, 0, SpringLayout.EAST, projectionPopUp.getContentPane());

        projectionPopUp.setVisible(true);
    }    

    // MODIFIES: popUp
    // EFFECTS: adds a prompt and a corresponding textbox asking the user to
    //          enter the savings for the projection item, returns a panel for 
    //          this, and adds it to the given popUp
    public JPanel makeSavingsPrompt(JFrame popUp) {
        JPanel prompt = new JPanel();
        JLabel promptText = new JLabel("Savings Thus Far: $");
        promptText.setFont(new Font("Elephant", Font.PLAIN, 20));
        prompt.add(promptText);

        JTextField promptResponse = new JTextField();
        promptResponse.setEditable(true);
        promptResponse.setFont(new Font("Elephant", Font.PLAIN, 20));
        promptResponse.setPreferredSize(new Dimension(300, 30));
        prompt.add(promptResponse);

        popUp.add(prompt);

        return prompt;
    }

    // MODIFIES: popUp
    // EFFECTS: adds a prompt and a corresponding textbox asking the user to
    //          enter the name for the projection item, returns a panel for 
    //          this, and adds it to the given popUp
    public JPanel makeProjectionItemNamePrompt(JFrame popUp) {
        JPanel prompt = new JPanel();
        JLabel promptText = new JLabel("Enter the Desired Item's Name:");
        promptText.setFont(new Font("Elephant", Font.PLAIN, 20));
        prompt.add(promptText);

        JTextField promptResponse = new JTextField();
        promptResponse.setEditable(true);
        promptResponse.setFont(new Font("Elephant", Font.PLAIN, 20));
        promptResponse.setPreferredSize(new Dimension(300, 30));
        prompt.add(promptResponse);

        popUp.add(prompt);

        return prompt;
    }

    // MODIFIES: popUp
    // EFFECTS: adds a prompt and a corresponding textbox asking the user to
    //          enter the value for the projection item, returns a panel for 
    //          this, and adds it to the given popUp
    public JPanel makeProjectionItemValuePrompt(JFrame popUp) {
        JPanel prompt = new JPanel();
        JLabel promptText = new JLabel("Enter the Desired Item's Value: $");
        promptText.setFont(new Font("Elephant", Font.PLAIN, 20));
        prompt.add(promptText);

        JTextField promptResponse = new JTextField();
        promptResponse.setEditable(true);
        promptResponse.setFont(new Font("Elephant", Font.PLAIN, 20));
        promptResponse.setPreferredSize(new Dimension(300, 30));
        prompt.add(promptResponse);

        popUp.add(prompt);

        return prompt;
    }

    // MODIFIES: button, popUp
    // EFFECTS: adds an ActionListener to the given button that disables the visibility of the given popUp, 
    //          and then initiates runProjectionResultsPopUp()
    public void initializeProjectionDoneClick(JTextField savingsField, JTextField itemNameField, 
            JTextField itemValueField, JButton button, JFrame popUp) {
        button.addActionListener(new ActionListener() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void actionPerformed(ActionEvent event) {
                double savings = Double.parseDouble(savingsField.getText());
                String itemName = itemNameField.getText();
                double itemValue = Double.parseDouble(itemValueField.getText());
                popUp.setVisible(false);
                runProjectionResultsPopUp(savings, itemName, itemValue);
            }
        });
    }

    // EFFECTS: displays the projection results pop up window
    public void runProjectionResultsPopUp(double savings, String itemName, double itemValue) {
        JFrame projectionResultsPopUp = makePopUp("Projection Results!", 700, 250);
        SpringLayout layout = new SpringLayout();
        projectionResultsPopUp.setLayout(layout);
        projectionResultsPopUp.setLocationRelativeTo(projectionResultsPopUp);
        JTextField message = makeProjectionResultsMessage(savings, itemName, itemValue, projectionResultsPopUp);
        JButton okButton = makeOkButton(projectionResultsPopUp);
        projectionResultsPopUp.getRootPane().setDefaultButton(okButton);

        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, message, 0, 
                SpringLayout.HORIZONTAL_CENTER, projectionResultsPopUp.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, message, 50, 
                SpringLayout.NORTH, projectionResultsPopUp.getContentPane());
        layout.putConstraint(SpringLayout.EAST, okButton, 0, 
                SpringLayout.EAST, projectionResultsPopUp.getContentPane());
        layout.putConstraint(SpringLayout.SOUTH, okButton, 0, 
                SpringLayout.SOUTH, projectionResultsPopUp.getContentPane());

        projectionResultsPopUp.setVisible(true);
    }

    // MODIFIES: popUp
    // EFFECTS: returns the projection results message and adds it to the given popUp
    public JTextField makeProjectionResultsMessage(double savings, String itemName, double itemValue, JFrame popUp) {
        int months = budget.getProjection(savings, itemValue);
        if (months == -1) {
            JTextField message = new JTextField("Sorry, it is IMPOSSIBLE for you to purchase "
                    + itemName + " with the current spending!");
            message.setEditable(false);
            message.setBorder(null);
            message.setFont(NAV_BUTTON_FONT);
            popUp.add(message);
            return message;
        } else {
            JTextField message = new JTextField("It would take you " + months 
                    + " months to purchase " + itemName + " with the current spending!");
            message.setEditable(false);
            message.setBorder(null);
            message.setFont(NAV_BUTTON_FONT);
            popUp.add(message);
            return message;
        }
    }

    // MODIFIES: button
    // EFFECTS: adds an ActionListener to the given button that initiates runDistributionsPopUp()  
    public void initializeDistributionsButtonClick(AbstractButton button) {
        button.addActionListener(new ActionListener() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void actionPerformed(ActionEvent event) {
                runDistributionPopUp();
            }
        });
    }

    // EFFECTS: displays the distribution pop up 
    public void runDistributionPopUp() {
        JFrame distributionsPopUp = makePopUp("Distributions!", DISTRIBUTION_WINDOW_WIDTH, DISTRIBUTION_WINDOW_HEIGHT);
        SpringLayout layout = new SpringLayout();
        distributionsPopUp.setLayout(layout);
        distributionsPopUp.setLocationRelativeTo(distributionsPopUp);
        JTabbedPane distributionsPane = makeDistributionsPane(distributionsPopUp);

        JPanel incomeDistribution = makeIncomeDistribution();
        JPanel expenseDistribution = makeExpenseDistribution();
        addDistributionTabs(incomeDistribution, expenseDistribution, distributionsPane);
        JButton okButton = makeOkButton(distributionsPopUp);
        initializeOkClick(okButton, distributionsPopUp);
        distributionsPopUp.getRootPane().setDefaultButton(okButton);

        layout.putConstraint(SpringLayout.SOUTH, okButton, 0, SpringLayout.SOUTH, distributionsPopUp.getContentPane());
        layout.putConstraint(SpringLayout.EAST, okButton, 0, SpringLayout.EAST, distributionsPopUp.getContentPane());

        distributionsPopUp.setVisible(true);
    }   

    // MODIFIES: popUp
    // EFFECTS: returns and adds the distributions tabbed pane to the given popUp
    public JTabbedPane makeDistributionsPane(JFrame popUp) {
        JTabbedPane distributionsPane = new JTabbedPane(JTabbedPane.LEFT);
        distributionsPane.setTabPlacement(JTabbedPane.TOP);
        popUp.add(distributionsPane);

        return distributionsPane;
    }

    // MODIFIES: tabbedPane
    // EFFECTS: adds the income & expense distribution tabs to the given tabbedPane
    public void addDistributionTabs(JPanel incomeTab, JPanel expenseTab, JTabbedPane tabbedPane) {
        tabbedPane.addTab("Income Distribution", incomeTab);
        tabbedPane.addTab("Expense Distribution", expenseTab);
    }

    // EFFECTS: returns the income distribution
    public JPanel makeIncomeDistribution() {
        JPanel distribution = new JPanel();
        SpringLayout layout = new SpringLayout();
        distribution.setPreferredSize(DISTRIBUTION_CONTAINER_DIMENSIONS);
        distribution.setLayout(layout);
        JLabel incomeTitle = makeIncomeDistributionTitle(distribution);
        JPanel incomeBar = makeIncomeDistributionBar(distribution);
        JPanel incomeCategoryDirectory = makeIncomeCategoryDirectory(distribution);

        layout.putConstraint(SpringLayout.NORTH, incomeTitle, 15, SpringLayout.NORTH, distribution);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, incomeTitle, 0, 
                SpringLayout.HORIZONTAL_CENTER, distribution);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, incomeBar, 0, 
                SpringLayout.HORIZONTAL_CENTER, distribution);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, incomeCategoryDirectory, 0, 
                SpringLayout.HORIZONTAL_CENTER, distribution);
        layout.putConstraint(SpringLayout.NORTH, incomeBar, 25, SpringLayout.NORTH, incomeTitle);
        layout.putConstraint(SpringLayout.NORTH, incomeCategoryDirectory, 25, SpringLayout.NORTH, incomeBar);

        return distribution;
    }

    // MODIFIES: distribution
    // EFFECTS: returns and adds the income distribution title to 
    //          the given distribution panel
    public JLabel makeIncomeDistributionTitle(JPanel distribution) {
        JLabel title = new JLabel("Income Distribution");
        title.setFont(NAV_BUTTON_FONT);
        distribution.add(title);

        return title;
    }

    // MODIFIES: distribution
    // EFFECTS: returns and adds the income distribution bar to 
    //          the given distribution panel
    public JPanel makeIncomeDistributionBar(JPanel distribution) {
        JPanel bar = new JPanel();
        bar.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        bar.setPreferredSize(DISTRIBUTION_BAR_DIMENSIONS);
        bar.setBorder(null);

        makeIncomeWorkBar(bar);
        makeIncomeGovernmentBenefitBar(bar);
        makeIncomeGiftBar(bar);
        makeIncomeOtherBar(bar);

        distribution.add(bar);

        return bar;
    }

    // REQUIRES: bar has DISTRIBUTION_BAR_DIMENSIONS
    // MODIFIES: bar
    // EFFECTS: adds the income work bar to the given bar
    public void makeIncomeWorkBar(JPanel bar) {
        JPanel workBar = new JPanel();
        ArrayList<Double> categoryPercentages = budget.getIncomeList().getDistributionPercentages();
        workBar.setPreferredSize(new Dimension((int) (DISTRIBUTION_BAR_WIDTH * categoryPercentages.get(0)), 
                DISTRIBUTION_BAR_HEIGHT));
        workBar.setBackground(WORK_CATEGORY_COLOUR);
        workBar.setBorder(null);
        bar.add(workBar);
    }

    // REQUIRES: bar has DISTRIBUTION_BAR_DIMENSIONS
    // MODIFIES: bar
    // EFFECTS: adds the income government benefit bar to the given bar
    public void makeIncomeGovernmentBenefitBar(JPanel bar) {
        JPanel governmentBenefitBar = new JPanel();
        ArrayList<Double> categoryPercentages = budget.getIncomeList().getDistributionPercentages();
        governmentBenefitBar.setPreferredSize(new Dimension((int) (DISTRIBUTION_BAR_WIDTH * categoryPercentages.get(1)),
                DISTRIBUTION_BAR_HEIGHT));
        governmentBenefitBar.setBackground(GOVERNMENTBENEFIT_CATEGORY_COLOUR);
        governmentBenefitBar.setBorder(null);
        bar.add(governmentBenefitBar);
    }

    // REQUIRES: bar has DISTRIBUTION_BAR_DIMENSIONS
    // MODIFIES: bar
    // EFFECTS: adds the income gift bar to the given bar
    public void makeIncomeGiftBar(JPanel bar) {
        JPanel giftBar = new JPanel();
        ArrayList<Double> categoryPercentages = budget.getIncomeList().getDistributionPercentages();
        giftBar.setPreferredSize(new Dimension((int) (DISTRIBUTION_BAR_WIDTH * categoryPercentages.get(2)), 
                DISTRIBUTION_BAR_HEIGHT));
        giftBar.setBackground(GIFT_CATEGORY_COLOUR);
        giftBar.setBorder(null);
        bar.add(giftBar);
    }

    // REQUIRES: bar has DISTRIBUTION_BAR_DIMENSIONS
    // MODIFIES: bar
    // EFFECTS: adds the income other bar to the given bar
    public void makeIncomeOtherBar(JPanel bar) {
        JPanel otherBar = new JPanel();
        ArrayList<Double> categoryPercentages = budget.getIncomeList().getDistributionPercentages();
        otherBar.setPreferredSize(new Dimension((int) (DISTRIBUTION_BAR_WIDTH * categoryPercentages.get(3)), 
                DISTRIBUTION_BAR_HEIGHT));
        otherBar.setBackground(OTHER_CATEGORY_COLOUR);
        otherBar.setBorder(null);
        bar.add(otherBar);
    }

    // MODIFIES: distribution
    // EFFECTS: returns and adds the income category directory to 
    //          the given distribution panel
    public JPanel makeIncomeCategoryDirectory(JPanel distribution) {
        JPanel directory = new JPanel();
        directory.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 10));
        directory.setPreferredSize(DIRECTORY_DIMENSIONS);
        directory.setBorder(null);

        makeIncomeWorkLabel(directory);
        makeIncomeGovernmentBenefitLabel(directory);
        makeIncomeGiftLabel(directory);
        makeIncomeOtherLabel(directory);

        distribution.add(directory);

        return directory;
    }

    // MODIFIES: panel
    // EFFECTS: adds the income work category label to the given panel
    public void makeIncomeWorkLabel(JPanel panel) {
        JPanel workLabel = new JPanel();
        workLabel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JPanel icon = new JPanel();
        icon.setBackground(WORK_CATEGORY_COLOUR);
        workLabel.add(icon);

        ArrayList<Double> categoryPercentages = budget.getIncomeList().getDistributionPercentages();
        JLabel label = new JLabel("Work - " + String.format("%.1f", categoryPercentages.get(0) * 100) + "%");
        label.setFont(NAV_BUTTON_FONT);
        workLabel.add(label);

        panel.add(workLabel);
    }

    // MODIFIES: panel
    // EFFECTS: adds the income government benefit category label to the given panel
    public void makeIncomeGovernmentBenefitLabel(JPanel panel) {
        JPanel workGovernmentBenefit = new JPanel();
        workGovernmentBenefit.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JPanel icon = new JPanel();
        icon.setBackground(GOVERNMENTBENEFIT_CATEGORY_COLOUR);
        workGovernmentBenefit.add(icon);

        ArrayList<Double> categoryPercentages = budget.getIncomeList().getDistributionPercentages();
        JLabel label = new JLabel("Government Benefit - " + String.format("%.1f", 
                categoryPercentages.get(1) * 100) + "%");
        label.setFont(NAV_BUTTON_FONT);
        workGovernmentBenefit.add(label);

        panel.add(workGovernmentBenefit);
    }

    // MODIFIES: panel
    // EFFECTS: adds the income gift category label to the given panel
    public void makeIncomeGiftLabel(JPanel panel) {
        JPanel giftLabel = new JPanel();
        giftLabel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JPanel icon = new JPanel();
        icon.setBackground(GIFT_CATEGORY_COLOUR);
        giftLabel.add(icon);

        ArrayList<Double> categoryPercentages = budget.getIncomeList().getDistributionPercentages();
        JLabel label = new JLabel("Gift - " + String.format("%.1f", categoryPercentages.get(2) * 100) + "%");
        label.setFont(NAV_BUTTON_FONT);
        giftLabel.add(label);

        panel.add(giftLabel);
    }

    // MODIFIES: panel
    // EFFECTS: adds the income other category label to the given panel
    public void makeIncomeOtherLabel(JPanel panel) {
        JPanel otherLabel = new JPanel();
        otherLabel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JPanel icon = new JPanel();
        icon.setBackground(OTHER_CATEGORY_COLOUR);
        otherLabel.add(icon);

        ArrayList<Double> categoryPercentages = budget.getIncomeList().getDistributionPercentages();
        JLabel label = new JLabel("Other - " + String.format("%.1f", categoryPercentages.get(3) * 100) + "%");
        label.setFont(NAV_BUTTON_FONT);
        otherLabel.add(label);

        panel.add(otherLabel);
    }

    // EFFECTS: returns the expense distribution
    public JPanel makeExpenseDistribution() {
        JPanel distribution = new JPanel();
        SpringLayout layout = new SpringLayout();
        distribution.setPreferredSize(DISTRIBUTION_CONTAINER_DIMENSIONS);
        distribution.setLayout(layout);
        JLabel expenseTitle = makeExpenseDistributionTitle(distribution);
        JPanel expenseBar = makeExpenseDistributionBar(distribution);
        JPanel expenseCategoryDirectory = makeExpenseCategoryDirectory(distribution);

        layout.putConstraint(SpringLayout.NORTH, expenseTitle, 15, SpringLayout.NORTH, distribution);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, expenseTitle, 0, 
                SpringLayout.HORIZONTAL_CENTER, distribution);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, expenseBar, 0, 
                SpringLayout.HORIZONTAL_CENTER, distribution);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, expenseCategoryDirectory, 0, 
                SpringLayout.HORIZONTAL_CENTER, distribution);
        layout.putConstraint(SpringLayout.NORTH, expenseBar, 25, SpringLayout.NORTH, expenseTitle);
        layout.putConstraint(SpringLayout.NORTH, expenseCategoryDirectory, 25, SpringLayout.NORTH, expenseBar);

        return distribution;
    }

    // MODIFIES: distribution
    // EFFECTS: returns and adds the expense distribution title to 
    //          the given distribution panel
    public JLabel makeExpenseDistributionTitle(JPanel distribution) {
        JLabel title = new JLabel("Expense Distribution");
        title.setFont(NAV_BUTTON_FONT);
        distribution.add(title);

        return title;
    }

    // MODIFIES: distribution
    // EFFECTS: returns and adds the expense distribution bar to 
    //          the given distribution panel
    public JPanel makeExpenseDistributionBar(JPanel distribution) {
        JPanel bar = new JPanel();
        bar.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        bar.setPreferredSize(DISTRIBUTION_BAR_DIMENSIONS);
        bar.setBorder(null);

        makeExpenseFoodBar(bar);
        makeExpenseEntertainmentBar(bar);
        makeExpensePersonalCareBar(bar);
        makeExpenseGiftBar(bar);
        makeExpenseDonationBar(bar);
        makeExpenseTransportationBar(bar);
        makeExpenseInsuranceBar(bar);
        makeExpenseMedicalBar(bar);
        makeExpenseTravelBar(bar);
        makeExpenseUtilitiesBar(bar);
        makeExpenseHousingBar(bar);
        makeExpenseEducationBar(bar);
        makeExpenseMembershipBar(bar);
        makeExpenseSavingsBar(bar);
        makeExpenseOtherBar(bar);

        distribution.add(bar);

        return bar;
    }

    // REQUIRES: bar has DISTRIBUTION_BAR_DIMENSIONS
    // MODIFIES: bar
    // EFFECTS: adds the expense food bar to the given bar
    public void makeExpenseFoodBar(JPanel bar) {
        JPanel foodBar = new JPanel();
        ArrayList<Double> categoryPercentages = budget.getExpenseList().getDistributionPercentages();
        foodBar.setPreferredSize(new Dimension((int) (DISTRIBUTION_BAR_WIDTH * categoryPercentages.get(0)), 
                DISTRIBUTION_BAR_HEIGHT));
        foodBar.setBackground(FOOD_CATEGORY_COLOUR);
        foodBar.setBorder(null);
        bar.add(foodBar);
    }

    // REQUIRES: bar has DISTRIBUTION_BAR_DIMENSIONS
    // MODIFIES: bar
    // EFFECTS: adds the expense entertainment bar to the given bar
    public void makeExpenseEntertainmentBar(JPanel bar) {
        JPanel entertainmentBar = new JPanel();
        ArrayList<Double> categoryPercentages = budget.getExpenseList().getDistributionPercentages();
        entertainmentBar.setPreferredSize(new Dimension((int) (DISTRIBUTION_BAR_WIDTH * categoryPercentages.get(1)), 
                DISTRIBUTION_BAR_HEIGHT));
        entertainmentBar.setBackground(ENTERTAINMENT_CATEGORY_COLOUR);
        entertainmentBar.setBorder(null);
        bar.add(entertainmentBar);
    }

    // REQUIRES: bar has DISTRIBUTION_BAR_DIMENSIONS
    // MODIFIES: bar
    // EFFECTS: adds the expense personal care bar to the given bar
    public void makeExpensePersonalCareBar(JPanel bar) {
        JPanel personalCareBar = new JPanel();
        ArrayList<Double> categoryPercentages = budget.getExpenseList().getDistributionPercentages();
        personalCareBar.setPreferredSize(new Dimension((int) (DISTRIBUTION_BAR_WIDTH * categoryPercentages.get(2)), 
                DISTRIBUTION_BAR_HEIGHT));
        personalCareBar.setBackground(PERSONALCARE_CATEGORY_COLOUR);
        personalCareBar.setBorder(null);
        bar.add(personalCareBar);
    }

    // REQUIRES: bar has DISTRIBUTION_BAR_DIMENSIONS
    // MODIFIES: bar
    // EFFECTS: adds the expense gift bar to the given bar
    public void makeExpenseGiftBar(JPanel bar) {
        JPanel giftBar = new JPanel();
        ArrayList<Double> categoryPercentages = budget.getExpenseList().getDistributionPercentages();
        giftBar.setPreferredSize(new Dimension((int) (DISTRIBUTION_BAR_WIDTH * categoryPercentages.get(3)), 
                DISTRIBUTION_BAR_HEIGHT));
        giftBar.setBackground(GIFT_CATEGORY_COLOUR);
        giftBar.setBorder(null);
        bar.add(giftBar);
    }

    // REQUIRES: bar has DISTRIBUTION_BAR_DIMENSIONS
    // MODIFIES: bar
    // EFFECTS: adds the expense donation bar to the given bar
    public void makeExpenseDonationBar(JPanel bar) {
        JPanel donationBar = new JPanel();
        ArrayList<Double> categoryPercentages = budget.getExpenseList().getDistributionPercentages();
        donationBar.setPreferredSize(new Dimension((int) (DISTRIBUTION_BAR_WIDTH * categoryPercentages.get(4)), 
                DISTRIBUTION_BAR_HEIGHT));
        donationBar.setBackground(DONATION_CATEGORY_COLOUR);
        donationBar.setBorder(null);
        bar.add(donationBar);
    }

    // REQUIRES: bar has DISTRIBUTION_BAR_DIMENSIONS
    // MODIFIES: bar
    // EFFECTS: adds the expense transportation bar to the given bar
    public void makeExpenseTransportationBar(JPanel bar) {
        JPanel transportationBar = new JPanel();
        ArrayList<Double> categoryPercentages = budget.getExpenseList().getDistributionPercentages();
        transportationBar.setPreferredSize(new Dimension((int) (DISTRIBUTION_BAR_WIDTH * categoryPercentages.get(5)), 
                DISTRIBUTION_BAR_HEIGHT));
        transportationBar.setBackground(TRANSPORTATION_CATEGORY_COLOUR);
        transportationBar.setBorder(null);
        bar.add(transportationBar);
    }

    // REQUIRES: bar has DISTRIBUTION_BAR_DIMENSIONS
    // MODIFIES: bar
    // EFFECTS: adds the expense insurance bar to the given bar
    public void makeExpenseInsuranceBar(JPanel bar) {
        JPanel insuranceBar = new JPanel();
        ArrayList<Double> categoryPercentages = budget.getExpenseList().getDistributionPercentages();
        insuranceBar.setPreferredSize(new Dimension((int) (DISTRIBUTION_BAR_WIDTH * categoryPercentages.get(6)), 
                DISTRIBUTION_BAR_HEIGHT));
        insuranceBar.setBackground(INSURANCE_CATEGORY_COLOUR);
        insuranceBar.setBorder(null);
        bar.add(insuranceBar);
    }

    // REQUIRES: bar has DISTRIBUTION_BAR_DIMENSIONS
    // MODIFIES: bar
    // EFFECTS: adds the expense medical bar to the given bar
    public void makeExpenseMedicalBar(JPanel bar) {
        JPanel medicalBar = new JPanel();
        ArrayList<Double> categoryPercentages = budget.getExpenseList().getDistributionPercentages();
        medicalBar.setPreferredSize(new Dimension((int) (DISTRIBUTION_BAR_WIDTH * categoryPercentages.get(7)), 
                DISTRIBUTION_BAR_HEIGHT));
        medicalBar.setBackground(MEDICAL_CATEGORY_COLOUR);
        medicalBar.setBorder(null);
        bar.add(medicalBar);
    }

    // REQUIRES: bar has DISTRIBUTION_BAR_DIMENSIONS
    // MODIFIES: bar
    // EFFECTS: adds the expense travel bar to the given bar
    public void makeExpenseTravelBar(JPanel bar) {
        JPanel travelBar = new JPanel();
        ArrayList<Double> categoryPercentages = budget.getExpenseList().getDistributionPercentages();
        travelBar.setPreferredSize(new Dimension((int) (DISTRIBUTION_BAR_WIDTH * categoryPercentages.get(8)), 
                DISTRIBUTION_BAR_HEIGHT));
        travelBar.setBackground(TRAVEL_CATEGORY_COLOUR);
        travelBar.setBorder(null);
        bar.add(travelBar);
    }

    // REQUIRES: bar has DISTRIBUTION_BAR_DIMENSIONS
    // MODIFIES: bar
    // EFFECTS: adds the expense utilities bar to the given bar
    public void makeExpenseUtilitiesBar(JPanel bar) {
        JPanel utilitiesBar = new JPanel();
        ArrayList<Double> categoryPercentages = budget.getExpenseList().getDistributionPercentages();
        utilitiesBar.setPreferredSize(new Dimension((int) (DISTRIBUTION_BAR_WIDTH * categoryPercentages.get(9)), 
                DISTRIBUTION_BAR_HEIGHT));
        utilitiesBar.setBackground(UTILITIES_CATEGORY_COLOUR);
        utilitiesBar.setBorder(null);
        bar.add(utilitiesBar);
    }

    // REQUIRES: bar has DISTRIBUTION_BAR_DIMENSIONS
    // MODIFIES: bar
    // EFFECTS: adds the expense housing bar to the given bar
    public void makeExpenseHousingBar(JPanel bar) {
        JPanel housingBar = new JPanel();
        ArrayList<Double> categoryPercentages = budget.getExpenseList().getDistributionPercentages();
        housingBar.setPreferredSize(new Dimension((int) (DISTRIBUTION_BAR_WIDTH * categoryPercentages.get(10)),
                 DISTRIBUTION_BAR_HEIGHT));
        housingBar.setBackground(HOUSING_CATEGORY_COLOUR);
        housingBar.setBorder(null);
        bar.add(housingBar);
    }

    // REQUIRES: bar has DISTRIBUTION_BAR_DIMENSIONS
    // MODIFIES: bar
    // EFFECTS: adds the expense education bar to the given bar
    public void makeExpenseEducationBar(JPanel bar) {
        JPanel educationBar = new JPanel();
        ArrayList<Double> categoryPercentages = budget.getExpenseList().getDistributionPercentages();
        educationBar.setPreferredSize(new Dimension((int) (DISTRIBUTION_BAR_WIDTH * categoryPercentages.get(11)), 
                DISTRIBUTION_BAR_HEIGHT));
        educationBar.setBackground(EDUCATION_CATEGORY_COLOUR);
        educationBar.setBorder(null);
        bar.add(educationBar);
    }

    // REQUIRES: bar has DISTRIBUTION_BAR_DIMENSIONS
    // MODIFIES: bar
    // EFFECTS: adds the expense membership bar to the given bar
    public void makeExpenseMembershipBar(JPanel bar) {
        JPanel membershipBar = new JPanel();
        ArrayList<Double> categoryPercentages = budget.getExpenseList().getDistributionPercentages();
        membershipBar.setPreferredSize(new Dimension((int) (DISTRIBUTION_BAR_WIDTH * categoryPercentages.get(12)), 
                DISTRIBUTION_BAR_HEIGHT));
        membershipBar.setBackground(MEMBERSHIP_CATEGORY_COLOUR);
        membershipBar.setBorder(null);
        bar.add(membershipBar);
    }

    // REQUIRES: bar has DISTRIBUTION_BAR_DIMENSIONS
    // MODIFIES: bar
    // EFFECTS: adds the expense savings bar to the given bar
    public void makeExpenseSavingsBar(JPanel bar) {
        JPanel savingsBar = new JPanel();
        ArrayList<Double> categoryPercentages = budget.getExpenseList().getDistributionPercentages();
        savingsBar.setPreferredSize(new Dimension((int) (DISTRIBUTION_BAR_WIDTH * categoryPercentages.get(13)), 
                DISTRIBUTION_BAR_HEIGHT));
        savingsBar.setBackground(SAVINGS_CATEGORY_COLOUR);
        savingsBar.setBorder(null);
        bar.add(savingsBar);
    }

    // REQUIRES: bar has DISTRIBUTION_BAR_DIMENSIONS
    // MODIFIES: bar
    // EFFECTS: adds the expense other bar to the given bar
    public void makeExpenseOtherBar(JPanel bar) {
        JPanel otherBar = new JPanel();
        ArrayList<Double> categoryPercentages = budget.getExpenseList().getDistributionPercentages();
        otherBar.setPreferredSize(new Dimension((int) (DISTRIBUTION_BAR_WIDTH * categoryPercentages.get(14)), 
                DISTRIBUTION_BAR_HEIGHT));
        otherBar.setBackground(OTHER_CATEGORY_COLOUR);
        otherBar.setBorder(null);
        bar.add(otherBar);
    }

    // MODIFIES: distribution
    // EFFECTS: returns and adds the expense category directory to 
    //          the given distribution panel
    public JPanel makeExpenseCategoryDirectory(JPanel distribution) {
        JPanel directory = new JPanel();
        directory.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 10));
        directory.setPreferredSize(DIRECTORY_DIMENSIONS);
        directory.setBorder(null);

        makeExpenseFoodLabel(directory);
        makeExpenseEntertainmentLabel(directory);
        makeExpensePersonalCareLabel(directory);
        makeExpenseGiftLabel(directory);
        makeExpenseDonationLabel(directory);
        makeExpenseTransportationLabel(directory);
        makeExpenseInsuranceLabel(directory);
        makeExpenseMedicalLabel(directory);
        makeExpenseTravelLabel(directory);
        makeExpenseUtilitiesLabel(directory);
        makeExpenseHousingLabel(directory);
        makeExpenseEducationLabel(directory);
        makeExpenseMembershipLabel(directory);
        makeExpenseSavingsLabel(directory);
        makeExpenseOtherLabel(directory);

        distribution.add(directory);

        return directory;
    }

    // MODIFIES: panel
    // EFFECTS: adds the expense food category label to the given panel
    public void makeExpenseFoodLabel(JPanel panel) {
        JPanel foodLabel = new JPanel();
        foodLabel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JPanel icon = new JPanel();
        icon.setBackground(FOOD_CATEGORY_COLOUR);
        foodLabel.add(icon);
        
        ArrayList<Double> categoryPercentages = budget.getExpenseList().getDistributionPercentages();
        JLabel label = new JLabel("Food - " + String.format("%.1f", categoryPercentages.get(0) * 100) + "%");
        label.setFont(NAV_BUTTON_FONT);
        foodLabel.add(label);

        panel.add(foodLabel);
    }

    // MODIFIES: panel
    // EFFECTS: adds the expense entertainment category label to the given panel
    public void makeExpenseEntertainmentLabel(JPanel panel) {
        JPanel entertainmentLabel = new JPanel();
        entertainmentLabel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JPanel icon = new JPanel();
        icon.setBackground(ENTERTAINMENT_CATEGORY_COLOUR);
        entertainmentLabel.add(icon);
        
        ArrayList<Double> categoryPercentages = budget.getExpenseList().getDistributionPercentages();
        JLabel label = new JLabel("Entertainment - " + String.format("%.1f", categoryPercentages.get(1) * 100) + "%");
        label.setFont(NAV_BUTTON_FONT);
        entertainmentLabel.add(label);

        panel.add(entertainmentLabel);
    }

    // MODIFIES: panel
    // EFFECTS: adds the expense personal care category label to the given panel
    public void makeExpensePersonalCareLabel(JPanel panel) {
        JPanel personalCareLabel = new JPanel();
        personalCareLabel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JPanel icon = new JPanel();
        icon.setBackground(PERSONALCARE_CATEGORY_COLOUR);
        personalCareLabel.add(icon);
        
        ArrayList<Double> categoryPercentages = budget.getExpenseList().getDistributionPercentages();
        JLabel label = new JLabel("Personal Care - " + String.format("%.1f", categoryPercentages.get(2) * 100) + "%");
        label.setFont(NAV_BUTTON_FONT);
        personalCareLabel.add(label);

        panel.add(personalCareLabel);
    }

    // MODIFIES: panel
    // EFFECTS: adds the expense gift category label to the given panel
    public void makeExpenseGiftLabel(JPanel panel) {
        JPanel giftLabel = new JPanel();
        giftLabel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JPanel icon = new JPanel();
        icon.setBackground(GIFT_CATEGORY_COLOUR);
        giftLabel.add(icon);
        
        ArrayList<Double> categoryPercentages = budget.getExpenseList().getDistributionPercentages();
        JLabel label = new JLabel("Gift - " + String.format("%.1f", categoryPercentages.get(3) * 100) + "%");
        label.setFont(NAV_BUTTON_FONT);
        giftLabel.add(label);

        panel.add(giftLabel);
    }

    // MODIFIES: panel
    // EFFECTS: adds the expense donation category label to the given panel
    public void makeExpenseDonationLabel(JPanel panel) {
        JPanel donationLabel = new JPanel();
        donationLabel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JPanel icon = new JPanel();
        icon.setBackground(DONATION_CATEGORY_COLOUR);
        donationLabel.add(icon);
        
        ArrayList<Double> categoryPercentages = budget.getExpenseList().getDistributionPercentages();
        JLabel label = new JLabel("Donation - " + String.format("%.1f", categoryPercentages.get(4) * 100) + "%");
        label.setFont(NAV_BUTTON_FONT);
        donationLabel.add(label);

        panel.add(donationLabel);
    }

    // MODIFIES: panel
    // EFFECTS: adds the expense transportation category label to the given panel
    public void makeExpenseTransportationLabel(JPanel panel) {
        JPanel transportationLabel = new JPanel();
        transportationLabel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JPanel icon = new JPanel();
        icon.setBackground(TRANSPORTATION_CATEGORY_COLOUR);
        transportationLabel.add(icon);
        
        ArrayList<Double> categoryPercentages = budget.getExpenseList().getDistributionPercentages();
        JLabel label = new JLabel("Transportation - " + String.format("%.1f", categoryPercentages.get(5) * 100) + "%");
        label.setFont(NAV_BUTTON_FONT);
        transportationLabel.add(label);

        panel.add(transportationLabel);
    }

    // MODIFIES: panel
    // EFFECTS: adds the expense insurance category label to the given panel
    public void makeExpenseInsuranceLabel(JPanel panel) {
        JPanel insuranceLabel = new JPanel();
        insuranceLabel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JPanel icon = new JPanel();
        icon.setBackground(INSURANCE_CATEGORY_COLOUR);
        insuranceLabel.add(icon);
        
        ArrayList<Double> categoryPercentages = budget.getExpenseList().getDistributionPercentages();
        JLabel label = new JLabel("Insurance - " + String.format("%.1f", categoryPercentages.get(6) * 100) + "%");
        label.setFont(NAV_BUTTON_FONT);
        insuranceLabel.add(label);

        panel.add(insuranceLabel);
    }

    // MODIFIES: panel
    // EFFECTS: adds the expense medical category label to the given panel
    public void makeExpenseMedicalLabel(JPanel panel) {
        JPanel medicalLabel = new JPanel();
        medicalLabel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JPanel icon = new JPanel();
        icon.setBackground(MEDICAL_CATEGORY_COLOUR);
        medicalLabel.add(icon);
        
        ArrayList<Double> categoryPercentages = budget.getExpenseList().getDistributionPercentages();
        JLabel label = new JLabel("Medical - " + String.format("%.1f", categoryPercentages.get(7) * 100) + "%");
        label.setFont(NAV_BUTTON_FONT);
        medicalLabel.add(label);

        panel.add(medicalLabel);
    }

    // MODIFIES: panel
    // EFFECTS: adds the expense travel category label to the given panel
    public void makeExpenseTravelLabel(JPanel panel) {
        JPanel travelLabel = new JPanel();
        travelLabel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JPanel icon = new JPanel();
        icon.setBackground(TRAVEL_CATEGORY_COLOUR);
        travelLabel.add(icon);
        
        ArrayList<Double> categoryPercentages = budget.getExpenseList().getDistributionPercentages();
        JLabel label = new JLabel("Travel - " + String.format("%.1f", categoryPercentages.get(8) * 100) + "%");
        label.setFont(NAV_BUTTON_FONT);
        travelLabel.add(label);

        panel.add(travelLabel);
    }

    // MODIFIES: panel
    // EFFECTS: adds the expense utilities category label to the given panel
    public void makeExpenseUtilitiesLabel(JPanel panel) {
        JPanel utilitiesLabel = new JPanel();
        utilitiesLabel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JPanel icon = new JPanel();
        icon.setBackground(UTILITIES_CATEGORY_COLOUR);
        utilitiesLabel.add(icon);
        
        ArrayList<Double> categoryPercentages = budget.getExpenseList().getDistributionPercentages();
        JLabel label = new JLabel("Utilities - " + String.format("%.1f", categoryPercentages.get(9) * 100) + "%");
        label.setFont(NAV_BUTTON_FONT);
        utilitiesLabel.add(label);

        panel.add(utilitiesLabel);
    }

    // MODIFIES: panel
    // EFFECTS: adds the expense housing category label to the given panel
    public void makeExpenseHousingLabel(JPanel panel) {
        JPanel housingLabel = new JPanel();
        housingLabel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JPanel icon = new JPanel();
        icon.setBackground(HOUSING_CATEGORY_COLOUR);
        housingLabel.add(icon);
        
        ArrayList<Double> categoryPercentages = budget.getExpenseList().getDistributionPercentages();
        JLabel label = new JLabel("Housing - " + String.format("%.1f", categoryPercentages.get(10) * 100) + "%");
        label.setFont(NAV_BUTTON_FONT);
        housingLabel.add(label);

        panel.add(housingLabel);
    }

    // MODIFIES: panel
    // EFFECTS: adds the expense education category label to the given panel
    public void makeExpenseEducationLabel(JPanel panel) {
        JPanel educationLabel = new JPanel();
        educationLabel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JPanel icon = new JPanel();
        icon.setBackground(EDUCATION_CATEGORY_COLOUR);
        educationLabel.add(icon);
        
        ArrayList<Double> categoryPercentages = budget.getExpenseList().getDistributionPercentages();
        JLabel label = new JLabel("Education - " + String.format("%.1f", categoryPercentages.get(11) * 100) + "%");
        label.setFont(NAV_BUTTON_FONT);
        educationLabel.add(label);

        panel.add(educationLabel);
    }

    // MODIFIES: panel
    // EFFECTS: adds the expense membership category label to the given panel
    public void makeExpenseMembershipLabel(JPanel panel) {
        JPanel membershipLabel = new JPanel();
        membershipLabel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JPanel icon = new JPanel();
        icon.setBackground(MEMBERSHIP_CATEGORY_COLOUR);
        membershipLabel.add(icon);
        
        ArrayList<Double> categoryPercentages = budget.getExpenseList().getDistributionPercentages();
        JLabel label = new JLabel("Membership - " + String.format("%.1f", categoryPercentages.get(12) * 100) + "%");
        label.setFont(NAV_BUTTON_FONT);
        membershipLabel.add(label);

        panel.add(membershipLabel);
    }

    // MODIFIES: panel
    // EFFECTS: adds the expense savings category label to the given panel
    public void makeExpenseSavingsLabel(JPanel panel) {
        JPanel savingsLabel = new JPanel();
        savingsLabel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JPanel icon = new JPanel();
        icon.setBackground(SAVINGS_CATEGORY_COLOUR);
        savingsLabel.add(icon);
        
        ArrayList<Double> categoryPercentages = budget.getExpenseList().getDistributionPercentages();
        JLabel label = new JLabel("Savings - " + String.format("%.1f", categoryPercentages.get(13) * 100) + "%");
        label.setFont(NAV_BUTTON_FONT);
        savingsLabel.add(label);

        panel.add(savingsLabel);
    }

    // MODIFIES: panel
    // EFFECTS: adds the expense other category label to the given panel
    public void makeExpenseOtherLabel(JPanel panel) {
        JPanel otherLabel = new JPanel();
        otherLabel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JPanel icon = new JPanel();
        icon.setBackground(OTHER_CATEGORY_COLOUR);
        otherLabel.add(icon);
        
        ArrayList<Double> categoryPercentages = budget.getExpenseList().getDistributionPercentages();
        JLabel label = new JLabel("Other - " + String.format("%.1f", categoryPercentages.get(14) * 100) + "%");
        label.setFont(NAV_BUTTON_FONT);
        otherLabel.add(label);

        panel.add(otherLabel);
    }

    // MODIFIES: button
    // EFFECTS: adds an ActionListener to the given button that initiates runIndividualTaxPopUp()  
    public void initializeIndividualTaxButtonClick(AbstractButton button) {
        button.addActionListener(new ActionListener() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void actionPerformed(ActionEvent event) {
                if (budget.getIncomeList().getSize() == 0) {
                    runNoIncomePopUp();
                } else {
                    runIndividualTaxPopUp();
                }
            }
        });
    }

    // EFFECTS: displays the individual tax pop up 
    public void runIndividualTaxPopUp() {
        JFrame individualTaxPopUp = makePopUp("Calculate Annual Individual Tax!", 700, 450);
        SpringLayout layout = new SpringLayout();
        individualTaxPopUp.setLayout(layout);
        individualTaxPopUp.setLocationRelativeTo(individualTaxPopUp);
        JPanel incomeSelectorPrompt = makeIncomeSelectorPrompt(individualTaxPopUp);
        JComboBox incomeField = (JComboBox) incomeSelectorPrompt.getComponent(1);
        JButton doneButton = makeDoneButton(individualTaxPopUp);
        initializeIndividualTaxDoneClick(incomeField, doneButton, individualTaxPopUp);
        individualTaxPopUp.getRootPane().setDefaultButton(doneButton);

        layout.putConstraint(SpringLayout.SOUTH, doneButton, 0, 
                SpringLayout.SOUTH, individualTaxPopUp.getContentPane());
        layout.putConstraint(SpringLayout.EAST, doneButton, 0, SpringLayout.EAST, individualTaxPopUp.getContentPane());

        individualTaxPopUp.setVisible(true);
    }    

    // MODIFIES: button, popUp
    // EFFECTS: adds an ActionListener to the given button that 
    //          disables the visibility of the given popUp, and then initiates runIndividualTaxResultsPopUp()
    public void initializeIndividualTaxDoneClick(JComboBox incomeInput, JButton button, JFrame popUp) {
        button.addActionListener(new ActionListener() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void actionPerformed(ActionEvent event) {
                String incomeName = incomeInput.getSelectedItem().toString();
                HashMap<String, Integer> incomeList = budget.getIncomeList().getIncomeDescriptions();
                Income item = budget.getIncomeList().getItem(incomeList.get(incomeName));
                popUp.setVisible(false);
                runIndividualTaxResultsPopUp(item);
            }
        });
    }

    @SuppressWarnings("methodlength")
    // EFFECTS: displays the individual tax results pop up window
    public void runIndividualTaxResultsPopUp(Income item) {
        JFrame projectionResultsPopUp = makePopUp("Individual Annual Tax Results!", 700, 250);
        SpringLayout layout = new SpringLayout();
        projectionResultsPopUp.setLayout(layout);
        projectionResultsPopUp.setLocationRelativeTo(projectionResultsPopUp);
        JLabel provincialTaxResults = makeIndividualProvincialTaxResultsMessage(item, projectionResultsPopUp);
        JLabel federalTaxResults = makeIndividualFederalTaxResultsMessage(item, projectionResultsPopUp);
        JLabel totalTaxResults = makeIndividualTotalTaxResultsMessage(item, projectionResultsPopUp);
        JButton okButton = makeOkButton(projectionResultsPopUp);
        projectionResultsPopUp.getRootPane().setDefaultButton(okButton);
        layout.putConstraint(SpringLayout.NORTH, provincialTaxResults, 20, 
                SpringLayout.NORTH, projectionResultsPopUp.getContentPane());
        layout.putConstraint(SpringLayout.WEST, provincialTaxResults, 20, 
                SpringLayout.WEST, projectionResultsPopUp.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, federalTaxResults, 20, SpringLayout.SOUTH, provincialTaxResults);
        layout.putConstraint(SpringLayout.WEST, federalTaxResults, 20, 
                SpringLayout.WEST, projectionResultsPopUp.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, totalTaxResults, 20, SpringLayout.SOUTH, federalTaxResults);
        layout.putConstraint(SpringLayout.WEST, totalTaxResults, 20, 
                SpringLayout.WEST, projectionResultsPopUp.getContentPane());
        layout.putConstraint(SpringLayout.EAST, okButton, 0, 
                SpringLayout.EAST, projectionResultsPopUp.getContentPane());
        layout.putConstraint(SpringLayout.SOUTH, okButton, 0, 
                SpringLayout.SOUTH, projectionResultsPopUp.getContentPane());
        projectionResultsPopUp.setVisible(true);
    }

    // MODIFIES: popUp
    // EFFECTS: returns the individual provincial tax results message panel and adds it to the given popUp
    public JLabel makeIndividualProvincialTaxResultsMessage(Income item, JFrame popUp) {
        String provincial = String.format("$%.2f", item.calculateProvincialTax());
        JLabel provincialResults = new JLabel("Provincial Tax: " + provincial);
        provincialResults.setFont(NAV_BUTTON_FONT);
        popUp.add(provincialResults);

        return provincialResults;
    }

    // MODIFIES: popUp
    // EFFECTS: returns the individual federal tax results message panel and adds it to the given popUp
    public JLabel makeIndividualFederalTaxResultsMessage(Income item, JFrame popUp) {
        String federal = String.format("$%.2f", item. calculateFederalTax());
        JLabel federalResults = new JLabel("Federal Tax: " + federal);
        federalResults.setFont(NAV_BUTTON_FONT);
        popUp.add(federalResults);

        return federalResults;
    }

    // MODIFIES: popUp
    // EFFECTS: returns the individual total tax results message panel and adds it to the given popUp
    public JLabel makeIndividualTotalTaxResultsMessage(Income item, JFrame popUp) {
        String total = String.format("$%.2f", item.calculateProvincialTax() + item.calculateFederalTax());
        JLabel totalResults = new JLabel("Total Tax: " + total);
        totalResults.setFont(NAV_BUTTON_FONT);
        popUp.add(totalResults);

        return totalResults;
    }

    // MODIFIES: button
    // EFFECTS: adds an ActionListener to the given button that initiates runOverallTaxPopUp()  
    public void initializeOverallTaxButtonClick(AbstractButton button) {
        button.addActionListener(new ActionListener() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void actionPerformed(ActionEvent event) {
                runOverallTaxPopUp();
            }
        });
    }

    @SuppressWarnings("methodlength")
    // EFFECTS: displays the overall tax pop up 
    public void runOverallTaxPopUp() {
        JFrame overallTaxPopUp = makePopUp("Calculate Annual Overall Tax!", 700, 250);
        SpringLayout layout = new SpringLayout();
        overallTaxPopUp.setLayout(layout);
        overallTaxPopUp.setLocationRelativeTo(overallTaxPopUp);
        JLabel provincialTaxResults = makeOverallProvincialTaxResultsMessage(overallTaxPopUp);
        JLabel federalTaxResults = makeOverallFederalTaxResultsMessage(overallTaxPopUp);
        JLabel totalTaxResults = makeOverallTotalTaxResultsMessage(overallTaxPopUp);
        JButton okButton = makeOkButton(overallTaxPopUp);
        overallTaxPopUp.getRootPane().setDefaultButton(okButton);
        layout.putConstraint(SpringLayout.NORTH, provincialTaxResults, 20, 
                SpringLayout.NORTH, overallTaxPopUp.getContentPane());
        layout.putConstraint(SpringLayout.WEST, provincialTaxResults, 20, 
                SpringLayout.WEST, overallTaxPopUp.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, federalTaxResults, 20, 
                SpringLayout.SOUTH, provincialTaxResults);
        layout.putConstraint(SpringLayout.WEST, federalTaxResults, 20, 
                SpringLayout.WEST, overallTaxPopUp.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, totalTaxResults, 20, 
                SpringLayout.SOUTH, federalTaxResults);
        layout.putConstraint(SpringLayout.WEST, totalTaxResults, 20, 
                SpringLayout.WEST, overallTaxPopUp.getContentPane());
        layout.putConstraint(SpringLayout.SOUTH, okButton, 0, SpringLayout.SOUTH, overallTaxPopUp.getContentPane());
        layout.putConstraint(SpringLayout.EAST, okButton, 0, SpringLayout.EAST, overallTaxPopUp.getContentPane());
        overallTaxPopUp.setVisible(true);
    }    

    // MODIFIES: popUp
    // EFFECTS: returns the overall provincial tax results message panel and adds it to the given popUp
    public JLabel makeOverallProvincialTaxResultsMessage(JFrame popUp) {
        String provincial = String.format("$%.2f", budget.getIncomeList().calculateProvincialTax());
        JLabel provincialResults = new JLabel("Provincial Tax: " + provincial);
        provincialResults.setFont(NAV_BUTTON_FONT);
        popUp.add(provincialResults);

        return provincialResults;
    }

    // MODIFIES: popUp
    // EFFECTS: returns the overall federal tax results message panel and adds it to the given popUp
    public JLabel makeOverallFederalTaxResultsMessage(JFrame popUp) {
        String federal = String.format("$%.2f", budget.getIncomeList().calculateFederalTax());
        JLabel federalResults = new JLabel("Federal Tax: " + federal);
        federalResults.setFont(NAV_BUTTON_FONT);
        popUp.add(federalResults);

        return federalResults;
    }

    // MODIFIES: popUp
    // EFFECTS: returns the overall total tax results message panel and adds it to the given popUp
    public JLabel makeOverallTotalTaxResultsMessage(JFrame popUp) {
        String total = String.format("$%.2f", budget.getIncomeList().calculateProvincialTax() 
                + budget.getIncomeList().calculateFederalTax());
        JLabel totalResults = new JLabel("Total Tax: " + total);
        totalResults.setFont(NAV_BUTTON_FONT);
        popUp.add(totalResults);

        return totalResults;
    }

    // MODIFIES: button, page
    // EFFECTS: adds an ActionListener to the given button that disables the visibility of
    //          page and runs displayWelcomePage()
    public void initializeMenuClick(AbstractButton button, JFrame page) {
        button.addActionListener(new ActionListener() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void actionPerformed(ActionEvent event) {
                page.setVisible(false);
                displayWelcomePage();
            }
        });
    }

    // MODIFIES: button
    // EFFECTS: adds an ActionListener to the given button that quits the program/app
    //          and prints the EventLog's contents to the console
    public void initializeQuitClick(AbstractButton button) {
        button.addActionListener(new ActionListener() {
            @ExcludeFromJacocoGeneratedReport
            @Override
            public void actionPerformed(ActionEvent event) {
                for (Event e : EventLog.getInstance()) {
                    System.out.println(e.toString() + "\n");
                }
                System.exit(0);
            }
        });
    }

    // MODIFIES: page
    // EFFECTS: returns the budget manager page title and adds it to the given page
    public JLabel makeBudgetManagerTitle(JFrame page) {
        JLabel title = new JLabel(budget.getName() + "'s Budget - " + budget.getMonth() + " " + budget.getYear());
        title.setFont(BUDGET_MANAGER_PAGE_TITLE_FONT);
        page.add(title);

        return title;
    }

    // MODIFIES: page
    // EFFECTS: returns the budget manager page budget scroller and adds it to the given page
    public JScrollPane makeBudgetScroller(JFrame page) {
        JScrollPane scroller = new JScrollPane(makeBudget());
        scroller.setPreferredSize(BUDGET_DIMENSIONS);
        page.add(scroller);

        return scroller;
    }

    // MODIFIES: scroller
    // EFFECTS: returns the budget table
    public JTable makeBudget() {
        JTable budget = initializeBudget();
        addMoneyItems(budget);

        return budget;
    }

    // EFFECTS: creates a JTable of this GUI's budget
    public JTable initializeBudget() {
        String[] columnNames = {"", "Description", "Category", "Frequency", "Value"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        model.addRow(new Object[] {"  INCOME:", null, null, null, null});
        model.addRow(new Object[] {"  Total Income:", null, null, null, 
                String.format("  $%.2f", budget.getIncomeList().getTotal())});
        model.addRow(new Object[] {"  EXPENSES:", null, null, null, null});
        model.addRow(new Object[] {"  Total Expenses:", null, null, null, 
                String.format("  $%.2f", budget.getExpenseList().getTotal())});
        model.addRow(new Object[] {"  NET TOTAL:", null, null, null, 
                String.format("  $%.2f", budget.getNetTotal())});
        JTable budget = new JTable(model) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        budget.setRowHeight(NAV_BUTTON_HEIGHT);
        budget.setFont(NAV_BUTTON_FONT);

        return budget;
    }

    // REQUIRES: budgetTable cannot currently contains rows for MoneyItems
    // MODIFIES: budgetTable
    // EFFECTS: adds rows to budgetTable for all the MoneyItems in budget
    public void addMoneyItems(JTable budgetTable) {
        IncomeList incomeItems = budget.getIncomeList();
        for (int i = 0; i < incomeItems.getSize(); i++) {
            Income curI = incomeItems.getItem(i);
            Object[] curIDetails = {"", "  " + curI.getDescription(), "  " + curI.getCategory(), 
                    "  " + curI.getFrequency(), String.format("  $%.2f", curI.getValue())};
            ((DefaultTableModel) budgetTable.getModel()).insertRow(1 + i, curIDetails);
        }

        ExpenseList expenseItems = budget.getExpenseList();
        for (int i = 0; i < expenseItems.getSize(); i++) {
            Expense curE = expenseItems.getItem(i);
            Object[] curEDetails = {"", "  " + curE.getDescription(), "  " + curE.getCategory(), 
                    "  " + curE.getFrequency(), String.format("  $%.2f", curE.getValue())};
            ((DefaultTableModel) budgetTable.getModel()).insertRow(budget.getIncomeList().getSize()
                    + 3 + i, curEDetails);
        }
    }
}
