package edu.pg.DiA.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import edu.pg.DiA.R;
import edu.pg.DiA.database.AppDatabase;
import edu.pg.DiA.holders.JournalViewHolder;
import edu.pg.DiA.interfaces.EventListener;
import edu.pg.DiA.models.Medicine;
import edu.pg.DiA.models.Note;
import edu.pg.DiA.ui.journal.NoteFragment;


public class JournalAdapter extends RecyclerView.Adapter<JournalViewHolder>{

    private Context context;
    private EventListener listener;
    private List<Note> changeNotes = null;
    private Note note;

    // Provide a suitable constructor (depends on the kind of dataset)
    public JournalAdapter(Context context, EventListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setNotes(List<Note> changeNotes) {
        this.changeNotes = changeNotes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public JournalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_note, parent, false);

        JournalViewHolder journalViewHolder = new JournalViewHolder(v);
        return journalViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull JournalViewHolder holder, int position) {

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.noteTitle.setText(changeNotes.get(position).name);

        String strDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(changeNotes.get(position).date);
        holder.date.setText(strDate);

        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, v);
                popup.inflate(R.menu.note_menu_item);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        note = changeNotes.get(position);

                        int id = item.getItemId();
                        if(id == R.id.note_edit) {

                            return true;
                        }
                        else if(id == R.id.note_delete) {
                            AppDatabase.getInstance(context).noteDao().delete(note);
                            return true;
                        }
                        else {
                            return false;
                        }
                    }
                });
                popup.show();
            }
        });

        holder.noteListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note = changeNotes.get(position);
                listener.onEvent(new NoteFragment());
            }
        });
    }

    @Override
    public int getItemCount() {
        if(changeNotes != null) {
            return changeNotes.size();
        }
        return 0;
    }

    public Note getNote() {
        return note;
    }
}
