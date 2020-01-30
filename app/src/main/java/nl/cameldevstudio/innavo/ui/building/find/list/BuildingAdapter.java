package nl.cameldevstudio.innavo.ui.building.find.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import javax.annotation.Nonnull;

import butterknife.BindView;
import butterknife.ButterKnife;
import nl.cameldevstudio.innavo.R;
import nl.cameldevstudio.innavo.models.Building;

public class BuildingAdapter extends RecyclerView.Adapter<BuildingAdapter.ViewHolder> {
    public interface BuildingClickListener{
        void onBuildingClick(String buildingId);
    }

    public static final String BUILDING_INFO_KEY = "building";

    private List<Building> buildings;

    final private BuildingClickListener clickListener;

    public BuildingAdapter(List<Building> buildings, BuildingClickListener listener) {
        this.buildings = buildings;
        clickListener = listener;
    }

    public void setBuildings(@Nonnull List<Building> buildings) {
        this.buildings = buildings;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_map_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Building building = buildings.get(i);
        viewHolder.bindBuilding(building);
    }

    @Override
    public int getItemCount() {
        return buildings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.title)
        protected TextView title;

        @BindView(R.id.subtitle)
        protected TextView subtitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bindBuilding(Building building) {
            title.setText(building.getName());
            subtitle.setText(building.getAddress());
        }

        @Override
        public void onClick(View v) {
            int clickPosition = getAdapterPosition();
            clickListener.onBuildingClick(buildings.get(clickPosition).getId());
        }
    }
}
