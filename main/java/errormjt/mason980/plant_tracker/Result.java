package errormjt.mason980.plant_tracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;


public class Result extends ActionBarActivity {

    public final static String PLANT_ID = "errormjt.mason980.plant_tracker.PLANT_ID";
    public final static int REQUEST_OPEN = 1;
    public final static int DELETED = 1;
    ListView resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        search();

    }
    private void search() {
        Intent intent = getIntent();
        setContentView(R.layout.activity_result);
        Cursor result;
        DatabaseHandler db = new DatabaseHandler(this);
        resultView = (ListView)findViewById(R.id.resultView);
        if (intent.getStringExtra(Search.QUERY) != null) {
            result = db.basicSearch(intent.getStringExtra(Search.QUERY));
        } else {
            result = db.advanceSearch(intent.getStringExtra(Search.ADV_QUERY), intent.getStringArrayExtra(Search.HARVEST_QUERY), intent.getStringExtra(Search.WATER_QUERY), intent.getStringExtra(Search.FERTILISE_QUERY));

        }

        final Cursor results = result;
        final ResourceCursorAdapter resultAdapter = new ResourceCursorAdapter(Result.this, R.layout.result_layout, results, false) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.result_layout, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView txt_name = (TextView) view.findViewById(R.id.txt_name);
                String name = cursor.getString(cursor.getColumnIndexOrThrow("common"));
                txt_name.setText(name);
            }
        };

        resultView.setAdapter(resultAdapter);

        resultView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                long id = resultAdapter.getItemId(position);
                Intent intent = new Intent(Result.this, PlantActivity.class);
                intent.putExtra("id", id);
                startActivityForResult(intent, REQUEST_OPEN);
            }

            public long getItemId(int position) {
                long id = Long.parseLong(results.getString(results.getColumnIndexOrThrow("_id")));

                return id;
            }
        });

        Button btn_search = (Button)findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Result.this, Search.class));
            }
        });

        Button btn_add = (Button)findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Result.this, Plant_edit.class));
            }
        });

        Button btn_garden = (Button)findViewById(R.id.btn_garden);
        btn_garden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Result.this, GardenActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_OPEN && resultCode == Activity.RESULT_OK) {
            if (data.getIntExtra("result", 0) == DELETED) {
                search();
                return;
            }
            return;
        }
    }
}
