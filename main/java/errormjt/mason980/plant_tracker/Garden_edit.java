package errormjt.mason980.plant_tracker;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;


public class Garden_edit extends ActionBarActivity {

    int summerID = 0;
    int winterID = 0;

    Spinner spnr_summer;
    Spinner spnr_winter;
    EditText txtE_rainfall;
    CheckBox chk_summer;
    CheckBox chk_spring;
    CheckBox chk_autumn;
    CheckBox chk_winter;
    CheckBox chk_clay;
    CheckBox chk_loam;
    CheckBox chk_sand;
    EditText txtE_soil_pH;
    EditText txtE_soil;
    CheckBox chk_north;
    CheckBox chk_east;
    CheckBox chk_south;
    CheckBox chk_west;
    Spinner spnr_aspect;
    EditText txtE_day_summer;
    EditText txtE_day_winter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garden_edit);

        initElements();
        final Intent intent = getIntent();
        if (utilities.isExternalStorageWritable()) {

        } else {
            //  show some alert dialog
            utilities.showErrorMessage("External storage cannot be accessed or is not writable.", Garden_edit.this);
            // return
        }

        Button btn = (Button)findViewById(R.id.btn_submit);

        if (intent.getStringExtra("id") != null) {				// an edit
            DatabaseHandler db = new DatabaseHandler(this);
            try {
                Garden garden = db.getGarden(Integer.parseInt(intent.getStringExtra("id")));
                populateGarden(garden);
            } catch (Exception e) {
                utilities.showErrorMessage(e.getMessage() +" : when trying to populate the garden", Garden_edit.this);
            }
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gardenEdit(intent.getStringExtra("id"));
                    Long id = Long.parseLong(intent.getStringExtra("id"));
                    Intent intentNew = new Intent(Garden_edit.this, GardenActivity.class);
                    intentNew.putExtra("id", id);
                    finish();
                    startActivity(intentNew);
                }
            });
/*                                                                                      // Maybe deleting gardens should be added
            Button btn_delete = (Button)findViewById(R.id.btn_delete);
            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Long id = Long.parseLong(intent.getStringExtra(GardenActivity.Garden_ID));
                    DatabaseHandler db = new DatabaseHandler(getParent());
                    db.deleteGarden(id);
                    finish();
                }
            });
*/
       } else {			// a create
            populateSpinners();
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        createGarden();
                        Intent intent = new Intent(Garden_edit.this, GardenActivity.class);
                        startActivity(intent);

                        /*
                        Long id = GardenCreate();
                        Intent intentNew = new Intent(Garden_edit.this, GardenActivity.class);
                        intentNew.putExtra("id", id);
                        startActivity(intentNew);
                        */
                    } catch (Exception e) {
                        utilities.showErrorMessage(e.getMessage() + " : when trying to create the garden", Garden_edit.this);
                    }
                }
            });
  //          Button btn_delete = (Button)findViewById(R.id.btn_delete);

 //           btn_delete.setVisibility(View.GONE);        // this could be changed to reset
        }


    }
	
	private void initElements() {		// connect the variables to the elements in the activity
		txtE_rainfall = (EditText) findViewById(R.id.txtE_rainfall);
        chk_spring = (CheckBox) findViewById(R.id.chk_spring);
        chk_summer = (CheckBox) findViewById(R.id.chk_summer);
        chk_autumn = (CheckBox) findViewById(R.id.chk_autumn);
        chk_winter = (CheckBox) findViewById(R.id.chk_winter);
        chk_clay = (CheckBox) findViewById(R.id.chk_clay);
        chk_loam = (CheckBox) findViewById(R.id.chk_loam);
        chk_sand = (CheckBox) findViewById(R.id.chk_sand);
        txtE_soil_pH = (EditText) findViewById(R.id.txtE_soil_pH);
        txtE_soil = (EditText) findViewById(R.id.txtE_soil);
        chk_north = (CheckBox) findViewById(R.id.chk_north);
        chk_east = (CheckBox) findViewById(R.id.chk_east);
        chk_south = (CheckBox) findViewById(R.id.chk_south);
        chk_west = (CheckBox) findViewById(R.id.chk_west);
        txtE_day_summer = (EditText) findViewById(R.id.txtE_day_summer);
        txtE_day_winter = (EditText) findViewById(R.id.txtE_day_winter);
	}

    private void populateSpinners() {			// populate the spinners with values and set the default/starting
        final Spinner spinnerSummer = (Spinner)findViewById(R.id.spnr_summer);
        ArrayAdapter<String> adapterS = new ArrayAdapter<String>(Garden_edit.this,
                android.R.layout.simple_spinner_item,Garden.summer_climate_array);

        adapterS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSummer.setAdapter(adapterS);
        spinnerSummer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                summerID = (int) spinnerSummer.getSelectedItemId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        spinnerSummer.setSelection(summerID);

        final Spinner spinnerWinter = (Spinner)findViewById(R.id.spnr_winter);
        ArrayAdapter<String> adapterW = new ArrayAdapter<String>(Garden_edit.this,
                android.R.layout.simple_spinner_item,Garden.winter_climate_array);

        adapterW.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWinter.setAdapter(adapterW);
        spinnerWinter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                winterID = (int) spinnerWinter.getSelectedItemId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        spinnerWinter.setSelection(winterID);

    }

    private void createGarden() {			// extract the data from the elements to make a Garden object
// replaced with initElements

        Garden garden = retrieveGardenData();
        DatabaseHandler db = new DatabaseHandler(this);
        long id = db.addGarden(garden);
        garden.setID(id);

    }

    private Garden retrieveGardenData () {
        int rainfall = 0;
        if (txtE_rainfall.getText().length() != 0) {
            rainfall = Integer.parseInt(txtE_rainfall.getText().toString());
        }

        int[] rainfall_dominant = {0, 0, 0, 0};
        if (chk_spring.isChecked()) {rainfall_dominant[0] = 1;}
        if (chk_summer.isChecked()) {rainfall_dominant[1] = 1;}
        if (chk_autumn.isChecked()) {rainfall_dominant[2] = 1;}
        if (chk_winter.isChecked()) {rainfall_dominant[3] = 1;}

        int[] soil_texture = {0, 0, 0};
        if (chk_clay.isChecked()) {soil_texture[0] = 1;}
        if (chk_loam.isChecked()) {soil_texture[1] = 1;}
        if (chk_sand.isChecked()) {soil_texture[2] = 1;}

        double soil_ph = 0;
        if (txtE_soil_pH.getText().length() != 0) {
            soil_ph = Double.parseDouble(txtE_soil_pH.getText().toString());
        }
        String soil = "";
        if (txtE_soil.getText().length() != 0) {
            soil = txtE_soil.getText().toString();
        }

        int[] aspect = {0, 0, 0, 0};
        if (chk_north.isChecked()) {aspect[0] = 1;}
        if (chk_east.isChecked()) {aspect[1] = 1;}
        if (chk_south.isChecked()) {aspect[2] = 1;}
        if (chk_west.isChecked()) {aspect[3] = 1;}

        int day_summer = 0;
        if (txtE_day_summer.getText().length() != 0) {
            day_summer = Integer.parseInt(txtE_day_summer.getText().toString());
        }
        int day_winter = 0;
        if (txtE_day_winter.getText().length() != 0) {
            day_winter = Integer.parseInt(txtE_day_winter.getText().toString());
        }
        return new Garden (summerID, winterID, rainfall, rainfall_dominant, soil_texture, soil_ph, soil, aspect, day_summer, day_winter);

    }

    private void populateGarden (Garden garden) {		// fill up the garden if editing
/*
        summer  = garden.getSummer();
        winter  = garden.getWinter();
        rainfall  = garden.getRainfall();
        rainfalldominant  = garden.getRainfallDominant();
        soiltexture  = garden.getSoilTexture();
        soilpH  = garden.getSoilpH();
        soil  = garden.getSoil();
        aspect  = garden.getAspect();
        daysummer  = garden.getDaySummer();
        daywinter  = garden.getDayWinter();
*/
        summerID = garden.get_summer();
        winterID = garden.get_winter();
        populateSpinners();

        txtE_soil.setText(garden.get_soil());
        txtE_rainfall.setText(String.valueOf(garden.get_rainfall()));
		txtE_soil_pH.setText(String.valueOf(garden.get_soil_pH() ));
		txtE_day_summer.setText(String.valueOf(garden.get_day_summer()));
		txtE_day_winter.setText(String.valueOf(garden.get_day_winter()));

        if (garden.get_soil_texture()[0] == 1) chk_clay.setChecked(true);
        if (garden.get_soil_texture()[1] == 1) chk_loam.setChecked(true);
        if (garden.get_soil_texture()[2] == 1) chk_sand.setChecked(true);

        if (garden.get_rainfall_dominant()[0] == 1) chk_spring.setChecked(true);
        if (garden.get_rainfall_dominant()[1] == 1) chk_summer.setChecked(true);
        if (garden.get_rainfall_dominant()[2] == 1) chk_autumn.setChecked(true);
        if (garden.get_rainfall_dominant()[3] == 1) chk_winter.setChecked(true);

		if (garden.get_aspect()[0] == 1) chk_north.setChecked(true);
        if (garden.get_aspect()[1] == 1) chk_south.setChecked(true);
        if (garden.get_aspect()[2] == 1) chk_east.setChecked(true);
        if (garden.get_aspect()[3] == 1) chk_west.setChecked(true);
    }

    private void gardenEdit(String garden_id) {			//	insert the garden into the database

        Garden garden = retrieveGardenData();
        DatabaseHandler db = new DatabaseHandler(this);
        try {
            db.insertGarden(garden, garden_id);
        } catch (Exception e) {
            utilities.showErrorMessage(e.getMessage(), Garden_edit.this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_garden_edit, menu);
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
