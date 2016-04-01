package com.mobile.expertsystem.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mobile.expertsystem.R;
import com.mobile.expertsystem.controller.Singleton;
import com.mobile.expertsystem.controller.XMLParser;
import com.mobile.expertsystem.model.Aturan;
import com.mobile.expertsystem.model.Organ;
import com.mobile.expertsystem.webservice.JavaServlet;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    ListView listOrgan;
    Organ organ [];
    String organName [];
    ArrayAdapter <String> adapter;
    String servletResp;
    Singleton publicVar = Singleton.getInstance();
    Intent i;

    JavaServlet servlet = new JavaServlet();
    XMLParser xmlParser = new XMLParser();

    //CertainlyFactor cf = new CertainlyFactor();
    Aturan aturan [];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //cf.calculate();

        listOrgan = (ListView) findViewById(R.id.listOrgan);

        //Param 1 means request List of Organ, Param 2 means nothing for now (Organ's case)
        try {
            servletResp = servlet.execute(1, 0).get();

        }
        catch(ExecutionException e){
            e.printStackTrace();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        organ = xmlParser.xmlParserOrgan(servletResp);

        organName = new String[organ.length];

        for(int i=0; i<organ.length; i++){
            organName[i] = organ[i].getName();
        }

        adapter = new ArrayAdapter <> (this, android.R.layout.simple_list_item_1, organName);
        listOrgan.setAdapter(adapter);

        listOrgan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            i = new Intent(MainActivity.this, Question.class);
            publicVar.setSelectedOrgan(organ[position]);
            startActivity(i);
            finish();
            }
        });

    }
}
