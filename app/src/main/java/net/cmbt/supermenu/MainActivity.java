package net.cmbt.supermenu;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import net.cmbt.supermenu.widget.ReverseMenu;
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ReverseMenu reverseMenu = (ReverseMenu) findViewById(R.id.float_circle);

        reverseMenu.SlideListener(new ReverseMenu.SlideListener() {
            @Override
            public void slideDirection(int type) {
                /**
                 * @param type 1 左边   2 左边
                 */
            }
        });

    }

}
