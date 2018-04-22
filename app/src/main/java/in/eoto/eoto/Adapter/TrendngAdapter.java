package in.eoto.eoto.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import in.eoto.eoto.Model.TrendingPojo;
import in.eoto.eoto.R;
import in.eoto.eoto.answer;
import in.eoto.eoto.view_ans;

/**
 * Created by Ashu on 20-Mar-18.
 */

public class TrendngAdapter extends RecyclerView.Adapter<TrendngAdapter.TrendingViewHolder> {
    List<TrendingPojo> trendingPojoList;
    DatabaseReference mRoot, mRef;
    public TrendngAdapter(List<TrendingPojo> trendingPojoList){
        this.trendingPojoList=trendingPojoList;
    }

    @Override
    public TrendngAdapter.TrendingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.trending_layout, parent, false);
        mRoot = FirebaseDatabase.getInstance().getReference();
        return new TrendngAdapter.TrendingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TrendngAdapter.TrendingViewHolder holder, int position) {
        mRef = mRoot.child("Users").child(trendingPojoList.get(position).getUid());
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("Name").getValue().toString();
                holder.username.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        holder.username.setText(trendingPojoList.get(position).getUname());
        holder.ques_date.setText(trendingPojoList.get(position).getDate());
        holder.ques_category.setText(trendingPojoList.get(position).getCategory());
        holder.tHead.setText(trendingPojoList.get(position).getQues_head());
        holder.tBody.setText(trendingPojoList.get(position).getQues_body());
    }

    @Override
    public int getItemCount() {
        return trendingPojoList.size();
    }

    public class TrendingViewHolder extends RecyclerView.ViewHolder{
        private TextView username, ques_date, ques_category, tHead, tBody, ansUname, ansQues,viewAns;
        private EditText ansans;
        private Button ans_it;
        private DatabaseReference mRoot, mRef;
        private FirebaseAuth mAuth;
        private String currentUser, uname;
        private RelativeLayout bookmark;

        public TrendingViewHolder(View view) {
            super(view);
            username = (TextView)view.findViewById(R.id.username);
            ques_date=(TextView)view.findViewById(R.id.ques_date);
            ques_category=(TextView)view.findViewById(R.id.ques_category);
            tHead=(TextView)view.findViewById(R.id.tHead);
            tBody=(TextView)view.findViewById(R.id.tBody);
            ans_it=(Button)view.findViewById(R.id.ans_it);
            viewAns=(TextView)view.findViewById(R.id.viewAns);
            bookmark=(RelativeLayout)view.findViewById(R.id.bookmark);

            mRoot = FirebaseDatabase.getInstance().getReference();
            mAuth = FirebaseAuth.getInstance();
            currentUser = mAuth.getCurrentUser().getUid().toString();

            mRef = mRoot.child("Users").child(currentUser);
            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    uname = dataSnapshot.child("Name").getValue().toString();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRef = mRoot.child("Users").child(currentUser).child("bookmark").child(trendingPojoList.get(getAdapterPosition()).getUname());
                    Map store = new HashMap();
                    store.put("body",trendingPojoList.get(getAdapterPosition()).getQues_body());
                    store.put("category",trendingPojoList.get(getAdapterPosition()).getCategory());
                    store.put("date",trendingPojoList.get(getAdapterPosition()).getDate());
                    store.put("head",trendingPojoList.get(getAdapterPosition()).getQues_head());
                    store.put("uid",trendingPojoList.get(getAdapterPosition()).getUid());
                    mRef.setValue(store);
                }
            });

            ans_it.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),answer.class);
                    intent.putExtra("qid",trendingPojoList.get(getAdapterPosition()).getUname());
                    v.getContext().startActivity(intent);
                }
            });

            viewAns.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),view_ans.class);
                    intent.putExtra("qid",trendingPojoList.get(getAdapterPosition()).getUname());
                    v.getContext().startActivity(intent);
                }
            });


        }
    }
}
