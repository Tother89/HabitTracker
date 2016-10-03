package com.example.cfs.toth_habittracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;

public class CompletedHabitsActivity extends AppCompatActivity {
    private static final String FILENAME = "file.sav";
    private HabitData habitData;
    private ListView habitView;
    private TextView message;
    private ArrayAdapter<Habit> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_habits);

        long item=0;
        Intent intent = getIntent();
        String newMessage = intent.getStringExtra(HabitMainActivity.HABIT_MESSAGE);
        loadFromFile();
        if(habitData==null){
            habitData = new HabitData();
        }

        habitView = (ListView) findViewById(R.id.completeListView);
        message = (TextView) findViewById(R.id.currentHabit) ;
        message.setText(newMessage+"'s habits");

        saveInFile();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFromFile();
        adapter = new ArrayAdapter<Habit>(this,R.layout.active_list,habitData.getHabitList());
        adapter.notifyDataSetChanged();
        saveInFile();
    }

    public void removeHabits(){
        loadFromFile();
        habitData.getHabitList().clear();
        adapter.notifyDataSetChanged();
        saveInFile();
    }

    /**
     * Code from 301 Lab https://github.com/joshua2ua/lonelyTwitter
     **/
    public void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            // Code from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            Type dataType = new TypeToken<HabitData>() {
            }.getType();
            habitData = gson.fromJson(in, dataType);
            ;
        } catch (FileNotFoundException e) {
			/* Create a brand new list if we can't find the file. */
            habitData = new HabitData();
        }
    }


    /**
     * Code from 301 Lab https://github.com/joshua2ua/lonelyTwitter
     **/
    public void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, 0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(habitData, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
			/* Rethrow. */
            throw new RuntimeException(e);
        } catch (IOException e) {
			/* Rethrow. */
            throw new RuntimeException(e);
        }
    }

}
