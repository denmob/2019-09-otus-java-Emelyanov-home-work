package ru.otus.hw07.ATM.MoneyCell;

import ru.otus.hw07.ATM.MoneyValue;
import java.util.Map;

public interface MoneyCell {

    void putMoney(MoneyValue moneyValue, Integer count);

    void getMoney(MoneyValue moneyValue, Integer count);

    int getMoneyValueCount(MoneyValue unitMoneyValue);

    Map<MoneyValue,Integer> getAllMoney();

}
