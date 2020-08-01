package com.learning.submityields0628;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.learning.gson.V_Style_Station_Process;
import com.learning.utils.BaseActivity;

public class V_Style_Station_Detail extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_picture_of_products);
        Intent intent = getIntent();
        String v_style_name =  intent.getStringExtra("v_style_name");
        String urlOfPic  = intent.getStringExtra("urlOfPic");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        ImageView styleImageView = (ImageView) findViewById(R.id.style_image_view);
        TextView tv_style_line_distribution = (TextView) findViewById(R.id.tv_style_line_distribution);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle(v_style_name);
        Glide.with(this).load(urlOfPic).into(styleImageView);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }
}