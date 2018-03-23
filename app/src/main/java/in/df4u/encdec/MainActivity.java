package in.df4u.encdec;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    Button enc, dec;
    EditText pass, message;

    String passwd, mesg, enmsg, dmsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enc = (Button)findViewById(R.id.encrypt);
        dec = (Button)findViewById(R.id.decrypt);

        pass = (EditText)findViewById(R.id.pass);
        message = (EditText)findViewById(R.id.message);

        enc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwd = pass.getText().toString();
                mesg = message.getText().toString();

                try {
                    enmsg = encrypt(mesg, passwd).toString();

                    message.setText(enmsg);
                }catch (Exception e){

                }
            }
        });

        dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwd = pass.getText().toString();
                mesg = message.getText().toString();

                try {
                    enmsg = decrypt(mesg, passwd).toString();

                    message.setText(enmsg);
                }catch (Exception e){

                }
            }
        });
    }

    private String encrypt(String data, String pwd) throws Exception{
        SecretKey key = generateKey(pwd);
        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.ENCRYPT_MODE,  key);
        byte[] encVal = c.doFinal(data.getBytes());
        String encv = Base64.encodeToString(encVal,Base64.DEFAULT);
        return encv;
    }

    private SecretKey generateKey(String pwd) throws Exception{
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] b = pwd.getBytes("UTF-8");
        digest.update(b,0,b.length);
        byte[] key = digest.digest();
        SecretKeySpec sec = new SecretKeySpec(key, "AES");
        return sec;
    }

    private String decrypt(String cip, String pwd)throws Exception{
        SecretKeySpec key = (SecretKeySpec) generateKey(pwd);
        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.DECRYPT_MODE,key);
        byte[]  decode = Base64.decode(cip,Base64.DEFAULT);
        byte[] decval = c.doFinal(decode);
        String fdec = new String(decval);
        return fdec;
    }
}
