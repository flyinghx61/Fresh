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
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;



public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        int[] icon = { R.drawable.zheng,R.drawable.zheng,R.drawable.zheng,
                R.drawable.green3,R.drawable.zheng,R.drawable.zheng,R.drawable.zheng,R.drawable.zheng,R.drawable.zheng
                ,R.drawable.zheng,R.drawable.zheng,R.drawable.zheng,R.drawable.zheng,R.drawable.mainaddbutton};
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
}
