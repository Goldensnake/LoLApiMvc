package com.juliens.lolapimvctest.view.champion;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.juliens.lolapimvctest.R;
import com.juliens.lolapimvctest.model.bus.SelectedChampionEvent;
import com.juliens.lolapimvctest.model.champion.ChampionData;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by juliens on 15/09/2017.
 */

public class ChampionAdapter extends RecyclerView.Adapter<ChampionViewHolder> {
    private LinkedHashMap<String, ChampionData> championsData;
    private Context context;

    public ChampionAdapter(LinkedHashMap<String, ChampionData> championsData) {
        this.championsData = championsData;
    }

    @Override
    public ChampionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_champion_simple,parent,false);
        return new ChampionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChampionViewHolder holder, int position) {
        ChampionData data = (new ArrayList<>(championsData.values())).get(position);
        Glide.with(context)
                .load("http://ddragon.leagueoflegends.com/cdn/6.24.1/img/champion/"+data.getImage().getFull())
                .into(holder.imageViewIcon);
        holder.textViewChampName.setText(data.getName()+" "+data.getTitle().toLowerCase());
        holder.textViewDifficulty.setText(String.valueOf(data.getInfo().getDifficulty()));
        holder.textViewChampAttack.setText(String.valueOf(data.getInfo().getAttack()));
        holder.textViewChampMagic.setText(String.valueOf(data.getInfo().getMagic()));
        holder.textViewChampDefense.setText(String.valueOf(data.getInfo().getDefense()));
        holder.itemView.setOnClickListener(view -> EventBus.getDefault().post(new SelectedChampionEvent(data)));
    }

    @Override
    public int getItemCount() {
        return championsData.size();
    }

    public void changeData(LinkedHashMap<String, ChampionData> championsListData){
        championsData.clear();
        championsData.putAll(championsListData);
        notifyDataSetChanged();
    }
}