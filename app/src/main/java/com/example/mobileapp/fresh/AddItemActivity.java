package com.example.mobileapp.fresh;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.gesture.GestureOverlayView;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


public class AddItemActivity extends ActionBarActivity {

    int click_times = 0;

    //default set fridge be clicked while freezer not being clicked
    boolean fridgeClicked = true;
    boolean freezerClicked = false;
    ArrayList add_times=new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_add_item);

        setActionBar();
        addFridgeFreezer();
        setReturnButton();
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(getTime());
        addTwoTab();
        addMostlyAdded();
    }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_add_item, menu);
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

    public String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String t = format.format(new Date());
        return t;
    }

    public void setActionBar() {
        android.support.v7.app.ActionBar.LayoutParams lp = new android.support.v7.app.ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View viewTitleBar = getLayoutInflater().inflate(R.layout.additem_action_bar, null);
        android.support.v7.app.ActionBar actionBar = this.getSupportActionBar();
        actionBar.setCustomView(viewTitleBar, lp);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#B9CB41"));
        actionBar.setBackgroundDrawable(colorDrawable);
    }

    public void setReturnButton() {
        Button mCancel = (Button) findViewById(R.id.main_return_button);
        mCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void category_onClick() {
            adjust_red_layout_category();
        if (0 == click_times) {
            add_tab();
            click_times++;
        }
    }

    public void addFridgeFreezer() {
        TabHost tabHost = (TabHost) findViewById(R.id.tabHostFridgeFreezer);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("scrollViewFridge").setIndicator("Refrigerator")
                .setContent(R.id.scrollViewFridge));

        tabHost.addTab(tabHost.newTabSpec("scrollViewFreezer").setIndicator("Freezer")
                .setContent(R.id.scrollViewFreezer));

        TabWidget tabWidget = tabHost.getTabWidget();
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
                    if (tabId == "scrollViewFridge") {
                        fridgeClicked = true;
                        freezerClicked = false;
                    }
                    if (tabId == "scrollViewFreezer") {
                        fridgeClicked = false;
                        freezerClicked = true;
                    }
                }
            });
        }

    }

    public void addTwoTab(){
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost2);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("MostlyAdded").setIndicator("Mostly Added")
                .setContent(R.id.MostlyAdded));

        tabHost.addTab(tabHost.newTabSpec("linearLayout2").setIndicator("Categories")
                .setContent(R.id.linearLayout2));
        TabWidget tabWidget = tabHost.getTabWidget();
        for (int i = 0; i < tabWidget.getChildCount(); i++) {
            TextView tv = (TextView) tabWidget.getChildAt(i).findViewById(android.R.id.title);
            tv.setAllCaps(false);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(17);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 20, 0, 1);
            tv.setLayoutParams(lp);
            tv.setWidth(200);
            tv.setSingleLine();
            tv.setTextColor(Color.WHITE);
        }

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equals("linearLayout2")) {
                    category_onClick();
                }
                if (tabId.equals("MostlyAdded")) {
                    RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.red_rectan);
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams();
                    layoutParams.height = 160;
                    relativeLayout.setLayoutParams(layoutParams);
                }
            }
        });
    }

    public void adjust_red_layout_category() {
        RelativeLayout relativeLayout = (RelativeLayout) this.findViewById(R.id.red_rectan);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams();
        layoutParams.height = 240;
        relativeLayout.setLayoutParams(layoutParams);
    }

    public void add_tab() {
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("Vegetable")
                .setContent(R.id.tab1));

        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("Fruit")
                .setContent(R.id.tab2));

        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("Meat")
                .setContent(R.id.tab3));

        tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator("Bakery")
                .setContent(R.id.tab4));

        tabHost.addTab(tabHost.newTabSpec("tab5").setIndicator("Dairy")
                .setContent(R.id.tab5));

        tabHost.addTab(tabHost.newTabSpec("tab6").setIndicator("Beverage")
                .setContent(R.id.tab6));

        TabWidget tabWidget = tabHost.getTabWidget();
        for (int i = 0; i < tabWidget.getChildCount(); i++) {
            TextView tv = (TextView) tabWidget.getChildAt(i).findViewById(android.R.id.title);
            tv.setAllCaps(false);
            tv.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0,30,0,10);
            tv.setLayoutParams(lp);
            tv.setWidth(200);
            tv.setSingleLine();
            tv.setTextColor(Color.WHITE);
        }
    }

    public void show_dialog(final View view){
        // show the name of food in AlertDialog
        final String tag = String.valueOf(view.getTag());
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.food_information_layout,(ViewGroup) findViewById(R.id.foodInformation));

        final AlertDialog alertDialog=new AlertDialog.Builder(AddItemActivity.this)
                .setTitle("Food Information").setView(layout).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        add_item_to_grid(view);
                        TextView textView2=(TextView)layout.findViewById(R.id.DaysLeft);
                        String quality_period=textView2.getText().toString();
                        qualityPeriodToDB(tag,quality_period);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
        TextView textView=(TextView)alertDialog.findViewById(R.id.foodName);
        textView.setText(tag);

        TextView textView2=(TextView)alertDialog.findViewById(R.id.DaysLeft);
        String quality_period= qualityPeriodFromDB(tag);
        textView2.setText(quality_period);

        TextView textView3=(TextView)layout.findViewById(R.id.bestBefore);
        try {
            String bestBefore=calculateDate(quality_period);
            textView3.setText(bestBefore);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void add_item_to_grid(final View view) {
        ImageButton imageButton=(ImageButton)view;

        //generate a new instance of ImageButton
        final ImageButton new_imageButton=new ImageButton(this);
        new_imageButton.setTag(view.getTag());
        new_imageButton.setImageDrawable(imageButton.getDrawable());
        new_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                final View layout = inflater.inflate(R.layout.food_information_layout, (ViewGroup) findViewById(R.id.foodInformation));

                TextView textView=(TextView)layout.findViewById(R.id.foodName);
                textView.setText((String)new_imageButton.getTag());

                TextView textView2=(TextView)layout.findViewById(R.id.DaysLeft);
                String quality_period= qualityPeriodFromDB((String)view.getTag());
                textView2.setText(quality_period);

                TextView textView3=(TextView)layout.findViewById(R.id.bestBefore);
                try {
                    String bestBefore=calculateDate(quality_period);
                    textView3.setText(bestBefore);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                AlertDialog alertDialog=new AlertDialog.Builder(AddItemActivity.this)
                        .setTitle("Food Information").setView(layout).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TextView textView2 = (TextView) layout.findViewById(R.id.DaysLeft);
                                String quality_period = textView2.getText().toString();
                                qualityPeriodToDB((String)view.getTag(),quality_period);
                            }
                        })
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (fridgeClicked) {
                                    GridLayout gridLayout = (GridLayout) findViewById(R.id.AlreadyAddFridge);
                                    gridLayout.removeView(new_imageButton);
                                }
                                if (freezerClicked) {
                                    GridLayout gridLayout = (GridLayout) findViewById(R.id.AlreadyAddFreezer);
                                    gridLayout.removeView(new_imageButton);
                                }
                            }
                        }).show();

            }
        });

        if (fridgeClicked) {
            GridLayout gridLayout=(GridLayout)this.findViewById(R.id.AlreadyAddFridge);
            gridLayout.addView(new_imageButton);
        }

        if (freezerClicked) {
            GridLayout gridLayout=(GridLayout)this.findViewById(R.id.AlreadyAddFreezer);
            gridLayout.addView(new_imageButton);
        }

    }

    public void confirm(View view)  throws JSONException {
        GridLayout gridLayoutFridge = (GridLayout) findViewById(R.id.AlreadyAddFridge);
        GridLayout gridLayoutFreezer = (GridLayout) findViewById(R.id.AlreadyAddFreezer);
        getAllImageButton(gridLayoutFridge,"fridge");
        getAllImageButton(gridLayoutFreezer,"freezer");

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void getAllImageButton(GridLayout gridLayout,String place) throws JSONException {
        int childCountFridge=gridLayout.getChildCount();
        for(int i=0;i<childCountFridge;i++){
            ImageButton imageButton=(ImageButton)gridLayout.getChildAt(i);
            String tag=(String)imageButton.getTag();

            String quality_period=qualityPeriodFromDB(tag);

            addTimesInDB(tag);
            sendPostMessage(tag,place,quality_period);
        }
    }

    public void sendPostMessage(final String tag,String place,String quality_period) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://52.11.25.130:8080/api/food";

        JSONObject obj = new JSONObject();
        obj.put("foodname", tag);
        obj.put("add_time", this.getTime());
        obj.put("store_place",place);
        obj.put("expire_period",quality_period);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url,obj, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Message: ", "Post Success");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Message: ","Post failed");
                    }
                });
        queue.add(jsObjRequest);
    }

    public String qualityPeriodFromDB(String tag){
       SQLiteDatabase mSQLiteDatabase=this.openOrCreateDatabase("QualityPeriod",MODE_PRIVATE,null);
     //  String CREATE_TABLE=" CREATE TABLE QualityPeriod (_id integer primary key autoincrement , name text , day text)";
     //  mSQLiteDatabase.execSQL(CREATE_TABLE);
      //  String INSERT_COLUMN="ALTER TABLE QualityPeriod ADD times integer";
     //   mSQLiteDatabase.execSQL(INSERT_COLUMN);
     //   String INSERT_DATA="INSERT INTO QualityPeriod (name, day, times) values ('egg', '10', '-6')";
      //    mSQLiteDatabase.execSQL(INSERT_DATA);
      // mSQLiteDatabase.delete("QualityPeriod", "name=?", new String[]{"egg"});
        //   String sql = "UPDATE QualityPeriod SET times = '0' WHERE name = 'pumpkin' ";

     //   mSQLiteDatabase.execSQL(sql);
        Cursor mcursor=mSQLiteDatabase.query("QualityPeriod", new String[]{"name","day","times"},"name=?",
                new String[]{tag}, null, null, null);
        mcursor.moveToFirst();
        String quality_period=mcursor.getString(1);
        mSQLiteDatabase.close();
        return quality_period;

    }

    public void qualityPeriodToDB(String tag,String qualityPeriod){
        SQLiteDatabase mSQLiteDatabase=this.openOrCreateDatabase("QualityPeriod",MODE_PRIVATE,null);
        String sql = "UPDATE QualityPeriod SET day = "+"'"+qualityPeriod+"'"+" WHERE name = "+"'"+tag+"'";
        mSQLiteDatabase.execSQL(sql);
        mSQLiteDatabase.close();
    }

    public String calculateDate(String period) throws ParseException {
        String dateInString = this.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance(); // Get Calendar Instance
        c.setTime(sdf.parse(dateInString));

        c.add(Calendar.DATE, Integer.parseInt(period));  // add 45 days

        Date resultdate = new Date(c.getTimeInMillis());   // Get new time
        dateInString = sdf.format(resultdate);
        return dateInString;
    }

    public void addTimesInDB(String tag){
        SQLiteDatabase mSQLiteDatabase=this.openOrCreateDatabase("QualityPeriod",MODE_PRIVATE,null);

        Cursor mcursor=mSQLiteDatabase.query("QualityPeriod", new String[]{"name","day","times"},"name=?",
                new String[]{tag}, null, null, null);
        mcursor.moveToFirst();
        int times=mcursor.getInt(2);

        int new_times=times-1;
        Log.d("Message: ", String.valueOf(times));
        ContentValues cv = new ContentValues();
        cv.put("times", new_times);
        mSQLiteDatabase.update("QualityPeriod", cv, "name=?",new String[]{tag});
        mSQLiteDatabase.close();
    }

    public void addMostlyAdded(){
        int added_number=0;
        SQLiteDB sqLiteDatabase=new SQLiteDB(this);
        SQLiteDatabase mSQLiteDatabase=sqLiteDatabase.getWritableDatabase();
        Cursor mcursor=mSQLiteDatabase.query("QualityPeriod", new String[]{"name","day","times"},null,
                null, null, null, null);
        mcursor.moveToFirst();
        do{
     //       Log.d("foodname: ",mcursor.getString(0)+mcursor.getInt(2));
            int times=mcursor.getInt(2);
            add_times.add(times);
        }
        while(mcursor.moveToNext());

        Object[] times_array=add_times.toArray();
        Arrays.sort(times_array);

        for(int i=0;i<16;i++){
            if(added_number>15){
                break;
            }

            if( i>0 && times_array[i-1] == times_array[i]){
                continue;
            }

            int query_times=(int)times_array[i];
            mcursor=mSQLiteDatabase.query("QualityPeriod", new String[]{"name","day","times"},"times=?",
                    new String[]{String.valueOf(query_times)}, null, null, null);
            mcursor.moveToFirst();
            do{
                if(added_number>15){
                    break;
                }
                String foodname=mcursor.getString(0);
                int identifier=0;
                switch(foodname){
                    case "ice cream": identifier = getResources().getIdentifier("ice_cream", "drawable", "com.example.mobileapp.fresh");break;
                    case "frozen pizza": identifier = getResources().getIdentifier("frozen_pizza", "drawable", "com.example.mobileapp.fresh");break;
                    case "iced tea": identifier = getResources().getIdentifier("iced_tea", "drawable", "com.example.mobileapp.fresh");break;
                    case "cheese slice": identifier = getResources().getIdentifier("cheese_slice", "drawable", "com.example.mobileapp.fresh");break;
                    case "mixed fruit": identifier = getResources().getIdentifier("mixed_fruit", "drawable", "com.example.mobileapp.fresh");break;
                    default: identifier = getResources().getIdentifier(foodname, "drawable", "com.example.mobileapp.fresh");
                }

                ImageButton imageButton=new ImageButton(this);
                imageButton.setImageResource(identifier);
                imageButton.setTag(foodname);
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show_dialog(v);
                    }
                });
                GridLayout gridLayout=(GridLayout)this.findViewById(R.id.MostlyAdded);
                gridLayout.addView(imageButton);
                GridLayout.LayoutParams layoutParams=(GridLayout.LayoutParams)imageButton.getLayoutParams();
                layoutParams.width=170;
                layoutParams.height=150;
                imageButton.setLayoutParams(layoutParams);
                added_number++;
            }
            while(mcursor.moveToNext());
        }
        mSQLiteDatabase.close();
    }

    }

