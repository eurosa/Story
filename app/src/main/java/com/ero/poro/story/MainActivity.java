package com.ero.poro.story;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
//private String jsonURL7 = "https://demonuts.com/Demonuts/JsonTest/Tennis/json_parsing.php";

public class MainActivity extends AppCompatActivity {
//http://localhost/inventory-management-system/php_action/json.php
private String jsonURL = "http://localhost/inventory-management-system/php_action/json.php";

    private final int jsoncode = 1;
    private ListView listView;
    ArrayList<TennisModel> tennisModelArrayList;
    private TennisAdapter tennisAdapter;

    private static ProgressDialog mProgressDialog;
    public ImageButton review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setTheme(R.style.splashScreenTheme);
        setContentView(R.layout.activity_main);

        Toolbar toolbar;
       // toolbar = findViewById(R.id.toolbar_main);

       // setSupportActionBar(toolbar);
        /*
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationIcon(R.drawable.ic_menu_share);  // Replace with your icon

        }
*/

        listView = findViewById(R.id.lv);




        fetchJSON();

    }

    @SuppressLint("StaticFieldLeak")
    private void fetchJSON(){

        showSimpleProgressDialog(this, "Loading...","Fetching Json",false);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.CUPCAKE) {
            new AsyncTask<Void, Void, String>(){
                protected String doInBackground(Void[] params) {
                    String response="";
                    HashMap<String, String> map=new HashMap<>();
                    try {
                        HttpRequest req = new HttpRequest(jsonURL);
                        Log.d("jsonurl",jsonURL);

                        response = req.prepare(HttpRequest.Method.POST).withData(map).sendAndReadString();
                    } catch (Exception e) {
                        response=e.getMessage();
                    }
                    return response;
                }
                protected void onPostExecute(String result) {
                    //do something with response
                    Log.d("newwwss",result);
                    onTaskCompleted(result,jsoncode);
                }
            }.execute();
        }
    }

    public void onTaskCompleted(String response, int serviceCode) {
        Log.d("responsejson", response.toString());
        switch (serviceCode) {
            case jsoncode:

                if (isSuccess(response)) {
                    removeSimpleProgressDialog();  //will remove progress dialog
                    tennisModelArrayList = getInfo(response);
                    tennisAdapter = new TennisAdapter(this,tennisModelArrayList);
                    listView.setAdapter(tennisAdapter);


                }else {
                    Toast.makeText(MainActivity.this, getErrorCode(response), Toast.LENGTH_SHORT).show();
                }
        }
    }

    public ArrayList<TennisModel> getInfo(String response) {
        ArrayList<TennisModel> tennisModelArrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true")) {

                JSONArray dataArray = jsonObject.getJSONArray("data");

                for (int i = 0; i < dataArray.length(); i++) {

                    TennisModel playersModel = new TennisModel();
                    JSONObject dataobj = dataArray.getJSONObject(i);
                    playersModel.setName(dataobj.getString("brand"));
                    playersModel.setCountry(dataobj.getString("category_name"));
                    playersModel.setCity(dataobj.getString("product_id"));
                    playersModel.setImgURL(dataobj.getString("imageUrl"));
                    tennisModelArrayList.add(playersModel);

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tennisModelArrayList;
    }

    public boolean isSuccess(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("status").equals("true")) {
                return true;
            } else {

                return false;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getErrorCode(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            return jsonObject.getString("message");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "No data";
    }

    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void showSimpleProgressDialog(Context context, String title,
                                                String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg);
                mProgressDialog.setCancelable(isCancelable);
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void share(View view) {
    }
}
