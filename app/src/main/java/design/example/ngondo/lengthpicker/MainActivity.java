package design.example.ngondo.lengthpicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private LengthPicker mWidth, mHeight;
    private TextView mArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWidth = (LengthPicker) findViewById(R.id.width);
        mHeight = (LengthPicker) findViewById(R.id.height);
        mArea = (TextView) findViewById(R.id.area);

        /*
        * Listen to the changes on the lengthpicker.
        * It monitors all the changes that you make
        * */
        LengthPicker.OnChangeListener listener = new LengthPicker.OnChangeListener() {
            @Override
            public void onChange(int length) {
                updateArea();
            }
        };
        //gets changes whenever width or height changes
        mWidth.setOnChangeListener(listener);
        mHeight.setOnChangeListener(listener);
    }

    private void updateArea() {
        int area = mWidth.getmNumInches() * mHeight.getmNumInches();
        mArea.setText(area + " sq inches");
    }
    /*
    * So that the acivity initially shows the area as zero on Launch
    * */
    @Override
    protected void onResume() {
        super.onResume();
        updateArea();
    }
}
