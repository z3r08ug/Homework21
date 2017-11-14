package dev.uublabs.homework21;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Admin on 11/13/2017.
 */

public class CustomList extends ArrayAdapter<Celebrity>
{
    private List<Celebrity> celebrities;
    private Context con;

    public CustomList(@NonNull Context context, int resource, @NonNull List<Celebrity> objects) {
        super(context, 0, objects);
        this.celebrities = objects;
        con = context;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent)
    {
        view = LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false);
        TextView tvCelebName = view.findViewById(R.id.tvCelebname);
        final ImageButton imageBtn = view.findViewById(R.id.btnFav);
        tvCelebName.setText(celebrities.get(position).toString());
        Log.d("MainActivity", "getView: "+celebrities.get(position).toString());
        if (celebrities.get(position).isFavorite())
            imageBtn.setImageResource(R.drawable.favorite);
        else
            imageBtn.setImageResource(R.drawable.notfavorite);
        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                 DatabaseHelper helper = new DatabaseHelper(getContext());
                ImageButton iBtn = v.findViewById(R.id.btnFav);
                Log.d("CLICK", "onClick: "+celebrities.get(position).isFavorite());
                if(!celebrities.get(position).isFavorite()) {
                    celebrities.get(position).setFavorite(true);
                    iBtn.setImageResource(R.drawable.favorite);
                }
               else {
                    celebrities.get(position).setFavorite(false);
                    iBtn.setImageResource(R.drawable.notfavorite);
                }
                Log.d("CLICK", "onClick: "+celebrities.get(position).isFavorite());
                long row = helper.upDateFavorites(celebrities.get(position));
                if (row > 0)
                {
                    Toast.makeText(con, "Updated", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(con, "Failed to update", Toast.LENGTH_SHORT).show();
            }
        });

        ImageButton btnDel = view.findViewById(R.id.btnDelete);
        btnDel.setImageResource(R.drawable.minus);
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper helper = new DatabaseHelper(getContext());
                long row = helper.deleteCeleb(celebrities.get(position));
                if (row > 0)
                {
                    Toast.makeText(con, "Deleted", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(con, "Failed to update", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
