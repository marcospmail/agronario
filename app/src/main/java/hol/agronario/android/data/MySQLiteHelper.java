package hol.agronario.android.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by shouts on 04/02/2015.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_PALAVRAS = "palavras";
    public static final String PALAVRAS_COLUMN_ID = "_id";
    public static final String PALAVRAS_COLUMN_ID_PALAVRA = "id";
    public static final String PALAVRAS_COLUMN_PALAVRA = "palavra";
    public static final String PALAVRAS_COLUMN_TEXTO_PALAVRA = "texto_palavra";
    public static final String PALAVRAS_COLUMN_DATA = "data";
    public static final String PALAVRAS_COLUMN_PRONUNCIA = "pronuncia";
    public static final String PALAVRAS_COLUMN_ID_CLASSE_GRAMATICAL = "id_classe_gramatical";

    public static final String TABLE_CLASSES_GRAMATICAIS = "classes_gramaticais";
    public static final String CLASSES_GRAMATICAIS_COLUMN_ID = "_id";
    public static final String CLASSES_GRAMATICAIS_COLUMN_ID_CLASSE = "id";
    public static final String CLASSES_GRAMATICAIS_COLUMN_NOME = "nome";
    public static final String CLASSES_GRAMATICAIS_COLUMN_DATA = "data";
    public static final String CLASSES_GRAMATICAIS_COLUMN_ID_IDIOMA = "id_idioma";

    public static final String TABLE_IDIOMAS = "idiomas";
    public static final String IDIOMAS_COLUMN_ID = "_id";
    public static final String IDIOMAS_COLUMN_ID_IDIOMAS = "id";
    public static final String IDIOMAS_COLUMN_IDIOMA = "idioma";

    private static final String DATABASE_NAME = "dictionary.db";
    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_CREATE_TABLE_IDIOMAS = "create table " + TABLE_IDIOMAS + "(" + IDIOMAS_COLUMN_ID + " integer primary key autoincrement, " + IDIOMAS_COLUMN_ID_IDIOMAS + " integer not null, "
            + IDIOMAS_COLUMN_IDIOMA + " text not null)";

    private static final String DATABASE_CREATE_TABLE_CLASSES_GRAMATICAIS = "create table " + TABLE_CLASSES_GRAMATICAIS + "( " + CLASSES_GRAMATICAIS_COLUMN_ID + " integer primary key autoincrement, " + CLASSES_GRAMATICAIS_COLUMN_ID_CLASSE + " integer not null, "
            + CLASSES_GRAMATICAIS_COLUMN_NOME + " text not null, " + CLASSES_GRAMATICAIS_COLUMN_DATA + " integer not null, " + CLASSES_GRAMATICAIS_COLUMN_ID_IDIOMA + " integer not null, FOREIGN KEY(" + CLASSES_GRAMATICAIS_COLUMN_ID_IDIOMA + ") REFERENCES " + TABLE_IDIOMAS + "(" + IDIOMAS_COLUMN_IDIOMA + "))";

    private static final String DATABASE_CREATE_TABLE_PALAVRAS = "create table " + TABLE_PALAVRAS + "(" + PALAVRAS_COLUMN_ID + " integer primary key autoincrement, " + PALAVRAS_COLUMN_ID_PALAVRA + " integer not null, " + PALAVRAS_COLUMN_PALAVRA + " text not null, " + PALAVRAS_COLUMN_TEXTO_PALAVRA + " text, " + PALAVRAS_COLUMN_DATA + " integer not null, " + PALAVRAS_COLUMN_PRONUNCIA + " text, " + PALAVRAS_COLUMN_ID_CLASSE_GRAMATICAL + " int not null, FOREIGN KEY(" + PALAVRAS_COLUMN_ID_CLASSE_GRAMATICAL + ") REFERENCES " + TABLE_CLASSES_GRAMATICAIS + "(" + CLASSES_GRAMATICAIS_COLUMN_ID_CLASSE + "))";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        //TODO CRIAR AS DEMAIS TABELAS
        database.execSQL(DATABASE_CREATE_TABLE_PALAVRAS);
        database.execSQL(DATABASE_CREATE_TABLE_CLASSES_GRAMATICAIS);
        database.execSQL(DATABASE_CREATE_TABLE_IDIOMAS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PALAVRAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASSES_GRAMATICAIS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IDIOMAS);

        onCreate(db);
    }

}

