package in.eoto.eoto;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class answer extends AppCompatActivity {
    private RelativeLayout send_ans,attach, back_from_ans;
    private TextView ansUname, ansQues;
    private EditText ansans;
    final int PICK_IMAGE_REQUEST = 71;
    private DatabaseReference mRoot, mRef, mRef2, mPRef;
    private String msg, currentUser;
    private FirebaseAuth mAuth;
    private ImageView attach_preview;
    private Uri imageUri;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        send_ans = (RelativeLayout)findViewById(R.id.send_ans);
        ansUname = (TextView)findViewById(R.id.ansUname);
        ansQues = (TextView)findViewById(R.id.ansQues);
        ansans = (EditText)findViewById(R.id.ansans);
        attach = (RelativeLayout)findViewById(R.id.attach);
        attach_preview = (ImageView)findViewById(R.id.attach_preview);
        back_from_ans=(RelativeLayout)findViewById(R.id.back_from_ans);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid().toString();

        mRoot = FirebaseDatabase.getInstance().getReference();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        msg = getIntent().getExtras().getString("qid");
        Log.d("msg",msg);

        mRef = mRoot.child("Users")
                    .child(currentUser);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("Name").getValue().toString();
                ansUname.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mPRef = mRoot.child("public_ques").child(msg);
        mPRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String ques = dataSnapshot.child("body").getValue().toString();
                ansQues.setText(ques);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        send_ans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ansans.length()>0&&attach_preview.getDrawable()!=null){
                    upoadImage();

                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();

                    String ans = ansans.getText().toString();
                    mRef2 = mRoot.child("public_ques").child(msg).child("answer").child(String.valueOf(UUID.randomUUID()));
                    Map store = new HashMap();
                    store.put("uid",currentUser);
                    store.put("answer",ans);
                    store.put("date",dateFormat.format(date));
                    mRef2.setValue(store);
                }else if (ansans.length()>0&&attach_preview.getDrawable()==null){
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();

                    String ans = ansans.getText().toString();
                    mRef2 = mRoot.child("public_ques").child(msg).child("answer").child(String.valueOf(UUID.randomUUID()));
                    Map store = new HashMap();
                    store.put("uid",currentUser);
                    store.put("answer",ans);
                    store.put("date",dateFormat.format(date));
                    mRef2.setValue(store);
                }else if (ansans.length()<0&&attach_preview.getDrawable()!=null){
                    upoadImage();
                }else if (ansans.length()<0&&attach_preview.getDrawable()==null){
                    Toast.makeText(getApplicationContext(),"Field is empty",Toast.LENGTH_LONG).show();
                }

                startActivity(new Intent(answer.this, trending.class));


            }
        });

        back_from_ans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(answer.this,trending.class));
            }
        });

        attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "select image"), PICK_IMAGE_REQUEST);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            attach_preview.setImageURI(imageUri);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(answer.this, trending.class));
    }

    private void upoadImage() {
        if (imageUri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/" +msg+"/"+ currentUser);
            ref.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Intent intent = new Intent(answer.this, trending.class);
                            startActivityForResult(intent, 1);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.setMessage("Uploading");
                        }
                    });
        }
    }


}
