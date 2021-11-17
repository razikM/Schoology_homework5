package org.example.request;

import org.example.ConsoleApp;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import java.util.stream.Stream;

public class Get implements Command{

    private Scanner scanner;

    public Get(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void handle() {
        while (true){
            ConsoleApp.printMethodInstructions("GET");

            String input = scanner.nextLine().toLowerCase();

            if(input.equals("exit")){
                System.out.println("You will be redirected to main console menu");
                break;
            }

            if(!ConsoleApp.entities.contains(input)){
                ConsoleApp.printErrorMessage("Your input could not be processed, try again.");
            }

            switch (input) {
                case "pet":
                    try {
                        pet();
                    } catch (URISyntaxException e) {
                        ConsoleApp.printErrorMessage("Your request could not be processed, try again.");
                    }
                    break;
                case "store":
                    try {
                        store();
                    } catch (URISyntaxException e) {
                        ConsoleApp.printErrorMessage("Your request could not be processed, try again.");
                    }
                    break;
                case "user":
                    try {
                        user();
                    } catch (URISyntaxException e) {
                        ConsoleApp.printErrorMessage("Your request could not be processed, try again.");
                    }
                    break;
            }
        }
    }

    private void pet() throws URISyntaxException {
        String url = "https://petstore.swagger.io/v2/pet";

        HttpClient httpClient = HttpClient.newBuilder().build();

        while (true){
            System.out.println("You can get pet by id or status");
            System.out.println("To get a pet by id print id");
            System.out.println("Otherwise print status");

            String input = scanner.nextLine().toLowerCase();

            if(!(input.equals("id") || input.equals("status"))){
                ConsoleApp.printErrorMessage("Your input could not be processed, try again.");
                continue;
            }

            if(input.equals("id")){
                System.out.println("Please enter the id of a pet");

                long id = 0;
                try {
                   id = Long.parseLong(scanner.nextLine());
                } catch (NumberFormatException e){
                    ConsoleApp.printErrorMessage("You entered an invalid id, try again.");
                    continue;
                }

                HttpRequest request = HttpRequest.newBuilder().uri(new URI(url + "/" + id)).build();
                try {
                    HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());
                    System.out.println(response.statusCode());
                    response.body().forEach(l -> System.out.println(l));
                } catch (Exception e) {
                    ConsoleApp.printErrorMessage("HTTP GET request was unsuccessful, try again.");
                    continue;
                }
            } else {
                System.out.println("Please enter the status of a pet");
                System.out.println("The following status options are available");
                System.out.println(ConsoleApp.statusList);

                String status = scanner.nextLine().toLowerCase();

                 if (!ConsoleApp.statusList.contains(status)){
                    ConsoleApp.printErrorMessage("You entered an invalid status, try again.");
                    continue;
                }

                HttpRequest request = HttpRequest.newBuilder().uri(new URI(url + "/findByStatus?status=" + status)).build();
                try {
                    HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());
                    System.out.println(response.statusCode());
                    response.body().forEach(l -> System.out.println(l));
                } catch (Exception e) {
                    ConsoleApp.printErrorMessage("HTTP GET request was unsuccessful, try again.");
                    continue;
                }
            }

            break;
        }
    }

    private void store() throws URISyntaxException {
        String url = "https://petstore.swagger.io/v2/store";

        HttpClient httpClient = HttpClient.newBuilder().build();

        while (true){
            System.out.println("You can get inventories per status");
            System.out.println("You can find order by id");
            System.out.println("For the first print inventory");
            System.out.println("For the second print order");

            String input = scanner.nextLine().toLowerCase();

            if(!(input.equals("inventory") || input.equals("order"))){
                ConsoleApp.printErrorMessage("Your input could not be processed, try again.");
                continue;
            }

            if(input.equals("inventory")){
                HttpRequest request = HttpRequest.newBuilder().uri(new URI(url + "/inventory")).build();
                try {
                    HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());
                    System.out.println(response.statusCode());
                    response.body().forEach(l -> System.out.println(l));
                } catch (Exception e) {
                    ConsoleApp.printErrorMessage("HTTP GET request was unsuccessful, try again.");
                    continue;
                }
            } else {
                System.out.println("Please enter the id of an order");

                long id = 0;
                try {
                    id = Long.parseLong(scanner.nextLine());
                } catch (NumberFormatException e){
                    ConsoleApp.printErrorMessage("You entered an invalid id, try again.");
                    continue;
                }

                HttpRequest request = HttpRequest.newBuilder().uri(new URI(url + "/order/" + id)).build();
                try {
                    HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());
                    System.out.println(response.statusCode());
                    response.body().forEach(l -> System.out.println(l));
                } catch (Exception e) {
                    ConsoleApp.printErrorMessage("HTTP GET request was unsuccessful, try again.");
                    continue;
                }
            }

            break;
        }
    }

    private void user() throws URISyntaxException {
        String url = "https://petstore.swagger.io/v2/user";

        HttpClient httpClient = HttpClient.newBuilder().build();

        while (true){
            System.out.println("You can get user by the username");
            System.out.println("You can log in into the system with username and password");
            System.out.println("You can log out from the system");
            System.out.println("For the first print get");
            System.out.println("For the second print login");
            System.out.println("For the second print logout");

            String input = scanner.nextLine().toLowerCase();

            if(!(input.equals("get") || input.equals("login") || input.equals("logout"))){
                ConsoleApp.printErrorMessage("Your input could not be processed, try again.");
                continue;
            }

            if(input.equals("get")){
                System.out.println("Print the username of a user");
                String username = scanner.nextLine();
                HttpRequest request = HttpRequest.newBuilder().uri(new URI(url + "/" + username)).build();
                try {
                    HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());
                    System.out.println(response.statusCode());
                    response.body().forEach(l -> System.out.println(l));
                } catch (Exception e) {
                    ConsoleApp.printErrorMessage("HTTP GET request was unsuccessful, try again.");
                    continue;
                }
            } else if(input.equals("login")) {
                System.out.println("Print the username of a user");
                String username = scanner.nextLine();
                System.out.println("Print the username of a user");
                String password = scanner.nextLine();

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI(url + "/login?username=" + username + "&password=" + password)).build();
                try {
                    HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());
                    System.out.println(response.statusCode());
                    response.body().forEach(l -> System.out.println(l));
                } catch (Exception e) {
                    ConsoleApp.printErrorMessage("HTTP GET request was unsuccessful, try again.");
                    continue;
                }
            } else {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI(url + "/logout")).build();
                try {
                    HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());
                    System.out.println(response.statusCode());
                    response.body().forEach(l -> System.out.println(l));
                } catch (Exception e) {
                    ConsoleApp.printErrorMessage("HTTP GET request was unsuccessful, try again.");
                    continue;
                }
            }

            break;
        }
    }
}
