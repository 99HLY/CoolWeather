package com.example.admin.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CountyActivity extends AppCompatActivity {
    private String[] data={"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""};
    private String[] weather_ids={"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""};
    private TextView textView=null;
    private ListView countylistview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_county);
        this.textView=findViewById(R.id.textView);
        this.countylistview=findViewById(R.id.countylistview);

        Intent intent=getIntent();
        int cityid=intent.getIntExtra("cid",0);
        int pid=intent.getIntExtra("pid",0);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,data);
        countylistview.setAdapter(adapter);
        this.countylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("点击了哪一个",""+position+":"+weather_ids[position]);
                Intent intent=new Intent(CountyActivity.this,WeatherActivity.class);
                intent.putExtra("wid",weather_ids[position]);
                startActivity(intent);
            }
        });
        String url="http://guolin.tech/api/china/"+pid+"/"+cityid;
        HttpUtil.sendOkHttpRequest(url, new Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                parseJSONObject(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(responseText);
                    }
                });

            }
            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }
    private void parseJSONObject(String responseText) {
        JSONArray jsonArray= null;
        try {
            jsonArray = new JSONArray(responseText);
            String[] result=new String[jsonArray.length()];
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                this.data[i]=jsonObject.getString("name");
                this.weather_ids[i]=jsonObject.getString("weather_id");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
