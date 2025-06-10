package collection.studentsystem;

import java.util.Objects;

public class Course {
  private final int id;
  private String name;

  public Course(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "Course{" +
        "id=" + id + ",name=" + name + '\'' + '}';
  }

  // 重写equals方法，判断标准是id是否相同
  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Course course = (Course) o;
    return id == course.id;
  }

  // 重写hashCode方法，与equals保持一致
  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
