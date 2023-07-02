package com.apys.learning.domain.register;

import java.util.ArrayList;
import java.util.List;

public class AnswerCityRegister {
    private List<AnswerCityRegisterItem> itemList;

    public List<AnswerCityRegisterItem> getItemList() {
        return itemList;
    }

    public void addItem(AnswerCityRegisterItem item) {
        if(itemList == null) {
            itemList = new ArrayList<>(10);
        }
        itemList.add(item);
    }
}
