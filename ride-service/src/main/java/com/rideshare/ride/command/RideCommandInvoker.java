package com.rideshare.ride.command;

import org.springframework.stereotype.Component;
import java.util.Stack;

/**
 * Command Pattern - Invoker
 * Manages command execution and maintains undo history
 */
@Component
public class RideCommandInvoker {

    // Stack to store executed commands for undo functionality
    private Stack<RideCommand> commandHistory = new Stack<>();

    /**
     * Execute a command and store it in history for undo
     */
    public void executeCommand(RideCommand command) {
        System.out.println("\n→ Executing: " + command.getDescription());
        try {
            command.execute();
            commandHistory.push(command);
            System.out.println("✓ Command stored in history (Total: " + commandHistory.size() + " commands)");
        } catch (Exception e) {
            System.out.println("Command execution failed: " + e.getMessage());
            throw new RuntimeException("Command execution failed", e);
        }
    }

    /**
     * Undo the last executed command
     */
    public void undoLastCommand() {
        if (!commandHistory.isEmpty()) {
            RideCommand command = commandHistory.pop();
            System.out.println("\n↶ Undoing: " + command.getDescription());
            try {
                command.undo();
                System.out.println("✓ Undo successful (Remaining: " + commandHistory.size() + " commands)");
            } catch (Exception e) {
                System.out.println("Undo failed: " + e.getMessage());
                throw new RuntimeException("Undo failed", e);
            }
        } else {
            System.out.println("\n✗ No commands to undo");
        }
    }

    /**
     * Get the number of commands in history
     */
    public int getHistorySize() {
        return commandHistory.size();
    }

    /**
     * Clear command history
     */
    public void clearHistory() {
        commandHistory.clear();
        System.out.println("Command history cleared");
    }
}