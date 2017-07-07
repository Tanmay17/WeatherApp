package com.example.android.weatherapp;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ImageView iv;
    TextView tv1,tv2,tv3,tv4,tv5,tv6;
    EditText et;
    Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv=(ImageView)findViewById(R.id.iv1);
        tv1=(TextView)findViewById(R.id.tv1);
        tv2=(TextView)findViewById(R.id.tv2);
        tv3=(TextView)findViewById(R.id.tv3);
        tv4=(TextView)findViewById(R.id.tv4);
        tv5=(TextView)findViewById(R.id.tv5);
        tv6=(TextView)findViewById(R.id.tv6);
        et=(EditText)findViewById(R.id.et1);
        bt=(Button)findViewById(R.id.bt1);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MyTask().execute();
            }
        });

    }
    public class MyTask extends AsyncTask<String ,Void,Void>{

        ProgressDialog pd;
        String loc;
        @Override
        protected void onPreExecute() {
            loc=et.getText().toString();
            pd=new ProgressDialog(MainActivity.this);
            pd.setMessage("Please Wait...");
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setCancelable(false);
            pd.show();
        }
    URL url;
        @Override
        protected Void doInBackground(String... params) {
            try {
                if(loc.equals("")){
                    url = new URL("http://api.apixu.com/v1/current.json?key=f1afde3c53264b3cb3271733170707&q=Paris");
                }else{
                    url = new URL("http://api.apixu.com/v1/current.json?key=f1afde3c53264b3cb3271733170707&q="+loc);
                }
                InputStream stream = url.openStream();
                DataInputStream din = new DataInputStream(stream);
                String s = "", res = "";
                while ((s = din.readLine()) != null) {
                    res += s;
                }
                JSONObject jsonObject = new JSONObject(res);
                JSONObject jsonObject1 = jsonObject.getJSONObject("location");
                JSONObject jsonObject2 = jsonObject.getJSONObject("current");
                c = jsonObject1.getString("name");
                r = jsonObject1.getString("region");
                cn = jsonObject1.getString("country");
                lt = jsonObject1.getString("localtime");
                lu = jsonObject2.getString("last_updated");
                tc = jsonObject2.getString("temp_c");
                JSONObject jsonObject3 = jsonObject2.getJSONObject("condition");
                text = jsonObject3.getString("text");
                String path = jsonObject3.getString("icon");
                URL u=new URL("http:"+path);
                d=Drawable.createFromStream((InputStream)u.getContent(),"src");
            }catch(Exception e)
            {
                Log.d("Error-->",e+"");
            }

            return null;
        }
        String text,tc,lu,lt,cn,r,c;
        Drawable d;

        @Override
        protected void onPostExecute(Void vVoid) {
            pd.dismiss();
            tv1.setText("Last Update: "+lu);
            tv2.setText(c);
            tv3.setText(r);
            tv4.setText(cn);
            tv5.setText(tc);
            tv6.setText(text);
            iv.setImageDrawable(d);
        }
    }
}
