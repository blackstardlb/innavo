package nl.cameldevstudio.innavo.ui.friend.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import nl.cameldevstudio.innavo.R;
import nl.cameldevstudio.innavo.ui.base.BaseFragment;

public class FriendListFragment extends BaseFragment {
    @BindView (R.id.friend_recycler_view)
    RecyclerView recyclerView;

    FragmentActivity fragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragment = getActivity();
        return inflater.inflate(R.layout.fragment_friend_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(fragment);

        recyclerView.setLayoutManager(layoutManager);
    }
}
