package com.rideshare.ride.command;

public interface RideCommand {

    /**
     * Execute the command
     */
    void execute();

    /**
     * Undo the command
     */
    void undo();

    /**
     * Get command description for logging
     */
    String getDescription();
}