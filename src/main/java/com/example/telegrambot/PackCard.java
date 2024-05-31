package com.example.telegrambot;

import jakarta.validation.constraints.Max;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PackCard {
    private String name;
    private ArrayList<String> list1, list2, currentList1, currentList2;
    private int curNum, prevNum;
    private Random rand;
    private boolean privacy;

    public PackCard() {
        name = "PackCard";
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        currentList1 = new ArrayList<>();
        currentList2 = new ArrayList<>();
        rand = new Random();
        privacy = false;
    }

    public int size() {
        return currentList1.size();
    }

    public boolean isPrivate() {
        return privacy;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean getPrivacy() {
        return this.privacy;
    }

    public void setPrivacy(boolean privacy) {
        this.privacy = privacy;
    }

    public void add(String item1, String item2) {
        list1.add(item1);
        list2.add(item2);
        currentList1.add(item1);
        currentList2.add(item2);
    }

    public void remove(int index) {
        if (index >= 0 && index < currentList1.size()) {
            list1.remove(currentList1.get(index));
            list2.remove(currentList2.get(index));
            currentList1.remove(index);
            currentList2.remove(index);
        }
    }

    public void removeCurTemp() {
        int index = curNum;
        if (index >= 0 && index < currentList1.size()) {
            currentList1.remove(index);
            currentList2.remove(index);
        }
    }

    public void next() {
        prevNum = curNum;
        curNum = rand.nextInt(currentList1.size());
    }

    public String getFirstSide() {
        return currentList1.get(curNum);
    }

    public String getSecondSide() {
        return currentList2.get(curNum);
    }

    public void renew() {
        currentList1 = new ArrayList<>(list1);
        currentList2 = new ArrayList<>(list2);
    }

    public List<String> getAllList() {
        return IntStream.range(0, currentList1.size())
                .mapToObj(i -> currentList1.get(i) + " . " + currentList2.get(i))
                .collect(Collectors.toList());
    }

    public PackCard copy() {
        PackCard newPack = new PackCard();
        for (int i = 0; i < list1.size(); i++) {
            newPack.add(new String(list1.get(i)), new String(list2.get(i)));
        }
        return newPack;
    }

    public void changeSides() {
        ArrayList<String> temp = list1;
        list1 = list2;
        list2 = temp;

        temp = currentList1;
        currentList1 = currentList2;
        currentList2 = temp;
    }
}
