package collection.noteapp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * 一个简易的命令行记事本应用程序。
 * 这个项目练习旨在巩固对 ArrayList 和 HashSet 的使用。
 */
public class NotePadApp {

  // ArrayList 顺序存储，索引访问
  private static final List<String> notes = new ArrayList<>();
  // Scanner输入
  private static final Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    // welcome and use tutriol
    printWelcomeMessage();

    // main loop 一直收命令，直到user select exit
    while (true) {
      System.out.println("\n请输入命令(add,list,delete,stats,exit):");
      String command = scanner.nextLine().trim().toLowerCase();

      // switch 处理 不同的命令
      switch (command) {
        case "add":
          addNote();
          break;
        case "list":
          listNotes();
          break;
        case "delete":
          deleteNote();
          break;
        case "stats":
          showWordStats();
          break;
        case "exit":
          System.out.println("感谢使用，再见!");
          scanner.close(); // 关闭scanner释放资源。
          return; // 退出main方法，结束程序
        default:
          System.out.println("无效的命令，请重新输入。");
          break;
      }
    }
  }

  /*
   * 打印欢迎信息和命令说明。
   */

  private static void printWelcomeMessage() {
    System.out.println("=====================================");
    System.out.println("    欢迎使用简易命令行记事本！");
    System.out.println("=====================================");
    System.out.println("可用命令:");
    System.out.println("  add    - 添加一条新笔记");
    System.out.println("  list   - 查看所有笔记");
    System.out.println("  delete - 根据序号删除一条笔记");
    System.out.println("  stats  - 统计所有笔记中的不重复单词数");
    System.out.println("  exit   - 退出程序");
    System.out.println("=====================================");
  }

  /**
   * 处理添加笔记的逻辑
   */

  private static void addNote() {
    System.out.println("请输入笔记内容：");
    String noteContent = scanner.nextLine();
    if (noteContent.isEmpty()) {
      System.out.println("笔记内容不能为空。");
      return;
    }
    notes.add(noteContent);
    System.out.println("笔记已成功添加。");
  }

  /**
   * 处理列出所有笔记的逻辑
   */
  private static void listNotes() {
    if (notes.isEmpty()) {
      System.out.println("记事本为空，还没有任何笔记。");
      return;
    }
    System.out.println("\n---所有笔记---");
    // 使用带索引的for循环，以便打印出用户易于理解的序号
    for (int i = 0; i < notes.size(); i++) {
      // 序号为索引加1
      System.out.println((i + 1) + ". " + notes.get(i));
    }
    System.out.println("--- 结束 ---");
  }

  /**
   * 处理删除笔记的逻辑
   */
  private static void deleteNote() {
    if (notes.isEmpty()) {
      System.out.println("记事本为空，无法删除。");
      return;
    }
    System.out.println("请输入要删除的笔记序号：");
    String input = scanner.nextLine();

    try {
      int number = Integer.parseInt(input);
      // 将用户输入的序号（从1开始）-> (从0开始)
      int index = number - 1;

      // 边界检查，确保索引在有效范围内
      if (index >= 0 && index < notes.size()) {
        String removeNote = notes.remove(index);
        System.out.println("成功删除笔记：\"" + removeNote + "\"");
      } else {
        System.out.println("无效的序号。请输入1到" + notes.size() + " 之间的数字。");
      }
    } catch (NumberFormatException e) {
      // 非数字情况
      System.out.println("输入无效，请输入一个有效的数字序号。");
    }
  }

  /**
   * 处理统计不重复单词数量的逻辑。
   */
  private static void showWordStats() {
    if (notes.isEmpty()) {
      System.out.println("记事本为空格，无法进行统计。");
      return;
    }

    // 使用HashSet来存储不重复的单词，利用其自动去重的特性。
    Set<String> uniqueWords = new HashSet<>();

    // 遍历每一条笔记
    for (String note : notes) {
      // 正则表达式 "\\s+"
      // toLowerCase() 用于忽略单词的大小写。
      String[] words = note.toLowerCase().split("\\s+");

      // 遍历分割出的单词数组
      for (String word : words) {
        // 清理单词，移除首尾可能存在的标点符号()
        String cleanedWord = word.replaceAll("[^a-zA-Z0-9]", "");
        if (!cleanedWord.isEmpty()) {
          uniqueWords.add(cleanedWord);
        }
      }
    }

    System.out.println("记事本中共有" + uniqueWords.size() + "个不重复的单词。");
  }

}