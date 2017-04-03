package hol.agronario.android.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hol.agronario.android.ob.Idioma;

/**
 * Created by shouts on 04/02/2015.
 */
public class IdiomaDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.IDIOMAS_COLUMN_ID_IDIOMAS,
            MySQLiteHelper.IDIOMAS_COLUMN_IDIOMA
    };

    public IdiomaDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public List<Idioma> getAllIdiomas() {
        List<Idioma> listIdioma = new ArrayList<Idioma>();

        String query = "SELECT DISTINCT i.id, i.idioma FROM " + MySQLiteHelper.TABLE_IDIOMAS + " i";

        Cursor cursor = database.rawQuery(query, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            listIdioma.add(cursorToComment(cursor));
            cursor.moveToNext();
        }

        // make sure to close the cursor
        cursor.close();

        return listIdioma;
    }

    private Idioma cursorToComment(Cursor cursor) {
        Idioma idioma = new Idioma();
        idioma.setId(cursor.getInt(cursor.getColumnIndex(MySQLiteHelper.IDIOMAS_COLUMN_ID_IDIOMAS)));
        idioma.setIdioma(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.IDIOMAS_COLUMN_IDIOMA)));

        return idioma;
    }
}
