import VFS.Allocator.Allocator;
import VFS.Allocator.Contiguous;
import VFS.Allocator.Indexed;
import VFS.Allocator.Linked;
import VFS.Parser.Parser;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Parser parser = null;
        Scanner input = new Scanner(System.in);

        int allocation; // Allocation method
        int blocks; // Number of blocks
        Allocator allocator;

        System.out.print("1. Contiguous Allocation\n" +
                "2. Indexed Allocation\n" +
                "3. Linked Allocation\n\n" +
                "==> Choice(1 - 3): ");
        allocation = input.nextInt();

        while (allocation < 1 || allocation > 3) {
            System.out.println("Invalid input!");
            System.out.print("==> Choice(1 - 3): ");
            allocation = input.nextInt();
        }
        System.out.print("\nNumber of blocks: ");

        blocks = input.nextInt();
        while (blocks < 0) {
            System.out.println("The number of blocks can't be negative");
            System.out.print("\n==>Number of blocks: ");
            blocks = input.nextInt();
        }
        switch (allocation)
        {
            case 1 -> {
                allocator = new Contiguous(blocks);
                parser = new Parser(allocator);
            }

            case 2 -> {
                allocator = new Indexed(blocks);
                parser = new Parser(allocator);
            }
            case 3 -> {
                allocator = new Linked(blocks);
                parser = new Parser(allocator);
            }
        }

        String command;
        input = new Scanner(System.in);
        System.out.print("\n--> ");
        while (input.hasNext()) {
            command = input.nextLine();
            if (command.equalsIgnoreCase("q")) break;
            parser.validate(command);
            System.out.print("\n--> ");
        }
    }
}
