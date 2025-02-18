package ma.ismagi.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

import ma.ismagi.notes.entities.Note;

public class AddNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.activity_add_note);

//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setTitle("Add New Note");

        FloatingActionButton fabSaveAction = findViewById(R.id.fab_save_note);

        fabSaveAction.setOnClickListener(view -> {
            TextInputEditText etNoteTitle = findViewById(R.id.etNoteTitle);
            TextInputEditText etNoteContent = findViewById(R.id.etNoteContent);

            String noteTitle = etNoteTitle.getText().toString().trim();
            String noteContent = etNoteContent.getText().toString().trim();

            if (noteTitle.isEmpty() || noteContent.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                return;
            }

            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseUser user = auth.getCurrentUser();
            Note note = new Note(user.getUid(), noteTitle, noteContent, new Date());

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("notes")
                    .add(note)
                    .addOnSuccessListener(ref -> {
                        Toast.makeText(this, "Note saved successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddNoteActivity.this, HomeActivity.class));
                        finish();
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show());

            finish();
        });
    }
}