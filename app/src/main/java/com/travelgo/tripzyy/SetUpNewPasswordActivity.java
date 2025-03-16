package com.travelgo.tripzyy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class SetUpNewPasswordActivity extends AppCompatActivity {
    EditText etNewPassword,etConfirmPassword;
    AppCompatButton btnSetUpPassword;
 String strMobileNo;

 ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_new_password);
        etNewPassword=findViewById(R.id.etSetUpNewPasswordNewPassword);
        etConfirmPassword=findViewById(R.id.etSetUpNewPasswordConfirmNewPassword);
        btnSetUpPassword=findViewById(R.id.acbtnConfirmRegisterMobileNoSetUpPassword);

        strMobileNo=getIntent().getStringExtra("mobile");
        btnSetUpPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etNewPassword.getText().toString().isEmpty() || etConfirmPassword.getText().toString().isEmpty())
                {
                    Toast.makeText(SetUpNewPasswordActivity.this,"Please enter new or confirm Password", Toast.LENGTH_SHORT).show();
                } else if (etNewPassword.getText().toString().equals(etConfirmPassword.getText().toString()))
                {
                    etConfirmPassword.setError("Password did not match");
                }
                else{
                    progressDialog=new ProgressDialog(SetUpNewPasswordActivity.this);
                    progressDialog.setTitle("Updating password");
                    progressDialog.setMessage("Please Wait...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    forgetPassword();
                    }
            }

            private void forgetPassword() {
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params=new RequestParams();
                params.put("mobileno",strMobileNo);
                params.put("password",etNewPassword.getText().toString());

                client.post("http://192.168.81.177:80/TripzyyAPI/userForgetPassword.php",params,new JsonHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            String status= response.getString("Success");
                            if (status.equals("1"))
                            {
                                Toast.makeText(SetUpNewPasswordActivity.this,"New Password set succesfully",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(SetUpNewPasswordActivity.this,LoginActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(SetUpNewPasswordActivity.this,"Password not changed",Toast.LENGTH_SHORT);
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Toast.makeText(SetUpNewPasswordActivity.this,"Server Error",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}