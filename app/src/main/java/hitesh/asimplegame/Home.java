package hitesh.asimplegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Home extends Activity {
    private Button start;
    private Button more;
    private ImageButton settings;
    private ImageButton mypage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        start = findViewById(R.id.startGame);
        more = findViewById(R.id.mainRanking_more);
//        settings = findViewById(R.id.btn_settings);
        mypage = findViewById(R.id.btn_mypage);

        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, setLevel.class);
                startActivity(intent);
                finish();
            }
        });

        mypage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Mypage.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
