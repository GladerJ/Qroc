package com.example.qroc;

import android.content.*;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.qroc.handler.DeleteQuestionnaireAsyncTask;
import com.example.qroc.handler.ShowQuestionnaireAsyncTask;
import com.example.qroc.pojo.User;
import com.example.qroc.pojo.behind.Questionnaire;
import com.example.qroc.util.MMKVUtils;

import java.util.ArrayList;
import java.util.List;

public class ShowQuestionnaire extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Questionnaire> questionnaireList;
    private QuestionnaireAdapter adapter;
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_questionnaire);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        questionnaireList = new ArrayList<>();
        adapter = new QuestionnaireAdapter(questionnaireList);
        recyclerView.setAdapter(adapter);
        User user = new User();
        MMKVUtils.init(getFilesDir().getAbsolutePath() + "/tmp");
        user.setUsername(MMKVUtils.getString("token"));
        ShowQuestionnaireAsyncTask showQuestionnaireAsyncTask = new ShowQuestionnaireAsyncTask(ShowQuestionnaire.this,user);
        showQuestionnaireAsyncTask.execute();

    }

    public void load(List<Questionnaire> questionnaireList) {
        this.questionnaireList.clear(); // 清空原有数据
        this.questionnaireList.addAll(questionnaireList); // 添加新的数据
        adapter.notifyDataSetChanged(); // 通知适配器数据已更新
    }




    private class QuestionnaireAdapter extends RecyclerView.Adapter<QuestionnaireAdapter.ViewHolder> {

        private List<Questionnaire> dataList;

        public QuestionnaireAdapter(List<Questionnaire> dataList) {
            this.dataList = dataList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_questionnaire, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Questionnaire questionnaire = dataList.get(position);

            // 设置问卷ID和名称
            holder.idTextView.setText("ID:" + questionnaire.getQuestionnaireId() + "");
            holder.nameTextView.setText("标题:" + questionnaire.getTitle());

            // 设置按钮点击事件
            holder.modifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 处理修改按钮点击事件
                   // Toast.makeText(ShowQuestionnaire.this, "点击了修改按钮，问卷ID：" + questionnaire.getId(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ShowQuestionnaire.this,CreateQuestionnaire.class);
                    intent.putExtra("flag",questionnaire.getQuestionnaireId() + "");
                    startActivity(intent);

                }
            });

            holder.viewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 处理查看按钮点击事件
                   // Toast.makeText(ShowQuestionnaire.this, "点击了查看按钮，问卷ID：" + questionnaire.getId(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ShowQuestionnaire.this,SurveyResultsActivity.class);
                    intent.putExtra("ID",questionnaire.getQuestionnaireId() + "");
                    startActivity(intent);
                }
            });

            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 处理删除按钮点击事件
                    //Toast.makeText(ShowQuestionnaire.this, "点击了删除按钮，问卷ID：" + questionnaire.getId(), Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(ShowQuestionnaire.this);
                    builder.setTitle("确认删除");
                    builder.setMessage("确定要删除吗？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 在此处处理用户点击确定的操作
                            // 执行删除操作的代码
                            MMKVUtils.init(getFilesDir().getAbsolutePath() + "/tmp");
                            String token = MMKVUtils.getString("token");
                            questionnaireList.remove(questionnaire);
                            adapter.notifyDataSetChanged();
                            questionnaire.setUsername(token);
                            DeleteQuestionnaireAsyncTask deleteQuestionnaireAsyncTask = new DeleteQuestionnaireAsyncTask(ShowQuestionnaire.this,questionnaire);
                            deleteQuestionnaireAsyncTask.execute();
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 在此处处理用户点击取消的操作
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.setCancelable(false);
                    dialog.show();
                }
            });

            holder.copyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 在此处处理按钮点击事件
                    String buttonText = questionnaire.getQuestionnaireId() + "";

                    // 获取剪贴板管理器
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

                    // 创建剪贴板数据
                    ClipData clip = ClipData.newPlainText("ID", buttonText);

                    // 将数据复制到剪贴板
                    clipboard.setPrimaryClip(clip);

                    // 显示复制成功的提示消息
                    Toast.makeText(getApplicationContext(), "已复制到剪贴板", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView idTextView;
            private TextView nameTextView;
            private Button modifyButton;
            private Button viewButton;
            private Button deleteButton;
            private Button copyButton;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                idTextView = itemView.findViewById(R.id.idTextView);
                nameTextView = itemView.findViewById(R.id.nameTextView);
                modifyButton = itemView.findViewById(R.id.modifyButton);
                viewButton = itemView.findViewById(R.id.viewButton);
                deleteButton = itemView.findViewById(R.id.deleteButton);
                copyButton = itemView.findViewById(R.id.copyButton);
            }
        }
    }
}