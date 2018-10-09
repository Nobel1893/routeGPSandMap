package route.com.gps.Base;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import route.com.gps.R;


/**
 * Created by Mohamed Nabil Mohamed (Nobel) on 9/9/2018.
 * byte code SA
 * m.nabil.fci2015@gmail.com
 */
public class MyBaseActivity extends AppCompatActivity {

    protected MaterialDialog dialog;
    protected AppCompatActivity activity;

    public MyBaseActivity(){
        activity=this;
    }

    public MaterialDialog ShowMessage(String title,String Message){
        dialog = new MaterialDialog.Builder(this)
                .title(title)
                .content(Message)
                .positiveText(R.string.ok)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
        return dialog;
    }
    public MaterialDialog ShowMessage(String title, String Message, MaterialDialog.SingleButtonCallback ok){
        dialog = new MaterialDialog.Builder(this)
                .title(title)
                .content(Message)
                .positiveText(R.string.ok)
                .onPositive(ok)
                .show();
        return dialog;
    }

   public MaterialDialog ShowProgressBar(){
       dialog = new MaterialDialog.Builder(this)
               .title(R.string.please_wait)
               .content(R.string.loading)
               .cancelable(false)
               .show();
       return dialog;
    }

    public void HideProgressBar(){

        if(dialog!=null&&dialog.isShowing())
        dialog.dismiss();
    }


    public static final String sharedPreferencesName= "HolyQuranFile";
  public void  SaveString(String key,String value) {


        //save to value with the key in SharedPreferences
      SharedPreferences.Editor editor
              =getSharedPreferences(sharedPreferencesName,MODE_PRIVATE).edit();
      editor.putString(key,value);
      editor.apply();
  }
  public String getString(String key,String defValue){
      SharedPreferences sharedPreferences =
              getSharedPreferences(sharedPreferencesName,MODE_PRIVATE);
      return  sharedPreferences.getString(key,defValue);
  }

}
