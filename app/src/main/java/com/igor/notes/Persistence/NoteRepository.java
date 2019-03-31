package com.igor.notes.Persistence;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.igor.notes.Models.Note;
import com.igor.notes.async.DeleteAsyncTask;
import com.igor.notes.async.InsertAsyncTask;
import com.igor.notes.async.UpdateAsyncTask;

import java.util.List;

public class NoteRepository {

    private NoteDatabase mNoteDatabase;

    public NoteRepository(Context context){
        mNoteDatabase = NoteDatabase.getInstance(context);
    }

    public void insertNoteTask(Note note){
        new InsertAsyncTask(mNoteDatabase.getNoteDao()).execute(note);

    }
    public void updateNote(Note note){
        new UpdateAsyncTask(mNoteDatabase.getNoteDao()).execute(note);
    }
    public LiveData<List<Note>> retrievNoteTask(){
        return mNoteDatabase.getNoteDao().getNotes();
    }

    public void deleteNote(Note note) {
        new DeleteAsyncTask(mNoteDatabase.getNoteDao()).execute(note);
        
    }
}
