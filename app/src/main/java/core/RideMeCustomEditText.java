package core;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by G09561636 on 7/12/2016.
 */
public class RideMeCustomEditText extends EditText{
    public RideMeCustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Express.ttf"));
    }
}
