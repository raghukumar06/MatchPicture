package main.matchpictures;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.matchpictures.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class PlayScreen extends AppCompatActivity {


    // SQLite database Variable.
//   SQLiteDatabase sqLiteDatabase;


    public static int wordvalue, scorevalue;

    /*
    // To Perform download task & to assign for images
    static DownloadImage task1, task2, task3, task4;
    */;

    //Score
    static TextView SCORETEXTVIEW, word;

    // Word Text String
    static String wordtext = "", correctanswer = "", tempanswer = "";

    static ImageView image1, image2, image3, image4;
    static Bitmap bitmap1, bitmap2, bitmap3, bitmap4;
    static String tempimage1, tempimage2, tempimage3, tempimage4;

    static int id = 1;


    Image im = new Image();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        // Inflate Menu is mapped with XML File by below code.
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Share Intent used for toolbars operations.
        Intent shareintent = new Intent(Intent.ACTION_SEND);
        shareintent.setType("text/plain");
        shareintent.putExtra(Intent.EXTRA_SUBJECT, "Check this cool Application");
        shareintent.putExtra(Intent.EXTRA_TEXT, "www.google.com");

        //Hint Intent used for toolbars operations.
        Intent hintintent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://en.wikipedia.org/wiki/" + "apple"));

        switch (item.getItemId()) {
            case R.id.share:
                startActivity(Intent.createChooser(shareintent, "Share Via"));
                break;

            case R.id.hint:
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


    public void evaluate(View view) {

        if(PlayScreen.id <= 10) {
            String currentanswer = getResources().getResourceName(view.getId());

            if(currentanswer.contains(correctanswer)) {
                Toast.makeText(PlayScreen.this, "Correct Answer!!!", Toast.LENGTH_SHORT).show();
                ++scorevalue;
                SCORETEXTVIEW.setText("SCORE : " + String.valueOf(scorevalue));
            } else {
                Toast.makeText(PlayScreen.this, "InCorrect Answer!!!", Toast.LENGTH_SHORT).show();
            }


            setImage();
        } else {
            Intent intent = new Intent(PlayScreen.this, ScoreScreen.class);
            intent.putExtra("currentscore", scorevalue);
            startActivity(intent);
        }

    }

    public void switchscreen(View view) {
        Intent intent = new Intent(getApplicationContext(), ScoreScreen.class);
        startActivity(intent);
    }


    public void finalSetImage(ImageView imagelink, String imagename) {
        //imagelink.setImageBitmap(imagename);
    }

    public void setImage() {
        word.setText(wordtext);
        word.setAllCaps(true);
        correctanswer = tempanswer;

        //System.out.println("Correct Answer: " + PlayScreen.correctanswer);

        image1.setImageBitmap(bitmap1);
        image2.setImageBitmap(bitmap2);
        image3.setImageBitmap(bitmap3);
        image4.setImageBitmap(bitmap4);

        long start = System.currentTimeMillis();

        //System.out.println("Start Time: " + TimeUnit.MILLISECONDS
        //.toSeconds(start));

        long end = System.currentTimeMillis();


        ++id;
        im.fetchImage(id);

    }

    public void init() {
        //random();
        setImage();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playscreen);
//        sqLiteDatabase = MainActivity.sqLiteDatabase;

        System.gc();
        // Intializing the Random Variable.
        SCORETEXTVIEW = findViewById(R.id.currentscore);
        scorevalue = 0;

        word = findViewById(R.id.word);

        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);

        //System.out.println("Bitmap Values is: " + bitmap1 + " " +
        // bitmap2 + " " + bitmap3 + " " + bitmap4);
        init();
        
    }
}


class Image extends Thread {

    public static void fetchImage(int id) {
        //System.out.println("Total Number of FetchImage Count: " + id);
        System.gc();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("images").whereGreaterThanOrEqualTo("id", id)
                .whereLessThanOrEqualTo("id", id)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.d("FETCHED DATA", document.getId() + " => "
                                        + document);

                                PlayScreen.wordtext = document.getString("name");
                                PlayScreen.tempanswer = document.getString("correctoption");

                                PlayScreen.tempimage1 = document.getString("option1");
                                PlayScreen.tempimage2 = document.getString("option2");
                                PlayScreen.tempimage3 = document.getString("option3");
                                PlayScreen.tempimage4 = document.getString("option4");

                                Thread t1 = new Thread(new Image(), "Thread - T1");
                                t1.start();
                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }

                });

    }

    @Override
    public void run() {
        try {
            DownloadFireBaseImage("image1", PlayScreen.tempimage1);
            DownloadFireBaseImage("image2", PlayScreen.tempimage2);
            DownloadFireBaseImage("image3", PlayScreen.tempimage3);
            DownloadFireBaseImage("image4", PlayScreen.tempimage4);
        } catch(Exception e) {
            // Throwing an exception
            //System.out.println("Exception is caught");
        }
    }


    public static void DownloadFireBaseImage(String imageoption, String imagename) {

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference gsReference = firebaseStorage.getReference().child(imagename);
        gsReference.getBytes(10000 * 10000)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {

                        if(imageoption.equals("image1")) {
                            System.out.println("Inside image1 block");
                            PlayScreen.bitmap1 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            PlayScreen.bitmap1 = Bitmap.createScaledBitmap(PlayScreen.bitmap1, 1000, 1000, true);
                        } else if(imageoption.equals("image2")) {
                            System.out.println("Inside image2 block");
                            PlayScreen.bitmap2 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            PlayScreen.bitmap2 = Bitmap.createScaledBitmap(PlayScreen.bitmap2, 1000, 1000, true);
                        } else if(imageoption.equals("image3")) {
                            System.out.println("Inside image3 block");
                            PlayScreen.bitmap3 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            PlayScreen.bitmap3 = Bitmap.createScaledBitmap(PlayScreen.bitmap3, 1000, 1000, true);
                        } else if(imageoption.equals("image4")) {
                            System.out.println("Inside image4 block");
                            PlayScreen.bitmap4 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            PlayScreen.bitmap4 = Bitmap.createScaledBitmap(PlayScreen.bitmap4, 1000, 1000, true);
                        }
                    }
                });
    }

}
