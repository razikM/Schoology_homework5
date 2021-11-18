package org.example;

import org.example.request.*;

import java.util.*;

public class ConsoleApp {
    private static final List<String> requestTypes = Arrays.asList("GET", "POST", "PUT", "DELETE");
    public static final List<String> entities = Arrays.asList("pet", "store", "user");
    public static final List<String> statusList = Arrays.asList("available", "pending", "sold");
    private static final Map<String, Command> commandMap = new HashMap<>();

    public static void run(){
        Scanner scanner = new Scanner(System.in);

        commandMap.putIfAbsent("GET", new Get(scanner));
        commandMap.putIfAbsent("DELETE", new Delete(scanner));
        commandMap.putIfAbsent("PUT", new Put(scanner));
        commandMap.putIfAbsent("POST", new Post(scanner));

        printWelcomeMessage();

        while (true){
            printInstructionForRequest();
            String input = scanner.nextLine().toUpperCase();

            if(input.toLowerCase().equals("exit")){
                System.out.println("Good bye:)");
                break;
            }


            if(!requestTypes.contains(input)){
                printErrorMessage("Your input could not be processed, try again.");
                continue;
            }

            commandMap.get(input).handle();
        }
    }

    private static void printWelcomeMessage(){
        System.out.println("Welcome to the app");
        System.out.println("Here you can send requests via console interface to Swagger API");
        System.out.println("Please follow the instructions");
    }

    private static void printInstructionForRequest(){
        System.out.println("Please enter a request type you want to send");
        System.out.println("The following options are available:");
        System.out.println(requestTypes.toString());
        System.out.println("In order to exit enter: exit");
    }

    public static void printErrorMessage(String msg){
        System.err.println(msg);
    }

    public static void printMethodInstructions(String method){
        System.out.println("You can now send " + method + " requests");
        System.out.println("The following entities are available:");
        System.out.println(entities);
        System.out.println("In order to return to the main console menu enter: exit");
    }
}
