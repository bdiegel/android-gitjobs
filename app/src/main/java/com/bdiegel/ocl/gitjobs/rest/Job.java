package com.bdiegel.ocl.gitjobs.rest;

import com.google.gson.annotations.SerializedName;

import java.util.Date;


public class Job {

    @SerializedName("id")
    public String id;

    @SerializedName("created_at")
    public Date createdDate;

    @SerializedName("title")
    public String title;

    @SerializedName("location")
    public String location;

    @SerializedName("type")
    public String type;

    @SerializedName("description")
    public String htmlDescription;

    @SerializedName("how_to_apply")
    public String applyUrl;

    @SerializedName("company")
    public String companyName;

    @SerializedName("company_url")
    public String companyUrl;

    @SerializedName("company_logo")
    public String companyLogo;

    @SerializedName("url")
    public String jobUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHtmlDescription() {
        return htmlDescription;
    }

    public void setHtmlDescription(String htmlDescription) {
        this.htmlDescription = htmlDescription;
    }

    public String getApplyUrl() {
        return applyUrl;
    }

    public void setApplyUrl(String applyUrl) {
        this.applyUrl = applyUrl;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public void setCompanyUrl(String companyUrl) {
        this.companyUrl = companyUrl;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getJobUrl() {
        return jobUrl;
    }

    public void setJobUrl(String jobUrl) {
        this.jobUrl = jobUrl;
    }
}
