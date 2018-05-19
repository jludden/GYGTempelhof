package me.jludden.gygtempelhof.data.model;

import java.util.List;

public class ReviewResponse {


    private Boolean status;

    private Integer totalReviewsComments;

    private List<Review> data = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getTotalReviewsComments() {
        return totalReviewsComments;
    }

    public void setTotalReviewsComments(Integer totalReviewsComments) {
        this.totalReviewsComments = totalReviewsComments;
    }

    public List<Review> getData() {
        return data;
    }

    public void setData(List<Review> data) {
        this.data = data;
    }

}