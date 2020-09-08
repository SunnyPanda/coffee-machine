package machine;

public class CoffeeMachineInAction {

    private int mlOfWater;
    private int mlOfMilk;
    private int grOfBeans;
    private int disposableCups;
    private int money;
    private State state;

    CoffeeMachineInAction(int water, int milk, int coffeeBeans, int disposableCups, int money) {
        this.mlOfWater = water;
        this.mlOfMilk = milk;
        this.grOfBeans = coffeeBeans;
        this.disposableCups = disposableCups;
        this.money = money;
        state = State.CHOOSING_ACTION;
    }

    public boolean keepWorking(String input) {
        return !input.equals("exit");
    }

    public void handle(String input) {
        switch (state) {
            case CHOOSING_ACTION: takeAction(input);
                break;
            case CHOOSING_COFFEE: makeCoffee(input);
                break;
            case FILLING_WATER: fillingWater(input);
                break;
            case FILLING_MILK: fillingMilk(input);
                break;
            case FILLING_COFFEE_BEANS: fillingCoffeeBeans(input);
                break;
            case FILLING_DISPOSABLE_CUPS: fillingDisposableCups(input);
        }
    }

    private void takeAction(String action) {
        switch (action) {
            case "buy":
                buy();
                break;
            case "fill":
                fill();
                break;
            case "take":
                takeMoney();
                break;
            case "remaining":
                remaining();
        }
    }

    private void buy() {
        changeState(State.CHOOSING_COFFEE);
        askNext();
    }

    private void makeCoffee(String typeOfCoffee) {
        switch (typeOfCoffee) {
            case "1":
                makeSpecificCoffee(Coffee.ESPRESSO);
                break;
            case "2":
                makeSpecificCoffee(Coffee.LATTE);
                break;
            case "3":
                makeSpecificCoffee(Coffee.CAPPUCCINO);
                break;
            case "back":
                changeState(State.CHOOSING_ACTION);
                askNext();
        }
    }

    private void fill() {
        changeState(State.FILLING_WATER);
        askNext();
    }

    private void fillingWater(String amountOfWater) {
        mlOfWater += Integer.parseInt(amountOfWater);

        changeState(State.FILLING_MILK);
        askNext();
    }

    private void fillingMilk(String amountOfMilk) {
        mlOfMilk += Integer.parseInt(amountOfMilk);

        changeState(State.FILLING_COFFEE_BEANS);
        askNext();
    }

    private void fillingCoffeeBeans(String amountOfCoffeeBeans) {
        grOfBeans += Integer.parseInt(amountOfCoffeeBeans);

        changeState(State.FILLING_DISPOSABLE_CUPS);
        askNext();
    }

    private void fillingDisposableCups(String amountOfDisposableCups) {
        disposableCups += Integer.parseInt(amountOfDisposableCups);

        changeState(State.CHOOSING_ACTION);
        askNext();
    }

    private void takeMoney() {
        System.out.printf("I gave you $%d%n", money);
        money = 0;

        askNext();
    }

    private void remaining() {
        System.out.println();
        System.out.println("The coffee machine has:");
        System.out.printf("%d of water\n", mlOfWater);
        System.out.printf("%d of milk\n", mlOfMilk);
        System.out.printf("%d of coffee beans\n", grOfBeans);
        System.out.printf("%d of disposable cups\n", disposableCups);
        System.out.printf("%d of money\n", money);

        askNext();
    }

    private void makeSpecificCoffee(Coffee typeOfCoffee) {
        if (isEnoughSupplies(typeOfCoffee)) {
            System.out.println("I have enough resources, making you a coffee!");
            mlOfWater -= typeOfCoffee.getWater();
            mlOfMilk -= typeOfCoffee.getMilk();
            grOfBeans -= typeOfCoffee.getBeans();
            disposableCups--;
            money += typeOfCoffee.getCost();
        } else {
            String missingIngredient = lackOfSupply(typeOfCoffee);
            System.out.printf("Sorry, not enough %s!%n", missingIngredient);
        }

        changeState(State.CHOOSING_ACTION);
        askNext();
    }

    private void changeState(State state) {
        this.state = state;
    }

    private void askNext() {
        switch (state) {
            case CHOOSING_ACTION:
                System.out.println();
                System.out.println("Write action (buy, fill, take, remaining, exit):");
                break;
            case CHOOSING_COFFEE:
                System.out.println();
                System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, " +
                        "back - to main menu: ");
                break;
            case FILLING_WATER:
                System.out.println();
                System.out.println("Write how many ml of water do you want to add: ");
                break;
            case FILLING_MILK:
                System.out.println("Write how many ml of milk do you want to add: ");
                break;
            case FILLING_COFFEE_BEANS:
                System.out.println("Write how many grams of coffee beans do you want to add: ");
                break;
            case FILLING_DISPOSABLE_CUPS:
                System.out.println("Write how many disposable cups of coffee do you want to add: ");
        }
    }

    private boolean isEnoughSupplies(Coffee typeOfCoffee) {

        return isEnoughCups() && isEnoughWater(typeOfCoffee) && isEnoughMilk(typeOfCoffee)
                && isEnoughMilk(typeOfCoffee);
    }

    private String lackOfSupply(Coffee typeOfCoffee) {

        String missingIngredient = "";
        if (!isEnoughCups()) {
            missingIngredient = "cups";
        } else if (!isEnoughWater(typeOfCoffee)) {
            missingIngredient = "water";
        } else if (!isEnoughMilk(typeOfCoffee)) {
            missingIngredient = "milk";
        } else if (!isEnoughBeans(typeOfCoffee)) {
            missingIngredient = "beans";
        }

        return missingIngredient;
    }

    private boolean isEnoughWater(Coffee typeOfCoffee) {

        return mlOfWater - typeOfCoffee.getWater() >= 0;
    }

    private boolean isEnoughMilk(Coffee typeOfCoffee) {

        return mlOfMilk - typeOfCoffee.getMilk() >= 0;
    }

    private boolean isEnoughBeans(Coffee typeOfCoffee) {

        return grOfBeans - typeOfCoffee.getBeans() >= 0;
    }

    private boolean isEnoughCups() {

        return disposableCups > 0;
    }
}
