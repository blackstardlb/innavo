package nl.cameldevstudio.innavo.ui.building.find.list;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import nl.cameldevstudio.innavo.R;
import nl.cameldevstudio.innavo.models.Building;
import nl.cameldevstudio.innavo.ui.base.BaseFragment;
import nl.cameldevstudio.innavo.ui.building.info.BuildingInfoActivity;

import static nl.cameldevstudio.innavo.ui.building.find.list.BuildingAdapter.BUILDING_INFO_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuildingsListFragment extends BaseFragment implements BuildingAdapter.BuildingClickListener {

    @BindView(R.id.building_search_view)
    protected SearchView buildingSearchView;

    @BindView(R.id.recommendations_scroll_view)
    protected ScrollView recommendationsScrollView;

    @BindView(R.id.background_color_layout)
    protected RelativeLayout backgroundColorLayout;

    @BindView(R.id.query_result_recycler_view)
    protected RecyclerView queryResultsRecyclerView;

    private BuildingsListViewModel buildingsListViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildingsListViewModel = getPrivateViewModel(BuildingsListViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        buildingSearchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                // show recommendations
                backgroundColorLayout.setBackgroundColor(getResources().getColor(R.color.hazy));
                if (buildingSearchView.getQuery().toString().isEmpty()) {
                    recommendationsScrollView.setVisibility(View.VISIBLE);
                } else {
                    queryResultsRecyclerView.setVisibility(View.VISIBLE);
                }
            } else {
                // hide recommendations
                recommendationsScrollView.setVisibility(View.GONE);
                backgroundColorLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                queryResultsRecyclerView.setVisibility(View.GONE);
            }
        });

        buildingSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return updateQuery(s);
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return updateQuery(s);
            }
        });

        buildingSearchView.setQuery(buildingsListViewModel.getQueryText(), false);
        buildingSearchView.clearFocus();

        BuildingAdapter buildingAdapter = new BuildingAdapter(new ArrayList<>(), this);
        queryResultsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        queryResultsRecyclerView.setAdapter(buildingAdapter);

        getCompositeDisposable().add(
                buildingsListViewModel.queryResults().subscribe(buildingAdapter::setBuildings)
        );
        return view;
    }

    private boolean updateQuery(String s) {
        buildingsListViewModel.setQueryText(s);
        if (s.isEmpty()) {
            queryResultsRecyclerView.setVisibility(View.GONE);

            // show recommendations
            if (buildingSearchView.hasFocus()) {
                recommendationsScrollView.setVisibility(View.VISIBLE);
            }
        } else {
            recommendationsScrollView.setVisibility(View.GONE);

            // show query results
            if (buildingSearchView.hasFocus()) {
                queryResultsRecyclerView.setVisibility(View.VISIBLE);
            }
        }
        return false;
    }

    @Override
    public void onBuildingClick(String buildingId) {
        Intent intent = new Intent(this.getActivity(), BuildingInfoActivity.class);
        intent.putExtra(BUILDING_INFO_KEY, buildingId);
        startActivity(intent);
    }
}
