package dev.uublabs.homework21;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity
{

    private EditText etFN;
    private EditText etLN;
    private Celebrity celeb;
    private DatabaseHelper helper;
    private String firstName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        firstName = getIntent().getExtras().getString("first");
        setContentView(R.layout.activity_update);

        etFN = findViewById(R.id.etFN);
        etLN = findViewById(R.id.etLN);

        helper = new DatabaseHelper(this);
        celeb = helper.getCeleb(firstName);

        etFN.setText(celeb.getFirstName());
        etLN.setText(celeb.getLastName());
    }

    public void updateCeleb(View view)
    {
        celeb.setFirstName(etFN.getText().toString());
        celeb.setLastName(etLN.getText().toString());
        long row = helper.updateCeleb(celeb, firstName);
        if(row>0) {
            Toast.makeText(this, "Updated Celebrity", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
        }
        else
            Toast.makeText(this, "Failed to update", Toast.LENGTH_SHORT).show();
    }
}
