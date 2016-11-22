package ui.android.theguardian;

/**
 * Created by Kubra on 20.11.2016.
 */

public class News {
    //title is news' title
    //section is news' section
    private String title;
    private String section;

    public String getUrl() {
        return url;
    }

    private String url;

    //return section
    public String getSection() {
        return section;
    }

    //return title
    public String getTitle() {
        return title;
    }


    //constructs News object with title and section
    public News(String section,String title,String url){
        this.title=title;
        this.section=section;
        this.url=url;
    }
}
