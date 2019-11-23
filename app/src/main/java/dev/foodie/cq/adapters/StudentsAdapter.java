package dev.foodie.cq.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import dev.foodie.cq.R;
import dev.foodie.cq.models.Student;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.MyViewHolder> {

    private Context context;
    private List<Student> students;

    public StudentsAdapter(Context context, List<Student> students) {
        this.context = context;
        this.students = students;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Student student = students.get(position);

        holder.name.setText(student.getName());
        holder.course.setText("Course: " + student.getCourse());

        holder.skills.setText("Skills: " + TextUtils.join(", ", student.getSkills()));
        holder.aptech.setText("About Aptech: " + student.getAptech());
        holder.about.setText(student.getAbout());

        Picasso.get()
                .load(student.getPhotoURL())
                .into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView name, course, skills, aptech, about;
        private ImageView photo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            course = itemView.findViewById(R.id.course);
            skills = itemView.findViewById(R.id.skills);
            aptech = itemView.findViewById(R.id.aptech);
            about = itemView.findViewById(R.id.about);
            photo = itemView.findViewById(R.id.photo);
        }
    }

}
