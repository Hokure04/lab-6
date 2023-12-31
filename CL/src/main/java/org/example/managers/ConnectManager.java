package org.example.managers;

import org.example.common.responses.BaseResponse;
import org.example.managers.customer.CommandSender;
import org.example.managers.customer.ResponseReceiver;

import java.io.*;
import java.net.*;
import java.nio.channels.*;

public class ConnectManager {
    private QueriesHandler queriesHandler;
    private Parser receiveHandler;
    private int port;
    private String hostname;
    private ObjectInputStream objectReader;
    private ObjectOutputStream objectWriter;
    private CommandSender commandSender;
    private ResponseReceiver responseReceiver;
    private InetSocketAddress socketAddress;

    public ConnectManager(int port, String hostname, QueriesHandler queriesHandler) {
        this.port = port;
        this.hostname = hostname;
        this.queriesHandler = queriesHandler;
        this.socketAddress = new InetSocketAddress(hostname, port);
    }

    public void activePooling() {
        while (true) {
            try {
                //Selector selector = Selector.open();
                SocketChannel socketChannel;
                this.queriesHandler.write("Идет подключение к серверу");
                while (true) {
                    try {
                        socketChannel = SocketChannel.open(this.socketAddress);
                        break;
                    } catch (IOException ignore) {
                    }
                }
                //socketChannel.configureBlocking(false);
                //SelectionKey selectionKey = socketChannel.register(selector, SelectionKey.OP_CONNECT);
                //selectionKey.interestOps(SelectionKey.);
                //this.queriesHandler.write("Новый клиент подключён");
                //SocketChannel channel = (SocketChannel) selectionKey.channel();
                //this.objectWriter = new ObjectOutputStream(channel.socket().getOutputStream());
                //this.objectReader = new ObjectInputStream(channel.socket().getInputStream());

//                this.responseReceiver = new ResponseReceiver(this.objectReader);
//                this.commandSender = new CommandSender(this.objectWriter);
                this.responseReceiver = new ResponseReceiver(socketChannel);
                this.commandSender = new CommandSender(socketChannel);

                this.receiveHandler = new Parser(this.queriesHandler, this.commandSender, this.responseReceiver, this.socketAddress, socketChannel);
                this.receiveHandler.Active();
            } catch (UnknownHostException ignored) {
            } catch (EOFException e) {
                this.queriesHandler.write("Сервер пропал. Идет попытка переподключиться");
            } catch (IOException e) {
                this.queriesHandler.write("Ошибка связи сервера и клиента");
                System.exit(1);
            } catch (UnresolvedAddressException e) {
                this.queriesHandler.write("Указано неверное имя хоста");
                System.exit(1);
            }
        }
    }
}
