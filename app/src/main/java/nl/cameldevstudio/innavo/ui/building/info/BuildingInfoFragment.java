package nl.cameldevstudio.innavo.ui.building.info;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import nl.cameldevstudio.innavo.R;
import nl.cameldevstudio.innavo.helpers.GlideApp;
import nl.cameldevstudio.innavo.models.Building;
import nl.cameldevstudio.innavo.ui.base.BaseFragment;
import nl.cameldevstudio.innavo.ui.indoorAtlas.WayfindingOverlayActivity;

import static com.google.common.base.Ascii.toLowerCase;
import static nl.cameldevstudio.innavo.ui.building.find.list.BuildingAdapter.BUILDING_INFO_KEY;

public class BuildingInfoFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.iv_building)
    ImageView image;

    @BindView(R.id.tv_title)
    TextView title;

    @BindView(R.id.tv_streetInfo)
    TextView street;

    @BindView(R.id.ib_planRoute)
    ImageButton planRoute;

    @BindView(R.id.ib_close)
    ImageButton close;

    @BindView(R.id.bt_show_map)
    Button show_map;

    private Building building;
    private String buildingId;
    private FragmentActivity activity;
    private BuildingInfoViewModel buildingInfoViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildingInfoViewModel = getPrivateViewModel(BuildingInfoViewModel.class);
        activity = getActivity();

        try {
            Intent intent = activity.getIntent();
            if (intent.hasExtra(BUILDING_INFO_KEY)) {
                buildingId = intent.getStringExtra(BUILDING_INFO_KEY);
            } else {
                Toast.makeText(activity, "No building found", Toast.LENGTH_SHORT).show();
                activity.finish();
            }
        } catch (Exception e) {
            showError(e);
            Toast.makeText(activity, "An error occured", Toast.LENGTH_SHORT).show();
            activity.finish();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_building_info, container, false);
        unbinder = ButterKnife.bind(this, view);

        getCompositeDisposable().add(
                buildingInfoViewModel.getBuilding(buildingId).subscribe(building1 -> {
                    if (building1 != null) {
                        title.setText(building1.getName());
                        street.setText(building1.getAddress());

                        Glide.with(image)
                                .load(buildingInfoViewModel.getImageReferenceService().getBuildingImageReference(building1.getImage()))
                                .into(image);
                        setBuilding(building1);
                    }
                }));

        planRoute.setOnClickListener(this);
        close.setOnClickListener(this);
        show_map.setOnClickListener(this);

        return view;
    }

    private void setBuilding(Building item) {
        building = item;
    }

    @Override
    public void onClick(View v) {
        if (v == planRoute) {
            String webURL = "https://9292.nl/?naar=" + stringModifier(building.getCity()) + "_" +
                    stringModifier(building.getStreet()) + "-" + toLowerCase(building.getHouseNumber());

            Uri webPage = Uri.parse(webURL);


            Intent intent = new Intent(Intent.ACTION_VIEW, webPage);

            if (intent.resolveActivity(activity.getPackageManager()) != null) {
                startActivity(intent);
            }
        }else if(v == close){
            activity.finish();
        }else if(v == show_map){
            Intent intent = new Intent(activity, WayfindingOverlayActivity.class);
            startActivity(intent);
        }
    }

    private String stringModifier(String destination) {
        return destination.replaceAll("[.\\s]+", "-")
                .toLowerCase();
    }
}
