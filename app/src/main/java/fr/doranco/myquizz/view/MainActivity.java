package fr.doranco.myquizz.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import fr.doranco.myquizz.controller.IUser;
import fr.doranco.myquizz.controller.UserImpl;
import fr.doranco.myquizz.entity.User;

public class MainActivity extends AppCompatActivity implements IConst {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_CODE = 1;

    private TextView tvTitle, tvUsers;
    private EditText etName;
    private Button btnStart;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Elements
        tvTitle = findViewById(R.id.tvTitle);
        tvUsers = findViewById(R.id.tvUsers);
        etName = findViewById(R.id.etName);
        btnStart = findViewById(R.id.btnStart);

        // Disable btnStart
        btnStart.setEnabled(false);
        user = new User();

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // One line verification
                btnStart.setEnabled(!charSequence.toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        tvTitle.setText("Bonjour, veuillez saisir votre nom:");

        displayScores();
    }

    public void onClickStart(View v) {
        String userName = etName.getText().toString();

        IUser userImpl = new UserImpl();
        user = userImpl.getUserByName(this, userName);

        if (user != null) {
            // User exists

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Cet utilisateur existe déjà, voulez-vous jouer avec ce profil ?")
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Play with existing user
                            Intent intentQuizActivity = new Intent(MainActivity.this, QuizActivity.class);
                            startActivityForResult(intentQuizActivity, REQUEST_CODE);
                        }
                    })
                    .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    })
                    .create()
                    .show();

        } else {
            // New User
            user = userImpl.addUser(this, userName);
            Intent intentQuizActivity = new Intent(MainActivity.this, QuizActivity.class);
            startActivityForResult(intentQuizActivity, REQUEST_CODE);
        }
    }

    public void displayScores() {
        IUser userImpl = new UserImpl();
        List<User> userList = userImpl.getAllUsers(this);

        StringBuilder sb = new StringBuilder();

        userList.forEach(currentUser -> {
            sb.append(currentUser.getName()).append(" - ");
            sb.append(currentUser.getScore()).append("\n");
        });

        tvUsers.setText(sb);
    }

    public void onClickResetScores(View view) {
        IUser userImpl = new UserImpl();
        userImpl.cleanDB(MainActivity.this);
        displayScores();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            int score = data.getIntExtra(EXTRA_SCORE_PARAM, 0);

            IUser userImpl = new UserImpl();
            userImpl.updateScore(this, score, user.getId());
        }
    }
}