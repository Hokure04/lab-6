package org.example.util;

import com.thoughtworks.xstream.core.util.QuickWriter;
import org.example.managers.QueriesHandler;

public class QueryDataForConnection {
    private QueriesHandler queriesHandler;

    public QueryDataForConnection(QueriesHandler queriesHandler) {
        this.queriesHandler = queriesHandler;
    }

}
