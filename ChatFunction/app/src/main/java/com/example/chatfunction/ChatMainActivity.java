package com.example.chatfunction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sendbird.uikit.fragments.ChannelListFragment;

import java.util.ArrayList;

public class ChatMainActivity extends AppCompatActivity {

    Button home, chat, orders, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);

        //==========================================================================================
        //NAV BAR
        home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //put path here
            }
        });
        chat = findViewById(R.id.chat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatActivity();
            }
        });
        orders = findViewById(R.id.orders);
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //put path here
            }
        });
        profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //put path here
            }
        });
        //==========================================================================================

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment chatFragment = getChatFragment();

        fragmentTransaction.add(R.id.frameLayout, chatFragment);
        fragmentTransaction.commit();

    }
    private Fragment getChatFragment(){
        ChannelListFragment.Builder fragment = new ChannelListFragment.Builder().setUseHeader(false);
        return fragment.build();
    }

    public void chatActivity(){
        Intent intent = new Intent(this, ChatMainActivity.class);
        startActivity(intent);
    }

}