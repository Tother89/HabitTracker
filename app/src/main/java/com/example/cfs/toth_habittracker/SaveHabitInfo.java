package com.example.cfs.toth_habittracker;

import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by tothd on 9/27/2016.
 */

public class SaveHabitInfo {
    //TODO: Get this saving and loading files from FILENAME
    /**Code from 301 Lab lonelyTwitter**/
    private static final String FILENAME = "file.sav";

    private static String[] loadFromFile(Context context) {
        ArrayList<String> habits = new ArrayList<String>();
        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            String line = in.readLine();
            while (line != null) {
                habits.add(line);
                line = in.readLine();
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return habits.toArray(new String[habits.size()]);
    }
    /**Code from 301 Lab lonelyTwitter**/
    public void saveInFile(String text, Date date, Context context) {
        try {

            FileOutputStream fos = context.openFileOutput(FILENAME,0);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(habitList, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
