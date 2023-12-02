package com.example.qroc.pojo;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class Problem {
    private LinearLayout linearLayout;
    private TextView id;
    private ArrayList<EditText> options;
    private ArrayList<TextView> optionsTv;

    public Problem(LinearLayout linearLayout, TextView id, ArrayList<EditText> options, ArrayList<TextView> optionsTv) {
        this.linearLayout = linearLayout;
        this.id = id;
        this.options = options;
        this.optionsTv = optionsTv;
    }

    public static Problem create(LinearLayout linearLayout, TextView id){
        Problem problem = new Problem();
        problem.setId(id);
        problem.setLinearLayout(linearLayout);
        return problem;
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

    public Problem(LinearLayout linearLayout, TextView id, ArrayList<EditText> options) {
        this.linearLayout = linearLayout;
        this.id = id;
        this.options = options;
    }

    public Problem() {
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
        Problem problem = (Problem) o;
        return Objects.equals(linearLayout, problem.linearLayout) && Objects.equals(id, problem.id) && Objects.equals(options, problem.options);
    }

    @Override
    public int hashCode() {
        return Objects.hash(linearLayout, id, options);
    }
}
