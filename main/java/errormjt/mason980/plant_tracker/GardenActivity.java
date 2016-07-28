package errormjt.mason980.plant_tracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class GardenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garden);
        DatabaseHandler db = new DatabaseHandler(this);

        Garden garden = db.getGarden(1);

        if (garden == null) {
            startActivity(new Intent(GardenActivity.this, Garden_edit.class));
        } else { populateGarden(garden); }

        Button btn_search = (Button)findViewById(R.id.btn_plant);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GardenActivity.this, Search.class));
            }
        });

        Button btn_add = (Button)findViewById(R.id.btn_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GardenActivity.this, Plant_edit.class));
            }
        });

        Button btn_edit = (Button)findViewById(R.id.btn_edit_garden);

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GardenActivity.this, Garden_edit.class);
                intent.putExtra("id", "1");
                finish();
                startActivity(intent);
            }
        });
    }

    private void populateGarden (Garden garden) {
        TextView txt_summer = (TextView) findViewById(R.id.txt_summer);
        txt_summer.setText(garden.getSummer());

        TextView txt_winter = (TextView) findViewById(R.id.txt_winter);
        txt_winter.setText(garden.getWinter());

        TextView txt_rainfall = (TextView) findViewById(R.id.txt_rainfall);
        txt_rainfall.setText(garden.getRainfall());

        TextView txt_rainfall_dominant = (TextView) findViewById(R.id.txt_rainfall_dominant);
        txt_rainfall_dominant.setText(garden.getRainfallDominant());

        TextView txt_soil_texture = (TextView) findViewById(R.id.txt_soil_texture);
        txt_soil_texture.setText(garden.getSoilTexture());

        TextView txt_soil_oH = (TextView) findViewById(R.id.txt_soil_pH);
        txt_soil_oH.setText(garden.getSoilpH());

        TextView txt_soil = (TextView) findViewById(R.id.txt_soil);
        txt_soil.setText(garden.getSoil());

        TextView txt_aspect = (TextView) findViewById(R.id.txt_aspect);
        txt_aspect.setText(garden.getAspect());

        TextView txt_day_summer = (TextView) findViewById(R.id.txt_day_summer);
        txt_day_summer.setText(garden.getDaySummer());

        TextView txt_day_winter = (TextView) findViewById(R.id.txt_day_winter);
        txt_day_winter.setText(garden.getDayWinter());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_garden, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

   /*     //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }

}
