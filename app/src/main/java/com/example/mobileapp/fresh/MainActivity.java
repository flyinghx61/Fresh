package com.example.mobileapp.fresh;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //for test if git works

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView2 = (TextView) findViewById(R.id.textView2);
        textView2.setText(getTime());

        TextView textView3 = (TextView) findViewById(R.id.textView3);
        textView3.setText(getTime());


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

        android.support.v7.app.ActionBar.LayoutParams lp =new android.support.v7.app.ActionBar.LayoutParams( ActionBar.LayoutParams.MATCH_PARENT,
           ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View viewTitleBar = getLayoutInflater().inflate(R.layout.main_action_bar, null);
        android.support.v7.app.ActionBar actionBar=this.getSupportActionBar();
        actionBar.setCustomView(viewTitleBar,lp);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        //actionBar.setHomeButtonEnabled(true);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#adff2f"));
        actionBar.setBackgroundDrawable(colorDrawable);
        //actionBar.setTitle("                             Fresh");

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






        int[] icon = { R.drawable.apple,R.drawable.bellpepper,R.drawable.bread,
                R.drawable.broccoli,R.drawable.beans,R.drawable.pear,R.drawable.pumpkin,R.drawable.mushroom,R.drawable.celery, R.drawable.broccoli,R.drawable.eggs,R.drawable.carrot,R.drawable.cauliflower,R.drawable.pear};
        GridView gridView=(GridView)this.findViewById(R.id.gridView);
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        for(int i=0;i<icon.length;i++){
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", icon[i]);
            lstImageItem.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, lstImageItem,R.layout.sample_image_view,
                                         new String[] {"ItemImage"}, new int[]{R.id.imageView});
        gridView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

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

    public void moveToAddItem(View view){
        Intent intent=new Intent(this, AddItemActivity.class);
        startActivity(intent);
    }

    public String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        String t = format.format(new Date());
        return t;
    }
}
