package nl.cameldevstudio.innavo.ui.building.find.recent;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import nl.cameldevstudio.innavo.R;
import nl.cameldevstudio.innavo.models.Building;
import nl.cameldevstudio.innavo.services.building.TestBuildings;
import nl.cameldevstudio.innavo.ui.base.BaseFragment;
import nl.cameldevstudio.innavo.ui.building.info.BuildingInfoActivity;
import nl.cameldevstudio.innavo.ui.building.find.list.BuildingAdapter;

import static nl.cameldevstudio.innavo.ui.building.find.list.BuildingAdapter.BUILDING_INFO_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindRecentFragment extends BaseFragment implements BuildingAdapter.BuildingClickListener {
    @BindView(R.id.recent)
    protected RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find_recent, container, false);
        unbinder = ButterKnife.bind(this, view);
        BuildingAdapter favorietenAdapter = new BuildingAdapter(TestBuildings.get(), this);

        // TODO add code for recent
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(favorietenAdapter);
        return view;
    }

    @Override
    public void onBuildingClick(String buildingId) {
        Intent intent = new Intent(this.getActivity(), BuildingInfoActivity.class);
        intent.putExtra(BUILDING_INFO_KEY, buildingId);
        startActivity(intent);
    }
}
