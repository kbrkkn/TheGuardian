package ui.android.theguardian;

/**
 * Created by Kubra on 20.11.2016.
 */

public class News {
    //title is news' title
    //section is news' section
    private String title,section;

    //return section
    public String getSection() {
        return section;
    }

    //return title
    public String getTitle() {
        return title;
    }


    //constructs News object with title and section
    public News(String section,String title){
        this.title=title;
        this.section=section;
    }
}
