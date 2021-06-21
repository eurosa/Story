package com.ero.poro.story;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.google.android.gms.ads.MobileAds;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.Menu;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected static final int SIXTY_SECONDS = 60000;
    protected  static final int DELAY_TIME=6;
    //===================Ad view=====================================================
    protected AdView adView;
    //----------------------------------------------------------------------------------
    //private String jsonURL = "https://demonuts.com/Demonuts/JsonTest/Tennis/json_parsing.php";
    //https must be given otherwise data not will be loaded because you already set ssl
    private String jsonURL = "https://timxn.com/ecom/sexposition.php";

    private final int jsoncode = 1;
    public ListView listView;
    ArrayList<TennisModel> tennisModelArrayList;
    private TennisAdapter tennisAdapter;

    private static ProgressDialog mProgressDialog;
    public ImageButton review;
    //--------------------------------------------------------------------------------
    Handler handler = new Handler();
    private AppBarConfiguration mAppBarConfiguration;
    public View share_button;
    public DrawerLayout drawerLayout;
    public View about_us;
    ProgressBar progbar;
    private NetworkChangeReceiver mNetworkReceiver;

    SearchView searchView = null;
    public MenuItem searchMenuItem;
     public SwipeRefreshLayout pullToRefresh;
    public DatabaseHelper sqlitDb;
    public Note jsonNote;
    private Runnable runnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

       // adView = new AdView(this, AdSize.SMART_BANNER, "a151bd35eeb068d");
        sqlitDb= new DatabaseHelper(this);
        jsonNote= new Note();

        Toolbar toolbar = findViewById(R.id.toolbar);
        about_us=findViewById(R.id.action_settings);
        setSupportActionBar(toolbar);
        progbar = findViewById(R.id.toolbarprogress);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_button);
//--------------------------------------------Initialize google add----------------------------------------------------------------------
        /*MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });*/
       // MobileAds.initialize(this, R.string.admob_app_id);
//=========================================Interstitial Ad=====================================================

       // if (mInterstitialAd.isLoaded()) {
           // mInterstitialAd.show();
       // } else {
         //   Log.d("TAG", "The interstitial wasn't loaded yet.");
        //}

//==============================================================================================================

        drawerLayout=findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
           //toolbar.setOverflowIcon(getDrawable(R.drawable.ic_question_mark_on_a_circular_black_background));

           //getActionBar().setDisplayHomeAsUpEnabled(true);
          // getActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_button);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);

         getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_button);
        }

        share_button= findViewById(R.id.nav_share);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
      */
        NavigationView navigationView = findViewById(R.id.nav_view);


      //  NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        listView = findViewById(R.id.lv);
       // listView.setBackgroundResource(R.drawable.customshape);
        adView = findViewById(R.id.adViewTop);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        //getActionBar().setDisplayHomeAsUpEnabled(true);
        //NavController navController = Navigation.findNavController(this, R.id.nav_share);
        //NavigationUI.setupActionBarWithNavController(this, null, mAppBarConfiguration);
        //NavigationUI.setupWithNavController(navigationView,navController);

        /*
        * For pull to refresh
        * */


         pullToRefresh = findViewById(R.id.pullToRefresh);

         /*
         *
         * SwipeRefreshLayout - swipe down to refresh but not move the view pull down
         * */
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                boolean enable = false;
                if(listView != null && listView.getChildCount() > 0){
                    // check if the first item of the list is visible
                    boolean firstItemVisible = listView.getFirstVisiblePosition() == 0;
                    // check if the top of the first item is visible
                    boolean topOfFirstItemVisible = listView.getChildAt(0).getTop() == 0;
                    // enabling or disabling the refresh layout
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                pullToRefresh.setEnabled(enable);
            }});
        /*==========================================================================================*/

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        if(sqlitDb.getNotesCount()>0) {
                            // removeSimpleProgressDialog();  //will remove progress dialog
                            try {
                                tennisModelArrayList = getArrayInfoSqlite();
                                tennisAdapter = new TennisAdapter((Context) runnable, tennisModelArrayList);
                                listView.setAdapter(tennisAdapter);
                                listView.setTextFilterEnabled(true);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }


                       // fetchJSON();
                        handler.postDelayed(this, SIXTY_SECONDS);
                    }
                };
                handler.postDelayed(runnable, DELAY_TIME);


                fetchJSON(); // your code
                pullToRefresh.setRefreshing(false);
            }
        });





        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        mNetworkReceiver = new NetworkChangeReceiver(this,handler);
        registerReceiver(mNetworkReceiver,intentFilter);


//=====================================Default sqlite data=======================================================//
/*
        // Toast.makeText(Main2Activity.this, ""+sqlitDb.getNotesCount(), Toast.LENGTH_SHORT).show();
        if(sqlitDb.getNotesCount()>0) {
            // removeSimpleProgressDialog();  //will remove progress dialog
            try {
                tennisModelArrayList = getArrayInfoSqlite();
                tennisAdapter = new TennisAdapter(this, tennisModelArrayList, mInterstitialAd);
                listView.setAdapter(tennisAdapter);
                listView.setTextFilterEnabled(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
*/
//=====================================Default sqlite data=======================================================//


        //if(isConnected()) {
            //To fetch json every 60 seconds
             runnable = new Runnable() {
                @Override
                public void run() {
                    if(sqlitDb.getNotesCount()>0) {
                        // removeSimpleProgressDialog();  //will remove progress dialog
                        try {
                            tennisModelArrayList = getArrayInfoSqlite();
                            tennisAdapter = new TennisAdapter((Context) runnable, tennisModelArrayList);
                            listView.setAdapter(tennisAdapter);
                            listView.setTextFilterEnabled(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                    fetchJSON();
                    handler.postDelayed(this, SIXTY_SECONDS);
                }
            };
            handler.postDelayed(runnable, DELAY_TIME);

    //    }




    }




    /*In your on resume check I/N*/
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub

        super.onResume();
        if (isNetworkOnline(this)) {

            //fetchJSON();

        }


     }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);

        // Associate searchable configuration with the SearchView
        searchMenuItem = menu.findItem(R.id.search);
            SearchManager searchManager =
                    (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            searchView =
                    (SearchView) menu.findItem(R.id.search).getActionView();
            searchView.setSearchableInfo(
                    searchManager.getSearchableInfo(getComponentName()));
           //searchView.setIconifiedByDefault(true);


        searchView.setIconifiedByDefault(true);
        searchView.setSubmitButtonEnabled(true);
        //searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search Here");



        if (searchView != null) {


         //   final SearchView finalSearchView = searchView;
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String s) {



                    listView.setTextFilterEnabled(true);
                    tennisAdapter.getFilter().filter(s);
                    Log.d("deer","count");

                   //tennisAdapter.notifyDataSetInvalidated();
                    try {
                        listView.setAdapter(tennisAdapter);
                    }catch(Exception e)
                    {

                    }


                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {


                    listView.setTextFilterEnabled(true);
                    tennisAdapter.getFilter().filter(s);
                    Log.d("deer","count");

                    //tennisAdapter.notifyDataSetInvalidated();
                    try {
                        listView.setAdapter(tennisAdapter);
                    }catch (Exception e)
                    {
                        //Toast.makeText(Main2Activity.this, "No Result Found", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }


            });
        }

        return true;
    }




    @Override
    public boolean onSupportNavigateUp() {
      //  NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
       // return NavigationUI.navigateUp(navController, mAppBarConfiguration)
         //       || super.onSupportNavigateUp();
        return false;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                aboutus();
                return true;
            case R.id.search:
                onSearchRequested();
                return true;



        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @SuppressLint("StaticFieldLeak")
    protected void fetchJSON(){
//to show the progressbar
        progbar.setVisibility(View.VISIBLE);
       // showSimpleProgressDialog(this, "Loading...","Fetching Json",false);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.CUPCAKE) {
            new AsyncTask<Void, Void, String>(){
                protected String doInBackground(Void[] params) {
                    String response="";
                    HashMap<String, String> map=new HashMap<>();
                    try {
                        HttpRequest req = new HttpRequest(jsonURL);
                        response = req.prepare(HttpRequest.Method.POST).withData(map).sendAndReadString();
                    } catch (Exception e) {
                        response=e.getMessage();
                    }
                    return response;
                }
                protected void onPostExecute(String result) {
                    //do something with response
                    Log.d("news",result);
                    onTaskCompleted(result,jsoncode);
                }
            }.execute();
        }
    }

    public void onTaskCompleted(String response, int serviceCode) {
        Log.d("responsejson", response);
        switch (serviceCode) {
            case jsoncode:

                if (isSuccess(response)) {
                    // to hide the progressbar
                    progbar.setVisibility(View.GONE);

                    removeSimpleProgressDialog();  //will remove progress dialog
                    tennisModelArrayList = getInfo(response);
                    tennisAdapter = new TennisAdapter(this,tennisModelArrayList);
                    listView.setAdapter(tennisAdapter);
                    listView.setTextFilterEnabled(true);
                    //setupSearchView();

// If it has loaded, perform these actions

                    /*if(mInterstitialAd.isLoaded()) {
                        // Step 1: Display the interstitial
                        mInterstitialAd.show();
                        // Step 2: Attach an AdListener
                        mInterstitialAd.setAdListener(new AdListener() {
                            @Override
                            public void onAdClosed() {
                                // Step 2.1: Load another ad
                                AdRequest adRequest = new AdRequest.Builder()
                                        .build();
                                mInterstitialAd.loadAd(adRequest);


                            }
                        });
                    }*/


                }else {
                 //  Toast.makeText(Main2Activity.this, ""+sqlitDb.getNotesCount(), Toast.LENGTH_SHORT).show();
                    if(sqlitDb.getNotesCount()>0) {
                        // removeSimpleProgressDialog();  //will remove progress dialog
                        try {
                            tennisModelArrayList = getArrayInfoSqlite();
                            tennisAdapter = new TennisAdapter(this, tennisModelArrayList);
                            listView.setAdapter(tennisAdapter);
                            listView.setTextFilterEnabled(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        }
    }

    //searchView
    private void setupSearchView()
    {
        /*
        * You need to change the value of android:showAsAction from ifRoom|collapseActionView
        * to always. The SearchView's attribute android:iconifiedByDefault should be true, which
        * is the default value, otherwise the user
        *  can not collapse the SearchView after it was expanded programmatically.
        * android:showAction="always", it's important to prevent collapse search option when I click on Item menu.
        * */

        searchView.setIconifiedByDefault(true);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search Here");

    }

    public ArrayList<TennisModel> getInfo(String response) {
        ArrayList<TennisModel> tennisModelArrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true")) {
/*==================================To insert json in sqlite==================================*/


                String stringToBeInserted = jsonObject.toString();


                if(sqlitDb.getNotesCount()==0){



                    long id=sqlitDb.insertNote(stringToBeInserted);
                    Log.d("Insert","one: "+id);

                    Note note = new Note();

                    List<Note> notes = new ArrayList<>();

                    // Select All Query
                    String selectQuery = "SELECT  * FROM " + Note.TABLE_NAME + " ORDER BY " +
                            Note.COLUMN_TIMESTAMP + " DESC";

                    SQLiteDatabase db = sqlitDb.getWritableDatabase();
                    Cursor cursor = db.rawQuery(selectQuery, null);

                    // looping through all rows and adding to list
                    if (cursor.moveToFirst()) {
                        do {
                            note.setId(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)));
                            note.setNote(cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE)));

                            note.setTimestamp(cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP)));

                            notes.add(note);
                        } while (cursor.moveToNext());
                    }
                    jsonObject = new JSONObject(note.getNote());

                    db.close();
                }else {

                    // jsonObject = new JSONObject(response);
                    Note note = new Note();

                    JSONArray dataArray = jsonObject.getJSONArray("data");
                    if(dataArray.length()>0)
                    {


                        List<Note> notes = new ArrayList<>();

                        // Select All Query
                        String selectQuery = "SELECT  * FROM " + Note.TABLE_NAME + " ORDER BY " +
                                Note.COLUMN_TIMESTAMP + " DESC";

                        SQLiteDatabase db = sqlitDb.getWritableDatabase();
                        Cursor cursor = db.rawQuery(selectQuery, null);

                        // looping through all rows and adding to list
                        if (cursor.moveToFirst()) {
                            do {
                                note.setId(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)));
                                note.setNote(cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE)));

                                note.setTimestamp(cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP)));

                                notes.add(note);
                            } while (cursor.moveToNext());
                        }
                        note.setNote(stringToBeInserted);

                        ContentValues values = new ContentValues();
                        values.put(Note.COLUMN_NOTE, note.getNote());

                        Log.d("Note_Update",""+note.getId());
                        // updating row
                        db.update(Note.TABLE_NAME, values, Note.COLUMN_ID + " = ?",
                                new String[]{String.valueOf(note.getId())});

                        jsonObject = new JSONObject(note.getNote());

                        db.close();


                }
                   //jsonObject = new JSONObject(note.getNote());


                }



                /*==================================To insert json in sqlite==================================*/
                jsonObject = new JSONObject(response);

                JSONArray dataArray = jsonObject.getJSONArray("data");

                for (int i = 0; i < dataArray.length(); i++) {

                    TennisModel playersModel = new TennisModel();
                    JSONObject dataobj = dataArray.getJSONObject(i);
                    playersModel.setName(dataobj.getString("name"));
                    playersModel.setTitle(dataobj.getString("meta_title"));
                    playersModel.setDescription(dataobj.getString("description"));
                    playersModel.setImgURL(dataobj.getString("imageUrl"));
                    tennisModelArrayList.add(playersModel);

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tennisModelArrayList;
    }


    public ArrayList<TennisModel> getArrayInfoSqlite() {
        ArrayList<TennisModel> tennisModelArrayList = new ArrayList<>();
        try {

                /*==================================To get json from sqlite==================================*/


                    List<Note> notes = new ArrayList<>();
                    Note note = new Note();

                    //Select All Query
                    String selectQuery = "SELECT  * FROM " + Note.TABLE_NAME + " ORDER BY " +
                            Note.COLUMN_TIMESTAMP + " DESC";

                    SQLiteDatabase db = sqlitDb.getWritableDatabase();
                    Cursor cursor = db.rawQuery(selectQuery, null);

                    //looping through all rows and adding to list
                    if(cursor.moveToFirst()) {
                        do {
                            //  Note note = new Note();
                            note.setId(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)));
                            note.setNote(cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE)));

                            note.setTimestamp(cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP)));

                            notes.add(note);
                        } while (cursor.moveToNext());
                    }



                JSONObject jsonObject = new JSONObject(note.getNote());
                /*==================================To get json from sqlite==================================*/

                JSONArray dataArray = jsonObject.getJSONArray("data");

                for (int i = 0; i < dataArray.length(); i++) {

                    TennisModel playersModel = new TennisModel();
                    JSONObject dataobj = dataArray.getJSONObject(i);
                    playersModel.setName(dataobj.getString("name"));
                    playersModel.setTitle(dataobj.getString("meta_title"));
                    playersModel.setDescription(dataobj.getString("description"));
                    playersModel.setImgURL(dataobj.getString("imageUrl"));
                    tennisModelArrayList.add(playersModel);

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




    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {





      //  Toast.makeText(Main2Activity.this, "Helooo", Toast.LENGTH_SHORT).show();

        Fragment fragment;
        int id = menuItem.getItemId();
        Log.d("show_id",""+id);

        if(id==R.id.nav_share)
        {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Love Story");
                String shareMessage= "\nLet me recommend you this application\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch(Exception e) {
                //e.toString();
            }


        }

        if(id==R.id.nav_review) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + this.getPackageName())));
            } catch (Exception e) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + this.getOpPackageName())));
                }
            }
        }

        if(id==R.id.help) {
            popupHelp();
        }

        return true;

    }


    private void popup(String title, String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(text)
                .setTitle(title)
                .setCancelable(true)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }



    public void aboutus() {
        // An activity may have been overkill AND for some reason
        // it appears in the task switcher and doesn't allow returning to the
        // emergency configuration mode. So a dialog is better for this.
        //IntroActivity.open(FreeButtonActivity.this);

        final String messages[] = {
                "RSysInfo\nrsysinfo@gmail.com",


        };

        // inverted order - They all popup and you hit "ok" to see the next one.
        //  popup("3/3", messages[2]);
        popup("RS POWER", messages[0]);
       // popup("1/2", messages[0]);
    }

    public void popupHelp() {
        // An activity may have been overkill AND for some reason
        // it appears in the task switcher and doesn't allow returning to the
        // emergency configuration mode. So a dialog is better for this.
        //IntroActivity.open(FreeButtonActivity.this);

        final String messages[] = {
                "Welcome to Love Story.",
                "To Read Details Click on Listed Item.",

        };

        // inverted order - They all popup and you hit "ok" to see the next one.
      //  popup("3/3", messages[2]);
        popup("2/2", messages[1]);
        popup("1/2", messages[0]);
    }

    public static boolean isNetworkOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();

    }


}
