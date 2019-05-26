package com.example.todolist;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private EditText todoIsh;
    private Button saveButt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoIsh = findViewById(R.id.toDoID);
        saveButt = findViewById(R.id.saveID);

        saveButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // check if the edit text contains values before writing to file
                if(!todoIsh.getText().toString().equals("")){
                    String message = todoIsh.getText().toString();

                    try {
                        writeToFIle(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else{

                    Toast.makeText(MainActivity.this, "Nothing to save", Toast.LENGTH_SHORT).show();
                }
            }
        });

        try {
            // there should be something in the file to read
            if(readFromFile() != null){

                // setting the edit text to whatever is in the file
                todoIsh.setText(readFromFile());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToFIle(String message) throws IOException {
        // Instantiating the OutputStreamWriter class and making a file to send data to

        OutputStreamWriter output = new OutputStreamWriter(openFileOutput("todolist.txt", Context.MODE_PRIVATE));
        output.write(message);

        // always close the stream
        output.close();
    }

    private String readFromFile() throws IOException {
        String result = null;

        // Instantiating the InputStream class and using it to find file created by output stream
        InputStream inputStream = openFileInput("todolist.txt");

        // It must find a file first
        if(inputStream != null){

            InputStreamReader input = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(input);

            String values = "";

            StringBuilder stringBuilder = new StringBuilder();

//            int i = 1;

            // keep sending in data as long as there is data to send
            while((values = bufferedReader.readLine()) != null){

                stringBuilder.append(values + "\n");

//                i++;
            }

            // always close stream
            input.close();
            result = stringBuilder.toString();
        }

        return result;
    }
}
