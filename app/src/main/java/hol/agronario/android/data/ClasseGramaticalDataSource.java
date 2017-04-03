package hol.agronario.android.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hol.agronario.android.ob.ClasseGramatical;

/**
 * Created by shouts on 04/02/2015.
 */
public class ClasseGramaticalDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.CLASSES_GRAMATICAIS_COLUMN_ID_CLASSE,
            MySQLiteHelper.CLASSES_GRAMATICAIS_COLUMN_NOME, MySQLiteHelper.CLASSES_GRAMATICAIS_COLUMN_DATA
    };

    public ClasseGramaticalDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public List<ClasseGramatical> getAllClassesGramaticais() {
        List<ClasseGramatical> listClasseGramatical = new ArrayList<ClasseGramatical>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_CLASSES_GRAMATICAIS, allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            listClasseGramatical.add(cursorToComment(cursor));
            cursor.moveToNext();
        }

        cursor.close();

        return listClasseGramatical;
    }

    public List<ClasseGramatical> getClasseGramaticalByIdioma(int idIdioma) {
        List<ClasseGramatical> listClasseGramatical = new ArrayList<ClasseGramatical>();

        String query = "SELECT DISTINCT c.* FROM " + MySQLiteHelper.TABLE_CLASSES_GRAMATICAIS + " c WHERE c." + MySQLiteHelper.CLASSES_GRAMATICAIS_COLUMN_ID_IDIOMA + " = ?";

        String[] whereArgs = new String[]{String.valueOf(idIdioma)};

        Cursor cursor = database.rawQuery(query, whereArgs);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            listClasseGramatical.add(cursorToComment(cursor));
            cursor.moveToNext();
        }

        cursor.close();

        return listClasseGramatical;
    }

    private ClasseGramatical cursorToComment(Cursor cursor) {
        ClasseGramatical cg = new ClasseGramatical();
        cg.setId(cursor.getInt(cursor.getColumnIndex(MySQLiteHelper.CLASSES_GRAMATICAIS_COLUMN_ID_CLASSE)));
        cg.setNome(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.CLASSES_GRAMATICAIS_COLUMN_NOME)));
        cg.setData(new Date(cursor.getInt(cursor.getColumnIndex(MySQLiteHelper.CLASSES_GRAMATICAIS_COLUMN_DATA))));
        cg.setIdIdioma(cursor.getInt(cursor.getColumnIndex(MySQLiteHelper.CLASSES_GRAMATICAIS_COLUMN_ID_IDIOMA)));

        return cg;
    }
}
