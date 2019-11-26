package ru.otus.hw06.ATM.MoneyCell;


import ru.otus.hw06.ATM.MoneyValue;

public interface MoneyCellInterface {

    void putMoney(MoneyValue moneyValue, Integer count);

    void getMoney(MoneyValue moneyValue, Integer count);

    int getMoneyValueCount(MoneyValue unitMoneyValue);

}
