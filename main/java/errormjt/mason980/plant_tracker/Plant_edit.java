package errormjt.mason980.plant_tracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


public class Plant_edit extends AppCompatActivity {

    public final static String PLANT_ID = "errormjt.mason980.plant_tracker.PLANT_ID";
    static final int REQUEST_IMAGE_CAPTURE = 1;

    int originID = 0;
    int typeID = 0;
    int durationID = 0;
    int habitID = 0;
    int easeID = 0;
    int sunlightID = 0;
    int droughtID = 0;
    int frostID = 0;

    ArrayList<Bitmap> images = new ArrayList();

    EditText txtE_common;
    EditText txtE_scientific;
    EditText txtE_category;
    Spinner spnr_origin;
    Spinner spnr_type;
    Spinner spnr_duration;
    Spinner spnr_habit;
    EditText txtE_spread;
    EditText txtE_height;
    EditText txtE_purpose;
    Spinner spnr_ease;
    Spinner spnr_sunlight;
    EditText txtE_interest;
    CheckBox chk_spring;
    CheckBox chk_summer;
    CheckBox chk_autumn;
    CheckBox chk_winter;
    Spinner spnr_drought;
    Spinner spnr_frost;
    EditText txtE_w_spring;
    EditText txtE_w_summer;
    EditText txtE_w_autumn;
    EditText txtE_w_winter;
    EditText txtE_f_spring;
    EditText txtE_f_summer;
    EditText txtE_f_autumn;
    EditText txtE_f_winter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent intent = getIntent();
        setContentView(R.layout.activity_plant_edit);

        if (utilities.isExternalStorageWritable()) {

        } else {
            //  show some alert dialog
            utilities.showErrorMessage("External storage cannot be accessed or is not writable.", Plant_edit.this);
            // return
        }

        Button btn = (Button)findViewById(R.id.btn_submit);

        if (intent.getStringExtra(PlantActivity.PLANT_ID) != null) {
            DatabaseHandler db = new DatabaseHandler(this);
            try {
                Plant plant = db.getPlant(Integer.parseInt(intent.getStringExtra(PlantActivity.PLANT_ID)));
                populatePlant(plant);
            } catch (Exception e) {
                utilities.showErrorMessage(e.getMessage(), Plant_edit.this);
            }
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    plantEdit(intent.getStringExtra(PlantActivity.PLANT_ID));
                    Long id = Long.parseLong(intent.getStringExtra(PlantActivity.PLANT_ID));
                    Intent intentNew = new Intent(Plant_edit.this, PlantActivity.class);
                    intentNew.putExtra("id", id);
                    finish();
                    startActivity(intentNew);
                }
            });

            Button btn_delete = (Button)findViewById(R.id.btn_delete);
            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Long id = Long.parseLong(intent.getStringExtra(PlantActivity.PLANT_ID));
                    utilities.promptForConfirmation("Are you sure you want to delete this record?", Plant_edit.this, new DeletePlantCommand(), id);

                }
            });

        } else {
            populateSpinners();
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Long id = plantCreate();
                        Intent intentNew = new Intent(Plant_edit.this, PlantActivity.class);
                        intentNew.putExtra("id", id);
                        startActivity(intentNew);
                    } catch (Exception e) {
                        utilities.showErrorMessage(e.getMessage(), Plant_edit.this);
                    }
                }
            });
            Button btn_delete = (Button)findViewById(R.id.btn_delete);

            btn_delete.setVisibility(View.GONE);        // this could be changed to reset
        }
        Button btn_garden = (Button)findViewById(R.id.btn_garden);
        btn_garden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Plant_edit.this, GardenActivity.class));
                finish();
            }
        });

        Button btn_image = (Button)findViewById(R.id.btn_image);
        btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();

          }
        });

    }

    private void populateSpinners() {
        final Spinner spinnerOrigin = (Spinner)findViewById(R.id.spnr_origin);
        ArrayAdapter<String> adapterO = new ArrayAdapter<String>(Plant_edit.this, android.R.layout.simple_spinner_item, Plant.origin_array);

        adapterO.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOrigin.setAdapter(adapterO);
        spinnerOrigin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                originID = (int) spinnerOrigin.getSelectedItemId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        spinnerOrigin.setSelection(originID);

        final Spinner spinnerType = (Spinner)findViewById(R.id.spnr_type);
        ArrayAdapter<String> adapterT = new ArrayAdapter<String>(Plant_edit.this, android.R.layout.simple_spinner_item, Plant.type_array);

        adapterT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapterT);
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                typeID = (int) spinnerType.getSelectedItemId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        spinnerType.setSelection(typeID);

        final Spinner spinnerDuration = (Spinner)findViewById(R.id.spnr_duration);
        ArrayAdapter<String> adapterD = new ArrayAdapter<String>(Plant_edit.this,
                android.R.layout.simple_spinner_item,Plant.duration_array);

        adapterD.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDuration.setAdapter(adapterD);
        spinnerDuration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                durationID = (int) spinnerDuration.getSelectedItemId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        spinnerDuration.setSelection(durationID);

        final Spinner spinnerHabit = (Spinner)findViewById(R.id.spnr_habit);
        ArrayAdapter<String> adapterH = new ArrayAdapter<String>(Plant_edit.this,
                android.R.layout.simple_spinner_item,Plant.habit_array);

        adapterH.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHabit.setAdapter(adapterH);
        spinnerHabit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                habitID = (int) spinnerHabit.getSelectedItemId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        spinnerHabit.setSelection(habitID);

        final Spinner spinnerEase = (Spinner)findViewById(R.id.spnr_ease);
        ArrayAdapter<String> adapterE = new ArrayAdapter<String>(Plant_edit.this,
                android.R.layout.simple_spinner_item,Plant.ease_array);

        adapterE.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEase.setAdapter(adapterE);
        spinnerEase.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                easeID = (int) spinnerEase.getSelectedItemId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        spinnerEase.setSelection(easeID);

        final Spinner spinnerSunlight = (Spinner)findViewById(R.id.spnr_sunlight);
        ArrayAdapter<String> adapterSunlight = new ArrayAdapter<String>(Plant_edit.this,
                android.R.layout.simple_spinner_item,Plant.sunlight_array);

        adapterSunlight.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSunlight.setAdapter(adapterSunlight);
        spinnerSunlight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                sunlightID = (int) spinnerSunlight.getSelectedItemId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        spinnerSunlight.setSelection(sunlightID);

        final Spinner spinnerDrought = (Spinner)findViewById(R.id.spnr_drought);
        ArrayAdapter<String> adapterDrought = new ArrayAdapter<String>(Plant_edit.this,
                android.R.layout.simple_spinner_item,Plant.drought_array);

        adapterDrought.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDrought.setAdapter(adapterDrought);
        spinnerDrought.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                droughtID = (int) spinnerDrought.getSelectedItemId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        spinnerDrought.setSelection(droughtID);

        final Spinner spinnerFrost = (Spinner)findViewById(R.id.spnr_frost);
        ArrayAdapter<String> adapterFrost = new ArrayAdapter<String>(Plant_edit.this,
                android.R.layout.simple_spinner_item,Plant.frost_array);

        adapterFrost.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrost.setAdapter(adapterFrost);
        spinnerFrost.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                frostID = (int) spinnerFrost.getSelectedItemId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        spinnerFrost.setSelection(frostID);

    }

    private void populatePlant (Plant plant) {
        originID = plant.get_origin();
        typeID = plant.get_type();
        durationID = plant.get_duration();
        habitID = plant.get_habit();
        easeID = plant.get_ease();
        sunlightID = plant.get_sunlight();
        droughtID = plant.get_drought();
        frostID = plant.get_frost();

        populateSpinners();


        EditText txt_common = (EditText) findViewById(R.id.txtE_common);
        txt_common.setText(plant.getCommon());
        EditText txt_scientific = (EditText) findViewById(R.id.txtE_scientific);
        txt_scientific.setText(plant.getScientific());
        EditText txt_category = (EditText) findViewById(R.id.txtE_category);
        txt_category.setText(plant.getCategory());

        EditText txt_spread = (EditText) findViewById(R.id.txtE_spread);
        txt_spread.setText(plant.getSpread());
        EditText txt_height = (EditText) findViewById(R.id.txtE_height);
        txt_height.setText(plant.getHeight());
        EditText txt_purpose = (EditText) findViewById(R.id.txtE_purpose);
        txt_purpose.setText(plant.getPurpose());

        EditText txt_interest = (EditText) findViewById(R.id.txtE_interest);
        txt_interest.setText(plant.getInterest());

        CheckBox chk_spring = (CheckBox) findViewById(R.id.chk_spring);
        if (plant.get_harvest()[0] == 1) chk_spring.setChecked(true);
        CheckBox chk_summer = (CheckBox) findViewById(R.id.chk_summer);
        if (plant.get_harvest()[1] == 1) chk_summer.setChecked(true);
        CheckBox chk_autumn = (CheckBox) findViewById(R.id.chk_autumn);
        if (plant.get_harvest()[2] == 1) chk_autumn.setChecked(true);
        CheckBox chk_winter = (CheckBox) findViewById(R.id.chk_winter);
        if (plant.get_harvest()[3] == 1) chk_winter.setChecked(true);

        EditText txt_w_spring = (EditText) findViewById(R.id.txtE_w_spring);
        txt_w_spring.setText(String.valueOf(plant.getWater()[0]));
        EditText txt_w_summer = (EditText) findViewById(R.id.txtE_w_summer);
        txt_w_summer.setText(String.valueOf(plant.getWater()[1]));
        EditText txt_w_autumn = (EditText) findViewById(R.id.txtE_w_autumn);
        txt_w_autumn.setText(String.valueOf(plant.getWater()[2]));
        EditText txt_w_winter = (EditText) findViewById(R.id.txtE_w_winter);
        txt_w_winter.setText(String.valueOf(plant.getWater()[3]));

        EditText txt_f_spring = (EditText) findViewById(R.id.txtE_f_spring);
        txt_f_spring.setText(String.valueOf(plant.getFertilise()[0]));
        EditText txt_f_summer = (EditText) findViewById(R.id.txtE_f_summer);
        txt_f_summer.setText(String.valueOf(plant.getFertilise()[1]));
        EditText txt_f_autumn = (EditText) findViewById(R.id.txtE_f_autumn);
        txt_f_autumn.setText(String.valueOf(plant.getFertilise()[2]));
        EditText txt_f_winter = (EditText) findViewById(R.id.txtE_f_winter);
        txt_f_winter.setText(String.valueOf(plant.getFertilise()[3]));

    }

    private void plantEdit(String plant_id) {

        Plant plant = retrievePlantData();
        DatabaseHandler db = new DatabaseHandler(this);
        try {
            db.insertPlant(plant, plant_id);
        } catch (Exception e) {
            utilities.showErrorMessage(e.getMessage(), Plant_edit.this);
        }
    }

    private long plantCreate() throws Exception {

        Plant plant = retrievePlantData();
        DatabaseHandler db = new DatabaseHandler(this);

        long id = 0;
        id = db.addPlant(plant);
        plant.setId(id);

        return id;
    }

    private Plant retrievePlantData() {
        txtE_common = (EditText)findViewById(R.id.txtE_common);
        txtE_scientific = (EditText)findViewById(R.id.txtE_scientific);
        txtE_category = (EditText)findViewById(R.id.txtE_category);
        txtE_spread = (EditText)findViewById(R.id.txtE_spread);
        txtE_height = (EditText)findViewById(R.id.txtE_height);
        txtE_purpose = (EditText)findViewById(R.id.txtE_purpose);
        txtE_interest = (EditText)findViewById(R.id.txtE_interest);
        chk_spring = (CheckBox)findViewById(R.id.chk_spring);
        chk_summer = (CheckBox)findViewById(R.id.chk_summer);
        chk_autumn = (CheckBox)findViewById(R.id.chk_autumn);
        chk_winter = (CheckBox)findViewById(R.id.chk_winter);
        txtE_w_spring = (EditText)findViewById(R.id.txtE_w_spring);
        txtE_w_summer = (EditText)findViewById(R.id.txtE_w_summer);
        txtE_w_autumn = (EditText)findViewById(R.id.txtE_w_autumn);
        txtE_w_winter = (EditText)findViewById(R.id.txtE_w_winter);
        txtE_f_spring = (EditText)findViewById(R.id.txtE_f_spring);
        txtE_f_summer = (EditText)findViewById(R.id.txtE_f_summer);
        txtE_f_autumn = (EditText)findViewById(R.id.txtE_f_autumn);
        txtE_f_winter = (EditText)findViewById(R.id.txtE_f_winter);

        String common = txtE_common.getText().toString();
        String scientific = txtE_scientific.getText().toString();
        String category = txtE_category.getText().toString();

        double spread = parseNumber(txtE_spread);
        double height = parseNumber(txtE_height);

        String purpose = txtE_purpose.getText().toString();
        String interest = txtE_interest.getText().toString();

        int[] harvest = {0, 0, 0, 0};
        if (chk_spring.isChecked()) {harvest[0] = 1;}
        if (chk_summer.isChecked()) {harvest[1] = 1;}
        if (chk_autumn.isChecked()) {harvest[2] = 1;}
        if (chk_winter.isChecked()) {harvest[3] = 1;}


        double[] water  = {0, 0, 0, 0};
        water[0] = parseNumber(txtE_w_spring);
        water[1] = parseNumber(txtE_w_summer);
        water[2] = parseNumber(txtE_w_autumn);
        water[3] = parseNumber(txtE_w_winter);

        double[] fertilise  = {0, 0, 0, 0};
        fertilise[0] = parseNumber(txtE_f_spring);
        fertilise[1] = parseNumber(txtE_f_summer);
        fertilise[2] = parseNumber(txtE_f_autumn);
        fertilise[3] = parseNumber(txtE_f_winter);


        Plant plant = new Plant (common, scientific, category, originID, typeID, durationID, habitID, spread, height, purpose, easeID, sunlightID, interest, harvest, droughtID, frostID, water, fertilise);
        if (images.size() > 0) {
            plant.setImg(images);
        }

        return plant;
    }

    private Plant plantAddImage(Plant plant) {

        return plant;
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

            images.add(imageBitmap);
        }
    }

    private double parseNumber (TextView v) {
        double a;
        String b = v.getText().toString();
        if (v.getText().toString().equals("") || v.getText().toString().equals(null)) {
            a = 0;
        } else {
            a = Double.parseDouble(v.getText().toString());}

        return a;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_plant_edit, menu);
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


    public class DeletePlantCommand implements Command              //  not very nice having a class within a class, but moving it elsewhere causes "Plant_edit.this" and "setResult" problems
    {
        public void execute(Long id)        // delete the plant record then finish the activity with the 'to delete' resulting action
        {
            DatabaseHandler db = new DatabaseHandler(Plant_edit.this);
            db.deletePlant(id);
            Intent in = new Intent();
            in.putExtra("action", PlantActivity.RESULT_TO_DELETE);
            setResult(Activity.RESULT_OK, in);
            finish();
        }
    }
}
