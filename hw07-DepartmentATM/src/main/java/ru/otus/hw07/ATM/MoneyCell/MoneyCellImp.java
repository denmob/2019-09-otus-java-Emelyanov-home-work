package ru.otus.hw07.ATM.MoneyCell;
import ru.otus.hw07.ATM.MoneyValueV2;
import java.util.Map;

public class MoneyCellImp implements MoneyCell,Cloneable {

    private final Map<MoneyValueV2,Integer> mapMoney;

    public MoneyCellImp(Map<MoneyValueV2,Integer> mapMoney) {
        this.mapMoney = mapMoney;
    }

    public void putMoney(MoneyValueV2 moneyValue, Integer count) {
         mapMoney.put(moneyValue,mapMoney.get(moneyValue)+count);
     }

    public void getMoney(MoneyValueV2 moneyValue, Integer count) {
        mapMoney.put(moneyValue,mapMoney.get(moneyValue)-count);
    }

    public Map<MoneyValueV2,Integer> getAllMoney() {
        return mapMoney;
    }

    public int getMoneyValueCount(MoneyValueV2 unitMoneyValue){
        return  mapMoney.get(unitMoneyValue) == null ?  0 :  mapMoney.get(unitMoneyValue);
    }

    @Override
    public MoneyCellImp clone() {
        return new MoneyCellImp(mapMoney);
    }

}
