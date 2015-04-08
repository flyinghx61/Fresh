package com.example.mobileapp.fresh;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends ActionBarActivity {
    boolean fridgeClicked = true;
    boolean freezerClicked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

     //   Intent intent=new Intent(this,LoginActivity.class);
     //   startActivity(intent);
        getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new SQLiteDB(this);
        Time time=new Time();
        time.execute();
      //  TextView textView2 = (TextView) findViewById(R.id.textView2);
    //    textView2.setText(getTime());

     //   TextView textView3 = (TextView) findViewById(R.id.textView3);
     //   textView3.setText(getTime());


        /*Parse.enableLocalDatastore(this);
        Parse.initialize(this, "TOjzpOCEukqSVUCK86fp7hs0Rk75IkkPxmttt1Tl", "j5w4OL8ZHHAyv0GAF8ItkN8hkOalm8XyOZRVh4hf");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Default");
        query.whereEqualTo("type", "apple");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {
                if (e == null) {
                   for(int i=0;i<parseObjects.size();i++){
                       String id=parseObjects.get(i).getObjectId();
                       Log.d("score", "Retrieved " + id + " scores");
                   }

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });*/

       // String score = defaultObj.getString("type");
      //  Log.d("score", "Retrieved " + score + " scores");

        addActionbar();
        addSpinner();
        addFridgeFreezer();
        getDatabaseResponse();



        // int identifier = getResources().getIdentifier("Beans", "drawable","com.example.mobileapp.fresh");
      //  ImageButton imageButton2=(ImageButton)this.findViewById(R.id.imageButton);
      //  imageButton2.setImageResource(identifier);

     /*   int[] icon = { R.drawable.apple,R.drawable.Bellpepper,R.drawable.bread,
                R.drawable.Broccoli,R.drawable.Beans,R.drawable.pear,R.drawable.Pumpkin,R.drawable.Mushroom,R.drawable.Celery, R.drawable.Broccoli,R.drawable.egg,R.drawable.Carrot,R.drawable.Cauliflower,R.drawable.pear};
        GridView gridView=(GridView)this.findViewById(R.id.gridView);
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        for(int i=0;i<icon.length;i++){
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", icon[i]);
            lstImageItem.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, lstImageItem,R.layout.sample_image_view,
                                         new String[] {"ItemImage"}, new int[]{R.id.imageView});
        gridView.setAdapter(adapter);*/
    }


//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void addActionbar() {
        android.support.v7.app.ActionBar.LayoutParams lp =new android.support.v7.app.ActionBar.LayoutParams( ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View viewTitleBar = getLayoutInflater().inflate(R.layout.main_action_bar, null);
        android.support.v7.app.ActionBar actionBar=this.getSupportActionBar();
        actionBar.setCustomView(viewTitleBar,lp);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        //actionBar.setHomeButtonEnabled(true);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#B9CB41"));
        actionBar.setBackgroundDrawable(colorDrawable);
        //actionBar.setTitle("                             Fresh");
    }


    public void addSpinner() {
        Spinner filterSpinner = (Spinner) findViewById(R.id.main_filter_spinner);

        ArrayAdapter<CharSequence> filterAdapter = ArrayAdapter.createFromResource(
                this, R.array.filter_array, R.layout.customized_spinner_item);
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(filterAdapter);
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String msg;
                if (position == 0) {
                    msg = "Items filtered by date added";
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    System.out.println("item filtered by date added.");
                } else if (position == 1) {
                    msg = "Items filtered by expire date";
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    System.out.println("item filtered by expire date.");
                } else if (position == 2) {
                    msg = "Items filtered by categories";
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    System.out.println("item filtered by categories.");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    public void addFridgeFreezer() {
        final TabHost tabHost = (TabHost) findViewById(R.id.tabHost3);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("FridgeScrollView").setIndicator("Refrigerator")
                .setContent(R.id.FridgeScrollView));

        tabHost.addTab(tabHost.newTabSpec("FreezerScrollView").setIndicator("Freezer")
                .setContent(R.id.FreezerScrollView));

        final TabWidget tabWidget = tabHost.getTabWidget();
        for (int i = 0; i < tabWidget.getChildCount(); i++) {
            TextView tv = (TextView) tabWidget.getChildAt(i).findViewById(android.R.id.title);
            tv.setAllCaps(false);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(17);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 20, 0, 1);
            tv.setLayoutParams(lp);
            tv.setWidth(180);
            tv.setSingleLine();
            tv.setTextColor(Color.WHITE);

            tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
                @Override
                public void onTabChanged(String tabId) {

                    if (tabId == "FridgeScrollView") {
                        fridgeClicked = true;
                        freezerClicked = false;
                    }
                    if (tabId == "FreezerScrollView") {
                        fridgeClicked = false;
                        freezerClicked = true;
                    }
                }
            });
        }

    }

    public void moveToAddItem(View view){
        Intent intent=new Intent(this, AddItemActivity.class);
        startActivity(intent);
    }

    public String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String t = format.format(new Date());
        return t;
    }

    public void getDatabaseResponse(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://52.11.25.130:8080/api/food";

        final JsonArrayRequest jsObjRequest = new JsonArrayRequest
                (url,new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Object[] fridge_date= getFridgeDate(response);
                            Object[] freezer_date= getFreezerDate(response);
                            FridgeDisplay(fridge_date, response);
                            FreezerDisplay(freezer_date, response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        queue.add(jsObjRequest);
    }

    public Object[] getFridgeDate(JSONArray response) throws JSONException {
        ArrayList fridge_date=new ArrayList();
        int length=response.length();
        int exist=0;
        for(int i=0;i<length;i++){
            JSONObject jsonObject=(JSONObject)response.get(i);
            String str=jsonObject.getString("add_time").substring(0,10);
            String place=jsonObject.getString("store_place");
            if(place.equals("fridge")){
                for(int j=0;j<fridge_date.size();j++){
                    if((fridge_date.get(j)).equals(str)){
                        exist=1;
                        break;
                    }
                }
                if(0 == exist)
                    fridge_date.add(str);
                exist=0;
            }
        }
        Object[] fridge_date_array=fridge_date.toArray();
        Arrays.sort( fridge_date_array);
        return  fridge_date_array;
    }

    public Object[] getFreezerDate(JSONArray response) throws JSONException{
        ArrayList freezer_date=new ArrayList();
        int length=response.length();
        int exist=0;
        for(int i=0;i<length;i++){
            JSONObject jsonObject=(JSONObject)response.get(i);
            String str=jsonObject.getString("add_time").substring(0,10);
            String place=jsonObject.getString("store_place");
            if(place.equals("freezer")){
                for(int j=0;j<freezer_date.size();j++){
                    if((freezer_date.get(j)).equals(str)){
                        exist=1;
                        break;
                    }
                }
                if(0 == exist)
                    freezer_date.add(str);
                exist=0;
            }
        }
        Object[] freezer_date_array=freezer_date.toArray();
        Arrays.sort(freezer_date_array);
        return freezer_date_array;
    }

    public void FridgeDisplay(Object[] date, JSONArray response) throws JSONException {

        for(int i=0;i<date.length;i++){
            String date1=String.valueOf(date[i]);
            displayDateAndImage(date1, i,"fridge",response);
        }
    }

    public void FreezerDisplay(Object[] date, JSONArray response) throws JSONException {
        for(int i=0;i<date.length;i++){
            String date1=(String)date[i];
            displayDateAndImage(date1, i,"freezer",response);
        }
    }

    public void displayDateAndImage(String date, int number, String place,JSONArray response) throws JSONException {
        int margin_top=number*400;
        RelativeLayout relativeLayout=new RelativeLayout(this);
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,80);
        param.setMargins(0,margin_top,0,0);
        relativeLayout.setLayoutParams(param);
        relativeLayout.setBackgroundColor(0x3a210404);

        RelativeLayout relativeLayout1=new RelativeLayout(this);

        if(place.equals("fridge")){
            relativeLayout1=(RelativeLayout)this.findViewById(R.id.FridgeInterface);
            relativeLayout1.addView(relativeLayout);
        }

        if(place.equals("freezer")){
            relativeLayout1=(RelativeLayout)this.findViewById(R.id.FreezerInterface);
            relativeLayout1.addView(relativeLayout);
        }

        // Display Date
        TextView textView=new TextView(this);
        textView.setText(date);
        textView.setTextSize(20);
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        textView.setLayoutParams(layoutParams);
        relativeLayout.addView(textView);

        // Add gridlayout
        int margin_top_grid=number*400+80;
        ScrollView scrollView=new ScrollView(this);
        final GridLayout gridLayout=new GridLayout(this);
        gridLayout.setColumnCount(4);
        RelativeLayout.LayoutParams grid_param = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,320);
        grid_param.setMargins(0,margin_top_grid,0,0);
        gridLayout.setLayoutParams(grid_param);
        scrollView.setLayoutParams(grid_param);
        relativeLayout1.addView(scrollView);
        relativeLayout1.addView(gridLayout);

        //   Add image on gridlayout
        for(int i=0;i<response.length();i++){
            JSONObject jsonObject=(JSONObject)response.get(i);
            String food_date=jsonObject.getString("add_time").substring(0,10);
            String store_place=jsonObject.getString("store_place");

            if(food_date.equals(date) && store_place.equals(place)){
                final String id=jsonObject.getString("_id");
                final String foodname=jsonObject.getString("foodname");
                final String quality_period=jsonObject.getString("expire_period");
                final String add_time=jsonObject.getString("add_time");
                final ImageButton imageButton=new ImageButton(this);
                int identifier=0;
                switch(foodname){
                    case "ice cream": identifier = getResources().getIdentifier("ice_cream", "drawable", "com.example.mobileapp.fresh");break;
                    case "frozen pizza": identifier = getResources().getIdentifier("frozen_pizza", "drawable", "com.example.mobileapp.fresh");break;
                    case "iced tea": identifier = getResources().getIdentifier("iced_tea", "drawable", "com.example.mobileapp.fresh");break;
                    case "cheese slice": identifier = getResources().getIdentifier("cheese_slice", "drawable", "com.example.mobileapp.fresh");break;
                    case "mixed fruit": identifier = getResources().getIdentifier("mixed_fruit", "drawable", "com.example.mobileapp.fresh");break;
                    default: identifier = getResources().getIdentifier(foodname, "drawable", "com.example.mobileapp.fresh");
                }

                imageButton.setImageResource(identifier);
                gridLayout.addView(imageButton);
                GridLayout.LayoutParams layoutParams1=(GridLayout.LayoutParams)imageButton.getLayoutParams();
                layoutParams1.width=170;
                layoutParams1.height=150;
                imageButton.setLayoutParams(layoutParams1);
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater inflater = getLayoutInflater();
                        final View layout = inflater.inflate(R.layout.food_information_layout_main, (ViewGroup) findViewById(R.id.foodInformation));

                        TextView textView = (TextView) layout.findViewById(R.id.foodName);
                        textView.setText(foodname);

                        TextView textView3=(TextView)layout.findViewById(R.id.bestBefore);
                        try {
                             String bestBefore=calculateDate(add_time,quality_period);
                             textView3.setText(bestBefore);
                            TextView textView2 = (TextView) layout.findViewById(R.id.DaysLeft);
                            int daysLeft=calDateDifference(bestBefore,getTime());
                            textView2.setText(String.valueOf(daysLeft));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Food Information").setView(layout).setPositiveButton("Confirm", null)
                                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        gridLayout.removeView(imageButton);
                                        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                                        String url = "http://52.11.25.130:8080/api/food/food_id/";
                                        url = url + id;

                                        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                                                (Request.Method.DELETE, url, new JSONObject(), new Response.Listener<JSONObject>() {
                                                    @Override
                                                    public void onResponse(JSONObject response) {
                                                        Log.d("Message: ", "1");
                                                    }
                                                }, new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        Log.d("Message: ", "Delete Failed");
                                                    }
                                                });
                                        queue.add(jsObjRequest);
                                    }
                                }).show();
                    }
                });
             }
        }
    }

    public String calculateDate(String add_time, String expire_period) throws ParseException {
        String dateInString = add_time;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance(); // Get Calendar Instance
        c.setTime(sdf.parse(dateInString));

        c.add(Calendar.DATE, Integer.parseInt(expire_period));  // add 45 days

        Date resultdate = new Date(c.getTimeInMillis());   // Get new time
        dateInString = sdf.format(resultdate);
        return dateInString;
    }

    public int calDateDifference(String end,String from) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar end_date = Calendar.getInstance(); // Get Calendar Instance
        end_date.setTime(sdf.parse(end));

        Calendar from_date = Calendar.getInstance(); // Get Calendar Instance
        from_date.setTime(sdf.parse(from));
        return (int)(end_date.getTime().getTime()-from_date.getTime().getTime())/ (1000 * 60 * 60 * 24);
    }

    private class Time extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            SQLiteDBSetting sqLiteDBSetting = new SQLiteDBSetting(MainActivity.this);
            SQLiteDatabase sqLiteDatabase = sqLiteDBSetting.getWritableDatabase();

            Cursor cursor = sqLiteDatabase.query("Setting", new String[] {"function", "state"}, "function=?",
                    new String[] {"alertTime"}, null, null, null);
            cursor.moveToFirst();
            String alertTime = cursor.getString(1);


            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String currentTime = sdf.format(new Date());
            sqLiteDatabase.close();



            while (!alertTime.equals(currentTime)) {
                try {
                    Thread.sleep(3000);
                    currentTime = sdf.format(new Date());
                    sqLiteDatabase = openOrCreateDatabase("Setting",MODE_PRIVATE,null);
                    cursor = sqLiteDatabase.query("Setting", new String[] {"function", "state"}, "function=?",
                            new String[] {"alertTime"}, null, null, null);
                    cursor.moveToFirst();
                    alertTime = cursor.getString(1);
                    sqLiteDatabase.close();
                    Log.d("message", "updating" + currentTime + " alerttime is" + alertTime);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            sendNotification();

            Log.d("Message: ", "time execute!!!sendNotification");
            return null;
        }

        public void sendNotification() {
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            String url = "http://52.11.25.130:8080/api/food";
            final Intent intent = new Intent(MainActivity.this, MainActivity.class);
            final MainActivity mainActivity=MainActivity.this;
            final JsonArrayRequest jsObjRequest = new JsonArrayRequest
                    (url,new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                           for(int i=0;i<response.length();i++) {
                               try {
                                   int NOTIFICATION_ID = (int)(Math.random()*10000);
                                   JSONObject jsonObject=(JSONObject)response.get(i);
                                   String foodname=jsonObject.getString("foodname");
                                   String add_time=jsonObject.getString("add_time");
                                   String quality_period=jsonObject.getString("expire_period");
                                   String bestBefore=MainActivity.this.calculateDate(add_time, quality_period);
                                   int daysLeft=calDateDifference(bestBefore,getTime());
                                   Log.d("left",String.valueOf(daysLeft));
                                   if(daysLeft >= 0 && daysLeft < 3){
                                       NotificationCompat.Builder mBuilder =
                                               new NotificationCompat.Builder(MainActivity.this)
                                                               .setContentIntent(PendingIntent.getActivity(mainActivity, 0, intent, PendingIntent.FLAG_ONE_SHOT))
                                                               .setContentTitle("Alert: " + foodname + " Nearly Expire !")
                                                               .setContentText(foodname + " will expired in " + String.valueOf(daysLeft) + " days !");
                                       switch(foodname){
                                           case "ice cream": mBuilder.setSmallIcon(MainActivity.this.getResources().getIdentifier("ice_cream", "drawable", "com.example.mobileapp.fresh"));break;
                                           case "frozen pizza":  mBuilder.setSmallIcon(MainActivity.this.getResources().getIdentifier("frozen_pizza", "drawable", "com.example.mobileapp.fresh"));break;
                                           case "iced tea":  mBuilder.setSmallIcon(MainActivity.this.getResources().getIdentifier("iced_tea", "drawable", "com.example.mobileapp.fresh"));break;
                                           case "cheese slice":  mBuilder.setSmallIcon(MainActivity.this.getResources().getIdentifier("cheese_slice", "drawable", "com.example.mobileapp.fresh"));break;
                                           case "mixed fruit":  mBuilder.setSmallIcon(MainActivity.this.getResources().getIdentifier("mixed_fruit", "drawable", "com.example.mobileapp.fresh"));break;
                                           default:  mBuilder.setSmallIcon(MainActivity.this.getResources().getIdentifier(foodname, "drawable", "com.example.mobileapp.fresh"));
                                       }

                                       NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                       nm.notify(NOTIFICATION_ID, mBuilder.build());
                                   }
                                   if(daysLeft<0){
                                       NotificationCompat.Builder mBuilder =
                                               new NotificationCompat.Builder(MainActivity.this)
                                                       .setSmallIcon(MainActivity.this.getResources().getIdentifier(foodname, "drawable","com.example.mobileapp.fresh"))
                                                       .setContentTitle("Caution: "+foodname+" Expired !!")
                                                       .setContentText( foodname+" has expired for "+String.valueOf(-daysLeft)+" days !");
                                       NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                       nm.notify(NOTIFICATION_ID, mBuilder.build());
                                   }
                               } catch (JSONException e) {
                                   e.printStackTrace();
                               } catch (ParseException e) {
                                   e.printStackTrace();
                               }
                           }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
            queue.add(jsObjRequest);
        }
    }


    public void alertSetting(View view) {
        SQLiteDBSetting sqLiteDBSetting = new SQLiteDBSetting(this);
        SQLiteDatabase sqLiteDatabase = sqLiteDBSetting.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query("Setting", new String[] {"function", "state"}, null,
                null, null, null, null);
        cursor.moveToFirst();
        String alertTime = cursor.getString(1);
        Log.d("Message", alertTime);
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        try {
            Date currentDate = df.parse(alertTime);
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
            String alerttime = sdf.format(currentDate);
            Log.d("Message", alerttime);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String currentHour = alertTime.substring(0,2);
        String currentMin = alertTime.substring(3,5);
        Log.d("Message", currentHour + ":" + currentMin);


        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String updateTime = String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute);
                SQLiteDatabase sqLiteDatabase= openOrCreateDatabase("Setting",MODE_PRIVATE,null);
                String sql = "UPDATE Setting SET state = "+"'"+updateTime+"'"+" WHERE function = "+"'alertTime'";
                sqLiteDatabase.execSQL(sql);
                sqLiteDatabase.close();

            }
        },Integer.parseInt(currentHour), Integer.parseInt(currentMin), false);
        timePickerDialog.setTitle("Alert Time Setting");
        timePickerDialog.show();
        sqLiteDatabase.close();

    }

}




