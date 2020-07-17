package pl.nowakprojects.socialmafia.utitles;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;

import pl.nowakprojects.socialmafia.R;

/**
 * Created by Mateusz on 30.10.2016.
 */

public class GameAlertDialog {

    private AlertDialog mAlertDialog;
    private Context context;
    String mTitle;
    String mBodyMessage;
    DialogInterface.OnClickListener mPositiveButtonClickListener;
    DialogInterface.OnClickListener mNegativeButtonClickListener;
    String mPositive = null;
    String mNegative = null;


    public GameAlertDialog(Context context, String mTitle, String mBodyMessage, DialogInterface.OnClickListener mPositiveButtonClickListener, DialogInterface.OnClickListener mNegativeButtonClickListener, String mPositive, String mNegative) {
        this.context = context;
        this.mTitle = mTitle;
        this.mBodyMessage = mBodyMessage;
        this.mPositive = mPositive;
        this.mNegative = mNegative;
        this.mPositiveButtonClickListener = mPositiveButtonClickListener;
        this.mNegativeButtonClickListener = mNegativeButtonClickListener;
        createPopupAlertDialog();
    }

    public GameAlertDialog(Context context, String mTitle, String mBodyMessage, DialogInterface.OnClickListener mPositiveButtonClickListener, DialogInterface.OnClickListener mNegativeButtonClickListener) {
        this.context = context;
        this.mTitle = mTitle;
        this.mBodyMessage = mBodyMessage;
        this.mPositiveButtonClickListener = mPositiveButtonClickListener;
        this.mNegativeButtonClickListener = mNegativeButtonClickListener;
        createPopupAlertDialog();
    }

    public GameAlertDialog(Context context, String mTitle, String mBodyMessage) {
        this.context = context;
        this.mTitle = mTitle;
        this.mBodyMessage = mBodyMessage;
        createPopupAlertDialog();
    }

    /*public GameAlertDialog(Context context, DialogInterface.OnClickListener mPositiveButtonClickListener, DialogInterface.OnClickListener mNegativeButtonClickListener) {
        this.context = context;
        this.mPositiveButtonClickListener = mPositiveButtonClickListener;
        this.mNegativeButtonClickListener = mNegativeButtonClickListener;
    }*/

    public AlertDialog getAlertDialog(){
        return mAlertDialog;
    }

    public void show(){
        mAlertDialog.show();
    }

    public void cancel(){
        mAlertDialog.cancel();
    }

    public void setmPositiveButtonClickListener(DialogInterface.OnClickListener mPositiveButtonClickListener) {
        this.mPositiveButtonClickListener = mPositiveButtonClickListener;
        createPopupAlertDialog();
    }

    public void setmNegativeButtonClickListener(DialogInterface.OnClickListener mNegativeButtonClickListener) {
        this.mNegativeButtonClickListener = mNegativeButtonClickListener;
        createPopupAlertDialog();
    }

    public void createPopupAlertDialog() {
        if(mTitle == null)
            mTitle = context.getString(R.string.confirm);
        if (mPositive == null)
            mPositive = context.getString(R.string.yes);
        if (mNegative == null)
            mNegative = context.getString(R.string.no);

        AlertDialog.Builder popupAlertDialog = new AlertDialog.Builder(context.getApplicationContext());
        popupAlertDialog.setTitle(mTitle);
        popupAlertDialog.setMessage(mBodyMessage);
        if(mPositiveButtonClickListener!=null)
            popupAlertDialog.setPositiveButton(mPositive, mPositiveButtonClickListener);
        if(mNegativeButtonClickListener!=null)
            popupAlertDialog.setNegativeButton(mNegative, mNegativeButtonClickListener);

        mAlertDialog = popupAlertDialog.create();
    }
}
