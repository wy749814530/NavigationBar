package com.view.navigation.fragmnet;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.view.navigation.R;

/**
 * @WYU-WIN
 * @date 2020/7/14 0014.
 * description：
 */
public class FragmnetD extends Fragment {
    View rootView;
    TextView textView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_d, container, false);
        textView = rootView.findViewById(R.id.textView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        textView.setText("FragmnetD");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(getClass().getSimpleName(), "=========  onDestroyView  ======");
    }

    public void refresh() {
        Log.i(getClass().getSimpleName(), "=========  refresh  ======");
        if (!isDetached()){
            Toast.makeText(getContext(), getClass().getSimpleName() + " 数据刷新了", Toast.LENGTH_SHORT).show();
        }
    }
}
