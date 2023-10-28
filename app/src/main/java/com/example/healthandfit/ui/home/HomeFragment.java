package com.example.healthandfit.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.healthandfit.R;
import com.example.healthandfit.databinding.FragmentHomeBinding;
import com.example.healthandfit.strengthActivity;
import com.example.healthandfit.stretchActivity;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private Button stretch;
    private Button strength;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
               new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
       View root = binding.getRoot();

       // final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
       return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
       binding = null;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        stretch = view.findViewById(R.id.stretch);
        stretch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // public void goToAttract(View v){
                Intent intent4 = new Intent(getActivity(), stretchActivity.class);
                startActivity(intent4);
                // }
            }
        });


        strength = view.findViewById(R.id.strength);
        strength.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // public void goToAttract(View v){
                Intent intent5 = new Intent(getActivity(), strengthActivity.class);
                startActivity(intent5);
                // }
            }
        });
    }

}