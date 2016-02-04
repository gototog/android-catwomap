package gotocorp.catwomapp2.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import gotocorp.catwomapp2.MapActivity;
import gotocorp.catwomapp2.R;
import gotocorp.catwomapp2.entity.Alert;

/**
 * Created by goto on 08/12/15.
 */
public class AlertAdapter extends BaseAdapter {

    //notre liste d'objet model
    private List<Alert> alerts;
    //a voir si ca sert
    private Context context;

    public AlertAdapter(final Context context, final List<Alert> alerts) {
        this.alerts = alerts;
        this.context = context;
    }

    public List<Alert> getData() {
        return alerts;
    }

    @Override
    public int getCount() {
        return alerts.size();
    }

    @Override
    public Object getItem(int position) {
        return alerts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alerts.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Alert entry = alerts.get(position);


        /*
         * ils le disent mieux que moi
         * The convertView argument is essentially a "ScrapView" as described is Lucas post
         * http://lucasr.org/2012/04/05/performance-tips-for-androids-listview/
         * It will have a non-null value when ListView is asking you recycle the row layout.
         * So, when convertView is not null, you should simply update its contents instead of inflating a new row layout.
         */
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.alert_row, null);

        }
        convertView.setTag(position);

        // get the TextView and then set the text (item name) and tag (item ID) values
        TextView textViewItemName = (TextView) convertView.findViewById(R.id.alertRowName);
        textViewItemName.setText( entry.getName() );
        TextView textViewItemNbPeople = (TextView) convertView.findViewById(R.id.alertRowNpPeople);
        textViewItemNbPeople.setText(entry.getMapCoordinate());

        Button btnShow = (Button) convertView.findViewById(R.id.btnShow);
        btnShow.setFocusableInTouchMode(false);
        btnShow.setFocusable(false);
        btnShow.setTag(entry);

        //todo trouver comment remettre cette fonction dans le main Activity, elle y a un peu plus sa place
        btnShow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Alert entry = (Alert) view.getTag();

                Activity activity = (Activity) context;
                Intent intent = new Intent(activity, MapActivity.class);
                intent.putExtra("alertName", entry.getName());

                activity.startActivity(intent);
                //petites animations
                activity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

            }
        });


        return convertView;
    }





}
