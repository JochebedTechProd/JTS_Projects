package cn.com.broadlink.happy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class Adapter_RoomList extends ArrayAdapter<String> {
    private Context context;
    private ArrayList<String> values;
    private ArrayList<String> values2;




    public Adapter_RoomList(Context context, ArrayList<String> array) {
        super(context, R.layout.activity_main, array);
        this.context = context;
        this.values = array;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.room_list, parent, false);
        TextView rm_lst = (TextView) rowView.findViewById(R.id.roomList);
        LinearLayout paird_rm = (LinearLayout) rowView.findViewById(R.id.bkid);

          /*  if(values2.get(position).contentEquals("0"))
                paird_rm.setBackgroundColor(Color.parseColor("#c0c0c0"));
            if(values2.get(position).contentEquals("1"))
                paird_rm.setBackgroundColor(Color.parseColor("#555555"));*/

       // String roomname = values.get(position);
        //String[] room_name=roomname.split(",");
       // Log.d("Room in name is", "" + roomname);
        if(values.get(position).contentEquals("livingroom")){
            paird_rm.setBackgroundResource(R.mipmap.livingroom_icon);
            rm_lst.setText(values.get(position));


        }
        if(values.get(position).contentEquals("diningroom")){
            paird_rm.setBackgroundResource(R.mipmap.dining_icon);
            rm_lst.setText(values.get(position));

        }
        if(values.get(position).contentEquals("guestroom")){
            paird_rm.setBackgroundResource(R.mipmap.guest_room_icon);
            rm_lst.setText(values.get(position));

        }
        else {

            paird_rm.setBackgroundResource(R.mipmap.guest_room_icon);
            rm_lst.setText(values.get(position));
        }
       /* if(values.get(position).contentEquals("room1"))
        paird_rm.setBackgroundResource(R.mipmap.dining_icon);
        rm_lst.setText(values.get(position));
*/
        return rowView;
    }

}
