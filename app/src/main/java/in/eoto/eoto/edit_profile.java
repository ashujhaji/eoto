package in.eoto.eoto;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class edit_profile extends AppCompatActivity {
    private EditText name_edit, age_edit, aim_edit;
    private Spinner proff_edit;
    private RelativeLayout submit_edit, back_from_edit;
    private String name, age, proff, aim, currentUid;
    private FirebaseAuth mAuth;
    private DatabaseReference mRoot, mRef;
    private final int PICK_IMAGE_REQUEST = 71;
    private ImageView pick_img;
    private StorageReference sRef;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        name_edit=(EditText)findViewById(R.id.name_edit);
        age_edit=(EditText)findViewById(R.id.age_edit);
        proff_edit=(Spinner) findViewById(R.id.proff_edit);
        aim_edit=(EditText)findViewById(R.id.aim_edit);
        submit_edit=(RelativeLayout)findViewById(R.id.submit_edit);
        pick_img=(ImageView)findViewById(R.id.pick_img);
        back_from_edit=(RelativeLayout)findViewById(R.id.back_from_edit);
        mAuth = FirebaseAuth.getInstance();
        mRoot = FirebaseDatabase.getInstance().getReference();
        currentUid = mAuth.getCurrentUser().getUid().toString();
        sRef = FirebaseStorage.getInstance().getReference();

        submit_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        proff_edit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(
                    AdapterView<?> adapterView, View view,
                    int i, long l) {
                Object item = adapterView.getSelectedItem();
                proff = item.toString();
            }

            public void onNothingSelected(
                    AdapterView<?> adapterView) {

            }
        });

        back_from_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(edit_profile.this,profile.class));
            }
        });

        pick_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // chooseImage();
            }
        });
    }
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            pick_img.setImageURI(imageUri);
        }
    }*/
    public void submit(){
        name = name_edit.getText().toString();
        age = age_edit.getText().toString();
        aim=aim_edit.getText().toString();

        if (name==null || name.isEmpty() ){
            name_edit.setError("Empty");
            name_edit.requestFocus();
            return;
        }

        if (age==null || age.isEmpty() ){
            age_edit.setError("Empty");
            age_edit.requestFocus();
            return;
        }


        if ((name!=null || !name.isEmpty())||(age!=null || !age.isEmpty())||(aim!=null || !aim.isEmpty())){
            mRef = mRoot.child("Users")
                    .child(currentUid);

            Map store = new HashMap();
            store.put("Name",name);
            store.put("Age",age);
            store.put("Profession",proff);
            store.put("Aim",aim);
            mRef.setValue(store);

            startActivity(new Intent(edit_profile.this, profile.class));
        }
    }

    /*private void chooseImage() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "select image"), PICK_IMAGE_REQUEST);
    }

    private void upoadImage() {
        if (imageUri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            StorageReference ref = sRef.child("images/" + currentUid);
            ref.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Intent intent = new Intent(edit_profile.this, profile.class);
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
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(edit_profile.this,profile.class));
    }
}
