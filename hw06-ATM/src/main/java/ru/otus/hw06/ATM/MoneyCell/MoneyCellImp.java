package ru.otus.hw06.ATM.MoneyCell;

import ru.otus.hw06.ATM.ATMImp;

import java.util.HashMap;
import java.util.Map;

public class MoneyCellImp implements  MoneyCellInterface {

    private Map<ATMImp.MoneyValue,Integer> VALUE_MAP = new HashMap<>();

    public MoneyCellImp() {
        VALUE_MAP.put(ATMImp.MoneyValue.unit20,0);
        VALUE_MAP.put(ATMImp.MoneyValue.unit50,0);
        VALUE_MAP.put(ATMImp.MoneyValue.unit100,0);
        VALUE_MAP.put(ATMImp.MoneyValue.unit200,0);
    }

    public void putMoney(ATMImp.MoneyValue moneyValue, Integer count) {
         VALUE_MAP.put(moneyValue,VALUE_MAP.get(moneyValue)+count);
     }

    public void getMoney(ATMImp.MoneyValue moneyValue, Integer count) {
        VALUE_MAP.put(moneyValue,VALUE_MAP.get(moneyValue)-count);
    }

    public int getMoneyValueCount(Integer unitMoneyValue){
        switch (unitMoneyValue) {
            case 20:
                return VALUE_MAP.get(ATMImp.MoneyValue.unit20);
            case 50:
                return VALUE_MAP.get(ATMImp.MoneyValue.unit50);
            case 100:
                return VALUE_MAP.get(ATMImp.MoneyValue.unit100);
            case 200:
                return VALUE_MAP.get(ATMImp.MoneyValue.unit200);
           default:
               return 0;
        }
    }

}
