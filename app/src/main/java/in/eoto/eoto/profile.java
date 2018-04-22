package in.eoto.eoto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {
    private RelativeLayout edit_prof;
    private TextView uname, uage, uproff, uaim, umail;
    private DatabaseReference mRoot, mRef;
    private FirebaseAuth mAuth;
    private String currentUid, currentUmail;
    private RelativeLayout back_from_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        edit_prof=(RelativeLayout)findViewById(R.id.edit_profile);
        uname=(TextView)findViewById(R.id.uname);
        uage=(TextView)findViewById(R.id.uage);
        uproff=(TextView)findViewById(R.id.uproff);
        uaim=(TextView)findViewById(R.id.uaim);
        back_from_profile=(RelativeLayout)findViewById(R.id.back_from_profile);
        umail = (TextView)findViewById(R.id.uemail);

        mRoot = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUid = mAuth.getCurrentUser().getUid().toString();
        currentUmail=mAuth.getCurrentUser().getEmail().toString();

            load_profile();

        edit_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profile.this,edit_profile.class ));
            }
        });

        back_from_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profile.this,trending.class));
            }
        });
    }

    public void load_profile(){
        umail.setText(currentUmail);
        mRef = mRoot.child("Users")
                .child(currentUid);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("Name").getValue().toString();
                uname.setText(name);
                String age = dataSnapshot.child("Age").getValue().toString();
                uage.setText(age);
                String proff = dataSnapshot.child("Profession").getValue().toString();
                uproff.setText(proff);
                String aim = dataSnapshot.child("Aim").getValue().toString();
                uaim.setText(aim);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(profile.this,trending.class));
    }
}
