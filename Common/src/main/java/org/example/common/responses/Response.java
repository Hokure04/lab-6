package org.example.common.responses;

import org.example.common.data.Vehicle;

import java.io.Serializable;
import java.util.Collection;

public class Response extends BaseResponse implements Serializable {
    private int status;
    private String error;
    private Collection<Vehicle> collection;
    private String value;

    public Response(int status, String error, String value) {
        this.status = status;
        this.error = error;
        this.collection = collection;
        this.value = value;
    }

    public Collection getCollection() {
        return collection;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getValue() {
        return value;
    }
}
