package ma.ismagi.notes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditNoteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        setTitle("Edit Note");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        TextInputEditText etNoteTitle = findViewById(R.id.etNoteTitle);
        TextInputEditText etNoteContent = findViewById(R.id.etNoteContent);
        FloatingActionButton fabSaveEdit = findViewById(R.id.fab_update_note);

        // Retrieve the note details from the intent extras
        String noteId = getIntent().getStringExtra("noteId");
        String noteTitle = getIntent().getStringExtra("noteTitle");
        String noteContent = getIntent().getStringExtra("noteContent");

        // Prepopulate the fields
        etNoteTitle.setText(noteTitle);
        etNoteContent.setText(noteContent);

        // Set click listener for saving edits
        fabSaveEdit.setOnClickListener(view -> {
            String updatedTitle = etNoteTitle.getText().toString().trim();
            String updatedContent = etNoteContent.getText().toString().trim();

            if (updatedTitle.isEmpty() || updatedContent.isEmpty()) {
                Toast.makeText(EditNoteActivity.this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Prepare data to update
            Map<String, Object> updatedNote = new HashMap<>();
            updatedNote.put("title", updatedTitle);
            updatedNote.put("content", updatedContent);

            // Update Firestore document
            db.collection("notes").document(noteId)
                    .update(updatedNote)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(EditNoteActivity.this, "Note updated successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditNoteActivity.this, MainActivity.class));
                        finish();  // Return to the previous screen
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(EditNoteActivity.this, "Error updating note: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
        });

        FloatingActionButton fabDeleteNote = findViewById(R.id.fab_delete_note);
        fabDeleteNote.setOnClickListener(v -> {
            db.collection("notes")
                    .document(noteId)
                    .delete()
                    .addOnSuccessListener(a -> {
                        Toast.makeText(EditNoteActivity.this, "Note deleted successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditNoteActivity.this, MainActivity.class));
                        finish();  // Return to the previous screen
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(EditNoteActivity.this, "Error deleting the note: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
        });
    }
}