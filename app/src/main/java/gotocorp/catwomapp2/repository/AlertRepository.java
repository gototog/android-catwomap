package gotocorp.catwomapp2.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import gotocorp.catwomapp2.entity.Alert;
import gotocorp.catwomapp2.sql.BddSql;

/**
 * Created by goto on 04/12/15.
 */
public class AlertRepository {



    private SQLiteDatabase sqlDatabase;

    private BddSql maBddSql;

    public AlertRepository(Context context){
        //On crée la BDD et sa table
        maBddSql = new BddSql(context, BddSql.DB_NAME, null, 1);

    }


    public void open(){
        //on ouvre la BDD en écriture
        sqlDatabase = maBddSql.getWritableDatabase();

    }

    public void close(){
        //on ferme l'accès à la BDD
        sqlDatabase.close();
    }

    public SQLiteDatabase getSqlDatabase(){
        return sqlDatabase;
    }

    public long insertAlert(Alert alert){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(BddSql.COL_ALERT_LOCATION, alert.getMapCoordinate().toLowerCase());
        values.put(BddSql.COL_ALERT_NAME, alert.getName());

        //on insère l'objet dans la BDD via le ContentValues
        return sqlDatabase.insert(BddSql.TABLE_ALERT, null, values);
    }

//    public int updateCity(int id, Alert alert){
//
//        ContentValues values = new ContentValues();
//        values.put(BddSql.COL_CITY_NAME, alert.getName());
//        values.put(BddSql.COL_CITY_NBPEOPLE, alert.getNbPeople());
//        return sqlDatabase.update(BddSql.TABLE_CITIES, values, BddSql.COL_CITY_ID + " = " + id, null);
//    }

    public int deleteAlertById(int id){
        return sqlDatabase.delete(BddSql.TABLE_ALERT, BddSql.COL_ALERT_ID + " = " + id, null);
    }

    public Alert getAlertByName(String textSearched){
        //Récupère dans un Cursor les valeurs correspondant à une ville contenu dans la BDD (ici on sélectionne la alert grâce à son titre)
        Cursor c = sqlDatabase.query(BddSql.TABLE_ALERT, new String[] {BddSql.COL_ALERT_ID, BddSql.COL_ALERT_LOCATION, BddSql.COL_ALERT_NAME}, BddSql.COL_ALERT_NAME + " LIKE \"" + textSearched +"\"", null, null, null, null);
        Alert alert = new Alert("error","");
        if (c.getCount() > 0) {
            c.moveToFirst(); //on gère pas les doublons pour l'instant
            alert = cursorToAlert(c);
        }
        return alert;
    }

    public List<Alert> getAlerts(){

        //Récupère dans un Cursor les valeurs correspondant à une ville contenu dans la BDD (ici on sélectionne la city grâce à son titre)
//        Cursor c = sqlDatabase.query(BddSql.TABLE_ALERT, new String[] {BddSql.COL_ALERT_ID, BddSql.COL_ALERT_NAME, BddSql.COL_ALERT_LOCATION}, null, null, null, null, null);
        List<Alert> alertList = new ArrayList<>();
        Alert alert = new Alert("alerte1", "-15;45");
        alertList.add(alert);
//        if (c.getCount() > 0) {
//
//
//            for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
//                Alert alert = cursorToAlert(c);
//                alertList.add(alert);
//            }
//        }

        //Sinon on se place sur le premier élément
//        c.close();
        return alertList;
    }

    public List<Alert> initAlert(){
        List<Alert> alertList = new ArrayList<>();

        alertList.add( new Alert("Lille", "227 560") );
        alertList.add( new Alert("Paris", "2.244 millions") );
        alertList.add( new Alert("Grenoble", "155 637") );
        alertList.add( new Alert("Dijon", "151 212") );
        alertList.add(new Alert("Marseille", "850 726"));

        //Sinon on se place sur le premier élément
        for(Alert alert : alertList) {
            this.insertAlert(alert);
        }
        return alertList;
    }
    //Cette méthode permet de convertir un cursor en une ville
    private Alert cursorToAlert(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null

        //On créé une ville
        Alert alert = new Alert();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        alert.setId(c.getInt(0));
        alert.setName(c.getString(1));
        alert.setMapCoordinate(c.getString(2));


        //On retourne la alert
        return alert;
    }
}
