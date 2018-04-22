package in.eoto.eoto.Adapter;

import android.app.Dialog;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import in.eoto.eoto.Model.AnsViewPojo;
import in.eoto.eoto.R;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Ashu on 20-Mar-18.
 */

public class AnsViewAdapter extends RecyclerView.Adapter<AnsViewAdapter.AnsViewHolder> {
    List<AnsViewPojo> ansList;
    private DatabaseReference mRoot, mRef;
    private FirebaseAuth mAuth;
    private String uname;
    public AnsViewAdapter(List<AnsViewPojo> ansList){
        this.ansList=ansList;
    }
    @Override
    public AnsViewAdapter.AnsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ans_view_layout, parent, false);

        mRoot = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        return new AnsViewAdapter.AnsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnsViewAdapter.AnsViewHolder holder, int position) {
      /*  mRef = mRoot.child("Users").child(ansList.get(position).getUid());
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                     holder.sub_name.setText(dataSnapshot.child("Name").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        holder.ans_date.setText(ansList.get(position).getDate());
        holder.sub_ans.setText(ansList.get(position).getAns());
        holder.viewQues.setText(ansList.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        return ansList.size();
    }

    public class AnsViewHolder extends RecyclerView.ViewHolder{
        private TextView sub_ans, ans_date, sub_name, viewQues;
        private RelativeLayout view_attach;
        private Dialog myDialog;
        private ImageView attachment;
        FirebaseStorage storage;
        StorageReference storageReference;
        FirebaseAuth mAuth;
        String uid;

        public AnsViewHolder(View itemView){
            super(itemView);
            viewQues=(TextView)itemView.findViewById(R.id.viewQues);
            sub_ans = (TextView)itemView.findViewById(R.id.sub_ans);
            ans_date=(TextView)itemView.findViewById(R.id.ans_date);
            sub_name=(TextView)itemView.findViewById(R.id.sub_name);
            view_attach=(RelativeLayout)itemView.findViewById(R.id.view_attach);

            view_attach.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    image_popup();
                }
            });
        }

        public void image_popup(){
            myDialog = new Dialog(itemView.getContext());
            myDialog.setContentView(R.layout.image_dialog);
            attachment = (ImageView)myDialog.findViewById(R.id.attach_preview);
            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();
            mAuth = FirebaseAuth.getInstance();
            uid = mAuth.getCurrentUser().getUid().toString();
            storageReference.child("images/f4441d7a-0d38-49bd-b111-d7cfce2b0199/vPTWyZ3cTHXfa7C2jJlehJ7lvMR2").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(getApplicationContext()).load(uri).into(attachment);
                }
            });
            myDialog.show();
        }
    }
}
