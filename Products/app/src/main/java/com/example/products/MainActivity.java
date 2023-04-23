package com.example.products;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.tapadoo.alerter.Alerter;
import com.yalantis.library.Koloda;
import com.yalantis.library.KolodaListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements KolodaListener {

    private Koloda koloda;
    private SwipeAdapter adapter;
    private List<Integer> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "You're Already at Home", Toast.LENGTH_SHORT).show();
            }
        });

        Button profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProfilePageActivity.class);
                startActivity(intent);
            }
        });

        ImageButton notifbutton = findViewById(R.id.notif);
        notifbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Alerter alerter = Alerter.create(com.example.products.MainActivity.this)
                        .setTitle("GOTCHA NOTIFICATIONS")
                        .setText("You have no Current Notifications")
                        .setBackgroundColorRes(R.color.black)
                        .setIcon(R.drawable.notif_yellowlogo)
                        .setIconColorFilter(0)
                        .setDuration(5000)
                        .setEnterAnimation(com.tapadoo.alerter.R.anim.alerter_slide_in_from_left)
                        .setExitAnimation(com.tapadoo.alerter.R.anim.alerter_slide_out_to_right)
                        .enableSwipeToDismiss()
                        .setTextAppearance(R.style.CustomAlerterTextAppearance)
                        .setTitleAppearance(R.style.CustomAlerterTextAppearance)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "GOTCHA", Toast.LENGTH_LONG).show();
                            }
                        });
                View alerterView = alerter.show();
            }});

        koloda = findViewById(R.id.koloda);
        list = new ArrayList<>();
        adapter = new SwipeAdapter(this, list);
        koloda.setAdapter(adapter);
        koloda.setKolodaListener(this);

        // Set up gesture detection on Koloda view
        koloda.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(MainActivity.this, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                    float deltaX = e2.getX() - e1.getX();
                    float deltaY = e2.getY() - e1.getY();
                    if (Math.abs(deltaX) > Math.abs(deltaY)) {
                        if (Math.abs(deltaX) > 100 && Math.abs(velocityX) > 100) {
                            if (deltaX > 0) {
                                // Swipe right
                                String prefix = "You have MINE ";
                                String swipeText = prefix + readTextFromCSV("mine.csv");
                                showAlert(swipeText);
                            } else {
                                // Swipe left
                                String prefix = "You have STEAL ";
                                String swipeText = prefix + readTextFromCSV("steal.csv");
                                showAlert(swipeText);
                            }
                            return true;
                        }
                    }
                    return false;
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    public void onCardSwipedLeft(int position) {
        // Called when a card is swiped left (or "mined")
        String prefix = "You have STEAL ";
        String swipeText = prefix + readTextFromCSV("steal.csv");
        showAlert(swipeText);
    }

    public void onCardSwipedRight(int position) {
        // Called when a card is swiped right (or "stolen")
        String prefix = "You have MINE ";
        String swipeText = prefix + readTextFromCSV("mine.csv");
        showAlert(swipeText);
    }

    private void showAlert(String text) {
        Alerter alerter = Alerter.create(this)
                .setTitle("GOTCHA NOTIFICATIONS")
                .setTextAppearance(R.style.CustomAlerterTextAppearance)
                .setTitleAppearance(R.style.CustomAlerterTextAppearance)
                .setText(text)
                .setBackgroundColorRes(R.color.black)
                .setIcon(R.drawable.notif_yellowlogo)
                .setIconColorFilter(0)
                .setDuration(5000)
                .setEnterAnimation(com.tapadoo.alerter.R.anim.alerter_slide_in_from_left)
                .setExitAnimation(com.tapadoo.alerter.R.anim.alerter_slide_out_to_right)
                .enableSwipeToDismiss()
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "GOTCHA", Toast.LENGTH_LONG).show();
                    }
                });
        alerter.show();
    }
    private String readTextFromCSV(String filename) {
        StringBuilder text = new StringBuilder();
        try {
            InputStream inputStream = getAssets().open(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }

    @Override
    public void onCardDoubleTap(int i) {

    }

    @Override
    public void onCardDrag(int i, @NonNull View view, float v) {

    }

    @Override
    public void onCardLongPress(int i) {

    }

    @Override
    public void onCardSingleTap(int i) {

    }

    @Override
    public void onClickLeft(int i) {

    }

    @Override
    public void onClickRight(int i) {

    }

    @Override
    public void onEmptyDeck() {

    }

    @Override
    public void onNewTopCard(int i) {

    }
}
