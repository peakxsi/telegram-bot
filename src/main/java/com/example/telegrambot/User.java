package com.example.telegrambot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class User {
    private int stan; //0=no, 1=search, 2=addName, 3=addCard, 4=searchPackCBD, 5=searchCardCBD, 6=searchPackToPlayCBD, 7=changeNameCBD
    private ArrayList<PackCard> packs;
    private PackCard choosedPack;
    private int choosedCard;

    User() {
        stan = 0;
        packs = new ArrayList<>();
        choosedPack = null;
        choosedCard = -1;
    }

    public boolean isChoosedPack() {
        if (choosedPack != null) {
            return true;
        } else {
            return false;
        }
    }

    public PackCard getChoosedPack() {
        return choosedPack;
    }

    public void setChoosedPack(PackCard choosedPack) {
        this.choosedPack = choosedPack;
    }

    public int getChoosedCard() {
        return choosedCard;
    }

    public void setChoosedCard(int choosedCard) {
        this.choosedCard = choosedCard;
    }

    public void addPack(PackCard newPack) {
        packs.add(newPack);
    }

    public PackCard getLastPack() {
        return packs.get(packs.size()-1);
    }

    public void removeLastPack() {
        packs.remove(packs.size()-1);
    }

    public List<String> getPacksNames() {
        return packs.stream()
                .map(PackCard::getName)
                .collect(Collectors.toList());
    }

    public ArrayList<PackCard> getPacks() {
        return packs;
    }

    public int getStan() {
        return stan;
    }

    public void setStan(int stan) {
        this.stan = stan;
    }
}
