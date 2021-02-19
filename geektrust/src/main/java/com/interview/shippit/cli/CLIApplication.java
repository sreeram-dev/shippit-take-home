package com.interview.shippit.cli;

import com.interview.shippit.adapter.family.FamilyAdapter;
import com.interview.shippit.db.InMemoryFamilyMemberRepository;
import com.interview.shippit.cli.controller.CLIFamilyController;
import com.interview.shippit.family.usecase.AddFamilyMemberUseCase;
import com.interview.shippit.family.usecase.GetRelationshipUseCase;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Driver application that prints successful command execution results to stdout
 * Failures/Exceptions to stderr.
 *
 * Stops execution on an invalid command.
 */
public class CLIApplication {

    // TODO: replace with a configuration as per clean architecture
    private static final InMemoryFamilyMemberRepository repository = new InMemoryFamilyMemberRepository();

    private static final FamilyAdapter adapter = new FamilyAdapter(
            new AddFamilyMemberUseCase(repository), new GetRelationshipUseCase(repository));

    private static final CLIFamilyController controller  = new CLIFamilyController(adapter);

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("ERROR: Requires file parameter");
            return;
        }

        repository.clearData();
        loadInitialData(controller);

        try (Stream<String> stream = Files.lines(Paths.get(args[0]))){
            stream.forEach(CLIApplication::processLine);
        } catch (IOException ex) {
            System.err.println("ERROR: File not found");
        } catch (IllegalArgumentException ex) {
            System.err.println("ERROR: " + ex.getMessage());
        }
    }

    /**
     * Processes each command and prints to stdout on non-failure cases
     * @param line
     */
    private static void processLine(String line) {
        String[] command = line.split(" ");

        if (command.length < 2) {
            throw new IllegalArgumentException("Invalid Command found in File");
        }

        if (command[0].equals("ADD_CHILD")) {
            String output = controller.addFamilyMember(command[1], command[2], command[3]);
            System.out.println(output);
        } else if (command[0].equals("GET_RELATIONSHIP")) {
            String output = controller.getRelationship(command[1], command[2]);
            System.out.println(output);
        } else {
            throw new IllegalArgumentException("Invalid Command found in File");
        }
    }

    public static void loadInitialData(CLIFamilyController controller) {

        controller.createNoParentFamilyMember("Arthur", "Male");
        controller.addIndividualPartner("Margret", "Female", "Arthur");

        // immediate members
        controller.addFamilyMember("Margret", "Percy", "Male");
        controller.addFamilyMember("Margret", "Charlie", "Male");
        controller.addFamilyMember("Margret", "Ronald", "Male");
        controller.addFamilyMember("Margret", "Bill", "Male");
        controller.addFamilyMember("Margret", "Ginerva", "Female");
        controller.addIndividualPartner("Flora", "Female","Bill");
        controller.addIndividualPartner("Audrey","Female", "Percy");
        controller.addIndividualPartner("Helen", "Female","Ronald");
        controller.addIndividualPartner("Harry", "Male", "Ginerva");


        // bill family
        controller.addFamilyMember("Flora", "Victoire", "Female");
        controller.addFamilyMember("Flora", "Dominique", "Female");
        controller.addFamilyMember("Flora", "Louis", "Male");
        controller.addIndividualPartner("Ted", "Male", "Victoire");
        controller.addFamilyMember("Victoire", "Remus", "Male");

        // percy family
        controller.addFamilyMember("Audrey", "Molly", "Female");
        controller.addFamilyMember("Audrey", "Lucy", "Female");

        // helen family
        controller.addFamilyMember("Helen", "Rose", "Female");
        controller.addFamilyMember("Helen", "Hugo", "Male");
        controller.addIndividualPartner("Malfoy", "Male", "Rose");
        controller.addFamilyMember("Rose", "Draco", "Male");
        controller.addFamilyMember("Rose", "Aster", "Female");

        // Ginerva family
        controller.addFamilyMember("Ginerva", "James", "Male");
        controller.addFamilyMember("Ginerva", "Albus", "Male");
        controller.addFamilyMember("Ginerva", "Lily", "Female");
        controller.addIndividualPartner("Darcy", "Female", "James");
        controller.addIndividualPartner("Alice", "Female", "Albus");

        controller.addFamilyMember("Darcy", "William", "Male");
        controller.addFamilyMember("Alice", "Ron", "Male");
        controller.addFamilyMember("Alice", "Ginny", "Female");
        //repository.getAllMembersStoredInMemory().forEach(System.out::println);
    }
}
