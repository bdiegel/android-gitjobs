package com.bdiegel.ocl.gitjobs.rest;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;


public class Job implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeLong(createdDate != null ? createdDate.getTime() : -1);
        dest.writeString(this.title);
        dest.writeString(this.location);
        dest.writeString(this.type);
        dest.writeString(this.htmlDescription);
        dest.writeString(this.applyUrl);
        dest.writeString(this.companyName);
        dest.writeString(this.companyUrl);
        dest.writeString(this.companyLogo);
        dest.writeString(this.jobUrl);
    }

    public Job() {
    }

    protected Job(Parcel in) {
        this.id = in.readString();
        long tmpCreatedDate = in.readLong();
        this.createdDate = tmpCreatedDate == -1 ? null : new Date(tmpCreatedDate);
        this.title = in.readString();
        this.location = in.readString();
        this.type = in.readString();
        this.htmlDescription = in.readString();
        this.applyUrl = in.readString();
        this.companyName = in.readString();
        this.companyUrl = in.readString();
        this.companyLogo = in.readString();
        this.jobUrl = in.readString();
    }

    public static final Parcelable.Creator<Job> CREATOR = new Parcelable.Creator<Job>() {
        public Job createFromParcel(Parcel source) {
            return new Job(source);
        }

        public Job[] newArray(int size) {
            return new Job[size];
        }
    };
}
