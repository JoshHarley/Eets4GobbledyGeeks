import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

public class MenuSearcher {
    /**
     * Created by Dr Andreas Shepley for COSC120 on 03/07/2023
     */
    private static final String filePath = "./menu.txt";
    private static final Icon icon = new ImageIcon("./gobbledy_geek_graphic_small.png");
    private static Menu menu;

    private static final String appName = "Eets 4 Gobbledy-Geeks";
    private static JFrame mainWindow = null;
    private static JPanel contentPanel = null;
    private static JPanel imagePanel = null;
    private static JPanel fixedPanel = null;
    private static JPanel selectedPanel = null;
    private static JComboBox<Type> typeSelectionCBox;

    private static SearchView searchView;

    public static void main(String[] args) {
        menu = loadMenu(filePath);
        searchView = new SearchView(menu);
        setMainWindow();
        DreamMenuItem dreamMenuItem = getFilters();
        //processSearchResults(dreamMenuItem);
        //System.exit(0);
    }

    /**
     * A method for set the content of the main window frame of the app. Displays the app image
     * as well as sets up the content panel.
     */
    public static void setMainWindow(){
        mainWindow = new JFrame("Gobbledy Geeks");
        mainWindow.setIconImage(new ImageIcon("./gobbledy_geek_graphic_small.png").getImage());
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setLayout(new BorderLayout());
        mainWindow.setPreferredSize(new Dimension(900, 600));
        mainWindow.setVisible(true);
        // Create panel to display constant options
        fixedPanel = new JPanel();
        fixedPanel.setPreferredSize(new Dimension(600, 250));
        fixedPanel.setLayout(new BorderLayout());
        fixedPanel.setVisible(true);
        mainWindow.setContentPane(fixedPanel);

        // Create a panel to display a constant image
        imagePanel = new JPanel();
        imagePanel.setVisible(true);
        imagePanel.setSize(new Dimension(300, 600));
        imagePanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        imagePanel.add(geekImage());

        // Create a panel to display the changing content such as searching screens and results
        contentPanel = new JPanel();
        contentPanel.setLayout(new FlowLayout());
        contentPanel.setVisible(true);
        contentPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
        contentPanel.setPreferredSize(new Dimension(600, 400));

        // Create a combobox for user type choice
        typeSelectionCBox = new JComboBox<>(Type.values());
        typeSelectionCBox.setSelectedIndex(0);
        typeSelectionCBox.setPreferredSize(new Dimension(550, 50));
        // Set the panel for view selection as card layout and add the different views to it
        selectedPanel = new JPanel(new CardLayout());
        selectedPanel.add("Please Select", searchView.getWelcomeViewPanel());
        selectedPanel.add("Salad", searchView.saladOptionsView());
        selectedPanel.add("Burger", searchView.burgerOptionsView());
        typeSelectionCBox.addActionListener(e -> {
            CardLayout cards = (CardLayout) (selectedPanel.getLayout());
            cards.show(selectedPanel, typeSelectionCBox.getItemAt(typeSelectionCBox.getSelectedIndex()).toString());
        });
        contentPanel.add(Box.createRigidArea(new Dimension(25, 0)));
        contentPanel.add(typeSelectionCBox);

        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        contentPanel.add(selectedPanel, BorderLayout.CENTER);

        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(searchView.fixedOptionsView());

        fixedPanel.add(imagePanel, BorderLayout.WEST);
        fixedPanel.add(contentPanel, BorderLayout.EAST);

        mainWindow.pack();
        mainWindow.setLocationRelativeTo(null);
    }


    public static JLabel geekImage(){
        Image geekImage = new ImageIcon("./gobbledy_geek_graphic.png").getImage().getScaledInstance(290, 600,  Image.SCALE_SMOOTH);
        ImageIcon geekImageIcon = new ImageIcon(geekImage);
        return new JLabel(geekImageIcon);
    }

    public static DreamMenuItem getFilters(){

        Map<Filter,Object> filterMap = new LinkedHashMap<>();
        String[] options = {"Yes", "No", "I don't mind"};

        Type type = (Type) typeSelectionCBox.getSelectedItem();
        filterMap.put(Filter.TYPE,type);

        if(type==Type.BURGER) {
            String bunType = searchView.getBunChoice();
            if(!bunType.equalsIgnoreCase("I don't mind")) {
                filterMap.put(Filter.BUN, bunType);
            }

            Set<Sauce> dreamSauces = searchView.getSauceSet();
            if(dreamSauces.size()>0) filterMap.put(Filter.SAUCE_S,dreamSauces);
        }

        if(type==Type.SALAD){
            Set<String> dreamLeafyGreens = searchView.getLeafyGreensSet();
            if(dreamLeafyGreens.size()>0) filterMap.put(Filter.LEAFY_GREENS, dreamLeafyGreens);

            boolean cucumber=false;
            String cucumberSelection = searchView.getCucumberChoice();
            if(cucumberSelection.equalsIgnoreCase("Yes")) {
                cucumber = true;
                filterMap.put(Filter.CUCUMBER, cucumber);
            } else if (cucumberSelection.equalsIgnoreCase("No")){
                filterMap.put(Filter.CUCUMBER, cucumber);
            }

            Dressing dressing = searchView.getDressingChoice();
            if(!dressing.equals(Dressing.NA)) filterMap.put(Filter.DRESSING, dressing);
        }

        Meat dreamMeat = searchView.getMeatChoice();
        if(!dreamMeat.equals(Meat.NA)) filterMap.put(Filter.MEAT,dreamMeat);

        boolean cheese = searchView.getCheeseStatus();;
        filterMap.put(Filter.CHEESE, cheese);

        boolean tomato=false;
        String tomatoSelection = searchView.getTomatoChoice();
        if(tomatoSelection.equalsIgnoreCase("Yes")) {
            tomato = true;
            filterMap.put(Filter.CUCUMBER, tomato);
        } else if (tomatoSelection.equalsIgnoreCase("No")){
            filterMap.put(Filter.CUCUMBER, tomato);
        }

        boolean pickle=false;
        String pickleSelection = searchView.getPickleChoice();
        if(pickleSelection.equalsIgnoreCase("Yes")) {
            pickle = true;
            filterMap.put(Filter.CUCUMBER, pickle);
        } else if (pickleSelection.equalsIgnoreCase("No")){
            filterMap.put(Filter.CUCUMBER, pickle);
        }

        double minPrice= searchView.getMinPrice();
        double maxPrice = searchView.getMaxPrice();
        return new DreamMenuItem(filterMap,minPrice,maxPrice);
    }

    public static void processSearchResults(DreamMenuItem dreamMenuItem){
        List<MenuItem> matching = menu.findMatch(dreamMenuItem);
        MenuItem chosenItem = null;
        System.out.println("number of results: "+matching.size());
        if(matching.size()>0) {
            Map<String, MenuItem> options = new HashMap<>();
            StringBuilder infoToShow = new StringBuilder("Matches found!! The following items meet your criteria: \n");
            for (MenuItem match : matching) {
                infoToShow.append(match.getMenuItemInformation());
                options.put(match.getMenuItemName(), match);
            }
            String choice = (String) JOptionPane.showInputDialog(null, infoToShow + "\n\nPlease select which item you'd like to order:", appName, JOptionPane.INFORMATION_MESSAGE, icon, options.keySet().toArray(), "");
            if(choice==null) System.exit(0);
            chosenItem = options.get(choice);
        }
        else{
            int custom = JOptionPane.showConfirmDialog(null, """
                    Unfortunately none of our items meet your criteria :(
                    \tWould you like to place a custom order?\s

                    **Price to be calculated at checkout and may exceed your chosen range**.""",appName, JOptionPane.YES_NO_OPTION);
            if(custom==0) chosenItem = new MenuItem(dreamMenuItem);
            else System.exit(0);
        }
        submitOrder(getUserContactInfo(),chosenItem);
        JOptionPane.showMessageDialog(null,"Thank you! Your order has been submitted. "+
                "Please wait for your name to be called out...",appName, JOptionPane.INFORMATION_MESSAGE);
    }

    public static Geek getUserContactInfo(){
        String name = JOptionPane.showInputDialog(null,"Please enter a name for the order.",appName, JOptionPane.QUESTION_MESSAGE);
        if(name==null) System.exit(0);
        long phoneNumber=0;
        while(phoneNumber==0) {
            try {
                String userInput = JOptionPane.showInputDialog(null, "Please enter your phone number. \nIt will be used as your order number.", appName, JOptionPane.QUESTION_MESSAGE);
                if(userInput==null) System.exit(0);
                phoneNumber = Long.parseLong(userInput);
            } catch (NumberFormatException e) {
                phoneNumber = Long.parseLong(JOptionPane.showInputDialog(null, "Invalid entry. Please enter your phone number.", appName, JOptionPane.ERROR_MESSAGE));
            }
            int length = String.valueOf(phoneNumber).length();
            if(length!=9) {
                phoneNumber=0;
                JOptionPane.showMessageDialog(null,"Invalid entry. Please enter your 10-digit phone number in the format 0412 123 345.",appName, JOptionPane.ERROR_MESSAGE);
            }
        }
        return new Geek(name,phoneNumber);
    }

    public static void submitOrder(Geek geek, MenuItem menuItem) {
        String filePath = geek.getName().replace(" ","_")+"_"+menuItem.getMenuItemIdentifier()+".txt";
        Path path = Path.of(filePath);
        String lineToWrite = "Order details:\n\t" +
                "Name: "+geek.getName()+
                " (0"+geek.getOrderNumber()+")";
        if(menuItem.getMenuItemIdentifier()==0) lineToWrite+="\n\nCUSTOM ORDER...\n"+menuItem.getMenuItemInformation();
        else lineToWrite+="\n\tItem: "+menuItem.getMenuItemName()+ " ("+menuItem.getMenuItemIdentifier()+")";

        try {
            Files.writeString(path, lineToWrite);
        }catch (IOException io){
            System.out.println("Order could not be placed. \nError message: "+io.getMessage());
            System.exit(0);
        }
    }

    public static Menu loadMenu(String filePath) {
        Menu menu = new Menu();
        Path path = Path.of(filePath);
        List<String> fileContents = null;
        try {
            fileContents = Files.readAllLines(path);
        }catch (IOException io){
            System.out.println("File could not be found");
            System.exit(0);
        }

        for(int i=1;i<fileContents.size();i++){

            String[] info = fileContents.get(i).split("\\[");
            String[] singularInfo = info[0].split(",");

            String leafyGreensRaw = info[1].replace("]","");
            String saucesRaw = info[2].replace("]","");
            String description = info[3].replace("]","");

            long menuItemIdentifier = 0;
            try{
                menuItemIdentifier = Long.parseLong(singularInfo[0]);
            }catch (NumberFormatException n) {
                System.out.println("Error in file. Menu item identifier could not be parsed for item on line "+(i+1)+". Terminating. \nError message: "+n.getMessage());
                System.exit(0);
            }

            Image image = new ImageIcon("./" + menuItemIdentifier + ".png").getImage();

            Type type = null;
            try{
                type = Type.valueOf(singularInfo[1].toUpperCase().strip());
            }catch (IllegalArgumentException e){
                System.out.println("Error in file. Type data could not be parsed for item on line "+(i+1)+". Terminating. \nError message: "+e.getMessage());
                System.exit(0);
            }

            String menuItemName = capitalise(singularInfo[2]);

            double price = 0;
            try{
                price = Double.parseDouble(singularInfo[3]);
            }catch (NumberFormatException n){
                System.out.println("Error in file. Price could not be parsed for item on line "+(i+1)+". Terminating. \nError message: "+n.getMessage());
                System.exit(0);
            }

            String bun = capitalise(singularInfo[4].strip());

            Meat meat = null;
            try {
                meat = Meat.valueOf(singularInfo[5].toUpperCase());
            }catch (IllegalArgumentException e){
                System.out.println("Error in file. Meat data could not be parsed for item on line "+(i+1)+". Terminating. \nError message: "+e.getMessage());
                System.exit(0);
            }

            boolean cheese = false;
            String cheeseRaw = singularInfo[6].strip().toUpperCase();
            if(cheeseRaw.equals("YES")) cheese = true;

            boolean pickles = false;
            String pickleRaw = singularInfo[7].strip().toUpperCase();
            if(pickleRaw.equals("YES")) pickles = true;

            boolean cucumber = false;
            String cucumberRaw = singularInfo[8].strip().toUpperCase();
            if(cucumberRaw.equals("YES")) cucumber = true;

            boolean tomato = false;
            String tomatoRaw = singularInfo[9].strip().toUpperCase();
            if(tomatoRaw.equals("YES")) tomato = true;

            Dressing dressing = null;
            try {
                dressing = Dressing.valueOf(singularInfo[10].toUpperCase().replace(" ","_"));
            }catch (IllegalArgumentException e){
                System.out.println("Error in file. Dressing data could not be parsed for item on line "+(i+1)+". Terminating. \nError message: "+e.getMessage());
                System.exit(0);
            }

            Set<String> leafyGreens = new HashSet<>();
            for(String l: leafyGreensRaw.split(",")){
                leafyGreens.add(capitalise(l.strip()));
            }

            Set<Sauce> sauces = new HashSet<>();
            for(String s: saucesRaw.split(",")){
                Sauce sauce = null;
                try {
                    sauce = Sauce.valueOf(s.toUpperCase().strip());
                }catch (IllegalArgumentException e){
                    System.out.println("Error in file. Sauce/s data could not be parsed for item on line "+(i+1)+". Terminating. \nError message: "+e.getMessage());
                    System.exit(0);
                }
                sauces.add(sauce);
            }

            Map<Filter,Object> filterMap = new LinkedHashMap<>();
            filterMap.put(Filter.TYPE,type);
            if(type.equals(Type.BURGER)){
                filterMap.put(Filter.BUN, bun);
                if(sauces.size()>0) filterMap.put(Filter.SAUCE_S,sauces);
            }
            if(!meat.equals(Meat.NA)) filterMap.put(Filter.MEAT,meat);
            filterMap.put(Filter.PICKLES, pickles);
            filterMap.put(Filter.CHEESE, cheese);
            filterMap.put(Filter.TOMATO, tomato);
            if(type.equals(Type.SALAD)){
                filterMap.put(Filter.DRESSING,dressing);
                filterMap.put(Filter.LEAFY_GREENS,leafyGreens);
                filterMap.put(Filter.CUCUMBER, cucumber);
            }

            DreamMenuItem dreamMenuItem = new DreamMenuItem(filterMap);
            MenuItem menuItem = new MenuItem(menuItemIdentifier, menuItemName, price, description, image, dreamMenuItem);
            menu.addItem(menuItem);
        }
        return menu;
    }

    /**
     * A method to capitalise a single or multi word String
     * @param toCapitalise - String to capitalise
     * @return - A String
     */
    public static String capitalise(String toCapitalise){
        String[] words = toCapitalise.trim().split(" ");
        StringBuilder capitalised = new StringBuilder();
        for(int i = 0; i < words.length; i++){
            String firstLetter = words[i].substring(0, 1).toUpperCase();
            capitalised.append(firstLetter).append(words[i].substring(1));
            if(i < words.length - 1){
                capitalised.append(" ");
            }
        }
        return capitalised.toString();
    }
}





























