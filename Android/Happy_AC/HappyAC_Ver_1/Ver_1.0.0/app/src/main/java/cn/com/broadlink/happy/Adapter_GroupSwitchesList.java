package cn.com.broadlink.happy;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static cn.com.broadlink.happy.MainActivity.edt_conrt;


/**
 * Created by Vinay on 1/23/2017.
 */

public class Adapter_GroupSwitchesList extends ArrayAdapter<String> {
    private Context context;
    private String[] values;

    public Adapter_GroupSwitchesList(Context context, String[] array) {
        super(context, R.layout.activity_main, array);
        this.context = context;
        this.values = array;


    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.ir_switches, parent, false);

        final TextView def_swtch = (TextView) rowView.findViewById(R.id.btn);
        final ImageView backicon = (ImageView) rowView.findViewById(R.id.img);
        final LinearLayout visible_invis = (LinearLayout) rowView.findViewById(R.id.swtchlyid);
       // Log.d("list of items",values[position]);

        if(values[position].startsWith("FAN")){
            def_swtch.setVisibility(View.VISIBLE);
            /*fan.setVisibility(View.VISIBLE);
            fan.setText("fan");*/
            String[] animalsArray = values[position].split(",");
            Log.d("list of items",animalsArray[1]);
            def_swtch.setText(animalsArray[1]);
            def_swtch.setTextSize(8);
            backicon.setBackgroundResource(R.mipmap.fan_icon_sm);
        }

        if(values[position].startsWith("TV"))
        {

            def_swtch.setVisibility(View.VISIBLE);
           /* light.setVisibility(View.VISIBLE);
            light.setText("light");*/
            String[] animalsArray = values[position].split(",");
            def_swtch.setText(animalsArray[1]);
            def_swtch.setTextSize(8);
            backicon.setBackgroundResource(R.mipmap.tv_icon_sm);
           // def_swtch.setBackgroundResource(isButtonClicked ? R.drawable.btn_star_on : R.drawable.btn_star_off);
        }

         if(values[position].startsWith("LAMP"))
        {
            def_swtch.setVisibility(View.VISIBLE);
            String[] animalsArray = values[position].split(",");
            def_swtch.setText(animalsArray[1]);
            def_swtch.setTextSize(8);
            /*tv.setVisibility(View.VISIBLE);
            tv.setText("tv");*/
            backicon.setBackgroundResource(R.mipmap.lamp_icon_sm);
        }

        if(values[position].startsWith("WASHINGMISION")){
            def_swtch.setVisibility(View.VISIBLE);
            /*fan.setVisibility(View.VISIBLE);
            fan.setText("fan");*/
            String[] animalsArray = values[position].split(",");
            def_swtch.setText(animalsArray[1]);
            def_swtch.setTextSize(8);
            backicon.setBackgroundResource(R.mipmap.washing_icon_sm);
        }

        if(values[position].startsWith("OVEN"))
        {
            def_swtch.setVisibility(View.VISIBLE);
           /* light.setVisibility(View.VISIBLE);
            light.setText("light");*/
            backicon.setBackgroundResource(R.mipmap.owen_icon_sm);
            String[] animalsArray = values[position].split(",");
            def_swtch.setText(animalsArray[1]);
            def_swtch.setTextSize(8);
            // def_swtch.setBackgroundResource(isButtonClicked ? R.drawable.btn_star_on : R.drawable.btn_star_off);
        }

        if(values[position].startsWith("PRINTER"))
        {
            def_swtch.setVisibility(View.VISIBLE);
            String[] animalsArray = values[position].split(",");
            def_swtch.setText(animalsArray[1]);
            def_swtch.setTextSize(8);
            /*tv.setVisibility(View.VISIBLE);
            tv.setText("tv");*/
            backicon.setBackgroundResource(R.mipmap.printer_icon_sm);
        }

        if(values[position].startsWith("CLEANER")){
            def_swtch.setVisibility(View.VISIBLE);
            /*fan.setVisibility(View.VISIBLE);
            fan.setText("fan");*/
            def_swtch.setTextSize(8);
            String[] animalsArray = values[position].split(",");
            def_swtch.setText(animalsArray[1]);
            backicon.setBackgroundResource(R.mipmap.cleaner_icon_sm);
        }

        if(values[position].startsWith("FRIDGE"))
        {
            def_swtch.setVisibility(View.VISIBLE);
           /* light.setVisibility(View.VISIBLE);
            light.setText("light");*/
            def_swtch.setTextSize(8);
            backicon.setBackgroundResource(R.mipmap.fridge_icon_sm);
            String[] animalsArray = values[position].split(",");
            def_swtch.setText(animalsArray[1]);
            // def_swtch.setBackgroundResource(isButtonClicked ? R.drawable.btn_star_on : R.drawable.btn_star_off);
        }

        if(values[position].startsWith("FAN2"))
        {
            def_swtch.setVisibility(View.VISIBLE);
            /*tv.setVisibility(View.VISIBLE);
            tv.setText("tv");*/
            def_swtch.setTextSize(8);
            String[] animalsArray = values[position].split(",");
            def_swtch.setText(animalsArray[1]);
            backicon.setBackgroundResource(R.mipmap.fan2_icon_sm);
        }
        if(values[position].startsWith("AC"))
        {
            def_swtch.setVisibility(View.VISIBLE);
            /*tv.setVisibility(View.VISIBLE);
            tv.setText("tv");*/
            String[] animalsArray = values[position].split(",");
            def_swtch.setText(animalsArray[1]);
            def_swtch.setTextSize(8);
            backicon.setBackgroundResource(R.mipmap.ac_icon_sm);
        }

        if(values[position].contentEquals("L"+position))
        {
            if(edt_conrt) {

                visible_invis.setVisibility(View.GONE);
                def_swtch.setText("L" + position);
            }else {
                visible_invis.setVisibility(View.VISIBLE);
                def_swtch.setVisibility(View.VISIBLE);
                def_swtch.setText("L" + position);
            }
          /*  fan.setVisibility(View.GONE);
            light.setVisibility(View.GONE);
            tv.setVisibility(View.GONE);*/
        }
        /*else {
            def_swtch.setVisibility(View.VISIBLE);
            def_swtch.setText("L"+position);
        }*/

        return rowView;
    }
}
