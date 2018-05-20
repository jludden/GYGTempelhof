package me.jludden.gygtempelhof.data.model;


public class Review {


    private Integer review_id;

    private String rating;

    private String title;

    private String message;

    private String author;

    private Boolean foreignLanguage;

    private String date;

    private DateUnformatted dateUnformatted;

    private String languageCode;

    private String travelerType;

    private String reviewerName;

    private String reviewerCountry;

    public Integer getReview_id() {
        return review_id;
    }

    public void setReview_id(Integer review_id) {
        this.review_id = review_id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return (title == null) ? "" : title;
    }

    public void setTitle(String title) {
        this.title = (title == null) ? "" : title ;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Boolean getForeignLanguage() {
        return foreignLanguage;
    }

    public void setForeignLanguage(Boolean foreignLanguage) {
        this.foreignLanguage = foreignLanguage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public DateUnformatted getDateUnformatted() {
        return dateUnformatted;
    }

    public void setDateUnformatted(DateUnformatted dateUnformatted) {
        this.dateUnformatted = dateUnformatted;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getTravelerType() {
        return travelerType;
    }

    public void setTravelerType(String travelerType) {
        this.travelerType = travelerType;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getReviewerCountry() {
        return reviewerCountry;
    }

    public void setReviewerCountry(String reviewerCountry) {
        this.reviewerCountry = reviewerCountry;
    }

    public boolean isValid() {
        Float stars = Float.parseFloat(rating);
        return !title.isEmpty() && !message.isEmpty() && stars > 0 && stars <= 5;
    }
}