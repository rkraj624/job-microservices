package com.job;

import com.job.clients.CompanyClient;
import com.job.clients.ReviewClient;
import com.job.dtos.JobWithCompanyAndReviewDTO;
import com.job.entity.Company;
import com.job.entity.Job;
import com.job.entity.Review;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobService{
    private static String BASIC_URL="http://COMPANY-SERVICE:8080/companies/";
    private JobRepository jobRepository;
    private RestTemplate restTemplate;
    private CompanyClient companyClient;
    private ReviewClient reviewClient;

    public JobService(JobRepository jobRepository, RestTemplate restTemplate, CompanyClient companyClient, ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.restTemplate = restTemplate;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    
    public List<JobWithCompanyAndReviewDTO> findAll() {
        List<JobWithCompanyAndReviewDTO> jobWithCompanyAndReviewDTOList = new ArrayList<>();
        jobRepository.findAll().forEach(job -> {
            Company company = getCompany(job);
            List<Review> reviewList = reviewClient.getCompanyById(job.getCompanyId());
            jobWithCompanyAndReviewDTOList.add(new JobWithCompanyAndReviewDTO(company, job, reviewList));
        });
        return jobWithCompanyAndReviewDTOList;
    }

    public boolean createJob(Job job) {
        try {
            Company company = getCompany(job);
            if (company != null) {
                jobRepository.save(job);
                return true;
            }
            return false;
        }catch (Exception exception){
            exception.printStackTrace();
            return false;
        }
    }

    private Company getCompany(Job job){
        return companyClient.getCompanyById(job.getCompanyId());
    }


    public Job getJobById(String id) {
        return jobRepository.findById(id).orElse(null);
    }

    public boolean deleteJobById(String id) {
        try{
            boolean job = jobRepository.existsById(id);
            if(job){
                jobRepository.deleteById(id);
                return true;
            }
            return false;
        }catch (Exception exception){
            exception.printStackTrace();
            return false;
        }
    }
    
    public boolean updateJob(String id, Job job) {
        try {
            Job dbJob = jobRepository.findById(id).orElse(null);
            if (dbJob != null) {
                dbJob.setDescription(job.getDescription());
                dbJob.setLocation(job.getLocation());
                dbJob.setTitle(job.getTitle());
                dbJob.setMaxSalary(dbJob.getMaxSalary());
                dbJob.setMinSalary(job.getMinSalary());
                jobRepository.save(dbJob);
                return true;
            }
            return false;
        }catch (Exception exception){
            exception.printStackTrace();
            return false;
        }

    }
}
