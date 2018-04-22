package in.eoto.eoto;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class signup extends AppCompatActivity {
    private EditText unewmail, unewpwd;
    private ProgressBar signupprogress;
    private FirebaseAuth mAuth;
    private TextView to_login;
    private Button signin;
    private RelativeLayout back_from_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        unewmail=(EditText)findViewById(R.id.unewmail);
        unewpwd=(EditText)findViewById(R.id.unewpwd);
        signupprogress=(ProgressBar)findViewById(R.id.signupprogress);
        to_login=(TextView)findViewById(R.id.to_login);
        signin=(Button)findViewById(R.id.signin);
        back_from_signin=(RelativeLayout)findViewById(R.id.back_from_signin);

        mAuth = FirebaseAuth.getInstance();

        to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signup.this, login.class));
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_register();
            }
        });

        back_from_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signup.this, login.class));
            }
        });
    }

    private void user_register(){
        String getEmail = unewmail.getText().toString().trim();
        String getPwd = unewpwd.getText().toString().trim();

        if (getEmail.isEmpty()){
            unewmail.setError("Email is required");
            unewmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(getEmail).matches()){
            unewmail.setError("Please enter a valid email");
            unewpwd.requestFocus();
            return;
        }

        if (getPwd.isEmpty()){
            unewpwd.setError("Password is required");
            unewpwd.requestFocus();
            return;
        }

        if (getPwd.length()<6){
            unewpwd.setError("Password is too short");
            unewpwd.requestFocus();
            return;
        }

        signupprogress.setVisibility(View.VISIBLE);

      mAuth.createUserWithEmailAndPassword(getEmail, getPwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    signupprogress.setVisibility(View.GONE);
                    Intent toLogin = new Intent(signup.this, login.class);
                    startActivity(toLogin);
                }else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
                        signupprogress.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Already Registered",Toast.LENGTH_SHORT).show();
                    }else Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(signup.this,login.class));
    }
}
