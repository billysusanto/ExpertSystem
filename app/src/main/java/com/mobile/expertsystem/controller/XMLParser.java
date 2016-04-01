package com.mobile.expertsystem.controller;

import android.app.Application;
import android.util.Log;

import com.mobile.expertsystem.model.Aturan;
import com.mobile.expertsystem.model.Detail;
import com.mobile.expertsystem.model.Gejala;
import com.mobile.expertsystem.model.Organ;
import com.mobile.expertsystem.model.Penyakit;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLParser extends Application{

    Organ organ [];
    Gejala gejala [];
    Aturan aturan [];
    Penyakit penyakit [];

    public Organ[] xmlParserOrgan(String xml){

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            //replace String as InputSource
            InputSource is = new InputSource(new StringReader(xml));
            Document doc = dBuilder.parse(is);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("name");

            organ = new Organ[nList.getLength()];

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                Element e = (Element) nNode;

                organ[i] = new Organ();

                organ[i].setId(Integer.parseInt(e.getAttribute("id")));
                organ[i].setName(nNode.getTextContent());
            }

        }
        catch(ParserConfigurationException e){
            Log.e("ParseConfigExc", e.toString());
        }
        catch(IOException e){
            Log.e("IOException", e.toString());
        }
        catch(SAXException e){
            Log.e("SAXEXception", e.toString());
        }

        return organ;
    }

    public Gejala[] xmlParserGejala(String xml){

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            //replace String as InputSource
            InputSource is = new InputSource(new StringReader(xml));
            Document doc = dBuilder.parse(is);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("name");

            gejala = new Gejala[nList.getLength()];

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                Element e = (Element) nNode;

                gejala[i] = new Gejala();

                NodeList dList = nNode.getChildNodes();

                gejala[i].setId(Integer.parseInt(e.getAttribute("id")));
                gejala[i].setName(dList.item(0).getTextContent());

                if (dList.getLength() > 1) {
                    for (int j = 1; j <= dList.getLength() - 1; j++) {
                        gejala[i].addDetail(
                                Integer.parseInt(dList.item(j).getAttributes().getNamedItem("id").getNodeValue()),
                                dList.item(j).getTextContent());
                    }
                }
            }
        }
        catch(ParserConfigurationException e){
            Log.e("ParseConfigExc", e.toString());
        }
        catch(IOException e){
            Log.e("IOException", e.toString());
        }
        catch(SAXException e){
            Log.e("SAXEXception", e.toString());
        }

        return gejala;
    }

    public Aturan [] xmlParserAturan(String xml){
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            //replace String as InputSource
            InputSource is = new InputSource(new StringReader(xml));
            Document doc = dBuilder.parse(is);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("rule-base");

            aturan = new Aturan[nList.getLength()];

            for (int i = 0; i < nList.getLength(); i++) {
                aturan[i] = new Aturan();

                Node nNode = nList.item(i);
                Element e = (Element) nNode;

                //out.println(nNode.getAttributes().getNamedItem("sickness-id").getNodeValue() + "sickness id");
                aturan[i].setSicknessId(Integer.parseInt(nNode.getAttributes().getNamedItem("sickness-id").getNodeValue()));

                NodeList symptomNode = e.getElementsByTagName("symptom");

                for(int j=0; j<symptomNode.getLength(); j++){
                    Detail detail;
                    Gejala gejala = new Gejala();

                    int symptomId = Integer.parseInt(symptomNode.item(j).getAttributes().getNamedItem("id").getNodeValue());
                    gejala.setId(symptomId);

                    NodeList cf = e.getElementsByTagName("cf");
                    aturan[i].getListCf().add(Double.parseDouble(cf.item(j).getTextContent()));

                    try{
                        int detailId = Integer.parseInt(
                                symptomNode.item(j).getAttributes().getNamedItem("detail").getNodeValue());

                        gejala.addDetail(detailId, "");
                    }
                    catch(NullPointerException ex){
                        Log.e("NullPointerEx", ex.toString());
                    }

                    aturan[i].addGejala(gejala);
                }
            }
        }
        catch(ParserConfigurationException e){
            Log.e("ParseConfigExc", e.toString());
        }
        catch(IOException e){
            Log.e("IOException", e.toString());
        }
        catch(SAXException e){
            Log.e("SAXEXception", e.toString());
        }

        return aturan;
    }

    public Penyakit [] xmlParserPenyakit(String xml){
        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            //replace String as InputSource
            InputSource is = new InputSource(new StringReader(xml));
            Document doc = dBuilder.parse(is);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("sickness");

            penyakit = new Penyakit[nList.getLength()];

            for (int i = 0; i < nList.getLength(); i++) {
                ArrayList<String> solution = new ArrayList<>();

                penyakit[i] = new Penyakit();

                Node nNode = nList.item(i);
                Element e = (Element) nNode;

                penyakit[i].setId(Integer.parseInt(nNode.getAttributes().getNamedItem("id").getNodeValue()));

                NodeList sicknessNameNode = e.getElementsByTagName("name");
                NodeList solutionNode = e.getElementsByTagName("solution");

                for(int j=0; j<sicknessNameNode.getLength(); j++){
                    penyakit[i].setName(sicknessNameNode.item(j).getTextContent());
                }

                for(int j=0; j<solutionNode.getLength(); j++){
                    solution.add(solutionNode.item(j).getTextContent());
                }

                penyakit[i].setSolution(solution);
            }
        }
        catch(ParserConfigurationException e){
            Log.e("ParseConfigExc", e.toString());
        }
        catch(IOException e){
            Log.e("IOException", e.toString());
        }
        catch(SAXException e){
            Log.e("SAXEXception", e.toString());
        }

        return penyakit;
    }
}
