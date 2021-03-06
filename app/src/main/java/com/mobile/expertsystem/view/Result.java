package com.mobile.expertsystem.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        for(int i=0; i<publicVar.getListGejala().size(); i++){
            String detail = "";
            if(publicVar.getListGejala().get(i).getDetail().size() != 0){
                detail = " : " + publicVar.getListGejala().get(i).getDetail().get(0).getName();
                int detailId = publicVar.getListGejala().get(i).getDetail().get(0).getId();
            }
            //Log.e("GEJALA RESULT", publicVar.getListGejala().get(i).getName() + detail);
            TextView tv = new TextView(this);
            tv.setText(i+1 + ". " + publicVar.getListGejala().get(i).getName() + detail);

            //Lightweight Pattern
            listTextViewGejala.add(tv);
            llMain.addView(tv);
        }

        hasilPersentasePenyakit = new double[penyakit.length];

        for(int i=0; i<aturan.length; i++){
            ArrayList <Double> calculate = new ArrayList<>();

            for(int j=0; j<aturan[i].getListGejala().size(); j++) {
                for(int k=0; k<publicVar.getListGejala().size(); k++) {
                    if (publicVar.getListGejala().get(k).getDetail().size() > 0) {
                        if (aturan[i].getListGejala().get(j).getId() == publicVar.getListGejala().get(k).getId()) {
                            for(int l=0; l<aturan[i].getListGejala().get(j).getDetail().size(); l++){
                                for(int m=0; m<publicVar.getListGejala().get(k).getDetail().size(); m++){
                                    if(aturan[i].getListGejala().get(j).getDetail().get(l).getId() == publicVar.getListGejala().get(k).getDetail().get(m).getId()){
                                        calculate.add(aturan[i].getListCf().get(j));
                                    }
                                }
                            }
                        }
                    }
                    else{
                        if (aturan[i].getListGejala().get(j).getId() == publicVar.getListGejala().get(k).getId()) {
                            calculate.add(aturan[i].getListCf().get(j));
                        }
                    }
                }
            }

            cf.setCf(calculate);
            hasilPersentasePenyakit[i] = cf.calculate();
        }

        llMain.addView(textViewHasilDiagnosa);

        for(int i=0; i<aturan.length; i++){

            TextView tv = new TextView(this);
            tv.setPadding(0, 25, 0, 0);
            if(hasilPersentasePenyakit[i] > 0.0) {
                tv.setText(penyakit[i].getName() + " : " + hasilPersentasePenyakit[i] + " %");
                llMain.addView(tv);
                for(int k=0; k<penyakit[i].getSolution().size(); k++) {
                    tv = new TextView(this);
                    tv.setText("Solusi : " + penyakit[i].getSolution().get(k));
                    llMain.addView(tv);
                }
            }
        }

        sv.addView(llMain);
    }
}
