package org.example.common.requests;

import org.example.common.data.FuelType;
import org.example.common.data.Vehicle;

import java.io.Serializable;

public class CommandRequest implements Serializable {
    private String command;
    private String[] args;
    private Vehicle object;
    private FuelType fuelType;

    public CommandRequest(String command, String[] args, Vehicle object, FuelType fuelType) {
        this.command = command;
        this.args = args;
        this.object = object;
        this.fuelType = fuelType;
    }

    public String[] getArgs() {
        return this.args;
    }

    public String getCommand() {
        return this.command;
    }

    public FuelType getFuelType() {
        return fuelType;
    }
    public Vehicle getObject() {
        return object;
    }
}
