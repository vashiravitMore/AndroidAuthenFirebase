package muenruekham.vashiravit.firebaseauthen;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button signInButton;
    private Button signUpButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        // AuthStateListener เพื่อรอรับข้อมูล user ที่ได้จากการ sign-up และ sign-in
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User sign
                    Log.d("TAG", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // user sign out
                    Log.d("TAG", "onAuthStateChanged:signed_out");
                }
            }
        };

        // initial
        usernameEditText = (EditText) findViewById(R.id.edtUsername);
        passwordEditText = (EditText) findViewById(R.id.edtPassword);
        signInButton = (Button) findViewById(R.id.signInButton);
        signUpButton = (Button) findViewById(R.id.signUpButton);
        signInButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);




    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == signInButton) {

        } else if (view == signUpButton) {
            String email = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            Toast.makeText(MainActivity.this,
                    "email : " + email + ", password : " + password,
                    Toast.LENGTH_SHORT)
                    .show();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this,
                                        "Authentication failed.",
                                        Toast.LENGTH_SHORT)
                                        .show();
                            } else {
                                Toast.makeText(MainActivity.this,
                                        "Success",
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }
                    });


        }

    }
}
