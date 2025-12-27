package LMS;
public class BookInfo {
    private String title, subject, author;
    public BookInfo(String t, String s, String a) {
        title = t; subject = s; author = a;
    }
    public String getTitle(){ return title; }
    public String getSubject(){ return subject; }
    public String getAuthor(){ return author; }
}

