package com.example.qroc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class HomeFragment extends Fragment {
    //onCreateView的意思是：创建视图。
    //我们在这个方法里来动态加载布局
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    //onViewCreate的意思是：视图创建
    //我们在这个方法里面操作视图控件、逻辑方法就像是Activity的onCreate方法。
    //但是要注意我们操作的控件的时候使用的是:onCreateView返回view，通过传递实参到onViewCreated方法的形参View中，所以在初始化控件的时候是view.findViewById();
    //最后要注意如果有些方法使用不了，大概是因为那些方法实在Actiivty中（未作具体了解），所以需要先调用getActivity()方法。
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }



}

