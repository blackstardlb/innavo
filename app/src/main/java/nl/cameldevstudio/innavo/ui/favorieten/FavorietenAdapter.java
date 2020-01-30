package nl.cameldevstudio.innavo.ui.favorieten;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import butterknife.BindView;
import butterknife.ButterKnife;
import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;
import nl.cameldevstudio.innavo.R;
import nl.cameldevstudio.innavo.models.Building;
import nl.cameldevstudio.innavo.services.building.TestBuildings;
import nl.cameldevstudio.innavo.services.image.ImageReferenceService;

public class FavorietenAdapter extends RecyclerView.Adapter<FavorietenAdapter.Holder> {
    private List<Building> favorieten;
    private String query = "";
    private ImageReferenceService imageReferenceService;

    public FavorietenAdapter(ImageReferenceService imageReferenceService) {
        this.imageReferenceService = imageReferenceService;
        favorieten = new ArrayList<>();
        favorieten.addAll(TestBuildings.get());
        favorieten.addAll(TestBuildings.get());
        favorieten.addAll(TestBuildings.get());
        favorieten.addAll(TestBuildings.get());
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.favoriet_building_list_item, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        Building building = getFiltered().get(i);
        holder.bind(building);
    }

    public void filter(@Nonnull String query) {
        this.query = query;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return getFiltered().size();
    }

    private List<Building> getFiltered() {
        String lowerCaseQuery = query.toLowerCase();
        return StreamSupport.stream(favorieten)
                .filter(building -> building.getAddress().toLowerCase().contains(lowerCaseQuery) || building.getName().toLowerCase().contains(lowerCaseQuery))
                .collect(Collectors.toList());
    }

    public class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.favorite_address)
        protected TextView address;

        @BindView(R.id.favorite_name)
        protected TextView name;

        @BindView(R.id.favorite_image)
        protected ImageView imageView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, this.itemView);
        }

        public void bind(Building building) {
            name.setText(building.getName());
            address.setText(building.getAddress());
            Glide.with(imageView.getContext())
                    .load(imageReferenceService.getBuildingImageReference(building.getImage()))
                    .into(imageView);
        }
    }
}
