package gotocorp.catwomapp2.sql;

/**
 * Created by goto on 04/12/15.
 */


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BddSql extends SQLiteOpenHelper {

    public static final String DB_NAME = "db_name_alert.db";
    public static final String TABLE_ALERT = "table_alerte";
    public static final String TABLE_USER = "table_user";
    public static final String COL_ALERT_ID = "ID";
    public static final String COL_ALERT_LOCATION = "alert_location";
    public static final String COL_ALERT_NAME = "alert_name";
    public static final String COL_USER_ID = "ID";
    public static final String COL_USER_LOCATION = "user_location";
    public static final String COL_USER_FIRSTNAME = "user_firstname";
    public static final String COL_USER_LASTNAME = "user_lastname";

    private static final String CREATE_BDD = "" +
            "CREATE TABLE " + TABLE_ALERT + " ("
            + COL_ALERT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_ALERT_LOCATION + " TEXT NOT NULL, " + COL_ALERT_NAME + " TEXT NOT NULL);" +
            "" +
            "CREATE TABLE " + TABLE_USER + " ("
            + COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_USER_FIRSTNAME + " TEXT NOT NULL, " + COL_USER_LASTNAME + " TEXT NOT NULL, " + COL_USER_LOCATION + " TEXT NOT NULL);" +
            "";

    public BddSql(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //on crée la table à partir de la requête écrite dans la variable CREATE_BDD
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On peut faire ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
        //comme ça lorsque je change la version les id repartent de 0
        db.execSQL("DROP TABLE " + TABLE_ALERT + ";");
        onCreate(db);
    }

}
