package common.activity.ride.common;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controller.RequestDispatcher;
import services.RideServices.ServiceListener;
import services.RideServices.ServiceResponse;


public class RegistrationActivity extends AppCompatActivity implements ServiceListener{
    private EditText etUsername;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button submitBtn;
    private final String PASSWORD_PATTERN="((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
    private RequestDispatcher dispatcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initializeUI();
        enableUI();
    }

    public void initializeUI(){
        etUsername=(EditText)findViewById(R.id.usernameSignup);
        etPassword=(EditText)findViewById(R.id.passwordSignup);
        etConfirmPassword=(EditText)findViewById(R.id.passwordConfirmSignup);
        submitBtn=(Button)findViewById(R.id.submitRegistration);

    }
    public void enableUI(){
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateAll()){
                    dispatcher=RequestDispatcher.getInstance();
                    String[] inputData=new String[2];
                    inputData[0]=etUsername.getText().toString();
                    inputData[1]=etPassword.getText().toString();

                    dispatcher.sendDataToWebService(RequestDispatcher.SIGNUP,RegistrationActivity.this,inputData);
                }
            }
        });
    }

    public boolean validateUsername(){
        return etUsername.getText().length()>0?true:false;
    }

    public boolean validatePassword(){

        Pattern pattern=Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher=pattern.matcher(etPassword.getText().toString());
        boolean valid=matcher.lookingAt();
        return valid;
    }
    public boolean validateConfirmPassword(){
        if(etPassword.getText().toString().equals(etConfirmPassword.getText().toString()))
            return true;
        else
            return false;
    }

    public boolean validateAll(){

       String errorMsg="";
        boolean valid=false;
        if(validateUsername()){
            if(validatePassword()){
                if(validateConfirmPassword()){
                    valid=true;
                }else{
                    errorMsg="The password you entered do not match.";
                }
            }else{
                errorMsg="The password you entered do not match the criteria of having at least 1 digit, 1 special character(@#$%), 1 lower case (a-z), 1 upper case(A-Z) and should be of 6-20 characters";
            }
        }else{
            errorMsg="Please enter username";
        }
        if(errorMsg.length()>0){
            buildValidationMsg(errorMsg);
        }
        return valid;
    }

    private void buildValidationMsg(String errorMsg){
        Toast.makeText(RegistrationActivity.this,errorMsg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onServiceCompletion(ServiceResponse response) {

    }

    @Override
    public void onServiceError(String errorCode, ServiceResponse response) {

    }
}
