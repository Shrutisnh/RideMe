package common.activity.ride.common;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import controller.RequestDispatcher;
import services.RideServices.ServiceListener;
import services.RideServices.ServiceResponse;
import services.data.Login;
import services.data.ServiceConstants;

public class LoginActivity extends AppCompatActivity implements ServiceListener {
    private EditText etUsername;
    private EditText etPassword;
    private Button btnSubmit;
    private Button btnSignUp;
    private RequestDispatcher dispatcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialiseUI();
        enableUI();
    }

    public void initialiseUI(){
        etUsername=(EditText)findViewById(R.id.username);
        etPassword=(EditText)findViewById(R.id.password);
        btnSubmit=(Button)findViewById(R.id.login);
        btnSignUp =(Button)findViewById(R.id.signUp);
        btnSubmit.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Express.ttf"));
        btnSignUp.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Express.ttf"));
    }

    public void enableUI(){
        dispatcher=RequestDispatcher.getInstance();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] data=new String[2];
                data[0]=etUsername.getText().toString();
                data[1]=etPassword.getText().toString();
                dispatcher.sendDataToWebService(RequestDispatcher.LOGIN_AUTHENTICATION,LoginActivity.this,data);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onServiceCompletion(ServiceResponse response) {
        if(ServiceConstants.AUTHENTICATE.equals(response.getOpCode())){
            Login data=(Login)response.getResponseData();
            if(data.getStatusFlag().equals("SUCCESS")){
                finish();
            }
        }
    }

    @Override
    public void onServiceError(String errorCode, ServiceResponse response) {

    }
}
