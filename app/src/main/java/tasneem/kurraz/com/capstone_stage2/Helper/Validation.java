package tasneem.kurraz.com.capstone_stage2.Helper;

import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

import tasneem.kurraz.com.capstone_stage2.R;

public class Validation {

    private final Context context;

    public Validation(Context context) {
        this.context = context;
    }

    public boolean userNameValidation(EditText editText){

        if( editText.getText().toString().length() == 0 ) {
            editText.setError(context.getResources().getString(R.string.emptyName));
            editText.requestFocus();
            return false;
        }else if(editText.getText().toString().length()< 5){
            editText.setError(context.getResources().getString(R.string.shortName));
            editText.requestFocus();
            return false;
        }else if( editText.getText().toString().length() >120){
            editText.setError( context.getResources().getString(R.string.rangeName) );
            editText.requestFocus();
            return false;
        }
        return true;
    }

    public boolean emailValidation(EditText email){
        CharSequence   target = email.getText();
        if (target == null) {
            email.setError(context.getResources().getString(R.string.emptyEmail));
            email.requestFocus();
            return false;
        }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()){
            email.setError(  context.getResources().getString(R.string.invalidEmail) );
            email.requestFocus();
            return false;
        }
        return true;
    }

    public boolean passWordValidation(EditText pass){
        if( pass.getText().toString().length() == 0 ) {
            pass.setError(context.getResources().getString(R.string.emptyPass));
            pass.requestFocus();
            return false;
        }else if(pass.getText().toString().length()< 3){
            pass.setError( context.getResources().getString(R.string.shortPass) );
            pass.requestFocus();
            return false;
        }else if(pass.getText().toString().length() >40){
            pass.setError( context.getResources().getString(R.string.rangePAss) );
            pass.requestFocus();
            return false;
        }
        return true;
    }


//    public boolean descriptionValidation(EditText pass){
//        if( pass.getText().toString().length() == 0 ) {
//            pass.setError("Description Is Required!");
//            pass.requestFocus();
//            return false;
//        }else if(pass.getText().toString().length()< 5){
//            pass.setError( "Description Must Be  5 At Least" );
//            pass.requestFocus();
//            return false;
//        }else if(pass.getText().toString().length() >12000){
//            pass.setError( "Description Must Be Less Than 12000 Character" );
//            pass.requestFocus();
//            return false;
//        }
//        return true;
//    }

    public boolean empty(EditText editText){
        if( editText.getText().toString().length() == 0 ){
            editText.setError(context.getResources().getString(R.string.emptyFiled));
            editText.requestFocus();
            return false;
        }
        return true;
    }

    public boolean empty(TextView editText){
        if( editText.getText().toString().length() == 0 ){
            editText.setError(context.getResources().getString(R.string.emptyFiled));
            editText.requestFocus();
            return false;
        }
        return true;
    }

}
