package design.example.ngondo.lengthpicker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LengthPicker extends RelativeLayout{
    private View mPLusButton, mMinusButton;
    private TextView mTextView;

    private int mNumInches;
    //used in java
    public LengthPicker(Context context) {
        super(context);
        init();
    }
    //Used in xml
    public LengthPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    private void init() {
        /*
        * We need a layout inflator to inflate our view because our customized component
        * needs a view. The two lines below replace "setContentView()"
        * */
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.length_picker, this);

        mPLusButton = findViewById(R.id.add);
        mMinusButton = findViewById(R.id.subtract);
        mTextView = (TextView) findViewById(R.id.text);

        updateControls();

        //listener for the buttons
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.add:
                        mNumInches++;
                        updateControls();
                        break;
                    case R.id.subtract:
                        if(mNumInches > 0){
                            mNumInches--;
                            updateControls();
                        }
                        break;
                }
            }
        };
//        set the listener
        mPLusButton.setOnClickListener(listener);
        mMinusButton.setOnClickListener(listener);
        //set bg color
//        setBackgroundColor(Color.CYAN);
    }

    //method that converts the number of inches to feet and vice versa
    private void updateControls(){
        int feet = mNumInches / 12;
        int inches = mNumInches % 12;

        String text = String.format("%d' %d\"",feet, inches);
        if(feet==0){
            text = String.format("%d\"", inches);
        }else{
            if(inches == 0){
                text = String.format("%d'", feet);
            }
        }
        //put the numbers in the text view
        mTextView.setText(text);
        //The number cannot go below zero
        mMinusButton.setEnabled(mNumInches > 0);
    }
}
