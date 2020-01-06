package cn.com.broadlink.happy;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Recycler_adapter  extends RecyclerView.Adapter<Recycler_adapter.ViewHolder> {
    private ArrayList<String> countries;

    public Recycler_adapter(ArrayList<String> countries) {
        this.countries = countries;
    }

    @Override
    public Recycler_adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_data, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Recycler_adapter.ViewHolder viewHolder, int i) {
       // String[] room_name=countries.get(i).split(",");
        //viewHolder.tv_country.setText(room_name[0]);
        viewHolder.tv_country.setText(countries.get(i));
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_country;
        public ViewHolder(View view) {
            super(view);

            tv_country = (TextView)view.findViewById(R.id.tv_country);
        }




    }

}