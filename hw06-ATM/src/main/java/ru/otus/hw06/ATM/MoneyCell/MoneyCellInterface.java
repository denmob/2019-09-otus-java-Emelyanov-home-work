package ru.otus.hw06.ATM.MoneyCell;

import ru.otus.hw06.ATM.ATMImp;

public interface MoneyCellInterface {

    void putMoney(ATMImp.MoneyValue moneyValue, Integer count);

    void getMoney(ATMImp.MoneyValue moneyValue, Integer count);

    int getMoneyValueCount(Integer unitMoneyValue);

}
