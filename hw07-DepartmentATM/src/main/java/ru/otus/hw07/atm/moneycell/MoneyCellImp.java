package ru.otus.hw07.atm.moneycell;
import ru.otus.hw07.atm.MoneyValue;
import java.util.Map;

public class MoneyCellImp implements MoneyCell,Cloneable {

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

    public Map<MoneyValue,Integer> getAllMoney() {
        return mapMoney;
    }

    public int getMoneyValueCount(MoneyValue unitMoneyValue){
        return  mapMoney.get(unitMoneyValue) == null ?  0 :  mapMoney.get(unitMoneyValue);
    }

    @Override
    public MoneyCellImp clone() {
        return new MoneyCellImp(mapMoney);
    }

}
