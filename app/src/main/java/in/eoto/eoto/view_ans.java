package in.eoto.eoto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import in.eoto.eoto.Adapter.AnsViewAdapter;
import in.eoto.eoto.Adapter.TrendngAdapter;
import in.eoto.eoto.Model.AnsViewPojo;

public class view_ans extends AppCompatActivity {
    private RecyclerView view_ans_recy;
    private String msg;
    private DatabaseReference mRoot, mRef;
    private AnsViewPojo ansViewPojo;
    private List<AnsViewPojo> ansViewPojoList = new ArrayList<>();
    private AnsViewAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private RelativeLayout back_from_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ans);
        view_ans_recy=(RecyclerView)findViewById(R.id.view_ans_recy);
        back_from_view=(RelativeLayout)findViewById(R.id.back_from_view);

        mRoot = FirebaseDatabase.getInstance().getReference();

        msg = getIntent().getExtras().getString("qid");

        load_ans();
        gridLayoutManager = new GridLayoutManager(this,1);
        view_ans_recy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new AnsViewAdapter(ansViewPojoList);

        back_from_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view_ans.this,trending.class));
            }
        });

    }

    public void load_ans(){
        mRef = mRoot.child("public_ques").child(msg);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String body = dataSnapshot.child("body").getValue().toString();
                Iterable<DataSnapshot> child = dataSnapshot.child("answer").getChildren();
                for (DataSnapshot children : child){
                    String ans = children.child("answer").getValue().toString();
                //    String uid = children.child("uid").getValue().toString();
                    String date = children.child("date").getValue().toString();

                    ansViewPojoList.add(new AnsViewPojo(body,ans,date));
                }
                Collections.reverse(ansViewPojoList);
                view_ans_recy.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(view_ans.this,trending.class));
    }
}
