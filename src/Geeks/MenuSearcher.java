package Geeks;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuSearcher {

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        String filePath = "./menu.txt";
        Menu fullMenu = searchMenu(filePath);
        System.out.println("Welcome to Eets 4 Gobbledy-Geeks!\nPlease enter your order below or write quit at any time to close the program.");
        Geek newGeek = getGeekInformation(scan);
        Burger burger = getBurgerInfo(scan);
        String newOrder = orderBurger(newGeek, burger, fullMenu, scan);
        System.out.println(newOrder);
    }

    public static Menu searchMenu(String filePath) {
        Menu menu = new Menu();
        try{
            Path path = Path.of(filePath);
            String fileContents = Files.readString(path);
            String[] recipeList = fileContents.split("\n");
            for (int i = 1; i < recipeList.length; i++) {
                String[] splitOnBrackets = recipeList[i].split("[\\[\\]]");
                String[] info = splitOnBrackets[0].split(",");
                String[] sauceArray = splitOnBrackets[1].split(",");
                String description = splitOnBrackets[3];
                Burger burger = new Burger(info[0], info[1], info[2], info[3], info[4], info[5],info[6], description);
                for(String newSauce: sauceArray){burger.setSauce(newSauce);}
                menu.addBurgerToMenu(burger);
            }
        } catch(IOException e){
            System.out.println("Error: " + e);
        }
        return menu;
    }

    public static Burger getBurgerInfo(Scanner scan){
        Burger newBurger = new Burger();
        String input;
        System.out.println("Please enter desired burger information:");
        System.out.println("What type of bun would you like? (please enter a number)\n1: Brioche\n2: Light Rye\n3: Signature\n4: Multigrain\n5: Wholemeal\n6: Rye");
        input = checkFroQuit(emptyCheck(scan, scan.nextLine()));
        newBurger.setBun(input);
        System.out.println("What protein would you like on your burger? (please enter a number)?\n1: Beef\n2: Chicken\n3: Vegan");
        input = checkFroQuit(emptyCheck(scan, scan.nextLine()));
        newBurger.setPattie(input);
        System.out.println("Do you want cheese on your burger? (please type yes or no)");
        input = checkFroQuit(emptyCheck(scan, scan.nextLine()));
        newBurger.setCheese(input);
        System.out.println("Do you want pickles on your burger? (please type yes or no)");
        input = checkFroQuit(emptyCheck(scan, scan.nextLine()));
        newBurger.setPickles(input);
        System.out.println("What sauce would you like on your burger? (please enter a number):\n1: Tomato\n2: garlic\n3: Aioli\n4: Big Mac\n5: BBQ\n6: Chilli\n7: Ranch\n8: Special");
        input = checkFroQuit(emptyCheck(scan, scan.nextLine()));
        newBurger.setSauce(input);
        while(true) {
            System.out.println("Would you like to add another sauce? (yes or no):");
            String moreSauce = checkFroQuit(emptyCheck(scan, scan.nextLine()));
            if (moreSauce.equalsIgnoreCase("yes")) {
                System.out.println("What sauce would you like on your burger? (please enter a number):\\n1: Tomato\\n2: garlic\\n3: Aioli\\n4: Big Mac\\n5: BBQ\\n6: Chilli\\n7: Ranch\\n8: Special\")");
                newBurger.setSauce(checkFroQuit(emptyCheck(scan, scan.nextLine())));
            } else {
                break;
            }
        }
        return newBurger;
    }

    public static String orderBurger(Geek geek, Burger burger, Menu menu, Scanner scan){
        ArrayList<Burger> matchingBurgers = menu.burgerCompare(burger);
        String order = "";
        if(matchingBurgers.isEmpty()){
            System.out.println("Sorry, there does not seem to be a burger that matches your search, would you like to create a custom order? (yes/no)");
            String input = checkFroQuit(emptyCheck(scan, scan.nextLine()));
            if(input.equalsIgnoreCase("yes")){
                burger.setName("CUSTOM ORDER");
                order = createReceipt(geek, burger);
            } else {
                System.out.println("Would you like to search for another burger? (yes/no)");
                input = checkFroQuit(emptyCheck(scan, scan.nextLine()));
                if(input.equalsIgnoreCase("yes")){
                    Burger newBurger = getBurgerInfo(scan);
                    order = orderBurger(geek, newBurger, menu, scan);
                } else {
                    System.out.println("Thank you for using our service, we hope to see you again soon.");
                    System.exit(0);
                }
            }
        } else {
            for(int i = 0; i < matchingBurgers.size(); i++){
                System.out.println((i + 1) + ": "+ matchingBurgers.get(i) + "\n");
            }
            System.out.println((matchingBurgers.size() + 1) + ": Custom order\n");
            System.out.println("Please enter the number of the burger you would like:");
            int input = Integer.parseInt(getInt(checkFroQuit(emptyCheck(scan, scan.nextLine())), scan));
            int index = matchingBurgers.size() + 1;
            if(input < index){
                System.out.println(createReceipt(geek, matchingBurgers.get(input)));
            } else if (input == index){
                burger.setName("CUSTOM ORDER");
                System.out.println(createReceipt(geek, burger));
            } else {
                System.out.println("wrong number");
            }
        }
        return order;
    }

    public static Geek getGeekInformation(Scanner scan){
        System.out.println("Please enter the name for the order: ");
        String input = checkFroQuit(emptyCheck(scan, scan.nextLine()));
        while(isInteger(input)){
            System.out.println("Invalid input, please try again:");
            input = checkFroQuit(emptyCheck(scan, scan.nextLine()));
        }
        String name = emptyCheck(scan, input);
        int phoneNumber;
        System.out.println("Please enter a contact number for the order: ");
        input = checkFroQuit(emptyCheck(scan, scan.nextLine()));
        while(!isInteger(input) || (input.length() != 10 && !input.startsWith("0"))){
            System.out.println("Invalid input, please try again:");
            input = checkFroQuit(emptyCheck(scan, scan.nextLine()));
        }
        phoneNumber = Integer.parseInt(input);
        return new Geek(name, phoneNumber);
    }

    public static String createReceipt(Geek geek, Burger burger){
        StringBuilder order = new StringBuilder();
        if(burger.getName().equalsIgnoreCase("CUSTOM ORDER")){
            order.append("Order details:");
            order.append("\n    Name: ").append(geek.getName());
            order.append("\n    Order number: ").append(geek.getPhoneNumber());
            order.append("\n    Item: ").append(burger.getName());
            order.append("\n*******************************");
            order.append("\n").append(burger);
        } else {
            order.append("Order details:");
            order.append("\n").append(padLeftString("Name: " + geek.getName()));
            order.append("\n").append(padLeftString("Order number: " + geek.getPhoneNumber()));
            order.append("\n").append(padLeftString("Item: " + burger.getName() + " (" + burger.getId() + ")"));
        }
        try{
            File orderFile = new File("order.txt");
            if(orderFile.createNewFile()){
                System.out.println("File created: " + orderFile.getName());
            } else {
                System.out.println("File already exists.");
            }
            FileWriter writeToFile = new FileWriter(orderFile.getName());
            writeToFile.write(order.toString());
            writeToFile.close();
        } catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return order.toString();
    }

    public static String padLeftString(String string){
        String newString = "";
        for(int i = 0; i < 4; i++){
            newString += " ";
        }
        return newString += string;
    }

    public static String emptyCheck(Scanner scan, String input){
        String newInput = input;
        while(newInput.isEmpty()){
            System.out.println("Nothing was entered, please try again:");
            newInput = checkFroQuit(scan.nextLine());
        }
        return newInput;
    }

    public static boolean isInteger(String potentialNumber){
        char[] array = potentialNumber.toCharArray();
        int isDigit = 0;
        for(char c: array){
            if(Character.isDigit(c)){
                isDigit++;
            }
        }
        if(isDigit < potentialNumber.length()){
            return false;
        }
        return true;
    }

    public static String getInt(String number, Scanner scan) {
        while (!isInteger(number)) {
            System.out.println("Input was invalid, please try again:");
            number = checkFroQuit(emptyCheck(scan, scan.nextLine()));
        }
        return number;
    }

    public static String checkFroQuit(String quit){
        if(quit.equalsIgnoreCase("quit")){
            System.exit(0);
        }
        return quit;
    }
}
