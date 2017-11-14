package dev.uublabs.homework21;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;

public class NicknameActivity extends AppCompatActivity
{

    private FileOutputStream fos;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);

        editText = findViewById(R.id.etNickName);
    }

    public void saveNickname(View view)
    {
        try {
            fos = openFileOutput("nickname.txt", Context.MODE_PRIVATE);
            fos.write(editText.getText().toString().getBytes());
            fos.close();
            Toast.makeText(this, "Saved nickname", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Failed to save", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        editText.setText("");
        startActivity(new Intent(this, MainActivity.class));
    }
}
