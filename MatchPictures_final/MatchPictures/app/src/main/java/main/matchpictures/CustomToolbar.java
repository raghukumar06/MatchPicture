package main.matchpictures;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.matchpictures.R;

public class CustomToolbar extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        // Inflate Menu is mapped with XML File by below code.
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem item) {

        sharedPreferences = getSharedPreferences("com.example.menuinflater", MODE_PRIVATE);

        switch (item.getItemId()) {
            case R.id.share:
                Toast.makeText(this, "Share Menu Selected", Toast.LENGTH_SHORT).show();
                Intent shareintent = new Intent(Intent.ACTION_SEND);
                shareintent.setType("text/plain");
                shareintent.putExtra(Intent.EXTRA_SUBJECT,"Check this cool Application");
                startActivity(Intent.createChooser(shareintent,"Share Via"));

            case R.id.hint:
                Intent hintintent  = new Intent(Intent.ACTION_WEB_SEARCH);
                hintintent.putExtra(SearchManager.QUERY, "My IP");
                startActivity(hintintent);
                break;

//            case R.id.help:
//                Toast.makeText(this, "Help Menu Selected", Toast.LENGTH_SHORT).show();
//                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


}
