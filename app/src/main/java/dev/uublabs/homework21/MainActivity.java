package dev.uublabs.homework21;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{

    private EditText etFirstName;
    private EditText etLastName;
    private ListView listView;
    private List<Celebrity> celebrities;
    private TextView tvNickname;
    private String nick;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);


        listView = findViewById(R.id.lvCelebs);

        tvNickname = findViewById(R.id.tvNickname);
        setNickName();
    }

    public void setNickName()
    {
        nick = readFromFile();
        tvNickname.setText(nick + "'s Celebrity List");
    }

    @Override
    protected void onResume() {
        super.onResume();
        setNickName();
    }

    private String readFromFile()
    {

        String ret = "";

        try {
            InputStream inputStream = this.openFileInput("nickname.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Toast.makeText(this, "The nickname file does not exist.\n" +
                    "Create a nickname.", Toast.LENGTH_SHORT).show();
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
        return ret;
    }

    public void handleDataSaving(View view)
    {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        switch (view.getId())
        {
            case R.id.btnSaveCeleb:
            {
                String first = etFirstName.getText().toString();
                String last = etLastName.getText().toString();
                Celebrity celebrity = new Celebrity(first, last);
                long row = databaseHelper.savePerson(celebrity);
                String toastString;
                if(row > 0)
                    toastString = "Saved at:" + row;
                else
                    toastString = "Not saved";
                Toast.makeText(this, toastString, Toast.LENGTH_SHORT).show();
                etFirstName.setText("");
                etLastName.setText("");
                break;
            }
            case  R.id.btnGetCeleb:
            {
                celebrities = databaseHelper.getCelebs();
                Log.d("MainActivity", "handleDataSaving: "+ celebrities.size());
                CustomList listAdapter = new CustomList(this, celebrities.size(), celebrities);

                listView.setAdapter(listAdapter);

                listView.setOnItemClickListener(this);
                break;
            }
        }
    }


    public void goToFaves(View view)
    {
        startActivity(new Intent(this, FavoritesActivity.class));
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Intent intent = new Intent(this, UpdateActivity.class);
        intent.putExtra("first", celebrities.get(position).getFirstName());
        startActivity(intent);
    }

    public void goToNickname(View view)
    {
        startActivity(new Intent(this, NicknameActivity.class));
    }
}
