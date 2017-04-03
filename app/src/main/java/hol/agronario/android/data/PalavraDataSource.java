package hol.agronario.android.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hol.agronario.android.ob.Palavra;

/**
 * Created by shouts on 04/02/2015.
 */
public class PalavraDataSource {
    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.PALAVRAS_COLUMN_ID_PALAVRA, MySQLiteHelper.PALAVRAS_COLUMN_PALAVRA, MySQLiteHelper.PALAVRAS_COLUMN_TEXTO_PALAVRA, MySQLiteHelper.PALAVRAS_COLUMN_DATA, MySQLiteHelper.PALAVRAS_COLUMN_ID_CLASSE_GRAMATICAL};

    public PalavraDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public List<Palavra> getPalavras(String searchedWord, int idClasseGramatical, int idIdioma) {
        List<Palavra> palavras = new ArrayList<Palavra>();

        String query = "SELECT distinct pal.* FROM " + MySQLiteHelper.TABLE_PALAVRAS + " pal ";
        String orderBy = "ORDER BY " + MySQLiteHelper.PALAVRAS_COLUMN_PALAVRA;

        List<String> listWhereArgs = new ArrayList<String>();

        String[] whereArgs = null;
        Cursor cursor = null;

        query = query + orderBy;

        if (listWhereArgs.size() > 0) {
            whereArgs = new String[listWhereArgs.size()];
            whereArgs = listWhereArgs.toArray(whereArgs);
        }

        cursor = database.rawQuery(query, whereArgs);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Palavra palavra = cursorToPalavra(cursor);
            palavras.add(palavra);
            cursor.moveToNext();
        }

        // make sure to close the cursor
        cursor.close();

        return palavras;
    }

    private Palavra cursorToPalavra(Cursor cursor) {
        Palavra palavra = new Palavra();
        palavra.setId(cursor.getInt(cursor.getColumnIndex(MySQLiteHelper.PALAVRAS_COLUMN_ID_PALAVRA)));
        palavra.setPalavra(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.PALAVRAS_COLUMN_PALAVRA)));
        palavra.setTextoPalavra(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.PALAVRAS_COLUMN_TEXTO_PALAVRA)));
        palavra.setData(new Date(cursor.getInt(cursor.getColumnIndex(MySQLiteHelper.PALAVRAS_COLUMN_DATA))));
        palavra.setIdClasseGramatical(cursor.getInt(cursor.getColumnIndex(MySQLiteHelper.PALAVRAS_COLUMN_ID_CLASSE_GRAMATICAL)));

        return palavra;
    }
}
