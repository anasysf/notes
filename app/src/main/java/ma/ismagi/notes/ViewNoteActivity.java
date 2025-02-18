package ma.ismagi.notes;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import ma.ismagi.notes.R;

public class ViewNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);
        setTitle("View Note");

        TextView tvViewNoteTitle = findViewById(R.id.tvViewNoteTitle);
        TextView tvViewNoteContent = findViewById(R.id.tvViewNoteContent);

        // Retrieve note details passed via Intent extras
        String noteTitle = getIntent().getStringExtra("noteTitle");
        String noteContent = getIntent().getStringExtra("noteContent");

        tvViewNoteTitle.setText(noteTitle);
        tvViewNoteContent.setText(noteContent);
    }
}
