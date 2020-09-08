package machine;

import java.util.Scanner;

public class CoffeeMachine {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CoffeeMachineInAction coffeeMachine = new CoffeeMachineInAction(400, 540, 120,
                9, 550);
        System.out.println("Write action (buy, fill, take, remaining, exit):");
        String input = scanner.next();
        while (coffeeMachine.keepWorking(input)) {
            coffeeMachine.handle(input);
            input = scanner.next();
        }
    }
}