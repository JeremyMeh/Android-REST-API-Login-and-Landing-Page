package com.example.androidrestapiloginandlandingpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignIn extends AppCompatActivity {
    private EditText Name;
    private EditText Password;
    private Button Login;
    private TextView Error_Message;
    private String[][] all_users;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        Name = (EditText) findViewById(R.id.username);
        Password = (EditText) findViewById(R.id.password);
        Login = (Button) findViewById(R.id.login);
        Error_Message = (TextView) findViewById(R.id.error_message);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetUser getUser = retrofit.create(GetUser.class);

        Call<List<User>> call = getUser.getUsers();


        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {

                List<User> users = response.body();

                int i= 0;

                for( User user : users){
                    String[] s_user = {};
                    s_user[0] = (user.getId().toString());
                    s_user[1] = (user.getUsername());

                    all_users[i] = s_user;
                }

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(Name.getText().toString(), Password.getText().toString());
            }
        });
    }

    private void validate (String userName, String userPassword) {
        for(int x = 0; 0 < all_users.length; x++){
            if ((userName.equals(all_users[x][1])) && (userPassword.equals("1234"))) {
                Intent intent = new Intent(SignIn.this, MainActivity.class);
                startActivity(intent);
            }
        }
        Error_Message.setText("Incorrect Username or Password.");

    }
}
