package com.igor.notes;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.igor.notes.Adapters.NotesRecyclerAdapter;
import com.igor.notes.Models.Note;
import com.igor.notes.Persistence.NoteRepository;
import com.igor.notes.Util.VerticalSpacingItemDecorator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NotesRecyclerAdapter.OnNoteListener,View.OnClickListener {

    private static final String TAG = "MainActivity";

    private RecyclerView mRecyclerView;

    private ArrayList<Note> mNotes = new ArrayList<>();
    private NotesRecyclerAdapter mNotesRecyclerAdapter;
    private NoteRepository mNoteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerView);

        findViewById(R.id.fab).setOnClickListener(this);

        mNoteRepository = new NoteRepository(this);

        initRecyclerView();
        retrieveNotes();
       // insertFakeNotes();

        Log.d(TAG, "onCreate: thread: " + Thread.currentThread().getName());

        setSupportActionBar((Toolbar)findViewById(R.id.notes_toolbar));
        setTitle("Notes");
    }
    private void retrieveNotes(){
        mNoteRepository.retrievNoteTask().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                if(mNotes.size() > 0){
                    mNotes.clear();
                }
                if (notes != null){
                    mNotes.addAll(notes);
                }
                mNotesRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }

    private void insertFakeNotes(){
        for (int i = 0;i < 1000; i++){
            Note note = new Note();
            note.setTitle("title" + i);
            note.setContent("content" +i);
            note.setTimestamp("jan 2018");
            mNotes.add(note);

        }
        mNotesRecyclerAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        VerticalSpacingItemDecorator verticalSpacingItemDecorator = new VerticalSpacingItemDecorator(10);
        mRecyclerView.addItemDecoration(verticalSpacingItemDecorator);
        new ItemTouchHelper(itemTochHelperCallback).attachToRecyclerView(mRecyclerView);
        mNotesRecyclerAdapter = new NotesRecyclerAdapter(mNotes,this);
        mRecyclerView.setAdapter(mNotesRecyclerAdapter);

    }

    @Override
    public void onNoteClick(int position) {
        Log.d(TAG, "onNoteClick: clicked" + position);

        Intent intent = new Intent(this,NoteActivity.class);
        intent.putExtra("selected_note",mNotes.get(position));
        startActivity(intent);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,NoteActivity.class);
        startActivity(intent);
    }
    private  void deleteNote(Note note){
        mNotes.remove(note);
        mNotesRecyclerAdapter.notifyDataSetChanged();

        mNoteRepository.deleteNote(note);
    }
    private ItemTouchHelper.SimpleCallback itemTochHelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            deleteNote(mNotes.get(viewHolder.getAdapterPosition()));

        }
    };
}
