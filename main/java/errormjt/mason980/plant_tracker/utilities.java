package errormjt.mason980.plant_tracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;

import java.io.File;

/**
 * Created by Mason on 24/07/2016.
 */

public class utilities {

    public static String albumName = "planter_tracker_images";

    public static void showErrorMessage(String mess, android.content.Context con) {            // this is bad
      //  int[] i = new int[1];
      //  int a = i[5];
        if (mess == ""  || mess == null) {
            mess = "Empty message";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        builder.setMessage(mess).setTitle("ALERT");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public static void promptForConfirmation(String mess, android.content.Context con, Command positive, Long id) {            // this is bad
        if (mess == ""  || mess == null) {
            mess = "Empty message";
            return;
        }
        final Command positive_function = positive;
        final Long fid = id;
        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        builder.setMessage(mess).setTitle("PLEASE CONFIRM");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                positive_function.execute(fid);
                dialog.cancel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public static String getFolderPath() {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);

        file.mkdirs();

        return file.getPath();
    }

}
