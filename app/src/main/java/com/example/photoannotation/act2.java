package com.example.photoannotation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

public class act2 extends AppCompatActivity {


    static final int IMAGE_CAPTURE = 1;
    ImageView iv;
    private Draw draw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act2);
        iv = (ImageView) findViewById(R.id.iv1);

        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, IMAGE_CAPTURE);

        draw = (Draw) findViewById(R.id.draw);
        DisplayMetrics m = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(m);
        draw.makeCanvas(m);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle ex = data.getExtras();
            Bitmap photo = (Bitmap) ex.get("data");
            iv.setImageBitmap(photo);
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu m) {

        getMenuInflater().inflate(R.menu.menus, m);
        return super.onCreateOptionsMenu(m);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch(id) {

            case (R.id.brush):
                onBrushTypeClick(item);
                break;

            case (R.id.color):
                onBrushColorClick(item.getSubMenu());
                break;

            case (R.id.size):
                onBrushSizeClick(item.getSubMenu());
                break;

            case (R.id.clear):
                draw.clearAll();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void onBrushTypeClick(MenuItem item) {
        int id = item.getSubMenu().getItem().getItemId();
        Log.d("Looh", String.valueOf(id));
        switch (id) {
            case (R.id.pen):
                Log.d("Looh2", String.valueOf(R.id.pen));
                draw.brush();
                break;

            case (R.id.eraser):
                draw.erase();
                break;
        }
        Log.d("Looh2", String.valueOf(R.id.pen));
    }

    public void onBrushColorClick(SubMenu item) {
        int id = item.getItem().getItemId();

        switch (id) {
            case (R.id.black):
                draw.colorPick(Color.BLACK);
                break;
            case (R.id.blue):
                draw.colorPick(Color.BLUE);
                break;
            case (R.id.red):
                draw.colorPick(Color.RED);
                break;
            case (R.id.green):
                draw.colorPick(Color.GREEN);
                break;
            case (R.id.magenta):
                draw.colorPick(Color.MAGENTA);
                break;
            case (R.id.yellow):
                draw.colorPick(Color.YELLOW);
                break;
        }
    }
    public void onBrushSizeClick(SubMenu item) {
        int id = item.getItem().getItemId();

        switch (id) {
            case (R.id.pt10):
                draw.sizeSelect(10);
                break;

            case (R.id.pt25):
                draw.sizeSelect(25);
                break;

            case (R.id.pt50):
                draw.sizeSelect(50);
                break;

            case (R.id.pt100):
                draw.sizeSelect(100);
                break;
        }
    }
}
