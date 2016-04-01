package com.mobile.expertsystem.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mobile.expertsystem.R;
import com.mobile.expertsystem.controller.Singleton;
import com.mobile.expertsystem.controller.XMLParser;
import com.mobile.expertsystem.model.Gejala;
import com.mobile.expertsystem.model.Organ;
import com.mobile.expertsystem.webservice.JavaServlet;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Question extends AppCompatActivity {

    ArrayList<CheckBox> checkBoxGejala = new ArrayList<>();
    ArrayList<RadioGroup> radioGroupGejala = new ArrayList<>();
    ArrayList<RadioButton> radioButtonGejala = new ArrayList<>();
    ArrayList<TextView> textViewGejala = new ArrayList<>();

    ScrollView sv;
    LinearLayout ll;
    Button submit;

    Singleton publicVar = Singleton.getInstance();
    Organ selectedOrgan = publicVar.getSelectedOrgan();
    JavaServlet servlet = new JavaServlet();
    String servletResp;
    Gejala gejala [];
    XMLParser xmlParser = new XMLParser();

    int idRadioButtonCounter = 0, idRadioGroupCounter =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        sv = (ScrollView) findViewById(R.id.scrollViewQuestion);
        ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);

        try {
            servletResp = servlet.execute(2, selectedOrgan.getId()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        finally{
            gejala = xmlParser.xmlParserGejala(servletResp);
//            ArrayList <Gejala> listGejala = new ArrayList<>();
//            for(int i=0; i<gejala.length; i++){
//                listGejala.add(gejala[i]);
//            }
//
//            publicVar.setListGejala(listGejala);
        }

        for(int i=0; i<gejala.length; i++){

            if(gejala[i].getDetail() == null || (gejala[i].getDetail().size() == 0)){
                CheckBox cb = new CheckBox(this);
                cb.setText(gejala[i].getName());
                cb.setId(gejala[i].getId());

                checkBoxGejala.add(cb);
                ll.addView(cb);
            }

            else{
                TextView tv = new TextView(this);
                tv.setText(gejala[i].getName());
                ll.addView(tv);
                textViewGejala.add(tv);

                RadioGroup rg = new RadioGroup(this);
                rg.setId(idRadioGroupCounter);
                rg.setOrientation(RadioGroup.VERTICAL);
                radioGroupGejala.add(rg);

                idRadioGroupCounter++;

                for(int j=0; j<gejala[i].getDetail().size(); j++){
                    RadioButton rb = new RadioButton(this);
                    rb.setText(gejala[i].getDetail().get(j).getName());
                    rb.setId(idRadioButtonCounter);
                    rg.addView(rb);
                    radioButtonGejala.add(rb);

//                    Log.e("Counter: " + i +"-"+ j, counter + "");
//                    Log.e("RB Text", gejala[i].getDetail().get(j));

                    idRadioButtonCounter++;
                }
                ll.addView(rg);
            }
        }

        submit = new Button(this);
        submit.setText("Submit Symptom");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSelectedSymptom();

                Intent i = new Intent(Question.this, Result.class);
                startActivity(i);
            }
        });

        ll.addView(submit);
        sv.addView(ll);
    }

    public void getSelectedSymptom(){
        for(int i=0; i<checkBoxGejala.size(); i++) {
            if (checkBoxGejala.get(i).isChecked()) {
                //Log.e("Checkbox Checked", "id : " + checkBoxGejala.get(i).getId() + " : " +checkBoxGejala.get(i).getText().toString());

                for(int j=0; j<gejala.length; j++){
                    if(gejala[j].getId() == checkBoxGejala.get(i).getId()){
                        publicVar.addGejala(gejala[j]);
                    }
                }
            }
        }

        for(int i=0; i<radioGroupGejala.size(); i++){

            int selected = radioGroupGejala.get(i).getCheckedRadioButtonId();
            if(selected != -1) {
//                Log.e("loop", i + "");
//                Log.e("selected", selected + "");
//
//                Log.e("Radio Checked", radioButtonGejala.get(selected).getText().toString());

                for(int j=0; j<gejala.length; j++){
//                    Log.e("gejala object", gejala[j].getName());
//                    Log.e("radio button", radioButtonGejala.get(selected).getText().toString());

                    if(gejala[j].getDetail().size() != 0) {
                        for(int k=0; k<gejala[j].getDetail().size(); k++) {
                            if (gejala[j].getDetail().get(k).getName().equalsIgnoreCase(radioButtonGejala.get(selected).getText().toString())) {
                                publicVar.addGejala(gejala[j]);
                            }
                        }
                    }
                }
            }
        }
    }

}
