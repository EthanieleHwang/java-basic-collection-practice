package collection.studentsystem;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CourseManagementSystem {
  // 使用HashMap->all students,key:student_id,value:student_object
  private final Map<Integer, Student> allStudents = new HashMap<>();

  // 使用HashMap-> all course,key:course_id,value:course_object
  private final Map<Integer, Course> allCourses = new HashMap<>();

  private final Map<Student, Set<Course>> studentCourseMap = new HashMap<>();

  // --学生管理--
  public void addStudent(Student student) {
    allStudents.put(student.getId(), student);
    // 为新学生在选课图中创建一个空的位置
    studentCourseMap.put(student, new HashSet<>());
    System.out.println("成功添加学生：" + student);
  }

  public Student findStudentById(int studentId) {
    return allStudents.get(studentId);
  }

  // ---课程管理---
  public void addCourse(Course course) {
    allCourses.put(course.getId(), course);
    System.out.println("成功添加课程：" + course);
  }

  public Course findCourseById(int courseId) {
    return allCourses.get(courseId);
  }

  // 选课系统
  public boolean registerCourse(int studentId, int courseId) {
    Student student = findStudentById(studentId);
    Course course = findCourseById(courseId);

    if (student == null) {
      System.out.println("错误：未找到ID为" + studentId + "的学生。");
      return false;
    }

    if (course == null) {
      System.out.println("错误：未找到ID为" + courseId + "的课程。");
      return false;
    }

    // 获取该学生的选课集合，并添加新课程。
    Set<Course> courses = studentCourseMap.get(student);
    if (courses.add(course)) {
      System.out.println("学生" + student.getName() + "成功选修课程:" + course.getName());
      return true;
    } else {
      System.out.println("学生" + student.getName() + "已选修过课程：" + course.getName() + "，无需重复选修。");
      return false;

    }
  }

  public Set<Course> getCoursesByStudent(int studentId) {
    Student student = findStudentById(studentId);
    if (student != null) {
      return studentCourseMap.get(student);
    }
    return Collections.emptySet();// 返回一个空的不可变集合，避免返回null;
  }

  public Set<Student> getStudentsByCourse(int coursseId) {
    Course course = findCourseById(coursseId);
    if (course == null) {
      return Collections.emptySet();
    }
    Set<Student> studentsInCourse = new HashSet<>();
    // 遍历选课图的每一个条目
    for (Map.Entry<Student, Set<Course>> entry : studentCourseMap.entrySet()) {
      // 如果该学生的选课集合中包含目标课程
      if (entry.getValue().contains(course)) {
        studentsInCourse.add(entry.getKey());
      }
    }
    return studentsInCourse;
  }

  // --- 报表生成 ---
  /**
   * 生成“选课王”报表，按选课数量降序排列。
   * 
   * @return 排序后的学生列表
   */
  public List<Student> getTopStudentsByCourseCount() {
    // 使用 Stream API 进行排序，更简洁现代
    return studentCourseMap.entrySet().stream()
        // 按选课数量 (Set的大小) 降序排序
        .sorted((entry1, entry2) -> Integer.compare(entry2.getValue().size(), entry1.getValue().size()))
        // 提取出排序后的学生对象
        .map(Map.Entry::getKey)
        // 收集成一个 List
        .collect(Collectors.toList());
  }

  /**
   * 生成“冷门课程”报表，即没有任何学生选修的课程。
   * 
   * @return 冷门课程集合
   */
  public Set<Course> getUnregisteredCourses() {
    // 1. 创建一个包含所有已注册课程的集合
    Set<Course> allRegisteredCourses = new HashSet<>();
    for (Set<Course> courses : studentCourseMap.values()) {
      allRegisteredCourses.addAll(courses);
    }

    // 2. 创建一个包含所有课程的集合
    Set<Course> allAvailableCourses = new HashSet<>(allCourses.values());

    // 3. 从所有课程中移除已注册的课程，剩下的就是冷门课程
    allAvailableCourses.removeAll(allRegisteredCourses);

    return allAvailableCourses;
  }

  // --- 主程序，用于测试 ---
  public static void main(String[] args) {
    CourseManagementSystem system = new CourseManagementSystem();

    System.out.println("--- 1. 初始化数据 ---");
    // 添加学生
    system.addStudent(new Student(101, "张三"));
    system.addStudent(new Student(102, "李四"));
    system.addStudent(new Student(103, "王五"));

    // 添加课程
    system.addCourse(new Course(201, "Java编程"));
    system.addCourse(new Course(202, "数据库原理"));
    system.addCourse(new Course(203, "数据结构"));
    system.addCourse(new Course(204, "操作系统")); // 这个是冷门课程

    System.out.println("\n--- 2. 学生选课 ---");
    system.registerCourse(101, 201); // 张三 选 Java
    system.registerCourse(101, 202); // 张三 选 数据库
    system.registerCourse(101, 203); // 张三 选 数据结构
    system.registerCourse(102, 201); // 李四 选 Java
    system.registerCourse(102, 202); // 李四 选 数据库
    system.registerCourse(103, 201); // 王五 选 Java
    system.registerCourse(101, 201); // 张三 重复选 Java

    System.out.println("\n--- 3. 查询信息 ---");
    System.out.println("张三选修的课程: " + system.getCoursesByStudent(101));
    System.out.println("选修Java编程的学生: " + system.getStudentsByCourse(201));

    System.out.println("\n--- 4. 生成报表 ---");
    // 选课王报表
    List<Student> topStudents = system.getTopStudentsByCourseCount();
    System.out.println("“选课王”排行榜 (按选课数量降序):");
    for (Student s : topStudents) {
      System.out.println("  - " + s.getName() + " 选了 " + system.getCoursesByStudent(s.getId()).size() + " 门课");
    }

    // 冷门课程报表
    Set<Course> unregisteredCourses = system.getUnregisteredCourses();
    System.out.println("\n“冷门课程”列表:");
    if (unregisteredCourses.isEmpty()) {
      System.out.println("  所有课程都有人选修。");
    } else {
      for (Course c : unregisteredCourses) {
        System.out.println("  - " + c.getName());
      }
    }
  }

}
