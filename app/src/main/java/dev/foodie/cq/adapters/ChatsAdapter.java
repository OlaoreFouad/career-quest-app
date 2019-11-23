package dev.foodie.cq.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import dev.foodie.cq.R;
import dev.foodie.cq.models.Message;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.MyViewHolder> {

    private Context context;
    private List<Message> messages;

    public ChatsAdapter(Context context, List<Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_row, parent, false);

        Log.d("CHATS", "onCreateViewHolder: creating the view holder!");

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Message message = messages.get(position);

        holder.author.setText(message.getAuthor());
        holder.author.setTextColor(message.getColor());
        holder.color.setBackgroundColor(message.getColor());
        holder.content.setText(message.getContent());

        DateFormat dateFormat = new SimpleDateFormat("h:mm");
        Date date = new Date(message.getTimeAdded());

        holder.time.setText(dateFormat.format(date));
    }

    @Override
    public int getItemCount() {
        return this.messages.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView author, time, content;
        ImageView color;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            author = itemView.findViewById(R.id.messageAuthor);
            time = itemView.findViewById(R.id.messageTime);
            content = itemView.findViewById(R.id.messageContent);
            color = itemView.findViewById(R.id.messageColor);

        }
    }

}
