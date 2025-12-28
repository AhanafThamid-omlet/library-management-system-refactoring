package LMS;
public class BookInfo {
    private String title, subject, author;
    public BookInfo(String t, String s, String a) {
        title = t; subject = s; author = a;
    }
    public String getTitle(){ return title; }
    public String getSubject(){ return subject; }
    public String getAuthor(){ return author; }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setAuthor(String author) {
        this.author = author;
    }




}

