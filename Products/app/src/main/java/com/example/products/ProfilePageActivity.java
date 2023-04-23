package com.example.products;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class ProfilePageActivity extends AppCompatActivity {
    private Switch sellerswitch;
    private UserDBHelper dbHelper;

    private TextView username;
    private ImageView profile_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        ImageView profilePic = findViewById(R.id.profile_pic);
        CircleTransform.applyCircularTransform(profilePic);
        sellerswitch = findViewById(R.id.sellerswitch);
        username = findViewById(R.id.user_name);
        profile_pic = findViewById(R.id.profile_pic);
        dbHelper = new UserDBHelper(this);
        loadSwitchState();
        loadSwitchText();

        sellerswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sellerswitch.setText("Seller Mode");
                } else {
                    sellerswitch.setText("Buyer Mode");
                }
                saveSwitchState(isChecked);
                saveSwitchText(sellerswitch.getText().toString());
            }
        });

        Button home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sellerswitch.isChecked()) {
                    GotoSellerHome();
                } else {
                    GotoHome();
                }
            }
        });

        ImageButton edit_info = findViewById(R.id.edit_info);
        edit_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditInformation();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("email", "");

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = { "name", "profile_pic" };
        String selection = "email = ?";
        String[] selectionArgs = { userEmail };
        Cursor cursor = db.query("users", projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow("profile_pic"));

            username.setText(name);
            if (image != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                profile_pic.setImageBitmap(bitmap);
            }
        }

        cursor.close();
        db.close();


    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        Switch sellerswitch = findViewById(R.id.sellerswitch);

        // Reset switch state and text to default
        sellerswitch.setChecked(false);
        sellerswitch.setText("Buyer Mode");

        SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("switch_text", sellerswitch.getText().toString());
        editor.putBoolean("switch_state", false);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSwitchState();
        loadSwitchText();
    }

    private void loadSwitchState() {
        SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
        boolean switchState = prefs.getBoolean("switch_state", false);
        sellerswitch.setChecked(switchState);
    }

    private void loadSwitchText() {
        SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
        String switchText = prefs.getString("switch_text", "Buyer Mode");
        sellerswitch.setText(switchText);
    }

    private void saveSwitchText(String text) {
        SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("switch_text", text);
        editor.apply();
    }

    private void saveSwitchState(boolean state) {
        SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("switch_state", state);
        editor.apply();
    }

    public void EditInformation(){
        Intent intent   = new Intent(this, EditInformation.class);
        startActivity(intent);
    }

    public void GotoHome(){
        Intent newIntent = new Intent(this, MainActivity.class);
        startActivity(newIntent);
    }

    public void GotoSellerHome(){
        Intent newIntent = new Intent(this, SellerHomeActivity.class);
        startActivity(newIntent);
    }
}
