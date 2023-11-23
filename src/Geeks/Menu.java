package Geeks;

import java.util.ArrayList;

public class Menu {
    ArrayList<Burger> menu;

    public Menu() {
        this.menu = new ArrayList<>();
    }

    public ArrayList<Burger> burgerCompare(Burger dreamBurger) {
        ArrayList<Burger> matches = new ArrayList<>();
        for (Burger burger : menu) {
            if (burger.equals(dreamBurger)) {
                matches.add(burger);
            }
        }
        return matches;
    }

    public void addBurgerToMenu(Burger newBurger) {
        menu.add(newBurger);
    }
}
