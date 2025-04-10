package com.travelgo.tripzyy;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class VehiclesFragment extends Fragment {

    private CardView cardBus, cardCar, cardTravellers;

    public VehiclesFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vehicles, container, false);

        cardBus = view.findViewById(R.id.cardBus);
        cardCar = view.findViewById(R.id.cardCar);
        cardTravellers = view.findViewById(R.id.cardTravellers);

        // Add click listeners
        cardBus.setOnClickListener(v-> {
            Intent intent = new Intent(getActivity(),BusesActivity.class);
            startActivity(intent);
        });
        cardCar.setOnClickListener(v-> {
            Intent intent = new Intent(getActivity(),CarsActivity.class);
            startActivity(intent);
        });
        cardTravellers.setOnClickListener(v-> {
            Intent intent = new Intent(getActivity(),TravellersActivity.class);
            startActivity(intent);
        });


        return view;
    }

}