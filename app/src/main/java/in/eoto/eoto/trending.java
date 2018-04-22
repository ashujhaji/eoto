package in.eoto.eoto;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import in.eoto.eoto.Adapter.TrendngAdapter;
import in.eoto.eoto.Model.TrendingPojo;

public class trending extends AppCompatActivity {

    private TextView mTextMessage, asker;
    private RelativeLayout profile_nav, ask, ask_ques, more_nav,bookmark_nav;
    private Dialog mDialog;
    private Spinner category;
    private EditText ques_head, ques_body;
    private FirebaseAuth mAuth;
    private String currentUid,categ, head, body, ques_id;
    private DatabaseReference mRoot, mRef, mPRef;
    private TrendngAdapter adapter;
    private List<TrendingPojo> trendingPojoList = new ArrayList<>();
    private TrendingPojo trendingPojo;
    String muname, mdate, mcategory, mhead, mbody, muid;
    private RecyclerView trending_recy;
    private GridLayoutManager gridLayoutManager;
    private RelativeLayout back_from_trending;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending);
        profile_nav=(RelativeLayout)findViewById(R.id.profile_nav);
        ask = (RelativeLayout)findViewById(R.id.ask);
        trending_recy=(RecyclerView)findViewById(R.id.trending_recy);
        more_nav=(RelativeLayout)findViewById(R.id.more_nav);
        back_from_trending=(RelativeLayout)findViewById(R.id.back_from_trending);
        bookmark_nav=(RelativeLayout)findViewById(R.id.bookmark_nav);
        mAuth = FirebaseAuth.getInstance();
        currentUid = mAuth.getCurrentUser().getUid().toString();
        mRoot = FirebaseDatabase.getInstance().getReference();

        load_trends();

        gridLayoutManager = new GridLayoutManager(this,1);
        trending_recy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new TrendngAdapter(trendingPojoList);

        profile_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(trending.this,profile.class));
            }
        });

        ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ask_dialog();
            }
        });

        more_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(trending.this,login.class));
            }
        });

        bookmark_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(trending.this,bookmark.class));
            }
        });

        back_from_trending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
            }
        });
    }



    public void ask_dialog(){
        mDialog = new Dialog(trending.this);
        mDialog.setContentView(R.layout.ask_dialog);

        ask_ques = (RelativeLayout)mDialog.findViewById(R.id.ask_ques);
        category = (Spinner)mDialog.findViewById(R.id.category);
        ques_head=(EditText)mDialog.findViewById(R.id.ques_head);
        ques_body=(EditText)mDialog.findViewById(R.id.ques_body);
        asker=(TextView)mDialog.findViewById(R.id.asker);
        ques_id = UUID.randomUUID().toString();


        ask_ques.setEnabled(true);
        mDialog.show();

        mRef = mRoot.child("Users")
                .child(currentUid);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("Name").getValue().toString();
                Log.d("Name",name);
                asker.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(
                    AdapterView<?> adapterView, View view,
                    int i, long l) {
                Object item = adapterView.getSelectedItem();
                categ = item.toString();
            }

            public void onNothingSelected(
                    AdapterView<?> adapterView) {

            }
        });

        ask_ques.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                head = ques_head.getText().toString();
                body = ques_body.getText().toString();

                mRef = mRoot.child("Users")
                        .child(currentUid)
                        .child("Asked_ques")
                        .child(ques_id);
                mRef.child("date").setValue(dateFormat.format(date));
                mRef.child("category").setValue(categ);
                mRef.child("head").setValue(head);
                mRef.child("body").setValue(body);

                mPRef = mRoot.child("public_ques").child(ques_id);
                Map store = new HashMap();
                store.put("uid",currentUid);
                store.put("date",dateFormat.format(date));
                store.put("category",categ);
                store.put("head",head);
                store.put("body",body);

                mPRef.setValue(store);

                Intent intent = getIntent();
                finish();
                startActivity(intent);

                mDialog.dismiss();

            }

        });


    }

    public void load_trends(){
        mRef = mRoot.child("public_ques");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child:children){
                    muname = child.getKey().toString();
                        mdate = child.child("date").getValue().toString();
                        mcategory = child.child("category").getValue().toString();
                        mhead = child.child("head").getValue().toString();
                        mbody = child.child("body").getValue().toString();
                        muid = child.child("uid").getValue().toString();
                    trendingPojoList.add(new TrendingPojo(muname,mdate,mcategory,mhead,mbody,muid));
                }

                Collections.reverse(trendingPojoList);
                trending_recy.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
