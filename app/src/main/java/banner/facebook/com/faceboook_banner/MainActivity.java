package banner.facebook.com.faceboook_banner;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.facebook.FacebookSdk;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    private AdView adView;
    private TextView info;
    private CallbackManager callbackManager;
    private LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        getFbKeyHash("e7BhQvl1Y/9DrBNZ0Nd/DgefaOY=");
        setContentView(R.layout.activity_main);
        adView = (AdView) findViewById(R.id.ad_view);

        loginButton = (LoginButton) findViewById(R.id.login_button);


        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);

   loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>(){

       @Override
       public void onSuccess(LoginResult loginResult) {
           Toast.makeText(MainActivity.this, "Inicion de sesión exitoso", Toast.LENGTH_SHORT).show();
       }

       @Override
       public void onCancel() {
           Toast.makeText(MainActivity.this, "Inicion de sesión cancelado", Toast.LENGTH_SHORT).show();
       }

       @Override
       public void onError(FacebookException error) {
           Toast.makeText(MainActivity.this, "Inicion de sesión NO exitoso", Toast.LENGTH_SHORT).show();
       }
   });
    }

    public void getFbKeyHash(String packageName){
        try{
            PackageInfo info = getPackageManager().getPackageInfo(
                    packageName, PackageManager.GET_SIGNATURES);
            for(Signature signature : info.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash :", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                System.out.println("KeyHash: " + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }

        }catch (PackageManager.NameNotFoundException e){

        }catch (NoSuchAlgorithmException e){

        }
    }

    protected void onActivityResult(int reqCode, int resCode, Intent i){
        callbackManager.onActivityResult(reqCode, resCode, i);
    }


    @Override
    protected void onDestroy() {
        if(adView != null){
            adView.destroy();
        }
        super.onDestroy();
    }
    @Override
    protected void onPause() {
        if(adView != null){
            adView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if(adView != null){
            adView.resume();
        }
        super.onResume();
    }


}











































