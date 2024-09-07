import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;

public class SearchView {
    private final Menu menu;

    private double minPrice, maxPrice;
    private String tomatoChoice, pickleChoice, cucumberChoice, bunChoice;
    private JLabel feedBack;
    private Meat meatChoice;
    private Dressing dressingChoice;
    private JCheckBox cheeseChkBox;
    private boolean cheeseStatus;
    private Set<String> leafyGreensSet;
    private Set<Sauce> sauceSet;
    private Map<Filter, Object> filterMap;

    public SearchView(Menu _menu){
        this.menu = _menu;
        this.minPrice = 0;
        this.maxPrice = menu.getMaxPrice();
        this.filterMap = new HashMap<>();
    }

    /**
     * A method that gathers all other JPanels into one JPanel for displaying in the main JFrame
     * @return - A JPanel
     */
    public JPanel fixedOptionsView(){
        // Create fixed panel ready for return
        JPanel fixed = new JPanel();
        fixed.setLayout(new BorderLayout());
        fixed.setVisible(true);
        fixed.setPreferredSize(new Dimension(600, 293));

        // Create panel for left side
        JPanel left = new JPanel();
        left.setVisible(true);
        left.setPreferredSize(new Dimension(300, 125));

        // Add left panel options
        left.add(Box.createRigidArea(new Dimension(300, 30)));
        left.add(getCustomRadioChoicePanel("Tomato?"));
        left.add(Box.createRigidArea(new Dimension(300, 15)));
        left.add(getCustomRadioChoicePanel("Pickles?"));

        // Create panel for right ride
        JPanel right = new JPanel();
        right.setVisible(true);
        right.setPreferredSize(new Dimension(300, 125));

        // Add right panel options
        right.add(Box.createRigidArea(new Dimension(300, 30)));
        right.add(getMeatChoicePanel());
        right.add(Box.createRigidArea(new Dimension(300, 10)));
        right.add(getCheeseChoicePanel());

        // Set up the search button
        JButton searchBtn = new JButton("Search");
        searchBtn.setPreferredSize(new Dimension(590, 30));
        searchBtn.addActionListener(e -> getChoices());

        // Set up feedback label
        this.feedBack = new JLabel("");
        this.feedBack.setForeground(Color.red);
        this.feedBack.setPreferredSize(new Dimension(300, 15));
        this.feedBack.setHorizontalTextPosition(SwingConstants.CENTER);

        // Create panel for the bottom
        JPanel bottom = new JPanel();
        bottom.setPreferredSize(new Dimension(300, 150));

        // Add bottom panel options
        bottom.add(Box.createRigidArea(new Dimension(600, 15)));
        bottom.add(getMinMaxPricePanel());
        bottom.add(Box.createRigidArea(new Dimension(600, 5)));
        bottom.add(this.feedBack);
        bottom.add(Box.createRigidArea(new Dimension(600, 5)));
        bottom.add(searchBtn);

        // Add left, right and bottom panels to the fixed panel
        fixed.add(left, BorderLayout.WEST);
        fixed.add(right, BorderLayout.EAST);
        fixed.add(bottom, BorderLayout.SOUTH);
        return fixed;
    }

    /**
     * A method the creates a JPanel offering a choice of meats through the use of a JComboBox
     * and the Meat enums values
     * @return - A JPanel
     */
    private JPanel getMeatChoicePanel(){
        JPanel meatPanel = new JPanel();
        meatPanel.setPreferredSize(new Dimension(300, 40));
        JLabel meatLbl = new JLabel("Meat:");
        JComboBox<Meat> meatOptionsCBox = new JComboBox<>(Meat.values());
        meatOptionsCBox.setSelectedItem(Meat.SELECT);
        meatOptionsCBox.addItemListener(e -> this.meatChoice = (Meat)e.getItem());
        meatPanel.add(meatLbl);
        meatPanel.add(meatOptionsCBox);
        return meatPanel;
    }

    /**
     * A method to be used in conjunction with the search button. Used to fetching the selected items
     * for each choice available.
     */
    public void getChoices(){
        System.out.println("meat: " + getMeatChoice());
        System.out.println("pickle: " + getPickleChoice());
        System.out.println("tomato: " + getTomatoChoice());
        System.out.println("Cheese: " + getCheeseStatus());
        System.out.println("min price: " + getMinPrice());
        System.out.println("max price: " + getMaxPrice());
        System.out.println("cucumber: " + getCucumberChoice());
        System.out.println("bun: " + getBunChoice());
        System.out.println("dressing: " + getDressingChoice());
    }

    /**
     * A method that creates a JPanel that shows a JCheckBox for a yes/no option for whether cheese is
     * required or not.
     * @return - A JPanel
     */
    private JPanel getCheeseChoicePanel(){
        JPanel cheeseChoice = new JPanel();
        cheeseChoice.setPreferredSize(new Dimension(300, 25));
        this.cheeseChkBox = new JCheckBox();
        this.cheeseChkBox.addChangeListener(e -> this.cheeseStatus = this.cheeseChkBox.isSelected());
        JLabel cheeseLbl = new JLabel("Cheese?");
        cheeseChoice.add(cheeseLbl);
        cheeseChoice.add(this.cheeseChkBox);
        return cheeseChoice;
    }

    /**
     * A method that returns a panel containing a customisable buttongroup that contains a JRadioButton
     * for each of the following options: yes, no and I don't mind. By passing in a description of what the
     * panel is for, this panel can be used for multiple search options.
     * @param description - A String containing the search description to be attached to the JLabel
     * @return - A JPanel
     */
    private JPanel getCustomRadioChoicePanel(String description){
        JPanel radioPanel = new JPanel();
        radioPanel.setPreferredSize(new Dimension(300, 30));
        JLabel descriptionLabel = new JLabel(description);
        JRadioButton yesBtn = new JRadioButton("Yes");
        yesBtn.setActionCommand("Yes");
        JRadioButton noBtn = new JRadioButton("No");
        noBtn.setActionCommand("No");
        JRadioButton naBtn = new JRadioButton("I don't mind");
        naBtn.setActionCommand("NA");
        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(yesBtn);
        btnGroup.add(noBtn);
        btnGroup.add(naBtn);
        ActionListener listener = e -> {
            if(description.equalsIgnoreCase("Tomato?")) {
                this.tomatoChoice = btnGroup.getSelection().getActionCommand();
            } else if(description.equalsIgnoreCase("Pickles?")){
                this.pickleChoice = btnGroup.getSelection().getActionCommand();
            } else if(description.equalsIgnoreCase("Cucumber?")){
                this.cucumberChoice = btnGroup.getSelection().getActionCommand();
            }
        };
        yesBtn.addActionListener(listener);
        noBtn.addActionListener(listener);
        naBtn.addActionListener(listener);
        radioPanel.setAlignmentX(0);
        radioPanel.add(Box.createRigidArea(new Dimension(25, 0)));
        radioPanel.add(descriptionLabel);
        radioPanel.add(yesBtn);
        radioPanel.add(noBtn);
        radioPanel.add(naBtn);
        return radioPanel;
    }

    /**
     * Creates a JPanel that offers the choice for min and max prices to the user in the form of JTextFields.
     * This code has been taken and adjusted from the code base of tutorial 10
     * @return - A JPanel
     */
    private JPanel getMinMaxPricePanel(){
        // Set up JPanel
        JPanel minMaxPricePanel = new JPanel();
        minMaxPricePanel.setAlignmentX(0);
        minMaxPricePanel.setLayout(new FlowLayout());
        minMaxPricePanel.setPreferredSize(new Dimension(600, 50));
        // Set up min price textfield and label
        JTextField minPriceTxtF = new JTextField(10);
        minPriceTxtF.setText("0.00");
        JLabel minPriceLbl = new JLabel("Min Price:   $");

        JTextField maxPriceTxtF = new JTextField(10);
        String maxPriceString = String.format("%.2f", this.maxPrice);
        maxPriceTxtF.setText(String.valueOf(maxPriceString));
        JLabel maxPriceLbl = new JLabel("Max Price:   $");
        // Set document listener for minimum price textfield
        minPriceTxtF.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!checkMinPrice(minPriceTxtF)) minPriceTxtF.requestFocusInWindow();
                checkMaxPrice(maxPriceTxtF);

            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!checkMinPrice(minPriceTxtF)) minPriceTxtF.requestFocusInWindow();
                checkMaxPrice(maxPriceTxtF);

            }
            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        // Set document listener for max price textfield
        maxPriceTxtF.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!checkMaxPrice(maxPriceTxtF)) maxPriceTxtF.requestFocusInWindow();
                checkMinPrice(minPriceTxtF);

            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!checkMaxPrice(maxPriceTxtF)) maxPriceTxtF.requestFocusInWindow();
                checkMinPrice(minPriceTxtF);
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        minMaxPricePanel.add(minPriceLbl);
        minMaxPricePanel.add(minPriceTxtF);
        minMaxPricePanel.add(Box.createRigidArea(new Dimension(15, 0)));
        minMaxPricePanel.add(maxPriceLbl);
        minMaxPricePanel.add(maxPriceTxtF);
        return minMaxPricePanel;
    }

    /**
     * A method to check the min price field, validating that a number was entered and that the number
     * was above 0 and less than max.
     * @param minPriceTxt - A JTextField containing the min price value as input by user
     * @return - A boolean value
     */
    private boolean checkMinPrice(JTextField minPriceTxt){
        try {
            double min = Double.parseDouble(minPriceTxt.getText());
            System.out.println(min);
            System.out.println(this.maxPrice);
            if (min < 0 || min > this.maxPrice) {
                this.feedBack.setText("Minimum price must be more than 0 and less than the maximum price");
                return false;
            } else {
                this.minPrice = min;
                this.feedBack.setText(" ");
                return true;
            }
        } catch (NumberFormatException nf) {
            this.feedBack.setText("Min price input was not a number, please try again");
            return false;
        }
    }

    /**
     * A method to check that the value in the max price textfield is a number and is not less
     * than the minimum price value.
     * @param maxPriceTxt - A JTextField containing the max price value as input by user
     * @return - A boolean
     */
    private boolean checkMaxPrice(JTextField maxPriceTxt){
        try {
            double max = Double.parseDouble(maxPriceTxt.getText());
            System.out.println(max);
            System.out.println(this.minPrice);
            if (max < this.minPrice) {
                this.feedBack.setText("Maximum price must be more than the minimum price");
                return false;
            } else {
                this.maxPrice = max;
                this.feedBack.setText(" ");
                return true;
            }
        } catch (NumberFormatException nf){
            this.feedBack.setText("Max price input was not a number, please try again");
            return false;
        }
    }

    /**
     * A method to display the options for a salad item if Salad is chosen from the type selection
     * combo box
     * @return - A JPanel
     */
    public JPanel saladOptionsView(){
        // Set up main and sub salad panels
        JPanel saladPanel = new JPanel();
        saladPanel.setPreferredSize(new Dimension(600, 200));
        saladPanel.setLayout(new BorderLayout());
        JPanel left = new JPanel();
        left.setPreferredSize(new Dimension(300, 200));
        JPanel right = new JPanel();
        right.setPreferredSize(new Dimension(300, 200));

        // Set up the list for multi selection of leafy greens
        JPanel leafyGreenPanel = new JPanel();
        leafyGreenPanel.setPreferredSize(new Dimension(300, 200));
        leafyGreenPanel.setLayout(new FlowLayout());
        this.leafyGreensSet = new HashSet<>();
        JLabel leafyGreenLbl = new JLabel("Please select 1 or more leafy green:");
        JLabel leafyGreenInfoLbl = new JLabel("(Hold ctrl to multi-select leafy greens)");
        JList<Object> allLeafyGreens = new JList<>(menu.getAllIngredientTypes(Filter.LEAFY_GREENS).toArray());
        allLeafyGreens.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        allLeafyGreens.addListSelectionListener(e ->
                this.leafyGreensSet.add((String)allLeafyGreens.getSelectedValue()));
        JScrollPane greensScrollPane = new JScrollPane(allLeafyGreens);
        greensScrollPane.setPreferredSize(new Dimension(200, 100));
        leafyGreenPanel.add(Box.createRigidArea(new Dimension(300, 25)));
        leafyGreenPanel.add(leafyGreenLbl);
        leafyGreenPanel.add(leafyGreenInfoLbl);
        leafyGreenPanel.add(greensScrollPane);

        // Set up radio panel for cucumber selection
        JPanel cucumberPanel = getCustomRadioChoicePanel("Cucumber?");

        // Set up combobox for salad dressing selection
        JPanel dressingPanel = new JPanel();
        dressingPanel.setPreferredSize(new Dimension(300, 50));
        JLabel dressingLbl = new JLabel("Preferred Dressing?");
        JComboBox<Dressing> dressingCBox = new JComboBox<>(Dressing.values());
        dressingCBox.addItemListener(e -> this.dressingChoice = (Dressing) dressingCBox.getSelectedItem());
        dressingPanel.add(Box.createRigidArea(new Dimension(25, 0)));
        dressingPanel.add(dressingLbl);
        dressingPanel.add(dressingCBox);

        // Add all panels to main salad panel
        left.add(Box.createRigidArea(new Dimension(300, 25)));
        left.add(dressingPanel);
        left.add(Box.createRigidArea(new Dimension(300, 25)));
        left.add(cucumberPanel);
        right.add(leafyGreenPanel);
        saladPanel.add(left, BorderLayout.WEST);
        saladPanel.add(right, BorderLayout.EAST);
        return saladPanel;
    }

    /**
     * A method to display the options for a burger item
     * @return - A JPanel
     */
    public JPanel burgerOptionsView(){
        // Set up main and sub salad panels
        JPanel burgerPanel = new JPanel();
        burgerPanel.setVisible(true);
        burgerPanel.setPreferredSize(new Dimension(600, 200));
        burgerPanel.setLayout(new BorderLayout());

        // Set up the list for multi selection of sauces
        JPanel saucePanel = new JPanel();
        saucePanel.setLayout(new FlowLayout());
        saucePanel.setPreferredSize(new Dimension(300, 200));
        JLabel sauceLbl = new JLabel("Please choose one or more sauces?");
        JLabel sauceInfoLbl = new JLabel("(Hold ctrl to multi-select sauces)");
        sauceSet = new HashSet<>();
        JList<Object> allSauces = new JList<>(Sauce.values());
        allSauces.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        allSauces.addListSelectionListener(e -> this.sauceSet.add((Sauce) allSauces.getSelectedValue()));
        JScrollPane sauceScrollPane = new JScrollPane(allSauces);
        sauceScrollPane.setPreferredSize(new Dimension(200, 100));
        saucePanel.add(Box.createRigidArea(new Dimension(300, 25)));
        saucePanel.add(sauceLbl);
        saucePanel.add(sauceInfoLbl);
        saucePanel.add(sauceScrollPane);


        // Set up combobox for bun type selection
        JPanel bunTypePanel = new JPanel();
        bunTypePanel.setPreferredSize(new Dimension(300, 200));
        bunTypePanel.setVisible(true);
        JLabel bunTypeLbl = new JLabel("Preferred bun type?");
        JComboBox<Object> bunTypeCBox = new JComboBox<>(menu.getAllIngredientTypes(Filter.BUN).toArray());
        bunTypeCBox.addItemListener(e -> this.bunChoice = (String) bunTypeCBox.getSelectedItem());
        bunTypePanel.add(Box.createRigidArea(new Dimension(300, 50)));
        bunTypePanel.add(Box.createRigidArea(new Dimension(25, 0)));
        bunTypePanel.add(bunTypeLbl);
        bunTypePanel.add(Box.createRigidArea(new Dimension(25, 0)));
        bunTypePanel.add(bunTypeCBox);

        // Combine panels into main burger panel
        burgerPanel.add(bunTypePanel, BorderLayout.WEST);
        burgerPanel.add(saucePanel, BorderLayout.EAST);
        return burgerPanel;
    }

    /**
     * A method to fill the main window content panel with content for the welcome splash page
     * @return - JPanel - content for the content panel
     */
    public JPanel getWelcomeViewPanel(){
        JPanel welcomeViewPanel = new JPanel();
        welcomeViewPanel.setVisible(true);
        welcomeViewPanel.setPreferredSize(new Dimension(600, 200));
        welcomeViewPanel.setLayout(new BoxLayout(welcomeViewPanel, BoxLayout.X_AXIS));
        Image image1 = new ImageIcon("./images/10895.png").getImage().getScaledInstance(135, 200,  Image.SCALE_SMOOTH);
        JLabel imageLabel1 = new JLabel(new ImageIcon(image1));
        Image image2 = new ImageIcon("./images/23563.png").getImage().getScaledInstance(135, 200,  Image.SCALE_SMOOTH);
        JLabel imageLabel2 = new JLabel(new ImageIcon(image2));
        Image image3 = new ImageIcon("./images/90132.png").getImage().getScaledInstance(135, 200,  Image.SCALE_SMOOTH);
        JLabel imageLabel3 = new JLabel(new ImageIcon(image3));
        Image image4 = new ImageIcon("./images/80943.png").getImage().getScaledInstance(135, 200,  Image.SCALE_SMOOTH);
        JLabel imageLabel4 = new JLabel(new ImageIcon(image4));
        welcomeViewPanel.add(Box.createRigidArea(new Dimension(35, 0)));
        welcomeViewPanel.add(imageLabel1);
        welcomeViewPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        welcomeViewPanel.add(imageLabel3);
        welcomeViewPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        welcomeViewPanel.add(imageLabel2);
        welcomeViewPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        welcomeViewPanel.add(imageLabel4);
        return welcomeViewPanel;
    }

    public String getCucumberChoice() {
        return cucumberChoice;
    }

    public Dressing getDressingChoice() {
        return dressingChoice;
    }

    public Set<String> getLeafyGreensSet() {
        return leafyGreensSet;
    }

    public Set<Sauce> getSauceSet() {
        return sauceSet;
    }

    public String getBunChoice() {
        return bunChoice;
    }

    public double getMinPrice() {
        return this.minPrice;
    }

    public double getMaxPrice() {
        return this.maxPrice;
    }

    public String getTomatoChoice() {
        return this.tomatoChoice;
    }

    public String getPickleChoice() {
        return this.pickleChoice;
    }

    public boolean getCheeseStatus() {
        return this.cheeseStatus;
    }

    public Meat getMeatChoice() {
        return this.meatChoice;
    }
}
