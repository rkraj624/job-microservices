package com.job.dtos;

import com.job.entity.Company;
import com.job.entity.Job;
import com.job.entity.Review;

import java.util.List;

public class JobWithCompanyAndReviewDTO {
    private Company company;
    private Job job;
    private List<Review> reviewList;

    public JobWithCompanyAndReviewDTO(Company company, Job job, List<Review> reviewList) {
        this.company = company;
        this.job = job;
        this.reviewList = reviewList;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }
}
