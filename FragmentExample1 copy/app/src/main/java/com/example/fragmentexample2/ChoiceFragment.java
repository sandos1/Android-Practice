package com.example.fragmentexample2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ChoiceFragment extends Fragment {
    private TextView textView;
    private static String userChoice;

    public ChoiceFragment() {
        // Required empty public constructor
    }

    public static String setChoice(String choice){
        userChoice=choice;
        return choice;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
         View rootView =
                inflater.inflate(R.layout.fragment_simple, container, false);

            textView=(TextView)rootView.findViewById(R.id.result);
            textView.setText(userChoice);

        //choice = bundle.getString("choice").toString();

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();



    }
}