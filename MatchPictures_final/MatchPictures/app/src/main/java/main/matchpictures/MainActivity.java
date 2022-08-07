package main.matchpictures;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.matchpictures.R;


public class MainActivity extends AppCompatActivity {
    Intent shareintent;
    Intent hintintent;

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        // Inflate Menu is mapped with XML File by below code.
        menuInflater.inflate(R.menu.main_menu, menu);
        MenuItem shareItem = menu.findItem(R.id.hint);
        shareItem.setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.share:
                startActivity(Intent.createChooser(shareintent,"Share Via"));
                break;

            case R.id.help:
                Intent  helpintent  = new Intent(Intent.ACTION_WEB_SEARCH);
                helpintent.putExtra(SearchManager.QUERY, "My IP");
                startActivity(helpintent);
                break;

//            case R.id.help:
//                Toast.makeText(this, "Help Menu Selected", Toast.LENGTH_SHORT).show();
//                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        return super.onOptionsItemSelected(item);
    }

    // Element's Methods
    public void play(View view){
        Log.i("Info","Play Button has been clicked...");
        System.gc();
        Intent intent = new Intent(getApplicationContext(), PlayScreen.class);
        startActivity(intent);
    }

    public void exit(View view){
        System.exit(0);
    }

    public void switchscreen(View view){
        Intent intent = new Intent(getApplicationContext(), HelpScreen.class);
        shareintent = null;
        hintintent = null;
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Share Intent used for toolbars operations.
        shareintent = new Intent(Intent.ACTION_SEND);
        shareintent.setType("text/plain");
        shareintent.putExtra(Intent.EXTRA_SUBJECT,"Check this cool Application");
        shareintent.putExtra(Intent.EXTRA_TEXT,"www.google.com");

        try {
            //PlayScreen.fetchImageCollection(PlayScreen.id);
            Image.fetchImage(PlayScreen.id);
        } catch(Exception e) {
            System.out.println("Exception: "+e);
        }

    }
}