package main.matchpictures;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.matchpictures.R;

public class HelpScreen extends AppCompatActivity {

    public void evaluate(View view){
        Toast.makeText(getApplicationContext(),"Well Done!!!",Toast.LENGTH_LONG).show();
    }
/*
    public void switchscreen(View view){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }*/

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helpscreen);
        Log.i("Info","You are on the play screen");
    }
}