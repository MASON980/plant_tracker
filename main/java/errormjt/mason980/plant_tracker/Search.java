package errormjt.mason980.plant_tracker;

import android.content.Intent;
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


public class Search extends AppCompatActivity {

    public final static String QUERY = "errormjt.mason980.plant_tracker.QUERY";
    public final static String ADV_QUERY = "errormjt.mason980.plant_tracker.ADV_QUERY";
    public final static String HARVEST_QUERY = "errormjt.mason980.plant_tracker.HARVEST_QUERY";
    public final static String WATER_QUERY = "errormjt.mason980.plant_tracker.WATER_QUERY";
    public final static String FERTILISE_QUERY = "errormjt.mason980.plant_tracker.FERTILISE_QUERY";
    String ao = "%' AND ";
    String aa = " AND ";

    int originID = 0;
    int typeID = 0;
    int durationID = 0;
    int habitID = 0;
    int easeID = 0;
    int sunlightID = 0;
    int droughtID = 0;
    int frostID = 0;

    EditText basicSearch;
    Button btn;
    Button btnAdvanced;

    CheckBox chk_common;
    CheckBox chk_scientific;
    CheckBox chk_category;
    CheckBox chk_spread;
    CheckBox chk_height;
    CheckBox chk_purpose;
    CheckBox chk_interest;
    CheckBox chk_harvest;
    CheckBox chk_water0;
    CheckBox chk_water1;
    CheckBox chk_water2;
    CheckBox chk_water3;

    CheckBox chk_fertilise0;
    CheckBox chk_fertilise1;
    CheckBox chk_fertilise2;
    CheckBox chk_fertilise3;

    CheckBox chk_notes;

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
    EditText txtE_notes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        populateSpinners();
        btn = (Button) findViewById(R.id.btn_search);
        basicSearch = (EditText) findViewById(R.id.txtE_search);


        //Onclick/Interactables

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Search.this, Result.class);
                intent.putExtra(QUERY, basicSearch.getText().toString());
                startActivity(intent);
            }
        });

        btnAdvanced = (Button) findViewById(R.id.btn_search_adv);
        btnAdvanced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Search.this, Result.class);
                intent.putExtra(ADV_QUERY, retrieveAdvancedQuery());
                intent.putExtra(HARVEST_QUERY, retrieveHarvest());
                intent.putExtra(WATER_QUERY, retrieveWater());
                intent.putExtra(FERTILISE_QUERY, retrieveFertilise());

                startActivity(intent);
            }
        });

    }
    private void populateSpinners() {
        String[] origin_array = addBegginer(Plant.origin_array);
        String[] type_array = addBegginer(Plant.type_array);
        String[] duration_array = addBegginer(Plant.duration_array);
        String[] habit_array = addBegginer(Plant.habit_array);
        String[] ease_array = addBegginer(Plant.ease_array);
        String[] sunlight_array = addBegginer(Plant.sunlight_array);
        String[] drought_array = addBegginer(Plant.drought_array);
        String[] frost_array = addBegginer(Plant.frost_array);

        final Spinner spinnerOrigin = (Spinner)findViewById(R.id.spnr_origin);
        ArrayAdapter<String> adapterO = new ArrayAdapter<String>(Search.this, android.R.layout.simple_spinner_item, origin_array);

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
        ArrayAdapter<String> adapterT = new ArrayAdapter<String>(Search.this, android.R.layout.simple_spinner_item, type_array);

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
        ArrayAdapter<String> adapterD = new ArrayAdapter<String>(Search.this,
                android.R.layout.simple_spinner_item,duration_array);

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
        ArrayAdapter<String> adapterH = new ArrayAdapter<String>(Search.this,
                android.R.layout.simple_spinner_item,habit_array);

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
        ArrayAdapter<String> adapterE = new ArrayAdapter<String>(Search.this,
                android.R.layout.simple_spinner_item,ease_array);

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
        ArrayAdapter<String> adapterSunlight = new ArrayAdapter<String>(Search.this,
                android.R.layout.simple_spinner_item,sunlight_array);

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
        ArrayAdapter<String> adapterDrought = new ArrayAdapter<String>(Search.this,
                android.R.layout.simple_spinner_item,drought_array);

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
        ArrayAdapter<String> adapterFrost = new ArrayAdapter<String>(Search.this,
                android.R.layout.simple_spinner_item,frost_array);

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

    private String retrieveAdvancedQuery() {
        chk_common = (CheckBox)findViewById(R.id.chk_common);
        chk_scientific = (CheckBox)findViewById(R.id.chk_scientific);
        chk_category = (CheckBox)findViewById(R.id.chk_category);
        chk_spread = (CheckBox)findViewById(R.id.chk_spread);
        chk_height = (CheckBox)findViewById(R.id.chk_height);
        chk_purpose = (CheckBox)findViewById(R.id.chk_purpose);
        chk_interest = (CheckBox)findViewById(R.id.chk_interest);
        chk_notes = (CheckBox)findViewById(R.id.chk_notes);

        txtE_common = (EditText)findViewById(R.id.txtE_common);
        txtE_scientific = (EditText)findViewById(R.id.txtE_scientific);
        txtE_category = (EditText)findViewById(R.id.txtE_category);
        txtE_spread = (EditText)findViewById(R.id.txtE_spread);
        txtE_height = (EditText)findViewById(R.id.txtE_height);
        txtE_purpose = (EditText)findViewById(R.id.txtE_purpose);
        txtE_interest = (EditText)findViewById(R.id.txtE_interest);
        txtE_notes = (EditText)findViewById(R.id.txtE_notes);

        String query = "";
        if (chk_common.isChecked()) {
            query = query + "common like '%" + txtE_common.getText().toString() + ao;
        }
        if (chk_scientific.isChecked()) {
            query = query + "scientific like '%" + txtE_scientific.getText().toString() + ao;
        }
        if (chk_category.isChecked()) {
            query = query + "category like '%" + txtE_category.getText().toString() + ao;
        }
        if (chk_spread.isChecked()) {
            query = query + "spread like '%" + String.valueOf(parseNumber(txtE_spread)) + ao;
        }
        if (chk_height.isChecked()) {
            query = query + "height like '%" + String.valueOf(parseNumber(txtE_height)) + ao;
        }
        if (chk_purpose.isChecked()) {
            query = query + "purpose like '%" + txtE_purpose.getText().toString() + ao;
        }
        if (chk_interest.isChecked()) {
            query = query + "interest like '%" + txtE_interest.getText().toString() + ao;
        }
        if (originID != 0) {
            query = query + "origin = " + String.valueOf(originID - 1) + aa;
        }
        if (typeID != 0) {
            query = query + "type = " + String.valueOf(typeID-1) + aa;
        }
        if (durationID != 0) {
            query = query + "duration = " + String.valueOf(durationID-1) + aa;
        }
        if (habitID != 0) {
            query = query + "habit = " + String.valueOf(habitID-1) + aa;
        }
        if (easeID != 0) {
            query = query + "ease = " + String.valueOf(easeID-1) + aa;
        }
        if (sunlightID != 0) {
            query = query + "sunlight = " + String.valueOf(sunlightID-1) + aa;
        }
        if (droughtID != 0) {
            query = query + "drought = " + String.valueOf(droughtID-1) + aa;
        }
        if (frostID != 0) {
            query = query + "frost = " + String.valueOf(frostID-1) + aa;
        }
        if (chk_notes.isChecked()) {
            query = query + "notes like '%" + txtE_notes.getText().toString() + ao;
        }
        if (query != "") {
            query = query.substring(0, query.length() - 5);
        }
        return query;
    }
    private String retrieveWater() {
        txtE_w_spring = (EditText)findViewById(R.id.txtE_w_spring);
        txtE_w_summer = (EditText)findViewById(R.id.txtE_w_summer);
        txtE_w_autumn = (EditText)findViewById(R.id.txtE_w_autumn);
        txtE_w_winter = (EditText)findViewById(R.id.txtE_w_winter);
        chk_water0 = (CheckBox)findViewById(R.id.chk_water0);
        chk_water1 = (CheckBox)findViewById(R.id.chk_water1);
        chk_water2 = (CheckBox)findViewById(R.id.chk_water2);
        chk_water3 = (CheckBox)findViewById(R.id.chk_water3);

        String query= "";
        if (chk_water0.isChecked()) {
            query = query + "spring = " + parseNumber(txtE_w_spring) + aa;
        }
        if (chk_water1.isChecked()) {
            query = query + "summer = " + parseNumber(txtE_w_summer) + aa;
        }
        if (chk_water2.isChecked()) {
            query = query + "autumn = " + parseNumber(txtE_w_autumn) + aa;
        }
        if (chk_water3.isChecked()) {
            query = query + "winter = " + parseNumber(txtE_w_winter) + aa;
        }
        if (query != "") {
            query = query.substring(0, query.length() - 5);
        }
        return query;
    }
    private String retrieveFertilise() {
        txtE_f_spring = (EditText)findViewById(R.id.txtE_f_spring);
        txtE_f_summer = (EditText)findViewById(R.id.txtE_f_summer);
        txtE_f_autumn = (EditText)findViewById(R.id.txtE_f_autumn);
        txtE_f_winter = (EditText)findViewById(R.id.txtE_f_winter);
        chk_fertilise0 = (CheckBox)findViewById(R.id.chk_fertilise0);
        chk_fertilise1 = (CheckBox)findViewById(R.id.chk_fertilise1);
        chk_fertilise2 = (CheckBox)findViewById(R.id.chk_fertilise2);
        chk_fertilise3 = (CheckBox)findViewById(R.id.chk_fertilise3);

        String query = "";
        if (chk_fertilise0.isChecked()) {
            query = query + "spring = " + parseNumber(txtE_f_spring) + aa;
        }
        if (chk_fertilise1.isChecked()) {
            query = query + "summer = " + parseNumber(txtE_f_summer) + aa;
        }
        if (chk_fertilise2.isChecked()) {
            query = query + "autumn = " + parseNumber(txtE_f_autumn) + aa;
        }
        if (chk_fertilise3.isChecked()) {
            query = query + "winter = " + parseNumber(txtE_f_winter) + aa;
        }
        if (query != "") {
            query = query.substring(0, query.length() - 5);
        }
        return query;
    }

    private String[] retrieveHarvest() {
        chk_spring = (CheckBox)findViewById(R.id.chk_spring);
        chk_summer = (CheckBox)findViewById(R.id.chk_summer);
        chk_autumn = (CheckBox)findViewById(R.id.chk_autumn);
        chk_winter = (CheckBox)findViewById(R.id.chk_winter);
        chk_harvest = (CheckBox)findViewById(R.id.chk_harvest);
        String[] harvest = {"2", "2", "2", "2"};

        if (chk_harvest.isChecked()) {
            if (chk_spring.isChecked()) {
                harvest[0] = "1";
            } else {harvest[0] = "0";}
            if (chk_summer.isChecked()) {
                harvest[1] = "1";
            } else {harvest[1] = "0";}
            if (chk_autumn.isChecked()) {
                harvest[2] = "1";
            } else {harvest[2] = "0";}
            if (chk_winter.isChecked()) {
                harvest[3] = "1";
            } else {harvest[3] = "0";}
        }
        return harvest;
    }

    private String[] addBegginer(String[] array) {
        String[] arrayNew = new String[array.length+1];
        arrayNew[0] = "Do not search";

        for (int i = 0; i < array.length; i++) {
            arrayNew[i+1] = array[i];
        }


        return arrayNew;
    }

    private String checkBoxChecker(TextView v, CheckBox c) {
        String s = "";
        if (c.isChecked()) {s = v.getText().toString();}

        return s;
    }

    private String parseNumber (TextView v) {
        String a;
        String b = v.getText().toString();
        if (v.getText().toString().equals("") || v.getText().toString().equals(null)) {
            a = "0.0";
        } else {
            a = v.getText().toString();}

        return a;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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
