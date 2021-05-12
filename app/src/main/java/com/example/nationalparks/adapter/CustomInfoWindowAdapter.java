package com.example.nationalparks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.nationalparks.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Context context;
    private View view;
    private LayoutInflater layoutInflater;

    //the InfoWindowAdapter does not give us the OnCreateView method where we usually have a layourinflator,
    //so we need to create out own layoutinflator
    public CustomInfoWindowAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.custom_info_window, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    //this is where we can get our widgets
    @Override
    public View getInfoContents(Marker marker) { // this marker comes from MapsActivity where we do mMap.addMarker
        TextView parkName = view.findViewById(R.id.info_title);
        TextView parkState = view.findViewById(R.id.info_state);

        parkName.setText(marker.getTitle());
        parkState.setText(marker.getSnippet());
        return view;
    }
}
