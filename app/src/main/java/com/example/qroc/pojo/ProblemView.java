package com.example.qroc.pojo;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class ProblemView {
    private LinearLayout linearLayout;
    private TextView id;
    private ArrayList<EditText> options;
    private ArrayList<TextView> optionsTv;

    public ProblemView(LinearLayout linearLayout, TextView id, ArrayList<EditText> options, ArrayList<TextView> optionsTv) {
        this.linearLayout = linearLayout;
        this.id = id;
        this.options = options;
        this.optionsTv = optionsTv;
    }

    public static ProblemView create(LinearLayout linearLayout, TextView id){
        ProblemView problemView = new ProblemView();
        problemView.setId(id);
        problemView.setLinearLayout(linearLayout);
        return problemView;
    }

    public void addOption(EditText editText,TextView textView){
        options.add(editText);
        optionsTv.add(textView);
    }

    public ArrayList<TextView> getOptionsTv() {
        return optionsTv;
    }

    public void setOptionsTv(ArrayList<TextView> optionsTv) {
        this.optionsTv = optionsTv;
    }

    public void removeOption(){
        if(options.size() == 0) return;
        optionsTv.remove(optionsTv.size() - 1);
        options.remove(options.size() - 1);
    }

    public ProblemView(LinearLayout linearLayout, TextView id, ArrayList<EditText> options) {
        this.linearLayout = linearLayout;
        this.id = id;
        this.options = options;
    }

    public ProblemView() {
        options = new ArrayList<>();
        optionsTv = new ArrayList<>();
    }

    public LinearLayout getLinearLayout() {
        return linearLayout;
    }

    public void setLinearLayout(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }

    public TextView getId() {
        return id;
    }

    public void setId(TextView id) {
        this.id = id;
    }

    public ArrayList<EditText> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<EditText> options) {
        this.options = options;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProblemView problemView = (ProblemView) o;
        return Objects.equals(linearLayout, problemView.linearLayout) && Objects.equals(id, problemView.id) && Objects.equals(options, problemView.options);
    }

    @Override
    public int hashCode() {
        return Objects.hash(linearLayout, id, options);
    }
}
