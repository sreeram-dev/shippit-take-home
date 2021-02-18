package com.interview.shippit.cli;

import com.interview.shippit.adapter.family.FamilyAdapter;
import com.interview.shippit.db.InMemoryFamilyMemberRepository;
import com.interview.shippit.cli.controller.CLIFamilyController;
import com.interview.shippit.family.usecase.AddFamilyMemberUseCase;
import com.interview.shippit.family.usecase.GetRelationshipUseCase;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class CLIApplication {

    // TODO: replace with a configuration as per clean architecture
    private static final InMemoryFamilyMemberRepository repository = new InMemoryFamilyMemberRepository();

    private static final FamilyAdapter adapter = new FamilyAdapter(
            new AddFamilyMemberUseCase(repository), new GetRelationshipUseCase(repository));

    private static final CLIFamilyController controller  = new CLIFamilyController(adapter);

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Requires file parameter");
            return;
        }

        loadInitialData(controller);

        try (Stream<String> stream = Files.lines(Paths.get(args[0]))){
            stream.forEach(CLIApplication::processLine);
        } catch (FileNotFoundException ex) {
            System.out.println("File Not found");
        } catch (IOException e) {
            e.printStackTrace();
        } catch(IllegalArgumentException ex) {
            System.out.println("Invalid file");
        }
    }

    private static void processLine(String line) {
        //System.out.println("Command: " + line);
        String[] command = line.split(" ");
        //Arrays.stream(command).forEach(System.out::println);

        if (command.length < 2) {
            throw new IllegalArgumentException("Invalid File");
        }

        if (command[0].equals("ADD_CHILD")) {
            String output = controller.addFamilyMember(command[1], command[2], command[3]);
            System.out.println(output);
        } else if (command[0].equals("GET_RELATIONSHIP")) {
            String output = controller.getRelationship(command[1], command[2]);
            System.out.println(output);
        } else {
            throw new IllegalArgumentException("Invalid File");
        }
    }

    public static void loadInitialData(CLIFamilyController controller) {

        controller.createNoParentFamilyMember("Arthur", "Male");
        controller.addIndividualPartner("Margaret", "Female", "Arthur");

        // immediate members
        controller.addFamilyMember("Margaret", "Percy", "Male");
        controller.addFamilyMember("Margaret", "Charlie", "Male");
        controller.addFamilyMember("Margaret", "Ronald", "Male");
        controller.addFamilyMember("Margaret", "Bill", "Male");
        controller.addFamilyMember("Margaret", "Ginerva", "Female");
        controller.addIndividualPartner("Flora", "Female","Bill");
        controller.addIndividualPartner("Audrey","Female", "Percy");
        controller.addIndividualPartner("Helen", "Female","Ronald");
        controller.addIndividualPartner("Harry", "Male", "Ginerva");

        // bill family
        controller.addFamilyMember("Flora", "Dominique", "Female");
        controller.addFamilyMember("Flora", "Louis", "Male");
        controller.addFamilyMember("Flora", "Victoire", "Female");
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
        controller.addIndividualPartner("Alice", "Female", "Albus");
        controller.addIndividualPartner("Darcy", "Female", "James");
        controller.addFamilyMember("Darcy", "William", "Male");
        controller.addFamilyMember("Alice", "Ron", "Male");
        controller.addFamilyMember("Alice", "Ginny", "Female");

        //repository.getAllMembersStoredInMemory().forEach(System.out::println);
    }
}
