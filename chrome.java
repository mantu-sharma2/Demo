package com.example.finalcheck;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.example.finalcheck.Constants.*;

public class ChromeBrowser implements Browser{
    @Override
    public void start(String url) throws IOException, InterruptedException {
        Process process=Runtime.getRuntime().exec(new String[]{"open","-a","Google Chrome",url});  // add more cases
        int exitCode=process.waitFor();
        if(exitCode!=0){
            System.out.println("Failed to launch Chrome. Exit Code: " + exitCode);
        }else {
            System.out.println("Started Google Chrome");
        }
    }

    @Override
    public void stop() throws IOException, InterruptedException {
        Process process=Runtime.getRuntime().exec(new String[]{"pkill","Google Chrome"});  // add more cases
        int exitCode=process.waitFor();
        if(exitCode!=0){
            System.out.println("Failed to stop Google Chrome");
        }else{
            System.out.println("Stopped Google Chrome");
        }
    }

    @Override
    public String getActiveUrl() throws IOException {
        Process process=Runtime.getRuntime().exec(
                new String[]{"osascript", "-e", "tell application \"Google Chrome\" to get URL of tab 1 of window 1"}
        );
//        osascript -e 'tell application "Google Chrome" to get URL of tab 1 of window 1'
        BufferedReader reader=new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder sb=new StringBuilder();
        String temp;
        while((temp=reader.readLine())!=null){
            sb.append(temp);
        }
        reader.close();
        return sb.toString();
    }

//    Process process = Runtime.getRuntime().exec(new String[]{
//            "osascript", "-e",
//            "try \n" +
//                    "tell application \"Google Chrome\" \n" +
//                    "if (count of windows) > 0 and (count of tabs of window 1) > 0 then \n" +
//                    "return URL of active tab of window 1 \n" +
//                    "else \n" +
//                    "return \"No active tab found\" \n" +
//                    "end if \n" +
//                    "end tell \n" +
//                    "on error \n" +
//                    "return \"Chrome is not running\" \n" +
//                    "end try"
//    });


    @Override
    public void cleanup() throws IOException, InterruptedException {
        stop();   // make sure to stop it before cleanup due to locking
        deleteGivenFilesOfChromeDir(new File(MAC_CHROME_PATH));
        System.out.println("History, cache, bookmarks Deleted!");
    }

    public static void deleteGivenFilesOfChromeDir(File directory) {
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Chrome data directory does not exist or is not a directory.");
            return;
        }
        // List of Chrome data files that need to be deleted
        String[] targetFiles = {"History", "Cookies", "Bookmarks"};

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {  // Ensure we are deleting files only
                    for (String target : targetFiles) {
                        if (file.getName().equalsIgnoreCase(target)) {   //file.getName().toLowerCase().contains("history")
                            if (file.delete()) {
                                System.out.println("Deleted: " + file.getAbsolutePath());
                            } else {
                                System.out.println("Failed to delete: " + file.getAbsolutePath());
                            }
                        }
                    }
                }
            }
        }
    }
    /**
     * Recursively deletes all files and subdirectories in the given directory.
     */
    public static void deleteDirectory(File directory) {
        if (!directory.exists()) {
            System.out.println("Chrome data directory does not exist.");
        }
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file); // Recursively delete subdirectories
                }
                if (!file.delete()) {
                    System.out.println("Failed to delete: " + file.getAbsolutePath());
                }
            }
        }
    }
}