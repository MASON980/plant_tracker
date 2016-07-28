package errormjt.mason980.plant_tracker;

/**
 * Created by Mason on 3/07/2015.
 */
public class Garden {

    public static String[] summer_climate_array = {"Hot", "Warm", "Mild"};
    public static String[] winter_climate_array = {"Mild", "Cold (some frost)", "Very cold"};
    public static String[] rainfall_dominant_array = {"Spring", "Summer", "Autumn", "Winter", "Year-round"};
    public static String[] soil_texture_array = {"Clay", "Loam", "Sand"};
    public static String[] soil_array = {"Sodic", "Saline", "Acid", "Alkaline", "Wet"};
    public static String[] aspect_array = {"North", "South", "East", "West", "Open"};

    private String _soil;
    private int _summer, _winter, _rainfall,_day_summer, _day_winter;
    private int[]  _rainfall_dominant, _soil_texture, _aspect;
    private long _id;
    private double _soil_pH;

    public Garden (int summer,int winter,int rainfall,int[] rainfall_dominant,int[] soil_texture, double soil_pH, String soil, int[] aspect,int day_summer,int day_winter) {
        //_id = id;
        _summer = summer;
        _winter = winter;
        _rainfall = rainfall;
        _rainfall_dominant = rainfall_dominant;
        _soil_texture = soil_texture;
        _soil_pH = soil_pH;
        _soil = soil;
        _aspect = aspect;
        _day_summer = day_summer;
        _day_winter = day_winter;
    }


    public Garden (long id, int summer,int winter,int rainfall,int[] rainfall_dominant,int[] soil_texture, double soil_pH, String soil, int[] aspect,int day_summer,int day_winter) {
        _id = id;
        _summer = summer;
        _winter = winter;
        _rainfall = rainfall;
        _rainfall_dominant = rainfall_dominant;
        _soil_texture = soil_texture;
        _soil_pH = soil_pH;
        _soil = soil;
        _aspect = aspect;
        _day_summer = day_summer;
        _day_winter = day_winter;
    }


    public Garden (long id) {
        _id = id;

    }

    // Getting whats going into the database
    public int get_summer() {
        return _summer;
    }
    public int get_winter() {
        return _winter;
    }
    public int get_rainfall() {
        return _rainfall;
    }
    public int[] get_rainfall_dominant() {
        return _rainfall_dominant;
    }
    public double get_soil_pH() {
        return _soil_pH;
    }
    public String get_soil() {
        return _soil;
    }
    public int[] get_soil_texture() {
        return _soil_texture;
    }
    public int get_day_summer() {
        return _day_summer;
    }
    public int[] get_aspect() {
        return _aspect;
    }
    public int get_day_winter() {
        return _day_winter;
    }

    //Getting what will go into text fields
    public String getId() { return String.valueOf(_id); }
    public String getSummer() {
        return summer_climate_array[_summer];
    }
    public String getWinter() {
        return winter_climate_array[_winter];
    }
    public String getRainfall() { return String.valueOf(_rainfall); }
    public String getRainfallDominant() {
        String a = "";
        for (int i = 0; i < _rainfall_dominant.length; i++){
            if (_rainfall_dominant[i] == 1) {
                a = a + rainfall_dominant_array[i];
                a = a + ", ";
            }
        }
        if (a != "") {
            a = a.substring(0, a.length() - 2);
        }
        if (_rainfall_dominant[0] == 1 && _rainfall_dominant[1] == 1 && _rainfall_dominant[2] == 1 && _rainfall_dominant[3] == 1) {
            a = rainfall_dominant_array[4];
        }
        return a;
    }
    public String getSoilTexture() {
        String a = "";
        for (int i = 0; i < _soil_texture.length; i++){
            if (_soil_texture[i] == 1) {
                a = a + soil_texture_array[i];
                a = a + ", ";
            }
        }
        if (a != "") {
            a = a.substring(0, a.length() - 2);
        }
        return a;
    }
    public String getSoilpH() {
        return String.valueOf(_soil_pH);
    }
    public String getSoil() {
        return _soil;
    }
    public String getAspect() {
        String a = "";
        for (int i = 0; i < _aspect.length; i++){
            if (_aspect[i] == 1) {
                a = a + aspect_array[i];
                a = a + ", ";
            }
        }
        if (a != "") {
            a = a.substring(0, a.length() - 2);
        }
        if (_aspect[0] == 1 && _aspect[1] == 1 && _aspect[2] == 1 && _aspect[3] == 1) {
            a = aspect_array[4];
        }
        return a;
    }
    public String getDaySummer() {
        return String.valueOf(_day_summer);
    }
    public String getDayWinter() {
        return String.valueOf(_day_winter);
    }

    //Setters
    public void setID (long a) {_id = a;}
    public void setSummer(int a) {
        _summer = a;
    }
    public void setWinter(int a) {
        _winter = a;;
    }
    public void setRainfall(int a) {
        _rainfall = a;
    }
    public void setRainfallDominant(int[] a) {
        _rainfall_dominant = a;
    }
    public void setSoilTexture(int[] a) {
        _soil_texture = a;
    }
    public void setSoilpH(double a) {
        _soil_pH = a;
    }
    public void setSoil(String a) {
        _soil = a;
    }
    public void setAspect(int[] a) {
        _aspect = a;
    }
    public void setDaySummer(int a) {
        _day_summer = a;
    }
    public void setDayWvoiderl(int a) {
        _day_winter = a;
    }
}
