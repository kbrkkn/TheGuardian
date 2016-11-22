package ui.android.theguardian;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Kubra on 21.11.2016.
 */

public final class QueryHelper {
    private static final String LOG_TAG=QueryHelper.class.getName();
    private  QueryHelper(){}
    public static ArrayList<News> fetchData(String requestUrl) {

        URL url=createUrl(requestUrl);
        String jsonResponse="";
        try {
            jsonResponse=makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG,"Couldn't receive jsonResponse",e);
        }
        //return News from json response
        ArrayList<News> newsList=extractNewsFromJson(jsonResponse);

        return newsList;
    }
    private static ArrayList<News> extractNewsFromJson(String jsonResponse){
        if (TextUtils.isEmpty(jsonResponse)) {
                       return null;
        }
        ArrayList<News> newsList=new ArrayList<>();

        try {
            JSONObject baseResponse=new JSONObject(jsonResponse);//root object
            JSONObject response=baseResponse.getJSONObject("response");//response object
            JSONArray resultsArray =response.getJSONArray("results");//get results array from response object
            for (int i = 0; i< resultsArray.length(); i++){//get sectionName and webTitle from each results object
                JSONObject resultObject= resultsArray.getJSONObject(i);
                String section=resultObject.getString("sectionName");
                String title=resultObject.getString("webTitle");
                News news=new News(section,title);
                newsList.add(news);//add to list News object
            }

        }
        catch(JSONException e){
            Log.e(LOG_TAG,e.getMessage());
        }
        return newsList;
    }
    //convert string url to URL object
    private static URL createUrl(String urlString){
        URL url=null;
        try {
            url=new URL(urlString);
        }
        catch(MalformedURLException exception){//Geçersiz Url
            Log.e(LOG_TAG,exception.getMessage());
        }
        return url;
    }
    //make http conn and return string jsonResponse
    private static String makeHttpRequest(URL url)throws IOException{
        String jsonResponse="";
        HttpURLConnection connection=null;
        InputStream inputStream=null;
        try{
            //get HttpURLConnection instance
            connection=(HttpURLConnection)url.openConnection();
            //set request method
            connection.setRequestMethod("GET");
            //set time out in milliseconds
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            //connect
            connection.connect();
            //if connection is success,then get inputStream instance
            //and assign as a attribute in readFromStream method.
            if(connection.getResponseCode()==200){
                inputStream=connection.getInputStream();
                jsonResponse=readFromStream(inputStream);
            }
            else{
                Log.e(LOG_TAG,"connection isn't success.Its Response Code is "+connection.getResponseCode());
            }
        }catch (IOException e){
            Log.e(LOG_TAG,"CONNECTION NOT ESTABLISHED",e);
        }
        finally {//finally disconnect connection and close inputStream
            if(connection!=null){
                connection.disconnect();
            }
            if(inputStream!=null){
                inputStream.close();
            }
        }

        return jsonResponse;
    }
    //read line by line from stream
    //return output as a string
    private static String readFromStream(InputStream inputStream) throws IOException
    {
        StringBuilder output=new StringBuilder();
        if(inputStream!=null) {
            //utf-8 for human readable string
            InputStreamReader streamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(streamReader);
            //read line by line from reader
            String line = reader.readLine();
            while (line != null) {
                output.append(line);//append line to output
                line = reader.readLine();//continue
            }

        }
        return  output.toString();//convert to string
    }
}
