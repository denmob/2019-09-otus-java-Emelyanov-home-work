package ru.otus.hw07.ATM.MoneyWithdraw;


import ru.otus.hw07.ATM.MoneyCell.MoneyCellImp;
import ru.otus.hw07.ATM.MoneyValue;

import java.util.HashMap;
import java.util.Map;


public class MoneyWithdrawImp implements MoneyWithdraw {

    private final Map<MoneyValue,Integer> mapMoney = new HashMap<>();
    private final Integer withdrawingValue;
    private final MoneyCellImp moneyCellImp;

    public MoneyWithdrawImp(Integer withdrawingValue, MoneyCellImp moneyCellImp) {
        this.withdrawingValue = withdrawingValue;
        this.moneyCellImp = moneyCellImp;
        initializationEmptyMoneyCells();
    }

    private void initializationEmptyMoneyCells(){
        for  (MoneyValue moneyValue: MoneyValue.values()){
            mapMoney.putIfAbsent(moneyValue, 0);
        }
    }

    public int getMoneyValueCount(MoneyValue unitMoneyValue){
        return mapMoney.get(unitMoneyValue);
    }

    private void printMoneyUnitForWithdraw() {
        int sum = 0;
        for  (MoneyValue moneyValue: MoneyValue.values()){
            int moneyValueCount =  moneyCellImp.getMoneyValueCount(moneyValue);
            sum = sum + moneyValueCount* moneyValue.getMoneyValue();
            System.out.println(String.format("Будет выдано количество купюр номиналом %s - " + moneyValueCount,moneyValue.toString()));
        }
        if (sum > 0) {
            System.out.println("Общая сумма денежных стредств для выдачи = " + sum);
        }
    }

    @Override
    public boolean validateMoneyToWithdraw() {
        MoneyValue[] moneyValue = MoneyValue.values();
        Integer iwithdrawingValue = withdrawingValue;
        for (int i=moneyValue.length-1;i>=0;i--) {
            iwithdrawingValue = createMoneyWithdraw(iwithdrawingValue,  moneyValue[i]);
        }
        if (iwithdrawingValue >0) {
            System.out.println("Не хватает купюр для выдачи суммы: " + iwithdrawingValue);
            return false;
        } else {
            printMoneyUnitForWithdraw();
            return true;
        }
    }

    private void setMoney(MoneyValue moneyValue, Integer unitCount){
        mapMoney.put(moneyValue,mapMoney.get(moneyValue)+unitCount);
    }

    private Integer createMoneyWithdraw(Integer withdrawing, MoneyValue moneyValueUnit){
        int moneyCellCount;
        int unitCount = withdrawing / moneyValueUnit.getMoneyValue();
        if (unitCount > 0) {
            moneyCellCount = moneyCellImp.getMoneyValueCount(moneyValueUnit);
            if (unitCount <= moneyCellCount) {
                setMoney(moneyValueUnit,unitCount);
                withdrawing = withdrawing - unitCount * Integer.parseInt(moneyValueUnit.toString());
            }
        }
        return withdrawing;
    }

}
