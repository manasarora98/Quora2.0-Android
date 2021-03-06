package com.project.quora20.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.quora20.R;
import com.project.quora20.ViewOrganisation;
import com.project.quora20.entity.Organization;
import com.project.quora20.entity.Question;

import java.util.List;

public class MyQuestionAdapter extends RecyclerView.Adapter<MyQuestionAdapter.MyQuestionViewHolder> {

    private List<Question> userQuestionList;


    public static class MyQuestionViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public MyQuestionViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.que_userQuestionText);
        }
    }

    public MyQuestionAdapter(List<Question> userQuestionList) {
        this.userQuestionList = userQuestionList;
    }


    @NonNull
    @Override
    public MyQuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_recycler_items, parent, false);
        MyQuestionViewHolder myQuestionViewHolder = new MyQuestionViewHolder(view);
        return myQuestionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyQuestionViewHolder holder, int position) {
//        int index = userQuestionList.get(position);
//        holder.textView.setText(String.valueOf(index));

    }

    @Override
    public int getItemCount() {
        if (userQuestionList != null)
            return userQuestionList.size();

        return 0;
    }

}

