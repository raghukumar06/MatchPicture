package main.matchpictures;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.matchpictures.R;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;

public class ScoreScreen extends AppCompatActivity {
    ImageView ballonstop, giftbox;
    KonfettiView celebrationview;
    TextView totalscorevalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Info", "Inside the ScoreScreen Class");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scorescreen);

        Intent intent = getIntent();
        int intentscorevalue = intent.getIntExtra("currentscore", 0);

        Log.i("INFO", "Score Value is:\t" + intentscorevalue);

        //Intializing the elements.
        ballonstop = findViewById(R.id.ballontop);
        giftbox = findViewById(R.id.giftbox);
        totalscorevalue = findViewById(R.id.totalscorevalue);

        //Intializing the Konfetti Celebration.
        celebrationview = findViewById(R.id.celebrationview);

        //Below Code written for setting text.
        totalscorevalue.setText("SCORE : "+String.valueOf(PlayScreen.scorevalue));

        // Below Code written for Ballon's Animation.
        RotateAnimation rotate1 = new RotateAnimation(-10, -5);
        RotateAnimation rotate2 = new RotateAnimation(-15, -10);

        rotate1.setDuration(100);
        rotate1.setRepeatCount(Animation.INFINITE);

        rotate2.setDuration(100);
        rotate2.setRepeatCount(Animation.INFINITE);

        ballonstop.startAnimation(rotate1);
        giftbox.startAnimation(rotate2);

        // Below Code written for Konfetti's Effect.
        celebrationview.build().addColors(Color.RED, Color.GREEN,
                Color.MAGENTA, Color.YELLOW)
                .setDirection(-100, 500)
                .setSpeed(50f, 20f)
                .setFadeOutEnabled(true)
                .setTimeToLive(500L)
                .addShapes(Shape.RECT, Shape.CIRCLE)
                .setPosition(500, celebrationview.getWidth() + 500f, 0f, 0f)
                .streamFor(1000, 1000000L);

/*

        ScaleAnimation scale = new ScaleAnimation(0, 2, 0, 7);
        scale.setDuration(500);
        ballons.startAnimation(scale);
*/


    }
}
