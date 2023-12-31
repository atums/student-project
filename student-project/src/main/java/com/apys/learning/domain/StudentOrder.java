package com.apys.learning.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StudentOrder {
   private long studentOrderId;
   private StudentOrderStatus studentOrderStatus;
   private LocalDateTime studentOrderDate;
   private Adult husband;
   private Adult wife;
   private List<Child> children;
   private String marriageCertificate;
   private LocalDate marriageDate;
   private RegisterOffice marriageOffice;

    public long getStudentOrderId() {
        return studentOrderId;
    }
    public void setStudentOrderId(long studentOrderId) {
        this.studentOrderId = studentOrderId;
    }

    public StudentOrderStatus getStudentOrderStatus() {
        return studentOrderStatus;
    }

    public void setStudentOrderStatus(StudentOrderStatus studentOrderStatus) {
        this.studentOrderStatus = studentOrderStatus;
    }

    public LocalDateTime getStudentOrderDate() {
        return studentOrderDate;
    }

    public void setStudentOrderDate(LocalDateTime studentOrderDate) {
        this.studentOrderDate = studentOrderDate;
    }

    public Adult getHusband() {
        return husband;
    }
    public void setHusband(Adult husband) {
        this.husband = husband;
    }
    public Adult getWife() {
        return wife;
    }
    public void setWife(Adult wife) {
        this.wife = wife;
    }
    public List <Child> getChildren() {
        return children;
    }
    public void addChild(Child child) {
        if(children == null) {
            children = new ArrayList<>(5);
        }
        children.add(child);
    }

    public String getMarriageCertificate() {
        return marriageCertificate;
    }

    public void setMarriageCertificate(String marriageCertificate) {
        this.marriageCertificate = marriageCertificate;
    }

    public LocalDate getMarriageDate() {
        return marriageDate;
    }

    public void setMarriageDate(LocalDate marriageDate) {
        this.marriageDate = marriageDate;
    }

    public RegisterOffice getMarriageOffice() {
        return marriageOffice;
    }

    public void setMarriageOffice(RegisterOffice marriageOffice) {
        this.marriageOffice = marriageOffice;
    }

    @Override
    public String toString() {
        return "StudentOrder{" +
                "studentOrderId=" + studentOrderId +
                ", studentOrderStatus=" + studentOrderStatus +
                ", studentOrderDate=" + studentOrderDate +
                ", husband=" + husband +
                ", wife=" + wife +
                ", children=" + children +
                ", marriageCertificate='" + marriageCertificate + '\'' +
                ", marriageDate=" + marriageDate +
                ", marriageOffice=" + marriageOffice +
                '}';
    }
}
