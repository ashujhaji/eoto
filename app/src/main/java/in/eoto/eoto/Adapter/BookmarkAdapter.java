package in.eoto.eoto.Adapter;

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

import java.util.List;

import in.eoto.eoto.Model.BookmarkPojo;
import in.eoto.eoto.R;

/**
 * Created by Ashu on 20-Mar-18.
 */

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder> {
    List<BookmarkPojo> bookmarkPojoList;
    public BookmarkAdapter(List<BookmarkPojo> bookmarkPojoList){
        this.bookmarkPojoList=bookmarkPojoList;
    }

    @Override
    public BookmarkAdapter.BookmarkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.trending_layout, parent, false);
        return new BookmarkAdapter.BookmarkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookmarkAdapter.BookmarkViewHolder holder, int position) {
        holder.ques_date.setText(bookmarkPojoList.get(position).getDate());
        holder.ques_category.setText(bookmarkPojoList.get(position).getCategory());
        holder.tHead.setText(bookmarkPojoList.get(position).getHead());
        holder.tBody.setText(bookmarkPojoList.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        return bookmarkPojoList.size();
    }

    public class BookmarkViewHolder extends RecyclerView.ViewHolder{
        private TextView username, ques_date, ques_category, tHead, tBody, ansUname, ansQues,viewAns;
        private EditText ansans;
        private Button ans_it;
        private DatabaseReference mRoot, mRef;
        private FirebaseAuth mAuth;
        private String currentUser, uname;
        private RelativeLayout bookmark;
        public BookmarkViewHolder(View view){
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
            /*mRef = mRoot.child("Users").child(bookmarkPojoList.get(getAdapterPosition()).getUid());
            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.child("Name").getValue().toString();
                    username.setText(name);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });*/

        }
    }
}
