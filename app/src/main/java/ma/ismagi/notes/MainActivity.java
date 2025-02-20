package ma.ismagi.notes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import ma.ismagi.notes.auth.LoginActivity;
import ma.ismagi.notes.entities.Note;

public class MainActivity extends AppCompatActivity {
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private final OnNoteClickListener onNoteClickListener = note -> {
        Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
        intent.putExtra("noteTitle", note.getTitle());
        intent.putExtra("noteContent", note.getContent());
        intent.putExtra("noteId", note.getId());
        startActivity(intent);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        FloatingActionButton fabAddNote = findViewById(R.id.fab_add_note);
        fabAddNote.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AddNoteActivity.class)));

        FloatingActionButton fabLogout = findViewById(R.id.fab_logout);
        fabLogout.setOnClickListener(view -> {
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerViewNotes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        NoteAdapter noteAdapter = new NoteAdapter(new ArrayList<>(), onNoteClickListener);
        recyclerView.setAdapter(noteAdapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            db.collection("notes")
                    .whereEqualTo("userId", user.getUid())
                    .orderBy("timestamp", Query.Direction.DESCENDING)
                    .addSnapshotListener((value, error) -> {
                        if (error != null) {
                            Log.e("Home Activity", "Listen failed", error);
                            return;
                        }

                        if (value != null) {
                            List<Note> noteList = new ArrayList<>();
                            for (DocumentSnapshot doc : value.getDocuments()) {
                                Note note = doc.toObject(Note.class);
                                note.setId(doc.getId());
                                noteList.add(note);
                            }

                            noteAdapter.setNoteList(noteList);
                        }
                    });
        }
    }
}