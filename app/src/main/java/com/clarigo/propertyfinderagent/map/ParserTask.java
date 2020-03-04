package com.clarigo.propertyfinderagent.map;


import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

    // Parsing the data in non-ui thread
    private ParserCallBack parserCallBack;

    public ParserTask(ParserCallBack parserCallBack) {
        this.parserCallBack = parserCallBack;
    }

    @Override
    protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

        JSONObject jObject;
        JSONObject j_Start_Address;
        JSONObject j_Desti_Address;
        List<List<HashMap<String, String>>> routes = null;

        try {
            jObject = new JSONObject(jsonData[0]);

            DirectionsJSONParser parser = new DirectionsJSONParser();
            // Starts parsing data
            routes = parser.parse(jObject);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return routes;
    }

    @Override
    protected void onPostExecute(List<List<HashMap<String, String>>> routes) {
       parserCallBack.getroot(routes);
    }
}

