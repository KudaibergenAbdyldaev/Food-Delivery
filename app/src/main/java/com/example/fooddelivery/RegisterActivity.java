package com.example.fooddelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText  email, username, phone, address, password;
    Button register;
    FirebaseAuth auth;
    DatabaseReference reference;
    TextView have_acc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        address = (EditText) findViewById(R.id.address);
        password = (EditText) findViewById(R.id.password);

        have_acc = (TextView) findViewById(R.id.have_acc);
        have_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        register = (Button) findViewById(R.id.register);

        auth = FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_username = username.getText().toString();
                String txt_email = email.getText().toString();
                String txt_phone = phone.getText().toString();
                String txt_address = address.getText().toString();
                String txt_password = password.getText().toString();

                if(TextUtils.isEmpty(email.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "Пожалуйста ведите ваш email", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if(TextUtils.isEmpty(username.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "Пожалуйста ведите ваше имя", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if(TextUtils.isEmpty(phone.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "Пожалуйста ведите ваш номер телефона", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if(TextUtils.isEmpty(address.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "Пожалуйста ведите ваш адресс", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if(TextUtils.isEmpty(password.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "Пожалуйста ведите пароль", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if(password.getText().toString().length() < 6){
                    Toast.makeText(RegisterActivity.this, "Пароль должен содержать не менее 6 символов", Toast.LENGTH_SHORT).show();
                    return;

                }else {
                    register(txt_username, txt_email, txt_phone, txt_address, txt_password);
                }

            }
        });

    }

    private void register(final String username, String email, final String phone, final String address, String password){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            assert firebaseUser != null;
                            String userId = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

                            HashMap<String, String> map = new HashMap<>();
                            map.put("id", userId);
                            map.put("username", username);
                            map.put("address", address);
                            map.put("phone", phone);

                            reference.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(RegisterActivity.this, "Добро пожаловать!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "Failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}

