package errormjt.mason980.plant_tracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class PlantActivity extends AppCompatActivity {

    public final static String PLANT_ID = "errormjt.mason980.plant_tracker.PLANT_ID";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_EDIT = 2;
    static final int RESULT_TO_DELETE = 6;

    Bitmap img;
    ListView listView;

    int imgIs = 1;
    Button btn_hide;
    Button btn_show;
    Plant plant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_plant);

        Long lId = (long) 0;

        if (utilities.isExternalStorageWritable()) {

        } else {
            //  show some alert dialog
            utilities.showErrorMessage("External storage cannot be accessed or is not writable.", PlantActivity.this);
            // return
        }

        Intent intent = getIntent();
        final DatabaseHandler db = new DatabaseHandler(this);       // Keep an eye out for this
        try {
            lId = intent.getLongExtra("id", lId);
            plant = db.getPlant(lId);

            final String id = plant.getId();

 //           final EditText txtEdit = (EditText) findViewById(R.id.txtE_notes);
            listView = (ListView)findViewById(R.id.listView);

            Button btn_search = (Button)findViewById(R.id.btn_search);
            btn_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
     //               db.addNotes(txtEdit.getText().toString(), id);
                    startActivity(new Intent(PlantActivity.this, Search.class));
                }
            });

            Button btn_add = (Button)findViewById(R.id.btn_add);
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
     //               db.addNotes(txtEdit.getText().toString(), id);
                    startActivity(new Intent(PlantActivity.this, Plant_edit.class));
                }
            });

            Button btn_garden = (Button)findViewById(R.id.btn_garden);
            btn_garden.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
    //                db.addNotes(txtEdit.getText().toString(), id);
                    startActivity(new Intent(PlantActivity.this, GardenActivity.class));
                }
            });

            Button btn_edit = (Button)findViewById(R.id.btn_edit);
            btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
     //               db.addNotes(txtEdit.getText().toString(), id);
                    Intent intent = new Intent(PlantActivity.this, Plant_edit.class);
                    intent.putExtra(PLANT_ID, id);
                    startActivityForResult(intent, REQUEST_EDIT);
                    //startActivity(intent);

                }
            });

            btn_hide = (Button) findViewById(R.id.btn_hide);
            btn_hide.setVisibility(View.GONE);
            btn_hide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //when play is clicked show stop button and hide play button
                    btn_hide.setVisibility(View.GONE);
                    listView.setVisibility(View.GONE);
                    btn_show.setVisibility(View.VISIBLE);
                }
            });

            btn_show = (Button) findViewById(R.id.btn_show);
            btn_show.setVisibility(View.VISIBLE);
/*
            txtEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        db.addNotes(txtEdit.getText().toString(), id);
                    }
                }
            });
/*                                                                                          //      currently defunct
            Button btn_take = (Button)findViewById(R.id.btn_take);
            btn_take.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    //when play is clicked show stop button and hide play button
                    dispatchTakePictureIntent();
                        if (img != null) {
                            plant.setImg(img);
                            try {
                                db.image(Long.parseLong(id), plant);
                            } catch (Exception e) {
                                utilities.showErrorMessage(e.getMessage() + " : take", PlantActivity.this);
                            }
                            populateImages();
                        }
                  }
                });
*/
            btn_show.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //when play is clicked show stop button and hide play button
                    btn_hide.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.VISIBLE);
                    btn_show.setVisibility(View.GONE);
                }
            });
            listView.setVisibility(View.GONE);

            populatePlant();

        } catch (Exception e) {
            utilities.showErrorMessage(e.getMessage(), PlantActivity.this);
            // say something about the failure to do whatever
     //       Intent intentNew = new Intent(PlantActivity.this, intent);
     //       intentNew.putExtra("error", e.getMessage());
     //       startActivity(intentNew);

//            finish();
        }
    }

    private void populatePlant() {
        ImageView imgView = (ImageView)findViewById(R.id.imgView);
        if (plant.img_length() <= 0) {
            imgIs = 0;
            btn_show = (Button) findViewById(R.id.btn_show);
            btn_show.setVisibility(View.GONE);
        } else {
            populateImages();
        }

   //     EditText txt_notes = (EditText) findViewById(R.id.txtE_notes);
   //     txt_notes.setText(plant.getNotes());

        TextView txt_common = (TextView) findViewById(R.id.txt_common);
        txt_common.setText(plant.getCommon());
        TextView txt_scientific = (TextView) findViewById(R.id.txtE_scientific);
        txt_scientific.setText(plant.getScientific());
        TextView txt_category = (TextView) findViewById(R.id.txt_category);
        txt_category.setText(plant.getCategory());
        TextView txt_origin = (TextView) findViewById(R.id.txt_origin);
        txt_origin.setText(plant.getOrigin());
        TextView txt_type = (TextView) findViewById(R.id.txt_type);
        txt_type.setText(plant.getType());
        TextView txt_duration = (TextView) findViewById(R.id.txt_duration);
        txt_duration.setText(plant.getDuration());
        TextView txt_habit = (TextView) findViewById(R.id.txt_habit);
        txt_habit.setText(plant.getHabit());
        TextView txt_spread = (TextView) findViewById(R.id.txt_spread);
        txt_spread.setText(plant.getSpread());
        TextView txt_height = (TextView) findViewById(R.id.txt_height);
        txt_height.setText(plant.getHeight());
        TextView txt_purpose = (TextView) findViewById(R.id.txt_purpose);
        txt_purpose.setText(plant.getPurpose());
        TextView txt_ease = (TextView) findViewById(R.id.txt_ease);
        txt_ease.setText(plant.getEase());
        TextView txt_sunlight = (TextView) findViewById(R.id.txt_sunlight);
        txt_sunlight.setText(plant.getSunlight());
        TextView txt_interest = (TextView) findViewById(R.id.txt_interest);
        txt_interest.setText(plant.getInterest());
        TextView txt_harvest = (TextView) findViewById(R.id.txt_harvest);
        txt_harvest.setText(plant.getHarvest());
        TextView txt_drought = (TextView) findViewById(R.id.txt_drought);
        txt_drought.setText(plant.getDrought());
        TextView txt_frost = (TextView) findViewById(R.id.txt_frost);
        txt_frost.setText(plant.getFrost());

        TextView txt_w_spring = (TextView) findViewById(R.id.txt_w_spring);
        txt_w_spring.setText(String.valueOf(plant.getWater()[0]));
        TextView txt_w_summer = (TextView) findViewById(R.id.txt_w_summer);
        txt_w_summer.setText(String.valueOf(plant.getWater()[1]));
        TextView txt_w_autumn = (TextView) findViewById(R.id.txt_w_autumn);
        txt_w_autumn.setText(String.valueOf(plant.getWater()[2]));
        TextView txt_w_winter = (TextView) findViewById(R.id.txt_w_winter);
        txt_w_winter.setText(String.valueOf(plant.getWater()[3]));

        TextView txt_f_spring = (TextView) findViewById(R.id.txt_f_spring);
        txt_f_spring.setText(String.valueOf(plant.getFertilise()[0]));
        TextView txt_f_summer = (TextView) findViewById(R.id.txt_f_summer);
        txt_f_summer.setText(String.valueOf(plant.getFertilise()[1]));
        TextView txt_f_autumn = (TextView) findViewById(R.id.txt_f_autumn);
        txt_f_autumn.setText(String.valueOf(plant.getFertilise()[2]));
        TextView txt_f_winter = (TextView) findViewById(R.id.txt_f_winter);
        txt_f_winter.setText(String.valueOf(plant.getFertilise()[3]));

   }

    private void populateImages() {                             // fill out a listview with all the relevant images

        final DatabaseHandler db = new DatabaseHandler(this);       // Keep an eye out for this
        final Plant plant_final = plant;

        ListView imageList = (ListView)findViewById(R.id.listView);
        ArrayList<Integer> int_arr = new ArrayList<>();
        int_arr.add(0);
        ArrayAdapter<Integer> resultAdapter = new ArrayAdapter<Integer>(PlantActivity.this, R.layout.img_url_layout, int_arr) {                //      added the <String>
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.img_url_layout, parent, false);
                ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView);
                imageView.setImageBitmap(plant_final.getImg(position));
                return convertView;
            }};

        imageList.setAdapter(resultAdapter);
        imageList.setSelection(0);
                     /*                                                                               //      For when I want to edit images
        imageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                final ImageView imgView = (ImageView)findViewById(R.id.imgView);
                final int pos = position;
                imgView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        long id = resultAdapter.getItemId(pos);

                        dispatchTakePictureIntent();
                        if (img != null) {
                            plantA.setImg(img);
                            try {
                                db.image(plantA.get_id(), plantA);
                                imgView.setImageBitmap(img);
                            } catch (Exception e) {
                                utilities.showErrorMessage(e.getMessage(), PlantActivity.this);
                            }
                        }
                    }
                });

            }

            public long getItemId(int position) {
                long id = urls.length;

                return id;
            }
        });
        //imgView.setImageBitmap();
*/
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            plant.addImg(imageBitmap);
            return;
        }
        if (requestCode == REQUEST_EDIT && resultCode == Activity.RESULT_OK) {
            if (data.getIntExtra("action", 0) == RESULT_TO_DELETE) {
                Intent in = new Intent();
                in.putExtra("result", Result.DELETED);
                setResult(Activity.RESULT_OK, in);

                finish();               //  still need to refresh the search
                return;
            }
            return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_plant, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
/*
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }

}
