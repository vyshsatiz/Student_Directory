package com.example.vyshalis.studentdirectory;

/**
 * Created by vijisat on 15-02-2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;

import java.util.List;

@SuppressWarnings("rawtypes")
public class RecordArrayAdapte extends ArrayAdapter<String> {

    private LayoutInflater inflater;

    // This would hold the database objects. It could be TeacherDetails or StudentDetails objects
    private List records;

    // Declaration of DAO to interact with corresponding table
    private Dao<TeacherDetails, Integer> teacherDao;

    @SuppressWarnings("unchecked")
    public RecordArrayAdapte(Context context, int resource, List objects, Dao<TeacherDetails, Integer> teacherDao) {
        super(context, resource, objects);

        this.records = objects;
        this.teacherDao = teacherDao;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Reuse the view to make the scroll effect smooth
        if(convertView == null)
            convertView = inflater.inflate(R.layout.list_item, parent, false);

        // If the ListView needs to display the records of StudentDetails objects
        if(records.get(position).getClass().isInstance(new StudentDetails())){
            final StudentDetails studentDetails = (StudentDetails) records.get(position);
            try {
                // Invoking refresh() method to fetch the reference data stored into TeacherDetails table/object
                // Basically, it is an example of Lazy loading. It will join two tables internally only on demand
                teacherDao.refresh(studentDetails.teacher);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ((TextView)convertView.findViewById(R.id.student_name_tv)).setText(studentDetails.studentName);
            ((TextView)convertView.findViewById(R.id.teacher_tv)).setText(studentDetails.teacher.teacherName);
        }
        // If the ListView needs to display the records of TeacherDetails objects
        else{
            final TeacherDetails teacherDetails = (TeacherDetails) records.get(position);
            ((TextView)convertView.findViewById(R.id.student_name_tv)).setText(teacherDetails.teacherName);
            ((TextView)convertView.findViewById(R.id.teacher_tv)).setText(teacherDetails.address);
        }
        return convertView;
    }

}