package hitesh.asimplegame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Mypage extends BaseActivity {
    private static final String TAG ="MYPAGE" ;

    private FirebaseAuth mAuth;
    private Button mypagesave, mypagelogout;
    private EditText name, number, email;
    private ImageButton homebtn, myprofile;
    private RadioGroup radioGroup;
    private String gender;
    private FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseReference = mFirebaseDatabase.getReference();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);


        mypagesave = findViewById(R.id.mypage_savebtn);
        mypagelogout = findViewById(R.id.mypage_logoutbtn);
        name = findViewById(R.id.mypage_namevaule);
        number = findViewById(R.id.mypage_numbervaule);
        email = findViewById(R.id.mypage_Emailvaule);
        homebtn = findViewById(R.id.mypage_backbtu);
        myprofile = findViewById(R.id.mypage_profile);

        radioGroup = (RadioGroup) findViewById(R.id.RadioGroup);
        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);

        mAuth = FirebaseAuth.getInstance();

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mypage.this, Home.class);
                startActivity(intent);
                finish();
            }
        });

        mypagelogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(Mypage.this, SignIn.class);
                startActivity(intent);
                finish();
                // .signOut(this)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    public void onComplete(@NonNull Task<Void> task) {
            }
        });

        mypagesave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newemail = email.getText().toString();
                String newnumber = number.getText().toString();

                DocumentReference docRef = db.collection("Users").document("UsersInfo");
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });


                Map<String, Object> newdata = new HashMap<>();
                newdata.put("email", newemail);
                newdata.put("gender", gender);
                newdata.put("phone", newnumber);

                db.collection("Users").document("UsersInfo")
                        .set(newdata)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {

                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                            }
                        });

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                mDatabaseReference.child(user.getUid()).child("전화번호").setValue(newnumber);
                mDatabaseReference.child(user.getUid()).child("E-mail").setValue(newemail);
                mDatabaseReference.child(user.getUid()).child("성별").setValue(gender);

                Toast.makeText(Mypage.this, "저장되었습니다", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    public void logout(View v) {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(Mypage.this);
//        builder.setTitle("로그아웃");
//        builder.setMessage("로그아웃 하시겠습니까?");
//        builder.setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Intent i = new Intent(Mypage.this, SignIn.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                startActivity(i);
//            }
//        });
//        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//            }
//
//        });
//        builder.show();
//    }


//    public void signOut() {
//        // [START auth_fui_signout]
//        AuthUI.getInstance()
//                .signOut(this)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    public void onComplete(@NonNull Task<Void> task) {
//                        // ...
//                    }
//                });
//        // [END auth_fui_signout]
//    }

    //라디오 그룹 클릭 리스너
    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            if (i == R.id.manbutton) {
                Toast.makeText(Mypage.this, "남성", Toast.LENGTH_SHORT).show();
                gender = "male";
            } else if (i == R.id.womanbutton) {
                Toast.makeText(Mypage.this, "여성", Toast.LENGTH_SHORT).show();
                gender = "female";
            }
        }
    };
}

