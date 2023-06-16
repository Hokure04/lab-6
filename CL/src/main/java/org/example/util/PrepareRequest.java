package org.example.util;

import org.example.common.data.FuelType;
import org.example.common.data.Vehicle;
import org.example.common.exceptions.TroubleObjectCreationException;
import org.example.common.requests.CommandRequest;
import org.example.common.requests.StartRequest;
import org.example.common.exceptions.UnknownCommandException;
import org.example.managers.QueriesHandler;
import org.example.managers.Validator;

import java.util.NoSuchElementException;

public class PrepareRequest {
    private StartRequest startRequest;
    private QueriesHandler queriesHandler;
    private VehicleBuilder builder;
    public PrepareRequest(QueriesHandler queriesHandler, StartRequest startRequest) {
        this.startRequest = startRequest;
        this.queriesHandler = queriesHandler;
        this.builder = new VehicleBuilder(queriesHandler);
    }
    public CommandRequest startPreparing(String command, String args) throws TroubleObjectCreationException, UnknownCommandException {
        Validator.validateCommand(command, this.startRequest);
        Validator.validateCountArgs(args.split(" +", 2), this.startRequest.getCommands().get(command).getCountArgs());
        int count = 0;
        if (args != "") {
            for (String arg : args.split(" +", 2)) {
                String type = this.startRequest.getCommands().get(command).getTypeArgs().get(count);
                if (type.equals("ID")) {
                    Validator.validateId(arg);
                } else if (type.equals("KEY")) {
                    Validator.validateKey(arg);
                }
                count += 1;
            }
        }

        Vehicle object = null;
        FuelType fuelType = null;
        for (String type : this.startRequest.getCommands().get(command).getTypeCompositArgs().values()) {
            if (type.equals("OBJECT")) {
                try {
                    object = this.builder.build();
                } catch (NoSuchElementException e) {
                    throw e;
                }
            } else if (type.equals("FUELTYPE")) {
                String var = "Возможные значения:";
                for (FuelType i : FuelType.values()) {
                    var += " " + i.name();
                }
                //System.out.println(var);
                String line = this.queriesHandler.query("Введите тип топлива. Значение должно быть одной из заранее определенных констант.\n"+var);
                try {
                    fuelType = FuelType.valueOf(line.toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Ошибка: введенной константы типа топлива не существует");
                }
            }
        }
        CommandRequest request = new CommandRequest(command, args.split(" +", 2), object, fuelType);
        return request;
    }
}
