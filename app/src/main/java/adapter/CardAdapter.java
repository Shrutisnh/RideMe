package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import app.data.RideMeSession;
import common.activity.ride.common.LoginActivity;
import common.activity.ride.common.R;
import services.data.OfferedRideData;

/**
 * Created by Shruti Sinha on 8/19/2016.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<OfferedRideData> list;

    public class MyViewHolder extends  RecyclerView.ViewHolder{
        public TextView tvFromToLocation,tvDateTime,tvfair,tvOwnerName;
        public Button btnCallOwner;
        public MyViewHolder(View view){

            super(view);
            Log.i("CardAdapter","In MyViewHolder:");
            tvFromToLocation=(TextView)view.findViewById(R.id.fromToLocationText);
            tvDateTime=(TextView)view.findViewById(R.id.dateTimeText);
            tvfair=(TextView)view.findViewById(R.id.fairText);
            tvOwnerName=(TextView)view.findViewById(R.id.ownerNameText);
            btnCallOwner=(Button)view.findViewById(R.id.callOwner);
            btnCallOwner.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
//                    if(RideMeSession(mContext).ge.isLoggedIn())
                    RideMeSession session=new RideMeSession(mContext);
                    if(session.isLoggedIn())
                    {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:" + btnCallOwner.getTag().toString()));

                        try {
                            mContext.startActivity(callIntent);
                        } catch (SecurityException e) {
                            Log.i("CardAdapter", "Security Exception");
                        }
                    }
                    else
                    {
//                        RideMeSession.getInstance().createLoginSession(-);
                        Toast.makeText(mContext,"You need to login first",Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(mContext, LoginActivity.class);
                        ((Activity)mContext).startActivity(i);

                    }
                }
            });
        }

    }

    public CardAdapter(Context context, ArrayList<OfferedRideData> list){
        this.mContext=context;
        this.list=list;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.i("CardAdapter:","In onBindViewHolder");
        OfferedRideData data=list.get(position);
        holder.tvFromToLocation.setText(data.getPickupLocation()+" to "+data.getDropLocation());
        holder.tvDateTime.setText(data.getDate()+" "+data.getTime());
        holder.tvOwnerName.setText(data.getOwnerName());
        holder.tvfair.setText(data.getFair());
        holder.btnCallOwner.setTag(data.getContactNo());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("CardAdapter","In onCreateViewHolder");
        View view= LayoutInflater.from(mContext).inflate(R.layout.content_find_ride_card_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        Log.i("CardAdapter","in getItemCount()");
        return list.size();
    }
}
