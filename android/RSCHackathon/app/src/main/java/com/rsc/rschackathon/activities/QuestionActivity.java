package com.rsc.rschackathon.activities;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.rsc.rschackathon.R;
import com.rsc.rschackathon.api.NetworkService;
import com.rsc.rschackathon.api.models.Question;
import com.rsc.rschackathon.fragments.FragmentQuestionTypeOne;
import com.rsc.rschackathon.fragments.FragmentQuestionTypeTwo;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionActivity extends AppCompatActivity {

    public static final String QUESTION_DETAILS = "QUESTION_DETAILS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Quiz name will go here");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        getQuestions();
    }

    private void getQuestions() {
        NetworkService networkService = new NetworkService();
        networkService.getAPI().getQuestion().enqueue(new Callback<Question>() {
            @Override
            public void onResponse(Call<Question> call, Response<Question> response) {
                //TODO HERE HANDLE FRAGMENT CHANGE FOR EVERY TYPE OF QUESTION
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if (response.body().getType_id() == 1) {
                        FragmentQuestionTypeOne questionTypeOne = new FragmentQuestionTypeOne();
                        final Bundle bundle = new Bundle();
                        Question question = response.body();
                        bundle.putParcelable(QUESTION_DETAILS, question);
                        questionTypeOne.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragment_container, questionTypeOne).commit();
                }
                else if (response.body().getType_id() == 2) {
                    FragmentQuestionTypeTwo questionTypeTwo = new FragmentQuestionTypeTwo();
                    final Bundle bundle = new Bundle();
                    Question question = response.body();
                    bundle.putParcelable(QUESTION_DETAILS, question);
                    questionTypeTwo.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragment_container, questionTypeTwo).commit();
                }
            }

            @Override
            public void onFailure(Call<Question> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        showDialogEvent();
    }

    private void showDialogEvent() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.leave_quizz_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button yes = (Button) dialog.findViewById(R.id.yes);
        Button no = (Button) dialog.findViewById(R.id.no);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuestionActivity.this, RecyclerViewActivity.class));
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
