package errormjt.mason980.plant_tracker;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Mason on 3/07/2015.
 */
public class Plant {

    public String folder_path;

    public static String[] parameters_array = {"common", "scientific", "category", "origin","type", "duration", "habit", "spread", "height", "purpose", "ease", "sunlight",
                                                "interest", "harvest", "drought", "frost", "water", "fertilise", "img"};
    public static String[] origin_array = {"Native", "Exotic"};
    public static String[] type_array = {"Evergreen", "Deciduous"};
    public static String[] duration_array = {"Annual", "Biennial", "Perennial"};
    public static String[] habit_array = {"Prostate (<0.1m)", "Groundcover (0.1-0.5m)", "Shrub (0.5-1m)", "Bush (1-2m)", "Small Tree (<5m)", "Large Tree", "Vine", "Epiphyte", "Aquatic"};
    public static String[] ease_array = {"Yes", "Average", "No"};
    public static String[] sunlight_array = {"Full sun", "Part sun/shade", "Full shade"};
    public static String[] harvest_array = {"Spring", "Summer", "Autumn", "Winter", "All year"};
    public static String[] drought_array = {"Yes", "Some", "No"};
    public static String[] frost_array =  {"Yes", "Some", "No"};

    private String _scientific, _category,  _common, _purpose, _interest, _notes;
    private int _origin, _type, _duration, _habit, _ease, _sunlight, _drought, _frost;
    private int[] _harvest; // maay require inits
    private long _id;
    private double _spread, _height;
    private double[] _water, _fertilise;    // these may require inits
    private ArrayList<Bitmap> _images = new ArrayList();

    public Plant (String common, String scientific, String category, int origin,int type,int duration,int habit, double spread, double height, String purpose, int ease, int sunlight,
                  String interest, int[] harvest, int drought, int frost, double[] water, double[] fertilise) {
        _common = common;
        _scientific = scientific;
        _category = category;
        _origin = origin;
        _type = type;
        _duration = duration;
        _habit = habit;
        _spread = spread;
        _height = height;
        _purpose = purpose;
        _ease = ease;
        _sunlight = sunlight;
        _interest = interest;
        _harvest = harvest;
        _drought = drought;
        _frost = frost;
        _water = water;
        _fertilise = fertilise;

    }

    public Plant (long id, String common, String scientific, String category, int origin,int type,int duration,int habit, double spread, double height, String purpose, int ease, int sunlight,
                  String interest, int[] harvest, int drought, int frost, double[] water, double[] fertilise) {
        _id = id;
        _common = common;
        _scientific = scientific;
        _category = category;
        _origin = origin;
        _type = type;
        _duration = duration;
        _habit = habit;
        _spread = spread;
        _height = height;
        _purpose = purpose;
        _ease = ease;
        _sunlight = sunlight;
        _interest = interest;
        _harvest = harvest;
        _drought = drought;
        _frost = frost;
        _water = water;
        _fertilise = fertilise;

    }

    public void setFolderPath (String p) {
        folder_path = p;
    }


    public void setId(long id) {_id = id;}
    public void setImg(ArrayList<Bitmap> imgs) { _images = imgs;}
    public void addImg(Bitmap i) {_images.add(i);}
    public void fill_from_imgUrl() throws Exception {            // load all the images up based upon the imgUrl

        String folder_path = utilities.getFolderPath();     // instead of doing based upon the imgUrl it can be searched for in the folder based upon the id
        // PLANT_54_0.jpg
        File folder = new File(folder_path);

        String[] paths = folder.list();
        Long index = _id;
        for (int i = 0; i < paths.length; i++) {
            if (paths[i].contains("PLANT_"+index.toString()+"_")) {
                Bitmap img;
                try {
                    File f = new File(folder_path+"/"+paths[i]);
                    img = BitmapFactory.decodeStream(new FileInputStream(f));
                    _images.add(img);
                } catch (FileNotFoundException e) {
                    throw new Exception(e.getMessage() + " : set_imgUrl");
                }
            }
        }
    }

    public void set_notes(String notes) { _notes = notes; }

    // Getting whats going into the database
    public long get_id() { return _id; }
    public String get_common() { return _common; }
    public String get_scientific() {
        return _scientific;
    }
    public String get_category() {
        return _category;
    }
    public int get_origin() {
        return _origin;
    }
    public int get_type() {
        return _type;
    }
    public int get_duration() {
        return _duration;
    }
    public int get_habit() {
        return _habit;
    }
    public double get_spread() {
        return _spread;
    }
    public double get_height() { return _height; }
    public String get_purpose() { return _purpose; }
    public int get_ease() { return _ease; }
    public int get_sunlight() {
        return _sunlight;
    }
    public String get_interest() {
        // Implement some join/split thing
        return _interest;
    }
    public int get_drought() { return _drought; }
    public int get_frost() { return _frost; }
    public int[] get_harvest() { return _harvest; }
    public double[] get_water() {
        return _water;
    }
    public double[] get_fertilise() {
        return _fertilise;
    }


    public int img_length() {
        return _images.size();
    }
    public ArrayList<Bitmap> get_images() { return _images;}


    //Getting what will go into text fields
    public String getId() { return String.valueOf(_id); }
    public String getCommon() {
        return _common;
    }
    public String getScientific() {
        return _scientific;
    }
    public String getCategory() {
        return _category;
    }
    public String getOrigin() {
        return origin_array[_origin];
    }
    public String getType() {
        return type_array[_type];
    }
    public String getDuration() {
        return duration_array[_duration];
    }
    public String getHabit() {
        return habit_array[_habit];
    }
    public String getSpread() {
        return String.valueOf(_spread);
    }
    public String getHeight() { return String.valueOf(_height); }
    public String getPurpose() { return _purpose; }
    public String getEase() {
        return ease_array[_ease];
    }
    public String getSunlight() {
        return sunlight_array[_sunlight];
    }
    public String getInterest() {
        return _interest;
    }
    public String getHarvest() {
        String a = "";
        for (int i = 0; i < _harvest.length; i++){
            if (_harvest[i] == 1) {
                a = a + harvest_array[i];
                a = a + ", ";
            }
        }
        if (a != "") {
            a = a.substring(0, a.length() - 2);
        }
        if (_harvest[0] == 1 && _harvest[1] == 1 && _harvest[2] == 1 && _harvest[3] == 1) {
            a = harvest_array[4];
        }
        return a;
    }
    public String getDrought() {
        return drought_array[_drought];
    }
    public String getFrost() {
        return frost_array[_frost];
    }
    public double[] getWater() { return _water; }
    public double[] getFertilise() { return _fertilise; }
    public Bitmap getImg(int i) {

        return _images.get(i);
    }

    public String getNotes() { return _notes; }

    public String createImagePath(long id, int i) throws IOException {
        // Create an image file name
        String imageFileName = "PLANT_" + String.valueOf(id) + "_" + String.valueOf(i);
        String plantTrackerImages = utilities.getFolderPath();
        String imagePath = plantTrackerImages + "/" + imageFileName +  ".jpg";

        // Save a file: path for use with ACTION_VIEW intents
        return imagePath;
    }
}

