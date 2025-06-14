package collection.studentsystem;

import java.util.Objects;

public class Student {
  private final int id;
  private String name;

  public Student(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Student{" +
        "id=" + id +
        ",name='" + name +
        '\'' +
        '}';
  }

  // 重写equals方法，判断两个Student对象是否相等的标准是id是否相同。
  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Student student = (Student) o;
    return id == student.id;
  }

  // 重写hashCode方法，id相同==》hashCode相同
  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

}
