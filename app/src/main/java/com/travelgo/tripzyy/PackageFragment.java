package com.travelgo.tripzyy;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class PackageFragment extends Fragment {

    CardView cardMaharashtra,cardGoa,cardGujrat,cardRajsthan,cardmadhePradesh,cardUttarPradesh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_package, container, false);

        cardMaharashtra = view.findViewById(R.id.maharashtraCard);
        cardGoa = view.findViewById(R.id.goaCard);
        cardGujrat = view.findViewById(R.id.gujratCard);
        cardRajsthan = view.findViewById(R.id.rajasthanCard);
        cardmadhePradesh = view.findViewById(R.id.madhepradeshCard);
        cardUttarPradesh = view.findViewById(R.id.uttarpradeshCard);


        cardMaharashtra.setOnClickListener(v-> {
            Intent intent = new Intent(getActivity(),MaharashtraActivity.class);
            startActivity(intent);
        });

        cardGoa.setOnClickListener(v-> {
            Intent intent = new Intent(getActivity(),GoaActivity.class);
            startActivity(intent);
        });

        cardGujrat.setOnClickListener(v-> {
            Intent intent = new Intent(getActivity(),GujratActivity.class);
            startActivity(intent);
        });

        cardRajsthan.setOnClickListener(v-> {
            Intent intent = new Intent(getActivity(),RajsthanActivity.class);
            startActivity(intent);
        });

        cardmadhePradesh.setOnClickListener(v-> {
            Intent intent = new Intent(getActivity(),MadhePradeshActivity.class);
            startActivity(intent);
        });

        cardUttarPradesh.setOnClickListener(v-> {
            Intent intent = new Intent(getActivity(),UttarPradeshActivity.class);
            startActivity(intent);
        });


        return view;
    }
}