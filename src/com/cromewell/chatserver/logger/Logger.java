package com.cromewell.chatserver.logger;

import java.io.*;
import java.net.URLDecoder;

/**
 * Created by Jo on 26.07.2016.
 *
 */
public class Logger {

    private BufferedWriter writer;

    public Logger() throws IOException {
        File logFile = getJarDir();
        if(logFile != null) {
            writer = new BufferedWriter(new FileWriter(logFile));
        }else{
            System.exit(1);
        }
    }

    public boolean log(String toLog){
        if(!toLog.equals("")){
            try {
                writer.write(toLog);
                writer.newLine();
                writer.flush();
                System.out.println(toLog);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }else{
            return false;
        }
    }

    private File getJarDir(){
        String path = Logger.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        try {
            String decodedPath = URLDecoder.decode(new File(path).getParent(), "UTF-8");
            System.out.println(decodedPath);
            return new File(decodedPath+File.separator+"ServerLog.txt");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
