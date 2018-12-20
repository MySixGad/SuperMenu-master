package supermenu.cmbt.net.supermenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import supermenu.cmbt.net.supermenu.widget.ReverseMenu;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ReverseMenu reverseMenu = (ReverseMenu) findViewById(R.id.float_circle);
        reverseMenu.setTVListener(new ReverseMenu.TVlisten() {
            @Override
            public void TvChangeListener(int type) {
                Toast.makeText(MainActivity.this, "" + type, Toast.LENGTH_SHORT).show();
            }
        });

    }

}
