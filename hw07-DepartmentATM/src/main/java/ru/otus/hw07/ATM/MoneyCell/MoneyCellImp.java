package ru.otus.hw07.ATM.MoneyCell;

import ru.otus.hw07.ATM.MoneyValue;
import java.util.Map;

public class MoneyCellImp implements  MoneyCellInterface,Cloneable {

    private final Map<MoneyValue,Integer> mapMoney;

    public MoneyCellImp(Map<MoneyValue,Integer> mapMoney) {
        this.mapMoney = mapMoney;
    }

    public void putMoney(MoneyValue moneyValue, Integer count) {
         mapMoney.put(moneyValue,mapMoney.get(moneyValue)+count);
     }

    public void getMoney(MoneyValue moneyValue, Integer count) {
        mapMoney.put(moneyValue,mapMoney.get(moneyValue)-count);
    }

    private Integer getMoneyValue(MoneyValue moneyValue) {
        Integer iMoney = mapMoney.get(moneyValue);
        mapMoney.put(moneyValue,0);
        return iMoney;
    }

    public Integer getAllMoney() {
        int sum = 0;
        for  (MoneyValue moneyValue: MoneyValue.values()){
            sum = sum + getMoneyValue(moneyValue) * Integer.parseInt(moneyValue.toString());
        }
        return sum;
    }

    public int getMoneyValueCount(MoneyValue unitMoneyValue){
               return mapMoney.get(unitMoneyValue);
    }

    @Override
    public MoneyCellImp clone() {
        return new MoneyCellImp(mapMoney);
    }

}
