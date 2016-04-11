package design.example.ngondo.lengthpicker;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LengthPicker extends RelativeLayout{
    private static final String KEY_SUPER_STATE ="superState" ;
    private static final String KEY_NUM_INCHES ="superState" ;

    private View mPLusButton, mMinusButton;
    private TextView mTextView;

    private int mNumInches;


    private OnChangeListener mListener = null;

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
                        if(mListener != null){
                            mListener.onChange(mNumInches);
                        }
                        updateControls();
                        break;
                    case R.id.subtract:
                        if(mNumInches > 0){
                            mNumInches--;
                            if(mListener != null){
                                mListener.onChange(mNumInches);
                            }
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
//      setBackgroundColor(Color.CYAN);
    }
    /*
    * Save the state when device orientation changes
    * */
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_SUPER_STATE, super.onSaveInstanceState());
        bundle.putInt(KEY_NUM_INCHES, mNumInches);
        return bundle;
    }

    /*
    * Restore the state when the device changes
    * */
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if(state instanceof Bundle){
            Bundle bundle = (Bundle) state;
            mNumInches = bundle.getInt(KEY_NUM_INCHES);
            super.onRestoreInstanceState(bundle.getParcelable(KEY_SUPER_STATE));
        }else{
            super.onRestoreInstanceState(state);
        }
        /*
        * We need to call update controls so that the number of the views can be seen
        * */
        updateControls();
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

    /*
    * Listener Interface here so that we can perform an action whenever anything on
    * the LP is clicked
    * */
    public void setOnChangeListener(OnChangeListener listener){
        mListener = listener;
    }

    public interface OnChangeListener{
        public void onChange(int length);
    }
    public int getmNumInches() {
        return mNumInches;
    }

}
