package com.cromewell.chatserver;

import com.cromewell.chatserver.logger.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Jo on 23.07.2016.
 * Creates a connection with the clients.
 */
class Server{

    private ServerSocket server;
    private Socket connection;
    private static ArrayList<ServerThread> threads = new ArrayList<>(); //The list to handle threads
    private Logger logger;

    Server() {
        try {
            logger = new Logger();
        } catch (IOException e) {
            System.out.println("#Couldn't create logger.");
            e.printStackTrace();
            System.exit(1);
        }
        try {
            server = new ServerSocket(6565); //Create server.
        } catch (IOException e) {
            logger.log("#Couldn't create server...exit.");
            System.exit(1);
        }
        while(true) { //Connect to clients and create thread for each of them.
            try {
                connection = server.accept();
                logger.log("#"+connection.getInetAddress().toString()+" connected.");
            } catch (IOException e) {
            }
            ServerThread sThread = new ServerThread(connection, logger); //Handle with the client
            sThread.setDaemon(true);
            threads.add(sThread); //Add the thread to the list.
            sThread.start();
        }
    }


    /**
     * Iterate through the threads and let each of them send the message to its client.
     * @param msg       Message to send.
     */
    static void sendMsg(String msg){
        for(ServerThread t: threads){
            t.sendMsgToClient(msg);
        }
    }

    static ArrayList<ServerThread> getThreads() {
        return threads;
    }
}
