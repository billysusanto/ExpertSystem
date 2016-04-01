package com.mobile.expertsystem.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.mobile.expertsystem.R;
import com.mobile.expertsystem.controller.CertainlyFactor;
import com.mobile.expertsystem.controller.Singleton;
import com.mobile.expertsystem.controller.XMLParser;
import com.mobile.expertsystem.model.Aturan;
import com.mobile.expertsystem.model.Penyakit;
import com.mobile.expertsystem.webservice.JavaServlet;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Result extends AppCompatActivity {

    LinearLayout llMain;
    TableLayout tlHasil;
    ScrollView sv;
    ArrayList<TextView> listTextViewGejala = new ArrayList<>();
    TextView textViewListGejala;
    TextView textViewHasilDiagnosa;

    JavaServlet servletAturan = new JavaServlet();
    JavaServlet servletPenyakit = new JavaServlet();
    XMLParser xmlParser = new XMLParser();
    Singleton publicVar = Singleton.getInstance();
    Aturan aturan [];
    Penyakit penyakit [];
    double hasilPersentasePenyakit [];
    CertainlyFactor cf = new CertainlyFactor();

    String servletAturanResp, servletPenyakitResp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        sv = (ScrollView) findViewById(R.id.scrollViewResult);

        llMain = new LinearLayout(this);
        tlHasil = new TableLayout(this);
        textViewListGejala = new TextView(this);
        textViewHasilDiagnosa = new TextView(this);

        llMain.setOrientation(LinearLayout.VERTICAL);

        textViewListGejala.setText("Gejala yang Anda alami :");
        textViewListGejala.setTextSize(TypedValue.COMPLEX_UNIT_SP,17);
        textViewListGejala.setPadding(0, 0, 0, 10);

        textViewHasilDiagnosa.setText("Hasil Diagnosa");
        textViewHasilDiagnosa.setTextSize(TypedValue.COMPLEX_UNIT_SP,17);
        textViewHasilDiagnosa.setPadding(0, 30, 0, 10);

        llMain.addView(textViewListGejala);

        for(int i=0; i<publicVar.getListGejala().size(); i++){
            String detail = "";
            if(publicVar.getListGejala().get(i).getDetail().size() != 0){
                detail = " : " + publicVar.getListGejala().get(i).getDetail().get(0).getName();
            }
            //Log.e("GEJALA RESULT", publicVar.getListGejala().get(i).getName() + detail);
            TextView tv = new TextView(this);
            tv.setText(i+1 + ". " + publicVar.getListGejala().get(i).getName() + detail);

            //Lightweight Pattern
            listTextViewGejala.add(tv);
            llMain.addView(tv);
        }

        llMain.addView(textViewHasilDiagnosa);

        try {
            servletAturanResp = servletAturan.execute(3, 1).get();
            aturan = xmlParser.xmlParserAturan(servletAturanResp);

            servletPenyakitResp = servletPenyakit.execute(4, 1).get();
            penyakit = xmlParser.xmlParserPenyakit(servletPenyakitResp);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }

        for(int i=0; i<penyakit.length; i++){
            Log.e("sickness-id", penyakit[i].getId() + "");
            Log.e("sickness-name", penyakit[i].getName() + "");
            for(int j=0; j<penyakit[i].getSolution().size(); j++){
                Log.e("solution", penyakit[i].getSolution().get(j) + "");
            }
        }

        hasilPersentasePenyakit = new double[penyakit.length];

        for(int i=0; i<aturan.length; i++){
            Log.e("sickness-id", aturan[i].getSicknessId() + "");
            for(int j=0; j<aturan[i].getListGejala().size(); j++){
                Log.e("symptom-id", aturan[i].getListGejala().get(j).getId() + "");
                if(aturan[i].getListGejala().get(j).getDetail().size() > 0) {
                    Log.e("detail-id", aturan[i].getListGejala().get(j).getDetail().get(0).getId() + "");
                }
                Log.e("cf", aturan[i].getListCf().get(j) + "");
            }

            cf.setCf(aturan[i].getListCf());
            hasilPersentasePenyakit[i] = cf.calculate();

            TextView tv = new TextView(this);
            tv.setText(aturan[i].getSicknessId() + " : "+ hasilPersentasePenyakit[i] + "");
            llMain.addView(tv);
        }

        for(int i=0; i<aturan.length; i++){

        }

        sv.addView(llMain);
    }
}
