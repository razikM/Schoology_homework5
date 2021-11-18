package org.example.request;

import org.example.ConsoleApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.stream.Stream;

public class Put implements Command{

    private Scanner scanner;

    public Put(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void handle() {
        while (true){
            ConsoleApp.printMethodInstructions("PUT");

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
                    store();
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

    private void store() {
        ConsoleApp.printErrorMessage("Unfortunately swagger.io does not provide" +
                " the update possibilities for store entity");
    }


    private void pet() throws URISyntaxException{
        String url = "https://petstore.swagger.io/v2/pet";

        HttpClient httpClient = HttpClient.newBuilder().build();

        while (true){
            System.out.println("You can update your pet");
            System.out.println("Please provide the request body in Json format in a text file and specify the path");
            System.out.println("Please enter the path");

            String path = scanner.nextLine();

            try {
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                        .PUT(HttpRequest.BodyPublishers.ofFile(Path.of(path)))
                        .header("Content-Type","application/json").build();

                HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());
                System.out.println(response.statusCode());
                response.body().forEach(l -> System.out.println(l));
            } catch (FileNotFoundException e) {
                ConsoleApp.printErrorMessage("The file could not be found, try again.");
                continue;
            } catch (Exception e) {
                ConsoleApp.printErrorMessage("HTTP DELETE request was unsuccessful, try again.");
                continue;
            }

            break;
        }
    }

    private void user() throws URISyntaxException{
        String url = "https://petstore.swagger.io/v2/user";

        HttpClient httpClient = HttpClient.newBuilder().build();

        while (true){
            System.out.println("You can update a user by the username");
            System.out.println("Print the username of a user");
            String username = scanner.nextLine();

            System.out.println("Please provide the request body in Json format in a text file and specify the path");
            System.out.println("Please enter the path");

            String path = scanner.nextLine();

            try {
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url + "/" + username))
                        .PUT(HttpRequest.BodyPublishers.ofFile(Path.of(path)))
                        .header("Content-Type","application/json").build();

                HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());
                System.out.println(response.statusCode());
                response.body().forEach(l -> System.out.println(l));
            } catch (FileNotFoundException e) {
                ConsoleApp.printErrorMessage("The file could not be found, try again.");
                continue;
            } catch (Exception e) {
                ConsoleApp.printErrorMessage("HTTP DELETE request was unsuccessful, try again.");
                continue;
            }

            break;
        }
    }
}
