package com.mnqobi.applicationfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Button btn_registra, btn_login,btn_fpsw;
    private EditText et_email, et_psw;
    ProgressDialog progressDialog;
    private FragmentTransaction fragmentTransaction;
    private Fragment fragment = null;

    private FirebaseAuth firebaseAuth;
    String TAG;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_email = findViewById(R.id.et_email);
        et_psw = findViewById(R.id.et_psw);

        progressDialog = new ProgressDialog(this);

        btn_login = findViewById(R.id.btn_login);
        btn_fpsw = findViewById(R.id.btn_fpsw);
        btn_registra = findViewById(R.id.btn_registrar);

        firebaseAuth = FirebaseAuth.getInstance();

        btn_registra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarUsuario();
            }
        });

    }


    private void registrarUsuario(){
        String email = et_email.getText().toString().trim();
        String psw = et_psw.getText().toString().trim();

        if(email.isEmpty()){
            Toast.makeText(getApplicationContext(),"El campo EMAIL se encuentra vacio",Toast.LENGTH_LONG).show();
        }else if(psw.isEmpty()){
            Toast.makeText(getApplicationContext(),"El campo PASSWORD se encuentra vacio",Toast.LENGTH_LONG).show();
        }else{
            progressDialog.setMessage("Realizando registro");
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email, psw)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                //Log.d(TAG, "createUserWithEmail:success");
                                //FirebaseUser user = firebaseAuth.getCurrentUser();
                                //updateUI(user);
                                Toast.makeText(getApplicationContext(),"El usuario ha sido registrado con exito",Toast.LENGTH_LONG).show();
                            } else {
                                // If sign in fails, display a message to the user.
                                // Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                //Toast.makeText(MainActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                                Toast.makeText(getApplicationContext(),"No se ha podido registrar el usuario",Toast.LENGTH_LONG).show();
                            }
                            progressDialog.dismiss();
                        }
                    });

        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new LoginFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.drawer_main, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

    }
}
