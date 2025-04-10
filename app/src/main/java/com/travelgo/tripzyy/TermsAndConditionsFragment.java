package com.travelgo.tripzyy;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.travelgo.tripzyy.HomeActivity;
import com.travelgo.tripzyy.R;

public class TermsAndConditionsFragment extends Fragment {

    private CheckBox checkAgree;
    private Button btnContinue;
   // private TextView tvTerms;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_terms_and_conditions, container, false);

        checkAgree = view.findViewById(R.id.checkAgree);
        btnContinue = view.findViewById(R.id.btnContinue);
    //    tvTerms = view.findViewById(R.id.tvTerms);

     //   tvTerms.setText(getTermsText());

        checkAgree.setOnCheckedChangeListener((buttonView, isChecked) -> {
            btnContinue.setEnabled(isChecked);
        });

        btnContinue.setOnClickListener(v -> {
            // Navigate or perform next action
            Intent intent = new Intent(getActivity(), HomeActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });

        return view;
    }

//    private String getTermsText() {
//            return "Terms and Conditions\n\n"
//                    + "1. Booking must be confirmed with correct personal info.\n"
//                    + "2. Cancellations may include fees and are subject to provider policies.\n"
//                    + "3. Users must hold valid travel documents.\n"
//                    + "4. We are not liable for flight delays or changes.\n"
//                    + "5. All user data is protected under our Privacy Policy.\n"
//                    + "6. Misuse of the app may lead to account suspension.\n"
//                    + "7. We may update these terms anytime without prior notice.\n"
//                    + "\nBy continuing, you agree to these terms.";
//    }
//

}
