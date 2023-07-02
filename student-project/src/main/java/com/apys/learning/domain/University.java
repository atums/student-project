package com.apys.learning.domain;

public class University {
    private long universityId;
    private String universityName;

    public University() {
    }

    public University(long universityId, String universityName) {
        this.universityId = universityId;
        this.universityName = universityName;
    }

    public long getUniversityId() {
        return universityId;
    }

    public void setUniversityId(long universityId) {
        this.universityId = universityId;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    @Override
    public String toString() {
        return "University{" +
                "universityId=" + universityId +
                ", universityName='" + universityName + '\'' +
                '}';
    }
}
