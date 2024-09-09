import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;

public class SearchView {
    private final Menu menu;
    private Type menuType;
    private double minPrice, maxPrice;
    private String tomatoChoice, pickleChoice, cucumberChoice, bunChoice;
    private JLabel feedBack;
    private final String appName = "Eets 4 Gobbledy-Geeks";
    private Meat meatChoice;
    private Dressing dressingChoice;
    private JCheckBox cheeseChkBox;
    private boolean cheeseStatus;
    private Set<String> leafyGreensSet;
    private Set<Sauce> sauceSet;
    private final JPanel selectedPanel;
    private JTextField minPriceTxtF, maxPriceTxtF;

    public SearchView(Menu _menu){
        this.menu = _menu;
        this.minPrice = 0;
        this.maxPrice = menu.getMaxPrice();
        this.selectedPanel = new JPanel(new CardLayout());
        this.meatChoice = Meat.NA;
        this.tomatoChoice = "NA";
        this.cucumberChoice = "NA";
        this.pickleChoice = "NA";
        //this.bunChoice = "NA";
        this.dressingChoice = Dressing.NA;
        this.menuType = Type.SELECT;
        this.feedBack = new JLabel("");
        this.feedBack.setPreferredSize(new Dimension(300, 20));
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
        fixed.setPreferredSize(new Dimension(600, 210));

        // Create panel for left side
        JPanel left = new JPanel();
        left.setVisible(true);
        left.setPreferredSize(new Dimension(300, 125));

        // Add left panel options
        left.add(Box.createRigidArea(new Dimension(300, 10)));
        left.add(getCustomRadioChoicePanel("Tomato?"));
        left.add(Box.createRigidArea(new Dimension(300, 5)));
        left.add(getCustomRadioChoicePanel("Pickles?"));

        // Create panel for right ride
        JPanel right = new JPanel();
        right.setVisible(true);
        right.setPreferredSize(new Dimension(300, 125));

        // Add right panel options
        right.add(Box.createRigidArea(new Dimension(300, 10)));
        right.add(getMeatChoicePanel());
        right.add(Box.createRigidArea(new Dimension(300, 5)));
        right.add(getCheeseChoicePanel());

        // Set up the search button
        JButton searchBtn = new JButton("Search");
        searchBtn.setPreferredSize(new Dimension(578, 30));
        searchBtn.addActionListener(e -> {
                    if(menuType == Type.SELECT) {
                        JOptionPane.showMessageDialog(fixed, "A menu item type (burger or salad) selection must be made to perform a search",
                                appName, JOptionPane.INFORMATION_MESSAGE, cheeseChkBox.getIcon());
                    } else if (!checkMinPrice() || !checkMaxPrice()) {
                        JOptionPane.showMessageDialog(fixed, "The price range entered is not valid",
                                appName, JOptionPane.INFORMATION_MESSAGE, cheeseChkBox.getIcon());
                    } else {
                        getChoices();
                    }
            DreamMenuItem dreamMenuItem = MenuSearcher.getFilters();
            MenuSearcher.processSearchResults(dreamMenuItem);
                });

        // Set up feedback label
        this.feedBack.setForeground(Color.red);
        this.feedBack.setHorizontalTextPosition(SwingConstants.CENTER);

        // Create panel for the bottom
        JPanel bottom = new JPanel();
        bottom.setPreferredSize(new Dimension(300, 100));

        // Add bottom panel options
        bottom.add(getMinMaxPricePanel());
        bottom.add(this.feedBack);
        bottom.add(Box.createRigidArea(new Dimension(600, 3)));
        bottom.add(Box.createRigidArea(new Dimension(7, 0)));
        bottom.add(searchBtn);

        // Add left, right and bottom panels to the fixed panel
        fixed.add(left, BorderLayout.WEST);
        fixed.add(right, BorderLayout.EAST);
        fixed.add(bottom, BorderLayout.SOUTH);
        return fixed;
    }

    public void getChoices(){
        System.out.println(getBunChoice());
        System.out.println(getCheeseStatus());
        System.out.println(getTomatoChoice());
        System.out.println(getPickleChoice());
        System.out.println(getMeatChoice());
        System.out.println(getCucumberChoice());
        System.out.println(getSauceSet());
    }

    /**
     * A method to set yp a combo box for the menu type selection
     * @return - A JPanel
     */
    public JPanel getTypeComboBoxPanel(){
        JPanel typeComboBoxPanel = new JPanel();
        typeComboBoxPanel.setPreferredSize(new Dimension(550, 35));
        // Create a combobox for user type choice
        JComboBox<Type> typeSelectionCBox;
        typeSelectionCBox = new JComboBox<>(Type.values());
        typeSelectionCBox.setSelectedIndex(0);
        typeSelectionCBox.setPreferredSize(new Dimension(550, 30));
        typeSelectionCBox.addActionListener(e -> {
            this.menuType = (Type) typeSelectionCBox.getSelectedItem();
            CardLayout cards = (CardLayout) (this.selectedPanel.getLayout());
            cards.show(this.selectedPanel, typeSelectionCBox.getItemAt(typeSelectionCBox.getSelectedIndex()).toString());
        });
        typeComboBoxPanel.add(typeSelectionCBox);
        return typeComboBoxPanel;
    }

    /**
     * A method the creates a JPanel offering a choice of meats through the use of a JComboBox
     * and the Meat enums values
     * @return - A JPanel
     */
    private JPanel getMeatChoicePanel(){
        JPanel meatPanel = new JPanel();
        meatPanel.setPreferredSize(new Dimension(300, 30));
        JLabel meatLbl = new JLabel("Meat:");
        JComboBox<Meat> meatOptionsCBox = new JComboBox<>(Meat.values());
        meatOptionsCBox.setSelectedItem(this.meatChoice);
        meatOptionsCBox.addItemListener(e -> this.meatChoice = (Meat)e.getItem());
        meatPanel.add(meatLbl);
        meatPanel.add(meatOptionsCBox);
        return meatPanel;
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
        radioPanel.setPreferredSize(new Dimension(300, 25));
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
        minMaxPricePanel.setPreferredSize(new Dimension(600, 25));
        // Set up min price textfield and label
        this.minPriceTxtF = new JTextField(10);
        this.minPriceTxtF.setText("0.00");
        JLabel minPriceLbl = new JLabel("Min Price:   $");

        this.maxPriceTxtF = new JTextField(10);
        String maxPriceString = String.format("%.2f", this.maxPrice);
        this.maxPriceTxtF.setText(String.valueOf(maxPriceString));
        JLabel maxPriceLbl = new JLabel("Max Price:   $");
        // Set document listener for minimum price textfield
        this.minPriceTxtF.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!checkMinPrice()) minPriceTxtF.requestFocusInWindow();
                checkMaxPrice();

            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!checkMinPrice()) minPriceTxtF.requestFocusInWindow();
                checkMaxPrice();

            }
            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        // Set document listener for max price textfield
        this.maxPriceTxtF.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!checkMaxPrice()) maxPriceTxtF.requestFocusInWindow();
                checkMinPrice();

            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!checkMaxPrice()) maxPriceTxtF.requestFocusInWindow();
                checkMinPrice();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        minMaxPricePanel.add(minPriceLbl);
        minMaxPricePanel.add(this.minPriceTxtF);
        minMaxPricePanel.add(Box.createRigidArea(new Dimension(15, 0)));
        minMaxPricePanel.add(maxPriceLbl);
        minMaxPricePanel.add(this.maxPriceTxtF);
        return minMaxPricePanel;
    }

    /**
     * A method to check the min price field, validating that a number was entered and that the number
     * was above 0 and less than max.
     * @return - A boolean value
     */
    private boolean checkMinPrice(){
        try {
            double min = Double.parseDouble(this.minPriceTxtF.getText());
            double max = Double.parseDouble(this.maxPriceTxtF.getText());

            if (min < 0 || min > max) {
                this.feedBack.setText("<html>Minimum price must be more than 0 and less than the maximum price</html>");
                this.minPriceTxtF.selectAll();
                return false;
            } else {
                this.minPrice = min;
                this.feedBack.setText("");
                return true;
            }
        } catch (NumberFormatException nf) {
            this.feedBack.setText("<html>Min price input was not a number, please try again</html>");
            this.minPriceTxtF.selectAll();
            return false;
        }
    }

    /**
     * A method to check that the value in the max price textfield is a number and is not less
     * than the minimum price value.
     * @return - A boolean
     */
    private boolean checkMaxPrice(){
        try {
            double max = Double.parseDouble(this.maxPriceTxtF.getText());
            double min = Double.parseDouble(this.minPriceTxtF.getText());
            System.out.println(min + ": " + max);
            if (max < min) {
                this.feedBack.setText("<html>The minimum price must be less than the maximum price</html>");
                this.maxPriceTxtF.selectAll();
                return false;
            } else {
                this.maxPrice = max;
                this.feedBack.setText("");
                return true;
            }
        } catch (NumberFormatException nf){
            this.feedBack.setText("<html>Max price input was not a number, please try again</html>");
            this.maxPriceTxtF.selectAll();
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
        JList<Object> allLeafyGreens = new JList<>(this.menu.getAllIngredientTypes(Filter.LEAFY_GREENS).toArray());
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
        this.sauceSet = new HashSet<>();
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
        bunTypeCBox.setSelectedItem("I don't mind");
        this.bunChoice = "I don't mind";
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

    public Type getMenuType(){
        return this.menuType;
    }
    public JPanel getSelectedPanel(){ return this.selectedPanel; }

}
