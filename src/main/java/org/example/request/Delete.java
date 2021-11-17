package org.example.request;

import org.example.ConsoleApp;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import java.util.stream.Stream;

public class Delete implements Command{
    private Scanner scanner;

    public Delete(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void handle() {
        while (true){
            ConsoleApp.printMethodInstructions("DELETE");

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
            System.out.println("You can delete a pet by id");

            System.out.println("Please enter the id of a pet");

            long id = 0;
            try {
                id = Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e){
                ConsoleApp.printErrorMessage("You entered an invalid id, try again.");
                continue;
            }

            HttpRequest request = HttpRequest.newBuilder().uri(new URI(url + "/" + id)).DELETE()
                    .header("api_key", "special-key").build();
            try {
                HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());
                System.out.println(response.statusCode());
                response.body().forEach(l -> System.out.println(l));
            } catch (Exception e) {
                ConsoleApp.printErrorMessage("HTTP DELETE request was unsuccessful, try again.");
                continue;
            }
            break;
        }
    }

    private void store() throws URISyntaxException {
        String url = "https://petstore.swagger.io/v2/store";

        HttpClient httpClient = HttpClient.newBuilder().build();

        while (true){
            System.out.println("You can delete an order by id");

            System.out.println("Please enter the id of an order");

            long id = 0;
            try {
                id = Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e){
                ConsoleApp.printErrorMessage("You entered an invalid id, try again.");
                continue;
            }

            HttpRequest request = HttpRequest.newBuilder().uri(new URI(url + "/order/" + id)).DELETE().build();
            try {
                HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());
                System.out.println(response.statusCode());
                response.body().forEach(l -> System.out.println(l));
            } catch (Exception e) {
                ConsoleApp.printErrorMessage("HTTP DELETE request was unsuccessful, try again.");
                continue;
            }
            break;
        }
    }

    private void user() throws URISyntaxException {
        String url = "https://petstore.swagger.io/v2/user";

        HttpClient httpClient = HttpClient.newBuilder().build();

        while (true){
            System.out.println("You can delete a user by the username");

            System.out.println("Print the username of a user");

            String username = scanner.nextLine();
            HttpRequest request = HttpRequest.newBuilder().uri(new URI(url + "/" + username)).DELETE().build();
            try {
                HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());
                System.out.println(response.statusCode());
                response.body().forEach(l -> System.out.println(l));
            } catch (Exception e) {
                ConsoleApp.printErrorMessage("HTTP DELETE request was unsuccessful, try again.");
                continue;
            }


            break;
        }
    }
}
