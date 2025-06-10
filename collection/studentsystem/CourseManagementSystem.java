package collection.studentsystem;

import java.util.HashMap;
import java.util.Map;

public class CourseManagementSystem {
  // 使用HashMap->all students,key:student_id,value:student_object
  private final Map<Integer, Student> allStudents = new HashMap<>();

  // 使用HashMap-> all course,key:course_id,value:course_object
  private final Map<Integer, Course> allCourses = new HashMap<>();
}
