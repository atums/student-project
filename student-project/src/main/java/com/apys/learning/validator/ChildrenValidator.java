package com.apys.learning.validator;

import com.apys.learning.domain.AnswerChildren;
import com.apys.learning.domain.StudentOrder;

public class ChildrenValidator {
    public AnswerChildren checkChildren(StudentOrder studentOrder) {
        System.out.println("Children chek is running");
        AnswerChildren ac = new AnswerChildren();
        return ac;
    }
}
