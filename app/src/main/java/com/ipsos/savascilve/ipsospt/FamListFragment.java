package com.ipsos.savascilve.ipsospt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ipsos.savascilve.ipsospt.Data.PTContract;

import java.util.ArrayList;

public class FamListFragment extends Fragment {

    private ArrayAdapter<String> _famListAdapter;

    public FamListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //ArrayList<String> demoList = new ArrayList<>();

        _famListAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.list_item_fam,
                new ArrayList<String>());

        ListView listView = (ListView) rootView.findViewById(R.id.famListListView);
        listView.setAdapter(_famListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String item = _famListAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class).putExtra(Intent.EXTRA_TEXT, "");
                startActivity(intent);
            }
        });

        return rootView;
    }

}
