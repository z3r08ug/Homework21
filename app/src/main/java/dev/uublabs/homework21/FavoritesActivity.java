package dev.uublabs.homework21;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class FavoritesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        DatabaseHelper helper = new DatabaseHelper(this);
        List<Celebrity> celebrities = helper.getFaveCelebs();
        CustomList listAdapter = new CustomList(this, celebrities.size(), celebrities);

        listView = findViewById(R.id.lvfaveCelebs);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {

    }
}
