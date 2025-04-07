package com.travelgo.tripzyy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.travelgo.tripzyy.common.NetworkChangeListener;
import com.travelgo.tripzyy.common.Urls;
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {


    TextView tvForgetPassword, tvNewUser;
    EditText etUsername,etPassword;
    CheckBox cbShowHidePassword;
    Button btnLogin;

    ProgressDialog progressDialog;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

   GoogleSignInOptions googleSignInOptions; //shows option of gmail;
   GoogleSignInClient googleSignInClient; //selected gmail option store
    AppCompatButton btnSignInWithGoogle;

    SharedPreferences preferences; //temp data store
    SharedPreferences.Editor editor; // data put sharedpreferences

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        editor = preferences.edit();

        tvNewUser = findViewById(R.id.tvLoginNewUser);
        etUsername = findViewById(R.id.etLoginUsername);
        etPassword = findViewById(R.id.etLoginPassword);
        tvForgetPassword=findViewById(R.id.tvLoginForgetPassword);
        cbShowHidePassword = findViewById(R.id.cbLoginShowHidePassword);
        tvForgetPassword = findViewById(R.id.tvLoginForgetPassword);
        btnLogin = findViewById(R.id.btnLoginLogin);
      btnSignInWithGoogle =findViewById(R.id.btnLoginSignInWithGoogle);

       googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(LoginActivity.this,googleSignInOptions);

btnSignInWithGoogle.setOnClickListener(new View.OnClickListener() {
           @Override
   public void onClick(View v) {
                signIn();
            }

        });

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        SharedPreferences.Editor editor = preferences.edit();

        if(preferences.getBoolean("isLogin",false))
        {
            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
            startActivity(intent);
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etUsername.getText().toString().isEmpty()) {
                    etUsername.setError("Please Enter Your Username");
                } else if (etPassword.getText().toString().isEmpty()) {
                    etPassword.setError("Please Enter Your Password");
                } else if (etUsername.getText().toString().length() < 8) {
                    etUsername.setError("username Length must be greater than 8");
                } else if (etPassword.getText().toString().length() < 8) {
                    etPassword.setError("Password must be greater than 8");
                } else if (!etUsername.getText().toString().matches(".*[A-Z].*")) {
                    etUsername.setError("Please used 1 Uppercase letter ");
                } else if (!etUsername.getText().toString().matches(".*[a-z].*")) {
                    etUsername.setError("Please used 1 Lowercase letter ");
                } else if (!etUsername.getText().toString().matches(".*[0-9].*")) {
                    etUsername.setError("Please used 1  Number ");
                } else if (!etUsername.getText().toString().matches(".*[@,$,#,&,*].*")) {
                    etUsername.setError("Please used 1 Special Symbol");
                } else
                {
                    progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setTitle("Please Wait");
                    progressDialog.setMessage("Login Under Process");
                    progressDialog.show();

                    Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                    editor.putString("Username",etUsername.getText().toString()).commit();
                    editor.putBoolean("isLogin",true).commit();
                    startActivity(intent);
                    finish();

                    userLogin();
                }
            }
        });

        cbShowHidePassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });

        tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ConfirmRegisterMobileNoActivity.class);
                startActivity(intent);
            }
        });



        tvNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(intent);
            }
        });

    }

   private void signIn()
    {
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent,999);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 999)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

           try {
                task.getResult(ApiException.class);
                Intent intent = new Intent(LoginActivity.this, MyProfileFragment.class);
                startActivity(intent);
                finish();
            } catch (ApiException e) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkChangeListener);
    }

    private void userLogin()
    {
        AsyncHttpClient client = new AsyncHttpClient(); //client-server communication
        RequestParams params = new RequestParams(); //data put

        params.put("username",etUsername.getText().toString());
        params.put("password",etPassword.getText().toString());

        client.post(Urls.loginUserWebService,params,
                new JsonHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        progressDialog.dismiss();
                        try {
                            String status = response.getString("success");
                            if(status.equals("1"))
                            {
                                Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                                editor.putString("username",etUsername.getText().toString()).commit();
                                startActivity(intent);
                                finish();

                            }else{
                                Toast.makeText(LoginActivity.this,"Invalid username and password",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this,"Server Error",Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
