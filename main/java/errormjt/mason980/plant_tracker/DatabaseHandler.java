package errormjt.mason980.plant_tracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.database.DatabaseUtils.dumpCursorToString;

/**
 * Created by Mason on 3/07/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int SCREEN_SIZE = 480;

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 8;

    // Database Name
    private static final String DATABASE_NAME = "Garden";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GARDENS_TABLE = "CREATE TABLE gardens ( id INTEGER PRIMARY KEY, summer INTEGER, winter INTEGER, rainfall INTEGER, rainfall_dominant INTEGER, " +
                "soil_texture INTEGER, soil_pH DOUBLE, soil TEXT, aspect INTEGER, day_summer INTEGER, day_winter INTEGER, " +
                "FOREIGN KEY(aspect) REFERENCES aspect(id), FOREIGN KEY(soil_texture) REFERENCES soil_texture(id), FOREIGN KEY(rainfall_dominant) REFERENCES rainfall_dominant(id))";
        String CREATE_RAINFALL_DOMINANT_TABLE = "CREATE TABLE rainfall_dominant ( id INTEGER PRIMARY KEY, a INTEGER, b INTEGER, c INTEGER, d INTEGER)";
        String CREATE_SOIL_TEXTURE_TABLE = "CREATE TABLE soil_texture ( id INTEGER PRIMARY KEY, a INTEGER, b INTEGER, c INTEGER)";
        String CREATE_ASPECT_TABLE = "CREATE TABLE aspect ( id INTEGER PRIMARY KEY, a INTEGER, b INTEGER, c INTEGER, d INTEGER)";

        db.execSQL(CREATE_RAINFALL_DOMINANT_TABLE);
        db.execSQL(CREATE_SOIL_TEXTURE_TABLE);
        db.execSQL(CREATE_ASPECT_TABLE);
        db.execSQL(CREATE_GARDENS_TABLE);

        String CREATE_PLANTS_TABLE = "CREATE TABLE plants ( _id INTEGER PRIMARY KEY, common TEXT, scientific TEXT, category TEXT, origin INTEGER, type INTEGER, duration INTEGER, habit INTEGER, spread DOUBLE, " +
                "height DOUBLE, purpose TEXT, ease INTEGER, sunlight INTEGER, interest TEXT, harvest INTEGER, drought INTEGER, frost INTEGER, water INTEGER, fertilise INTEGER,  notes TEXT," +
                "FOREIGN KEY(harvest) REFERENCES harvest(id), FOREIGN KEY(water) REFERENCES water(id), FOREIGN KEY(fertilise) REFERENCES fertilise(id))";

        String CREATE_HARVEST_TABLE = "CREATE TABLE harvest ( id INTEGER PRIMARY KEY, a INTEGER, b INTEGER, c INTEGER, d INTEGER, e INTEGER)";
        String CREATE_WATER_TABLE = "CREATE TABLE water ( id INTEGER PRIMARY KEY, spring INTEGER, summer INTEGER, autumn INTEGER, winter INTEGER)";
        String CREATE_FERTILISE_TABLE = "CREATE TABLE fertilise ( id INTEGER PRIMARY KEY, spring INTEGER, summer INTEGER, autumn INTEGER, winter INTEGER)";

        db.execSQL(CREATE_HARVEST_TABLE);
        db.execSQL(CREATE_WATER_TABLE);
        db.execSQL(CREATE_FERTILISE_TABLE);
        db.execSQL(CREATE_PLANTS_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS gardens");
        db.execSQL("DROP TABLE IF EXISTS rainfall_dominant");
        db.execSQL("DROP TABLE IF EXISTS soil_texture");
        db.execSQL("DROP TABLE IF EXISTS aspect");
        db.execSQL("DROP TABLE IF EXISTS plants");
        db.execSQL("DROP TABLE IF EXISTS harvest");
        db.execSQL("DROP TABLE IF EXISTS water");
        db.execSQL("DROP TABLE IF EXISTS fertilise");

        // Create tables again
        onCreate(db);
    }

    // Adding new garden
    public long addGarden(Garden garden) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("summer", garden.get_summer());
        values.put("winter", garden.get_winter());
        values.put("rainfall", garden.get_rainfall());
        values.put("rainfall_dominant", addRainfallDominant(garden.get_rainfall_dominant(), db));
        values.put("soil_texture", addSoil_Texture(garden.get_soil_texture(), db));
        values.put("soil_pH", garden.get_soil_pH());
        values.put("soil", garden.get_soil());
        values.put("aspect", addAspect(garden.get_aspect(), db));
        values.put("day_summer", garden.get_day_summer());
        values.put("day_winter", garden.get_day_winter());

        // Inserting Row
        long id = db.insert("gardens", null, values);
        db.close(); // Closing database connection
        return id;
    }

    public void insertGarden(Garden garden, String id) throws Exception {

        // delete old rainfall_dominant, soilTexture records        --      I could just update them
        SQLiteDatabase db_r = this.getReadableDatabase();
        Cursor cursor = db_r.query("gardens", null, null, null, null, null, null);
        if (cursor.moveToFirst() == false) {
            return;
        }
        if (cursor != null)
            cursor.moveToFirst();
        db_r.delete("rainfall_dominant", "id=?", new String[] {cursor.getString(4)});
        db_r.delete("soil_texture", "id=?", new String[] {cursor.getString(5)});
        db_r.delete("aspect", "id=?", new String[] {cursor.getString(8)});
        db_r.close();

        // create new record
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("summer", garden.get_summer());
        values.put("winter", garden.get_winter());
        values.put("rainfall", garden.get_rainfall());
        values.put("rainfall_dominant", addRainfallDominant(garden.get_rainfall_dominant(), db));
        values.put("soil_texture", addSoil_Texture(garden.get_soil_texture(), db));
        values.put("soil_pH", garden.get_soil_pH());
        values.put("soil", garden.get_soil());
        values.put("aspect", addAspect(garden.get_aspect(), db));
        values.put("day_summer", garden.get_day_summer());
        values.put("day_winter", garden.get_day_winter());

        // Inserting Row
        long Lid = db.update("gardens", values, "id = ?", new String[] {id});

        db.close(); // Closing database connection

    }

    // Adding new rainfall_dominant
    public long addRainfallDominant(int[] a, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("a", a[0]);
        values.put("b", a[1]);
        values.put("c", a[2]);
        values.put("d", a[3]);

        // Inserting Row
        return db.insert("rainfall_dominant", null, values);
    }

    // Adding new soil_texture
    public long addSoil_Texture(int[] a, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("a", a[0]);
        values.put("b", a[1]);
        values.put("c", a[2]);

        // Inserting Row
        return db.insert("soil_texture", null, values);
    }

    // Adding new aspect
    public long addAspect(int[] a, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("a", a[0]);
        values.put("b", a[1]);
        values.put("c", a[2]);
        values.put("d", a[3]);

        // Inserting Row
        return db.insert("aspect", null, values);
    }

    // Getting single garden
    public Garden getGarden(long id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("gardens", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor.moveToFirst() == false) {
           return null;
        }

        if (cursor != null)
            cursor.moveToFirst();

        Cursor cursorRain = db.query("rainfall_dominant", new String[] {"a", "b", "c", "d"}, "id =?", new String[] {cursor.getString(4)}, null, null, null, null);
        Cursor cursorSoil = db.query("soil_texture", new String[] {"a", "b", "c"}, "id = ?", new String[] {cursor.getString(5)}, null, null, null, null);
        Cursor cursorAspect = db.query("aspect", new String[] {"a", "b", "c", "d"}, "id = ?", new String[] {cursor.getString(8)}, null, null, null, null);

        if (cursorRain != null)
            cursorRain.moveToFirst();

        if (cursorSoil != null)
            cursorSoil.moveToFirst();

        if (cursorAspect != null)
            cursorAspect.moveToFirst();

        Garden garden = new Garden(Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(2)), Integer.parseInt(cursor.getString(3)),
                new int[] {Integer.parseInt(cursorRain.getString(0)), Integer.parseInt(cursorRain.getString(1)), Integer.parseInt(cursorRain.getString(2)), Integer.parseInt(cursorRain.getString(3))},
                new int[] {Integer.parseInt(cursorSoil.getString(0)), Integer.parseInt(cursorSoil.getString(1)), Integer.parseInt(cursorSoil.getString(2))},
                Double.parseDouble(cursor.getString(6)), cursor.getString(7), new int[] {Integer.parseInt(cursorAspect.getString(0)), Integer.parseInt(cursorAspect.getString(1)), Integer.parseInt(cursorAspect.getString(2)),
                Integer.parseInt(cursorAspect.getString(3))}, Integer.parseInt(cursor.getString(9)), Integer.parseInt(cursor.getString(10)));

        db.close();
        return garden;
    }

				   // Adding new plant
    public long addPlant(Plant plant) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("common", plant.get_common());
        values.put("scientific", plant.get_scientific());
        values.put("category", plant.get_category());
        values.put("origin", plant.get_origin());
        values.put("type", plant.get_type());
        values.put("duration", plant.get_duration());
        values.put("habit", plant.get_habit());
        values.put("spread", plant.get_spread());
        values.put("height", plant.get_height());
		values.put("purpose", plant.get_purpose());
		values.put("ease", plant.get_ease());
		values.put("sunlight", plant.get_sunlight());
		values.put("interest", plant.get_interest());
        values.put("harvest", addHarvest(plant.get_harvest(), db));
		values.put("drought", plant.get_drought());
		values.put("frost", plant.get_frost());
        values.put("water", addWater(plant.get_water(), db));
        values.put("fertilise", addFertilise(plant.get_fertilise(), db));

        // Inserting Row
        long id = db.insert("plants", null, values);
        if (plant.img_length() != 0) {
            saveImage(id, plant);
        }

        db.close(); // Closing database connection
        return id;
    }

    private String saveImage(long id, Plant plant) throws Exception {
        String totalImgPath = "";
        String imgPath = "";
        for (int i = 0; i < plant.img_length(); i++) {
            try {
                imgPath = plant.createImagePath(id, i);
            } catch (IOException e) {
                throw new Exception(e.getMessage());
            }
            File file = new File(imgPath);
            totalImgPath = totalImgPath + imgPath + " ";        // they will be split over the spaces
            FileOutputStream image;
            try {
                image = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                throw new Exception(e.getMessage());
            }
           try {
                Bitmap img = plant.getImg(i);
                int width = img.getWidth();
                int height = img.getHeight();
                double ratio = height / width;
                int newHeight = (int) Math.round(SCREEN_SIZE * ratio);
                img = Bitmap.createScaledBitmap(img, SCREEN_SIZE, newHeight, true);

               img.compress(Bitmap.CompressFormat.JPEG, 80, image);
                image.close();
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
        return totalImgPath;
    }

    // Adding new harvest
    public long addHarvest(int[] a, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("a", a[0]);
        values.put("b", a[1]);
        values.put("c", a[2]);
        values.put("d", a[3]);

        // Inserting Row
        return db.insert("harvest", null, values);
    }

    // Adding new water
    public long addWater(double[] a, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("spring", a[0]);
        values.put("summer", a[1]);
        values.put("autumn", a[2]);
        values.put("winter", a[3]);

        // Inserting Row
        return db.insert("water", null, values);
    }

    // Adding new fertilise
    public long addFertilise(double[] a, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("spring", a[0]);
        values.put("summer", a[1]);
        values.put("autumn", a[2]);
        values.put("winter", a[3]);

        // Inserting Row
        return db.insert("fertilise", null, values);
    }

	public void addNotes(String notes, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("notes", notes);

        db.update("plants", values, "_id = ?", new String[]{id});
        db.close(); // Closing database connection
	}

    public void insertPlant(Plant plant, String id) throws Exception {
        // delete old rainfall_dominant, soilTexture records        --      I could just update them
        SQLiteDatabase db_r = this.getReadableDatabase();
        Cursor cursor = db_r.query("plants", null, "_id = ?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor.moveToFirst() == false) {
            return;
        }
        if (cursor != null)
            cursor.moveToFirst();
        db_r.delete("harvest", "id=?", new String[] {cursor.getString(14)});
        db_r.delete("water", "id=?", new String[] {cursor.getString(17)});
        db_r.delete("fertilise", "id=?", new String[] {cursor.getString(18)});
        db_r.close();

        // create new record
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("common", plant.get_common());
        values.put("scientific", plant.get_scientific());
        values.put("category", plant.get_category());
        values.put("type", plant.get_type());
        values.put("duration", plant.get_duration());
        values.put("habit", plant.get_habit());
        values.put("spread", plant.get_spread());
        values.put("height", plant.get_height());
        values.put("purpose", plant.get_purpose());
        values.put("ease", plant.get_ease());
        values.put("sunlight", plant.get_sunlight());
        values.put("interest", plant.get_interest());
        values.put("harvest", addHarvest(plant.get_harvest(), db));
        values.put("drought", plant.get_drought());
        values.put("frost", plant.get_frost());
        values.put("water", addWater(plant.get_water(), db));
        values.put("fertilise", addFertilise(plant.get_fertilise(), db));

        if (plant.img_length() != 0) {
        //    saveImage(Long.parseLong(id), plant);
        }

        // Inserting Row
        long Lid = db.update("plants", values, "_id = ?", new String[] {id});

        saveImage(Long.parseLong(id), plant);
        db.close(); // Closing database connection
    }

    // Getting single plant
    public Plant getPlant(long id) throws Exception {       // would be better to handle the exception here so even if the image doesnt work it can still load the rest

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("plants", null, "_id = ?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Cursor cursorHarvest = db.query("harvest", new String[] {"a", "b", "c", "d"}, "id =?", new String[] {cursor.getString(14)}, null, null, null, null);
        Cursor cursorWater = db.query("water", new String[] {"spring", "summer", "autumn", "winter"}, "id = ?", new String[] {cursor.getString(17)}, null, null, null, null);
        Cursor cursorFertilise = db.query("fertilise", new String[] {"spring", "summer", "autumn", "winter"}, "id = ?", new String[] {cursor.getString(18)}, null, null, null, null);

        if (cursorHarvest != null)
            cursorHarvest.moveToFirst();

        if (cursorWater != null)
            cursorWater.moveToFirst();

        if (cursorFertilise != null)
            cursorFertilise.moveToFirst();

        Plant plant = new Plant (Long.parseLong(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), Integer.parseInt(cursor.getString(4)), Integer.parseInt(cursor.getString(5)), Integer.parseInt(cursor.getString(6)),
                Integer.parseInt(cursor.getString(7)), Double.parseDouble(cursor.getString(8)), Double.parseDouble(cursor.getString(9)), cursor.getString(10), Integer.parseInt(cursor.getString(11)), Integer.parseInt(cursor.getString(12)), cursor.getString(13),
                new int[] {Integer.parseInt(cursorHarvest.getString(0)), Integer.parseInt(cursorHarvest.getString(1)), Integer.parseInt(cursorHarvest.getString(2)), Integer.parseInt(cursorHarvest.getString(3))},
				Integer.parseInt(cursor.getString(15)), Integer.parseInt(cursor.getString(16)),
                new double[] {Double.parseDouble(cursorWater.getString(0)), Double.parseDouble(cursorWater.getString(1)), Double.parseDouble(cursorWater.getString(2)), Double.parseDouble(cursorWater.getString(3))},
                new double[] {Double.parseDouble(cursorFertilise.getString(0)), Double.parseDouble(cursorFertilise.getString(1)), Double.parseDouble(cursorFertilise.getString(2)), Double.parseDouble(cursorFertilise.getString(3))});

        plant.fill_from_imgUrl();

		if (cursor.getString(19) != null && cursor.getString(19) != "" ) {
			plant.set_notes(cursor.getString(19));
		}

        db.close();

        return plant;
    }

    public void deletePlant(long id) {

		SQLiteDatabase db = this.getWritableDatabase();
		db.delete("plants", "_id = ?", new String[] { String.valueOf(id) });
		db.close();

        String folder_path = utilities.getFolderPath();
        File folder = new File(folder_path);

        String[] paths = folder.list();
        Long index = id;
        for (int i = 0; i < paths.length; i++) {
            if (paths[i].contains("PLANT_"+index.toString()+"_")) {
                File f = new File (folder_path+"/"+paths[i]);
                boolean b = f.delete();
            }
        }

	}

	public Cursor basicSearch (String query) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Normal like search, then search the arrays and go through the secondary tables
		String origin = arrayCheck(Plant.origin_array, query);
		String type = arrayCheck(Plant.type_array, query);
		String duration = arrayCheck(Plant.duration_array, query);
		String habit = arrayCheck(Plant.habit_array, query);
		String ease = arrayCheck(Plant.ease_array, query);
		String sunlight = arrayCheck(Plant.sunlight_array, query);
		String drought = arrayCheck(Plant.drought_array, query);
		String frost = arrayCheck(Plant.frost_array, query);

        query = "%"+ query + "%";
        String[] fullQuery = {query, query, query, query, query, query, query, origin, type, duration, habit, ease, sunlight, drought, frost, query};

        Cursor cursorResult = db.query("plants", new String[] {"_id", "common"}, "common like ? OR scientific like ? OR category like ? OR spread like ? OR height like ? OR purpose like ? " +
                "OR interest like ? OR origin like ? OR type like ? OR duration like ? OR habit like ? OR ease like ? OR sunlight like ? OR drought like ? OR water like ? OR notes like ? COLLATE NOCASE", fullQuery, null, null, "common ASC", "100");

		return cursorResult;


	}

    public Cursor advanceSearch (String advQuery, String[] harvestQuery, String waterQuery, String fertiliseQuery) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorHarvest = db.query("harvest", new String[]{"id"}, "a = ? AND b = ? AND c = ? AND d = ?", new String[]{harvestQuery[0], harvestQuery[1], harvestQuery[2], harvestQuery[3]}, null, null, null, null);
        Cursor cursorWater = db.query("water", new String[]{"id"}, waterQuery, null, null, null, null, null);
        Cursor cursorFertilise = db.query("fertilise", new String[]{"id"}, fertiliseQuery, null, null, null, null, null);

        String a = "";
        String b = "";
        String c = "";

        if (cursorHarvest != null) { a = getString(cursorHarvest);}
        if (cursorWater != null && !waterQuery.equals("")) {b = getString(cursorWater);}
        if (cursorFertilise != null && !fertiliseQuery.equals("")) { c = getString(cursorFertilise);}

        if (a != "") {
            if (advQuery.equals("")) {
                a = "harvest in (" + a + ")";
            } else {
            a = " AND harvest in (" + a + ")";
            }
        }
        if (b != "") {
            if (advQuery.equals("") && a.equals("")) {
                b = "water in (" + b + ")";
            } else {
                b = " AND water in (" + b + ")";
            }
        }
        if (c != "") {
            if (advQuery.equals("") && a.equals("") && b.equals("")) {
                c = "fertilise in (" + c + ")";
            } else {
                c = " AND fertilise in (" + c + ")";
            }
        }
        String fullQuery = advQuery +  a + b + c + " COLLATE NOCASE";
        if (fullQuery.equals(" COLLATE NOCASE")) {
                fullQuery = "_id = -1";
        }

        Cursor cursorResult = db.query("plants", new String[] {"_id", "common"}, fullQuery, null, null, null, "common ASC", "100");

        return cursorResult;

    }

    private String getString(Cursor c) {
        String a = "";
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i ++) {
            a = a + c.getString(0);
            if (i != c.getCount() -1) {a = a + ",";}
            c.moveToNext();
        }
        return a;
    }

	public String arrayCheck (String[] array, String target) {
	    for(int i = 0; i < array.length; i++){
				if(array[i].toLowerCase().contains(target.toLowerCase())) {
                    return String.valueOf(i);

		    	}
        }
        return "";
	}
}
