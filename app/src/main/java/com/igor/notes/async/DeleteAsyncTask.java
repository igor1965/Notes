package com.igor.notes.async;

import android.os.AsyncTask;
import android.util.Log;

import com.igor.notes.Models.Note;
import com.igor.notes.Persistence.NoteDao;

public class DeleteAsyncTask extends AsyncTask<Note,Void,Void> {
    private static final String TAG = "InsertAsyncTask";

    private NoteDao mNoteDao;

    public DeleteAsyncTask(NoteDao dao) {
        mNoteDao = dao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        Log.d(TAG, "doInBackground: thread" + Thread.currentThread().getName());
        mNoteDao.delete(notes);
        return null;
    }
}
