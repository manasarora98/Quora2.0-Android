package com.project.quora20.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.project.quora20.R;
import com.project.quora20.entity.Question;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private List<Question> questionList;
    private QuestionCommunication questionCommunication;

    public HomeAdapter(List<Question>questionList,QuestionCommunication questionCommunication){
        this.questionList=questionList;
        this.questionCommunication=questionCommunication;
    }
    public class HomeViewHolder extends RecyclerView.ViewHolder{
        TextView questionBody;
        TextView questionLike;
        TextView questionDislike;

        public HomeViewHolder(View view){
            super(view);
            this.questionBody =view.findViewById(R.id.userQuestionText);
            this.questionDislike=view.findViewById(R.id.dislikesCount);
            this.questionLike=view.findViewById(R.id.likesCount);

        }
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.home_recycler_items, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder,final int position){
        holder.questionBody.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionCommunication.onClick(questionList.get(position));
            }
        });
        holder.questionBody.setText(questionList.get(position).getQuestionBody());
        holder.questionLike.setText(questionList.get(position).getLikeCount());
        holder.questionDislike.setText(questionList.get(position).getDisLikeCount());
    }

    @Override
    public int getItemCount() {
        if(questionList!=null){
            return questionList.size();
        }
        return 0;
    }
    public interface QuestionCommunication{
        void onClick(Question question);
    }
}



