package ma.ismagi.notes;

import androidx.annotation.NonNull;

import ma.ismagi.notes.entities.Note;

public interface OnNoteClickListener {
    void onNoteClick(@NonNull Note note);
}
