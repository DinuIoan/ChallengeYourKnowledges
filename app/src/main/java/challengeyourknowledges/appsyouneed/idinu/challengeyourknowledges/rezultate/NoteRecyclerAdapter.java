package challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.rezultate;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.R;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model.Nota;

public class NoteRecyclerAdapter extends RecyclerView.Adapter<NoteRecyclerAdapter.MyViewHolder> {
    private List<Nota> note;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView notaTextView;
        public TextView dataTextView;

        public MyViewHolder(LinearLayout v) {
            super(v);
            notaTextView = v.findViewById(R.id.nota);
            dataTextView = v.findViewById(R.id.data);
        }
    }

    public NoteRecyclerAdapter(List<Nota> note) {
        this.note = note;
    }

    @Override
    public NoteRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_recycler_row, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(note.get(position).getTimestamp());
        holder.notaTextView.setText("" + note.get(position).getNota());
        holder.dataTextView.setText(calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH)
                + "-" + calendar.get(Calendar.DAY_OF_MONTH)
                + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
    }

    @Override
    public int getItemCount() {
        return note.size();
    }
}
