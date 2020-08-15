package com.example.android.newsapp;

/**
 * An {@link News} object contains information related to a single news story.
 */
public class News {

    /** Section Name of the news story */
    private String SectionName;

    /** Date of the news story */
    private String WebPublicationDate;

    /** Title of the news story */
    private String WebTitle;

    /** Author of the news story */
    private String Contributor;

    /** Website URL of the news story */
    private String WebUrl;

    /**
     * Constructs a new {@link News} object.
     * @param sectionName is the section name of the news story
     * @param webPublicationDate is the date of the news story
     * @param webTitle is the title of the news story
     * @param contributor is the author of the news story
     * @param webUrl is the website URL to find more details about the news story
     */
    public News(String sectionName, String webPublicationDate, String webTitle, String contributor, String webUrl) {

        SectionName = sectionName;
        WebPublicationDate = webPublicationDate;
        WebTitle = webTitle;
        Contributor = contributor;
        WebUrl = webUrl;
    }

    /**
     * Returns the section name of the news story.
     */
    public String getSectionName() {
        return SectionName;
    }

    /**
     * Returns the section name of the news story.
     */
    public String getWebPublicationDate() {
        return WebPublicationDate;
    }

    /**
     * Returns the title of the news story.
     */
    public String getWebTitle() {
        return WebTitle;
    }

    /**
     * Returns the author of the news story.
     */
    public String getContributor() {
        return Contributor;
    }

    /**
     * Returns the website URL to find more information about the news story.
     */
    public String getWebUrl() {
        return WebUrl;
    }
}
