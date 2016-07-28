package com.cromewell.chatserver;

import com.cromewell.chatserver.logger.Logger;

import java.io.*;
import java.net.Socket;

/**
 * Created by Jo on 24.07.2016.
 *
 * Waits for messages handle them.
 */
class ServerThread extends Thread{

    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private Logger logger;

    ServerThread(Socket socket, Logger logger) {
        this.socket = socket;
        this.logger = logger;
    }

    public void run(){
        try{
            out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch (IOException e) {
            logger.log("#Couldn't get Streams.");
        }
        String line;
        while(true){
            try{
                Server.sendMsg(in.readUTF()); //Pass the message to send it to all clients.

            } catch (IOException e) {
                logger.log("#Client disconnected "+socket.getInetAddress());
                //client disconnected - stop thread and remove it from the thread list.
                Server.getThreads().remove(this);
                break;
            }
        }
    }

    /**
     * Called while iterating through the thread list.
     * @param msg       Message to send to the client.
     */
    void sendMsgToClient(String msg){
        try {
            out.writeUTF(msg+"\n\r");
            out.flush();
        } catch (IOException e) {
            logger.log("#Couldn't send message to client.");
        }
    }
}
