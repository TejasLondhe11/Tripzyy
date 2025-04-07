package com.travelgo.tripzyy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.travelgo.tripzyy.common.Urls;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.travelgo.tripzyy.common.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MyProfileActivity extends AppCompatActivity {

    ImageView ivProfilePhoto;
    TextView tvName,tvMobileNo,tvEmailid,tvUsername;
    AppCompatButton  btnSignOut;
    Button btnEditProfile;

    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;

    SharedPreferences preferences;
    String strUsername;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        preferences = PreferenceManager.getDefaultSharedPreferences(MyProfileActivity.this);

        strUsername = preferences.getString("username","");

        ivProfilePhoto = findViewById(R.id.ivProfilePhoto);
        btnEditProfile = findViewById(R.id.btn_edit_profile);
        tvName = findViewById(R.id.tv_name);
        tvMobileNo = findViewById(R.id.tv_phone);
        tvEmailid = findViewById(R.id.tv_email);
        tvUsername = findViewById(R.id.tv_username);
        btnSignOut = findViewById(R.id.btn_signout);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(MyProfileActivity.this,googleSignInOptions);


        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);

        if(googleSignInAccount != null)
        {
            String name = googleSignInAccount.getDisplayName();
            String email = googleSignInAccount.getEmail();

            tvName.setText(name);
            tvEmailid.setText(email);

            btnSignOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent intent = new Intent(MyProfileActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog = new ProgressDialog(MyProfileActivity.this);
        progressDialog.setTitle("My Profile");
        progressDialog.setMessage("Please Wait....");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

        getMyDetails();
    }

    private void getMyDetails() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username",strUsername);

        client.post(Urls.myDetailsWebSerive,params,new JsonHttpResponseHandler(){

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        progressDialog.dismiss();
                        try {
                            JSONArray jsonArray = response.getJSONArray("getMyDetails");

                            for(int i=0; i<jsonArray.length();i++)
                            {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String strid = jsonObject.getString("id");
                                String strImage = jsonObject.getString("image");
                                String strname = jsonObject.getString("name");
                                String strmobileno = jsonObject.getString("mobileno");
                                String stremailid = jsonObject.getString("emailid");
                                String strusername = jsonObject.getString("username");

                                tvName.setText(strname);
                                tvMobileNo.setText(strmobileno);
                                tvEmailid.setText(stremailid);
                                tvUsername.setText(strUsername);

                                Glide.with(MyProfileActivity.this).load("http://192.168.227.177:80/TripzyyAPI/images"+strImage)
                                        .skipMemoryCache(true)
                                        .error(R.drawable.image_not_found)
                                        .into(ivProfilePhoto);

                            }

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        progressDialog.dismiss();
                        Toast.makeText(MyProfileActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

        );

    }
}