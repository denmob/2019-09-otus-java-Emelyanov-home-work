package ru.otus.hw07.ATM.MoneyCell;

import ru.otus.hw07.ATM.MoneyValueV2;
import java.util.Map;

public interface MoneyCell {

    void putMoney(MoneyValueV2 moneyValue, Integer count);

    void getMoney(MoneyValueV2 moneyValue, Integer count);

    int getMoneyValueCount(MoneyValueV2 unitMoneyValue);

    Map<MoneyValueV2,Integer> getAllMoney();

}
