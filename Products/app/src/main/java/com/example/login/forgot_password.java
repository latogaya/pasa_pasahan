package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.ui.login.DBHelper;
import com.example.login.ui.login.LoginActivity;
import com.example.products.R;

import org.w3c.dom.Text;

public class forgot_password extends AppCompatActivity {

    TextView enterPass;
    EditText email3, passwordx2;
    Button sendbutton, backButton;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        enterPass = (TextView) findViewById(R.id.enterPass);
        email3 = (EditText) findViewById(R.id.email3);
        passwordx2 = (EditText) findViewById(R.id.passwordx2);
        sendbutton= (Button) findViewById(R.id.sendbutton);
        backButton = (Button) findViewById(R.id.backButton);
        DB = new DBHelper(this);

        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = email3.getText().toString();
                Boolean checkuser = DB.checkEmailExists(user);
                if(checkuser == true) {
                    if (sendbutton.getText().equals("CHECK")) {
                        sendbutton.setText("Send");
                        enterPass.setVisibility(View.VISIBLE);
                        passwordx2.setVisibility(View.VISIBLE);
                        Toast.makeText(forgot_password.this,"Email found", Toast.LENGTH_SHORT).show();
                    } else {
                        String newPassword = passwordx2.getText().toString();
                        if (newPassword.isEmpty()) {
                            Toast.makeText(forgot_password.this,"Please enter a new password", Toast.LENGTH_SHORT).show();
                        } else {
                            DB.updatePassword(user, newPassword);
                            Toast.makeText(forgot_password.this,"Password updated successfully", Toast.LENGTH_SHORT).show();
                            openLoginActivity();
                        }
                    }
                } else {
                    Toast.makeText(forgot_password.this,"Email does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginActivity();
            }
        });
    }
    public void openLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
