package VFS.Parser;

import VFS.Allocator.Allocator;
import VFS.VFileManager.VirtualFileSystem;

import java.io.IOException;
import java.util.Map;
import static java.util.Map.entry;
public class Parser {
    VirtualFileSystem vfs;
    // a map of the <commands, numberOfArguments>
    private final Map<String, Integer> cmdArgs = Map.ofEntries(
            entry("CreateFile", 3),
            entry("CreateFolder", 1),
            entry("DeleteFile", 1),
            entry("DeleteFolder", 1),
            entry("DisplayDiskStatus", 0),
            entry("DisplayDiskStructure", 0)
    );

    public Parser(Allocator allocator) throws IOException {
        this.vfs = new VirtualFileSystem(allocator);
    }

    /**
     * Validate the given String of command and arguments.
     * Print a help message if the command is not found.
     * <p>Print a help message with its usage if the command is found,
     * but the number of arguments don't match.
     * @param command command to validate
     */
    public void validate(String command) {
        String cmd = command.split(" ")[0];
        if (!cmdArgs.containsKey(cmd))
            help();
        else
        {
            int spaceOne = command.indexOf(" ");
            String[] args = command.substring(spaceOne+1).split(" ");
            int argsLen = command.substring(1).split(" ").length -1;
            if (argsLen != cmdArgs.get(cmd))
            {
                helpArg(cmd);
            }
            else {
                this.vfs.execute(cmd, args, cmdArgs.get(cmd));
            }
        }
    }

    /**
     * Print the usage for a specific command.
     * <p>Printed if the given command was found in the existing commands list and,
     * the number of arguments don't match.
     * @param cmd given command
     */
    private void helpArg(String cmd) {
        if (cmdArgs.get(cmd) == 0)
            System.out.println("\nUsage: " + cmd);
        else if (cmdArgs.get(cmd) == 1)
            System.out.println("\nUsage: " + cmd + " Path");
        else
            System.out.println("\nUsage: " + cmd + " Path Size");
    }

    /**
     * Output a generalized help for all commands.
     * <p>Printed if the given command doesn't exist in the commands list.
     */
    public void help() {
        System.out.println("\nYou are allowed to use only these commands: \n");
        for(String command: cmdArgs.keySet())
            System.out.println("* " + command);
        System.out.println("* q to quit");
    }
}
