package hitesh.asimplegame;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Ranking extends AppCompatActivity {
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mMessagesRef;
    private Query mMessagesQuery;
    private ValueEventListener mMessagesListener;
    private ChildEventListener mMessagesQueryListener;
    private EditText rank_1, rank_2, rank_3, rank_4, rank_5, rank_6, rank_7, rank_8, rank_9, rank_10;
    private static final String TAG = "Query : ";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        rank_1 = (EditText) findViewById(R.id.value_mainrank_1st);
        rank_2 = findViewById(R.id.value_mainrank_2nd);
        rank_3 = findViewById(R.id.value_mainrank_3rd);
        rank_4 = findViewById(R.id.value_mainrank_4th);
        rank_5 = findViewById(R.id.value_mainrank_5th);
        rank_6 = findViewById(R.id.value_rank_6th);
        rank_7 = findViewById(R.id.value_rank_7th);
        rank_8 = findViewById(R.id.value_rank_8th);
        rank_9 = findViewById(R.id.value_rank_9th);
        rank_10 = findViewById(R.id.value_rank_10th);
    }

    public void Query(){
        Query Ranking = mDatabaseReference.child("moapp-simple").orderByChild("점수");

        Ranking.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Load:onCanclled", databaseError.toException());
            }
        });
    }

    public void onStart() {
        super.onStart();
        Query();
    }
}
