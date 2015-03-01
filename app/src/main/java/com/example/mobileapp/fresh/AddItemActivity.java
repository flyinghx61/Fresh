package com.example.mobileapp.fresh;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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

import java.text.SimpleDateFormat;
import java.util.Date;


public class AddItemActivity extends ActionBarActivity {

    int click_times = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_add_item);

        setActionBar();
        setReturnButton();
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(getTime());
        addTwoTab();
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
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
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
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#adff2f"));
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
            adjust_red_layout();
        if (0 == click_times) {
            add_tab();
            click_times++;
        }
    }

    public void addTwoTab(){
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost2);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("linearLayout").setIndicator("Mostly Added")
                .setContent(R.id.linearLayout));

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
                if(tabId.equals("linearLayout2")){
                    category_onClick();
                }
                if(tabId.equals("linearLayout")){
                    RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.red_rectan);
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams();
                    layoutParams.height = 180;
                    relativeLayout.setLayoutParams(layoutParams);
                }
            }
        });
    }

    public void adjust_red_layout() {
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

    public void add_item_to_grid(View view) {
        show_dialog(view);
        ImageButton imageButton=(ImageButton)view;
        final ImageButton new_imageButton=new ImageButton(this);
        new_imageButton.setTag(view.getTag());
        new_imageButton.setImageDrawable(imageButton.getDrawable());
        new_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.food_information_layout, (ViewGroup) findViewById(R.id.foodInformation));
                AlertDialog alertDialog=new AlertDialog.Builder(AddItemActivity.this)
                        .setTitle("Food Information").setView(layout).setPositiveButton("Confirm", null)
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                GridLayout gridLayout=(GridLayout)findViewById(R.id.AlreadyAdd);
                                gridLayout.removeView(new_imageButton);
                            }
                        }).show();
                TextView textView=(TextView)alertDialog.findViewById(R.id.foodName);
                textView.setText((String)new_imageButton.getTag());
            }
        });
        GridLayout gridLayout=(GridLayout)this.findViewById(R.id.AlreadyAdd);
        gridLayout.addView(new_imageButton);
    }

    public void show_dialog(View view){
        String str = String.valueOf(view.getTag());
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.food_information_layout,(ViewGroup) findViewById(R.id.foodInformation));
        AlertDialog alertDialog=new AlertDialog.Builder(AddItemActivity.this)
                .setTitle("Food Information").setView(layout).setPositiveButton("Confirm", null)
                .setNegativeButton("Cancel", null).show();
        TextView textView=(TextView)alertDialog.findViewById(R.id.foodName);
        textView.setText(str);
    }

}

