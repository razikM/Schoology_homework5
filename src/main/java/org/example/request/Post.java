package org.example.request;

import org.example.ConsoleApp;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.stream.Stream;

public class Post implements Command{

    private Scanner scanner;

    public Post(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void handle() {
        while (true){
            ConsoleApp.printMethodInstructions("POST");

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
            System.out.println("You can upload a picture of your pet");
            System.out.println("You can create a pet by specifying a path to a Json file");
            System.out.println("You can update an existing pet by specifying its id new name and new status");
            System.out.println("For the first option print picture");
            System.out.println("For the second print create");
            System.out.println("For the third print update");

            String input = scanner.nextLine().toLowerCase();

            if(!(input.equals("picture") || input.equals("create") || input.equals("update"))){
                ConsoleApp.printErrorMessage("Your input could not be processed, try again.");
                continue;
            }

            if(input.equals("update")){
                System.out.println("Please enter the id of a pet");

                long id = 0;
                try {
                    id = Long.parseLong(scanner.nextLine());
                } catch (NumberFormatException e){
                    ConsoleApp.printErrorMessage("You entered an invalid id, try again.");
                    continue;
                }

                System.out.println("Please enter the new name of a pet");
                String name = scanner.nextLine();

                System.out.println("Please enter the new status of a pet");
                String status = scanner.nextLine().toLowerCase();

                if (!ConsoleApp.statusList.contains(status)){
                    ConsoleApp.printErrorMessage("Wrong status! Try again.");
                    continue;
                }

                HttpRequest request = HttpRequest.newBuilder().uri(new URI(url + "/" + id))
                        .POST(HttpRequest.BodyPublishers.ofString("name=" + name + "&status=" + status))
                        .header("Content-Type", "application/x-www-form-urlencoded").build();
                try {
                    HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());
                    System.out.println(response.statusCode());
                    response.body().forEach(l -> System.out.println(l));
                } catch (Exception e) {
                    ConsoleApp.printErrorMessage("HTTP POST request was unsuccessful, try again.");
                    continue;
                }
            } else if (input.equals("picture")){
                ConsoleApp.printErrorMessage("Picture functionality might not work. We are working on it.");
                System.out.println("Please enter the id of a pet");

                long id = 0;
                try {
                    id = Long.parseLong(scanner.nextLine());
                } catch (NumberFormatException e){
                    ConsoleApp.printErrorMessage("You entered an invalid id, try again.");
                    continue;
                }

                System.out.println("Please specify the path to a picture");
                String path = scanner.nextLine().toLowerCase();

                try {
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(new URI(url + "/" + id + "/uploadImage"))
                            .header("Content-Type", "image/png")
                            .POST(HttpRequest.BodyPublishers.ofFile(Path.of(path))).build();

                    HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());
                    System.out.println(response.statusCode());
                    response.body().forEach(l -> System.out.println(l));
                }catch (FileNotFoundException e) {
                    ConsoleApp.printErrorMessage("The specified file could not be found, try again.");
                    continue;
                } catch (Exception e) {
                    ConsoleApp.printErrorMessage("HTTP GET request was unsuccessful, try again.");
                    continue;
                }
            } else {
                System.out.println("Please specify the path to a user json file");
                String path = scanner.nextLine().toLowerCase();

                try {
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(new URI(url))
                            .header("Content-Type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofFile(Path.of(path))).build();

                    HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());
                    System.out.println(response.statusCode());
                    response.body().forEach(l -> System.out.println(l));
                }catch (FileNotFoundException e) {
                    ConsoleApp.printErrorMessage("The specified file could not be found, try again.");
                    continue;
                } catch (Exception e) {
                    ConsoleApp.printErrorMessage("HTTP GET request was unsuccessful, try again.");
                    continue;
                }
            }

            break;
        }
    }

    private void store() throws URISyntaxException {
        String url = "https://petstore.swagger.io/v2/store/order";

        HttpClient httpClient = HttpClient.newBuilder().build();

        while (true){
            System.out.println("You can create order by specifying the order json file path");
            System.out.println("Please enter the path");
            String path = scanner.nextLine().toLowerCase();

            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI(url))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofFile(Path.of(path))).build();

                HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());
                System.out.println(response.statusCode());
                response.body().forEach(l -> System.out.println(l));
            }catch (FileNotFoundException e) {
                ConsoleApp.printErrorMessage("The specified file could not be found, try again.");
                continue;
            } catch (Exception e) {
                ConsoleApp.printErrorMessage("HTTP GET request was unsuccessful, try again.");
                continue;
            }

            break;
        }
    }

    private void user() throws URISyntaxException {
        String url = "https://petstore.swagger.io/v2/user";

        HttpClient httpClient = HttpClient.newBuilder().build();

        while (true){
            System.out.println("You can create one or multiple users by specifying the path to a json file");
            System.out.println("To create one user print one");
            System.out.println("To create multiple users print multiple");

            String input = scanner.nextLine().toLowerCase();

            if(!(input.equals("one") || input.equals("multiple"))){
                ConsoleApp.printErrorMessage("Your input could not be processed. Try again.");
                continue;
            }

            if(input.equals("multiple")){
                url += "/createWithArray";
            }

            System.out.println("Please print the path to the json file");

            String path = scanner.nextLine();

            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI(url))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofFile(Path.of(path))).build();

                HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());
                System.out.println(response.statusCode());
                response.body().forEach(l -> System.out.println(l));
            }catch (FileNotFoundException e) {
                ConsoleApp.printErrorMessage("The specified file could not be found, try again.");
                continue;
            } catch (Exception e) {
                ConsoleApp.printErrorMessage("HTTP GET request was unsuccessful, try again.");
                continue;
            }

            break;
        }
    }
}
