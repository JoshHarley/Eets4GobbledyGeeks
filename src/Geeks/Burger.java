package Geeks;

import java.util.ArrayList;
import java.util.Scanner;

public class Burger {
    /**
     * Class to hold data for a burger recipe.
     * Contains id, name, price, bun type, description (for menu items, not custom). pattie type,
     * boolean values for cheese or pickles (no types here, just yes or no), and the option for multiple sauces.
     */
    private String id, name, bun, description;
    private double price;
    private Patties pattie;
    private boolean cheese, pickle;
    private ArrayList<Sauces> sauceList;
    private Scanner scan;

    public Burger() {
        /*
          Create an empty sauce list and set default values for boolean options.
         */
        this.id = "";
        this.name = "";
        this.bun = "";
        this.sauceList = new ArrayList<>();
        this.description = "";
        this.scan = new Scanner(System.in);
    }

    public Burger(String _id,String _name,String _price,String _bun, String _pattie, String _cheese, String _pickle, String _description){
        this.id = _id;
        this.name = _name;
        this.price = Double.parseDouble(_price);
        this.bun = _bun;
        this.setPattie(_pattie);
        this.setCheese(_cheese);
        this.setPickles(_pickle);
        this.sauceList = new ArrayList<>();
        this.description = _description;
    }

    //region get sets
    /**
     *Return Burger object id when requested.
     */
    public String getId() {
        return this.id;
    }

    /**
     *Set the Burger Object id to provided id.
     */
    public void setId(String _id) {
        this.id = _id;
    }

    /**
     * Return Burger object name when requested.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name of the burger object.
     */
    public void setName(String _name) {
        this.name = _name;
    }

    /**
     * Return Burger object price when requested.
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Set the price of the Burger object.
     */
    public void setPrice(String _price) {
        this.price = Double.parseDouble(_price);
    }

    /**
     * Return Burger object bun type when requested.
     */
    public String getBunType() {
        return this.bun;
    }

    /**
     * set the bun type of the Burger object using a switch comparing provided string with available options.
     */
    public void setBun(String _bun) {
        while(this.bun.isEmpty()) {
            switch (_bun) {
                case "1" -> {
                    this.bun = "brioche";
                }
                case "2" -> {
                    this.bun = "light rye";
                }
                case "3" -> {
                    this.bun = "signature";
                }
                case "4" -> {
                    this.bun = "multi grain";
                }
                case "5" -> {
                    this.bun = "wholemeal";
                }
                case "6" -> {
                    this.bun = "rye";
                }
                default -> {
                    System.out.println("You seem to have selected an invalid option, please try again.");
                    System.out.println("What type of bun would you like? (please enter a number)\n1: Brioche\n2: Light Rye\n3: Signature\n4: Multigrain\n5: Wholemeal\n6: Rye");
                    this.setBun(this.scan.nextLine());
                }
            }
        }
    }

    /**
     * Return Burger object pattie type when requested.
     */
    public Patties getMeat() {
        return this.pattie;
    }

    /**
     * set the pattie type of the Burger object using a switch to compare with available enums.
     */
    public void setPattie(String _pattie) {
        switch (_pattie.toLowerCase()) {
            //check string value is a valid pattie option and assign appropriate enum else throw error.
            case "1" -> this.pattie = Patties.Beef;
            case "beef" -> this.pattie = Patties.Beef;
            case "2" -> this.pattie = Patties.Chicken;
            case "chicken" -> this.pattie = Patties.Chicken;
            case "3" -> this.pattie = Patties.Veggie;
            case "vegan" -> this.pattie = Patties.Veggie;
            default -> {
                System.out.println("Invalid input, please try again");
                System.out.println("What protein would you like on your burger? (please enter a number)?\n1: Beef\n2: Chicken\n3: Vegan");
                this.setPattie(this.scan.nextLine());
            }
        }
    }

    /**
     * Return whether Burger object has cheese or not when requested.
     */
    public String getCheese() {
        if (cheese) {
            return "yes";
        } else {
            return "no";
        }
    }

    /**
     * Sets whether the Burger object has cheese or not.
     */
    public void setCheese(String _cheese) {
        if (_cheese.equalsIgnoreCase("yes")) {
            this.cheese = true;
        } else if (_cheese.equalsIgnoreCase("no")) {
            this.cheese = false;
        } else {
            System.out.println("Invalid input, please try again");
            setCheese(scan.nextLine());
        }
    }

    /**
     * Return whether Burger object has pickles or not when requested.
     */
    public String getPickles() {
        if (pickle) {
            return "yes";
        } else {
            return "no";
        }
    }

    /**
     * Sets whether the Burger object has pickles or not.
     */
    public void setPickles(String _pickles) {
        if (_pickles.equalsIgnoreCase("yes")) {
            this.pickle = true;
        } else if (_pickles.equalsIgnoreCase("no")) {
            this.pickle = false;
        } else {
            System.out.println("Invalid input, please try again");
            setPickles(scan.nextLine());
        }    }

    /**
     * Return Burger object sauces when requested.
     */
    public ArrayList<Sauces> getSauce() {
        return this.sauceList;
    }

    /**
     * Adds the provided sauce to the list of sauces based on the string provided using a switch against the available enums.
     */

    public void setSauce(String _sauce) {
        switch (_sauce.toLowerCase().trim()) {
            //Compare string options for sauces and assign correct enum, else throw error
            case "tomato" -> this.sauceList.add(Sauces.Tomato);
            case "garlic" -> this.sauceList.add(Sauces.Garlic);
            case "aioli" -> this.sauceList.add(Sauces.Aioli);
            case "big mac" -> this.sauceList.add(Sauces.BigMac);
            case "bbq" -> this.sauceList.add(Sauces.BBQ);
            case "chilli" -> this.sauceList.add(Sauces.Chilli);
            case "ranch" -> this.sauceList.add(Sauces.Ranch);
            case "special" -> this.sauceList.add(Sauces.Special);
            case "1" -> this.sauceList.add(Sauces.Tomato);
            case "2" -> this.sauceList.add(Sauces.Garlic);
            case "3" -> this.sauceList.add(Sauces.Aioli);
            case "4" -> this.sauceList.add(Sauces.BigMac);
            case "5" -> this.sauceList.add(Sauces.BBQ);
            case "6" -> this.sauceList.add(Sauces.Chilli);
            case "7" -> this.sauceList.add(Sauces.Ranch);
            case "8" -> this.sauceList.add(Sauces.Special);
            default -> {
                System.out.println("Invalid input, please try again");
                System.out.println("What sauce would you like on your burger? (please enter a number):\n1: Tomato\n2: garlic\n3: Aioli\n4: Big Mac\n5: BBQ\n6: Chilli\n7: Ranch\n8: Special");
                this.setSauce(this.scan.nextLine());
            }
        }
    }

    /**
     * Return Burger object description when requested.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the Burger objects description with the provided description.
     */
    public void setDescription(String _description) {
        this.description = _description;
    }

    /**
     * Method for setting the list of sauces into a string value for printing purposes.
     */
    public String printSauce() {
        StringBuilder sauce = new StringBuilder();
        //check if list is empty, if so return nothing as there are no sauces on this burger.
        if (this.sauceList.isEmpty()) {
            return sauce.toString();
        }
        // iterate through sauce list and add sauces to string value for printing.
        sauce = new StringBuilder(this.sauceList.get(0).getSauce());
        for (int i = 1; i < this.sauceList.size(); i++) {
            sauce.append(", ").append(this.sauceList.get(i).getSauce());
        }
        return sauce.toString();
    }
    //endregion


    //region Overrides

    /**
     * Overriding the toString method to suit my purposes for this project. This allows me to print the entire recipe in the format specified.
     */
    @Override
    public String toString() {
        String toPrint = "";
        // Set up string value for printing with initial values
        if(this.name == "CUSTOM ORDER"){
            toPrint = "Ingredients:\n" +  MenuSearcher.padLeftString("Bun type: ") + this.bun + "\n" +  MenuSearcher.padLeftString("Meat: ") + this.pattie + "\n" +  MenuSearcher.padLeftString("Sauce/s: ") + printSauce() + "\n" +  MenuSearcher.padLeftString("Other: ");
        } else {
            toPrint = this.name + " (" + this.id + ")\n" + this.description + "\n" + "Ingredients:\nBun type: " + this.bun + "\nMeat: " + this.pattie + "\nSauce/s: " + printSauce() + "\nOther: ";
        }
        //find cheese and pickle values and add to string
        if (this.cheese && this.pickle) {
            toPrint += "Cheese, Pickles";
        } else if (this.cheese) {
            toPrint += "Cheese";
        } else if (this.pickle) {
            toPrint += "Pickles";
        }
        //add price to string
        if(this.name != "CUSTOM ORDER") {
            toPrint += "\nPrice: $" + this.price;
        }
        return toPrint;
    }

    /**
     *Overriding the equals method for easy comparison of Burger objects
     */
    @Override
    public boolean equals(Object compared) {
        if (this == compared) {
            return true;
        }

        if (!(compared instanceof Burger)) {
            return false;
        }

        // compare Burger object ingredients and return true if the same
        Burger comparedBurger = (Burger) compared;
        int countOfSauces = 0;
        for(Sauces sauce: comparedBurger.sauceList){
            if(this.sauceList.contains(sauce)){
                countOfSauces++;
            }
        }
        if(countOfSauces == comparedBurger.sauceList.size()){
            if (this.cheese == comparedBurger.cheese &&
                    this.pickle == comparedBurger.pickle &&
                    this.bun.equalsIgnoreCase(comparedBurger.bun) &&
                    this.pattie.equals(comparedBurger.pattie)){
                return true;
            }
        }
        return false;
    }
    //endregion


    //region Enums

    /**
     * set up enums for pattie. Options for this project are beef, chicken and veggie
     */
    public enum Patties {
        None("None"),
        Beef("Beef"),
        Chicken("Chicken"),
        Veggie("Vegan");

        private String newPattie;

        Patties(String burgerPattie) {
            this.newPattie = burgerPattie;
        }

        public String getPattie() {
            return this.newPattie;
        }
    }

    ;

    /**
     * Set up the enums for sauces. Options for sauces are tomato. garlic, aioli, big mac, bbq, chilli, ranch and house.
     * Also set up string values for the enums for ease of comparison
     */
    enum Sauces {
        //enum values with string values
        None("None"),
        Tomato("Tomato"),
        Garlic("Garlic"),
        Aioli("Aioli"),
        BigMac("Big mac"),
        BBQ("BBQ"),
        Chilli("Chilli"),
        Ranch("Ranch"),
        Special("Special");

        //set up string return values
        private String newSauce;

        Sauces(String burgerSauce) {
            this.newSauce = burgerSauce;
        }

        public String getSauce() {
            return this.newSauce;
        }
    }
    //endregion
}
