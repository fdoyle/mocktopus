package com.lacronicus.mocktopus.mocktopusdriver.mocktopus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.lacronicus.mocktopus.mocktopusdriver.R;

/**
 * Created by fdoyle on 7/24/14.
 */
public class ConfigurationFragment extends Fragment {
    ExpandableListView lv;
    MockInvocationHandler handler;

    public void setContent(MockInvocationHandler handler) {
        this.handler = handler;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mock_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv = (ExpandableListView) view.findViewById(R.id.lv);

        final OptionsAdapter adapter = new OptionsAdapter(getActivity());
        adapter.setContent(handler.getFlattenedOptions());
        lv.setAdapter(adapter);
        lv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                FieldSettings settings = handler.getSettings();
                switch (adapter.getGroup(groupPosition).getType()) {
                    case FlattenedOptions.FlatOptionsItem.TYPE_FIELD:
                        FlattenedOptions.MethodFieldItem item = adapter.getGroup(groupPosition).methodFieldItem; //currently the
                        settings.put(item.getPair(), item.fieldOptions.get(childPosition));
                        break;
                    default:
                        break;

                }


                return true;
            }
        });

    }


}
