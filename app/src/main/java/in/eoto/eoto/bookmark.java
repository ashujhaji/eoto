package in.eoto.eoto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import in.eoto.eoto.Adapter.BookmarkAdapter;
import in.eoto.eoto.Adapter.TrendngAdapter;
import in.eoto.eoto.Model.BookmarkPojo;

public class bookmark extends AppCompatActivity {
    private RecyclerView bookmark_recy;
    private List<BookmarkPojo> bookmarkPojoList = new ArrayList<>();
    private BookmarkAdapter adapter;
    private BookmarkPojo bookmarkPojo;
    private DatabaseReference mRoot, mRef;
    private FirebaseAuth mAuth;
    private String currentUid;
    private GridLayoutManager gridLayoutManager;
    private RelativeLayout back_from_bookmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        bookmark_recy = (RecyclerView)findViewById(R.id.bookmark_recy);
        back_from_bookmark=(RelativeLayout)findViewById(R.id.back_from_bookmark);
        mRoot = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUid = mAuth.getCurrentUser().getUid().toString();

        load_bookmark();
        gridLayoutManager = new GridLayoutManager(this,1);
        bookmark_recy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new BookmarkAdapter(bookmarkPojoList);

        back_from_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(bookmark.this,trending.class));
            }
        });

    }

    public void load_bookmark(){
        mRef = mRoot.child("Users")
                    .child(currentUid)
                    .child("bookmark");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children){
                    String qid = child.getKey().toString();
                    String body = child.child("body").getValue().toString();
                    String category = child.child("category").getValue().toString();
                    String date = child.child("date").getValue().toString();
                    String head = child.child("head").getValue().toString();
                    String uid = child.child("uid").getValue().toString();
                    bookmarkPojoList.add(new BookmarkPojo(qid,body,category,date,head,uid));
                }
                bookmark_recy.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(bookmark.this,trending.class));
    }
}
