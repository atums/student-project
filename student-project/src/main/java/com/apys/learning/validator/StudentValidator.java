package com.apys.learning.validator;

import com.apys.learning.domain.AnswerStudent;
import com.apys.learning.domain.StudentOrder;

public class StudentValidator {
    public AnswerStudent checkStudent(StudentOrder studentOrder) {
        System.out.println("Student chek is running");
        AnswerStudent as =new AnswerStudent();
        return as;
    }
}
