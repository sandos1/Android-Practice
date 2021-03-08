package com.example.applemusicapicall;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.Viewholder> {
    interface  Listener{
        void getItemClickPosition(int position);
    }
    private Listener listener;
    private List<Result> resultList;
    private Context context;

    public MyAdapter(){

    }

    public MyAdapter(List<Result> personList, Context context) {
        this.resultList = personList;
        this.context = context;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    //inflates the row layout (cardView) from xml when needed/ inject at runtime the cardViewLayout inside the
    // the recyclerView whenever needed
    public MyAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.Viewholder holder, int position) {
        //grabs a single Person object attribute from the List/data set using index(position)
        Result result =resultList.get(position);


        // pass your object attribute to holder object to be displayed
        // my imageview == holder.circleImageView

        Glide.with(context)
                .load(result.artworkUrl100)
                .placeholder(R.drawable.cancel)  //this image will show in case the image fail to load
                .dontAnimate()
                .into(holder.circleImageView);
        holder.artistname.setText(result.artistName.toString());

        if (result.trackName ==null){
            holder.songName.setText("Song....");
        }else{
            holder.songName.setText(result.trackName.toString());
        }

        holder.price.setText(String.valueOf(result.collectionPrice));
    }

    @Override
    //
    public int getItemCount() {
        return resultList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CircleImageView circleImageView;
        public TextView artistname;
        public TextView songName;
        public TextView price;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            circleImageView = (CircleImageView)itemView.findViewById(R.id.musicImage) ;
            artistname =(TextView)itemView.findViewById(R.id.artistNameTv);
            songName =(TextView)itemView.findViewById(R.id.songNameTv);
            price =(TextView)itemView.findViewById(R.id.priceTv);
            //register view click to fire an event on click
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
//            Toast.makeText(context,"item "+String.valueOf(getAdapterPosition()),Toast.LENGTH_LONG).show();
//            Intent intent = new Intent(context,MusicPlayer.class);
//
//            context.startActivity(intent);

            if(listener!=null){
                listener.getItemClickPosition(getAdapterPosition());
            }
        }
    }



}
