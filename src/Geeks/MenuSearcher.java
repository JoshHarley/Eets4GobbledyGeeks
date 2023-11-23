package Geeks;

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
        System.out.println("Please enter desired burger information:");
        System.out.println("What type of bun would you like? (please enter a number)\n1: Brioche\n2: Light Rye\n3: Signature\n4: Multigrain\n5: Wholemeal\n6: Rye");
        newBurger.setBun(scan.nextLine());
        System.out.println("What protein would you like on your burger? (please enter a number)?\n1: Beef\n2: Chicken\n3: Vegan");
        newBurger.setPattie(scan.nextLine());
        System.out.println("Do you want cheese on your burger? (please type yes or no)");
        newBurger.setCheese(scan.nextLine());
        System.out.println("Do you want pickles on your burger? (please type yes or no)");
        newBurger.setPickles(scan.nextLine());
        System.out.println("What sauce would you like on your burger? (please enter a number):\n1: Tomato\n2: garlic\n3: Aioli\n4: Big Mac\n5: BBQ\n6: Chilli\n7: Ranch\n8: Special");
        newBurger.setSauce(scan.nextLine());
        while(true) {
            System.out.println("Would you like to add another sauce? (yes or no):");
            String input = scan.nextLine();
            if (input.equalsIgnoreCase("yes")) {
                System.out.println("What sauce would you like on your burger? (please enter a number):\\n1: Tomato\\n2: garlic\\n3: Aioli\\n4: Big Mac\\n5: BBQ\\n6: Chilli\\n7: Ranch\\n8: Special\")");
                newBurger.setSauce(scan.nextLine());
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
            String input = scan.nextLine();
            if(input.equalsIgnoreCase("yes")){
                burger.setName("CUSTOM ORDER");
                order = createReceipt(geek, burger);
            } else {
                System.out.println("Would you like to search for another burger? (yes/no)");
                input = scan.nextLine();
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
            int input = Integer.valueOf(scan.nextLine());
            int index = matchingBurgers.size();
            if(input <= index){
                System.out.println(createReceipt(geek, matchingBurgers.get(input)));
            } else if (input == index + 1){
                Burger newBurger = getBurgerInfo(scan);
                System.out.println(createReceipt(geek, newBurger);
            } else {
                System.out.println("wrong number");
            }
        }
        return order;
    }

    public static Geek getGeekInformation(Scanner scan){
        System.out.println("Please enter the name for the order: ");
        String name = scan.nextLine();
        System.out.println("Please enter a contact number for the order: ");
        int phoneNumber = Integer.valueOf(scan.nextLine());
        return new Geek(name, phoneNumber);
    }

    public static String createReceipt(Geek geek, Burger burger){
        StringBuilder customOrder = new StringBuilder();
        customOrder.append("Order details:");
        customOrder.append("\n    Name: " + geek.getName());
        customOrder.append("\n    Order number: " + geek.getPhoneNumber());
        customOrder.append("\n    Item: " + burger.getName());
        customOrder.append("\n*******************************");
        customOrder.append("\n" + burger.toString());
        return customOrder.toString();
    }
}
