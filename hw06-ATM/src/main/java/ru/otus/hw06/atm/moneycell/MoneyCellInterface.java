package ru.otus.hw06.atm.moneycell;


import ru.otus.hw06.atm.MoneyValue;

public interface MoneyCellInterface {

    void putMoney(MoneyValue moneyValue, Integer count);

    void getMoney(MoneyValue moneyValue, Integer count);

    int getMoneyValueCount(MoneyValue unitMoneyValue);

}
