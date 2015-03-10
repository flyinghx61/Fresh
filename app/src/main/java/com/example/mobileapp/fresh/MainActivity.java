package com.example.mobileapp.fresh;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
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
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


public class MainActivity extends ActionBarActivity {
    boolean fridgeClicked = true;
    boolean freezerClicked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        addFridgeFreezer();
        getDatabaseResponse();


       // int identifier = getResources().getIdentifier("Beans", "drawable","com.example.mobileapp.fresh");
      //  ImageButton imageButton2=(ImageButton)this.findViewById(R.id.imageButton);
      //  imageButton2.setImageResource(identifier);

     /*   int[] icon = { R.drawable.apple,R.drawable.Bellpepper,R.drawable.bread,
                R.drawable.Broccoli,R.drawable.Beans,R.drawable.pear,R.drawable.Pumpkin,R.drawable.Mushroom,R.drawable.Celery, R.drawable.Broccoli,R.drawable.eggs,R.drawable.Carrot,R.drawable.Cauliflower,R.drawable.pear};
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

    public void addFridgeFreezer() {
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost3);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("FridgeScrollView").setIndicator("Refrigerator")
                .setContent(R.id.FridgeScrollView));

        tabHost.addTab(tabHost.newTabSpec("FreezerScrollView").setIndicator("Freezer")
                .setContent(R.id.FreezerScrollView));

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
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        String t = format.format(new Date());
        return t;
    }

    public void getDatabaseResponse(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://52.10.237.82:8080/api/food";

        final JsonArrayRequest jsObjRequest = new JsonArrayRequest
                (url,new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ArrayList fridge_date= getFridgeDate(response);
                            ArrayList freezer_date= getFreezerDate(response);
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

    public ArrayList getFridgeDate(JSONArray response) throws JSONException {
        ArrayList fridge_date=new ArrayList();
        int length=response.length();
        int exist=0;
        for(int i=5;i<length;i++){
            JSONObject jsonObject=(JSONObject)response.get(i);
            String str=jsonObject.getString("add_time");
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
        Arrays.sort(fridge_date.toArray());
        return fridge_date;
    }

    public ArrayList getFreezerDate(JSONArray response) throws JSONException{
        ArrayList freezer_date=new ArrayList();
        int length=response.length();
        int exist=0;
        for(int i=5;i<length;i++){
            JSONObject jsonObject=(JSONObject)response.get(i);
            String str=jsonObject.getString("add_time");
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
        Arrays.sort(freezer_date.toArray());
        return freezer_date;
    }

    public void FridgeDisplay(ArrayList date, JSONArray response) throws JSONException {
        for(int i=0;i<date.size();i++){
            String date1=(String)date.get(i);
            displayDateAndImage(date1, i,"fridge",response);
        }
    }

    public void FreezerDisplay(ArrayList date, JSONArray response) throws JSONException {
        for(int i=0;i<date.size();i++){
            String date1=(String)date.get(i);
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
        GridLayout gridLayout=new GridLayout(this);
        gridLayout.setColumnCount(4);
        RelativeLayout.LayoutParams grid_param = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,320);
        grid_param.setMargins(0,margin_top_grid,0,0);
        gridLayout.setLayoutParams(grid_param);
        relativeLayout1.addView(gridLayout);

            //   Add image on gridlayout
            for(int i=5;i<response.length();i++){
            JSONObject jsonObject=(JSONObject)response.get(i);
            String food_date=jsonObject.getString("add_time");
            String store_place=jsonObject.getString("store_place");
            if(food_date.equals(date) && store_place.equals(place)){
                int identefier=Integer.valueOf(jsonObject.getString("image_id"));
                ImageButton imageButton=new ImageButton(this);
                imageButton.setImageResource(identefier);
                gridLayout.addView(imageButton);
            }
        }
    }
}
