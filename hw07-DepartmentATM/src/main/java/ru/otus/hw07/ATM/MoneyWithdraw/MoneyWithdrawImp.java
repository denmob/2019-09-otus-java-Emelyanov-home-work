package ru.otus.hw07.ATM.MoneyWithdraw;


import ru.otus.hw07.ATM.MoneyCell.MoneyCellImp;
import ru.otus.hw07.ATM.MoneyValueV2;

import java.util.HashMap;
import java.util.Map;


public class MoneyWithdrawImp implements MoneyWithdraw {

    private final Map<MoneyValueV2,Integer> mapMoney = new HashMap<>();
    private final Integer withdrawingValue;
    private final MoneyCellImp moneyCellImp;

    public MoneyWithdrawImp(Integer withdrawingValue, MoneyCellImp moneyCellImp) {
        this.withdrawingValue = withdrawingValue;
        this.moneyCellImp = moneyCellImp;
        initializationEmptyMoneyCells();
    }

    private void initializationEmptyMoneyCells(){
        for  (MoneyValueV2 moneyValue: MoneyValueV2.values()){
            mapMoney.putIfAbsent(moneyValue, 0);
        }
    }

    public int getMoneyValueCount(MoneyValueV2 unitMoneyValue){
        return mapMoney.get(unitMoneyValue);
    }

    private void printMoneyUnitForWithdraw() {
        int sum = 0;
        for  (MoneyValueV2 moneyValue: MoneyValueV2.values()){
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
        MoneyValueV2[] moneyValue = MoneyValueV2.values();
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

    private void setMoney(MoneyValueV2 moneyValue, Integer unitCount){
        mapMoney.put(moneyValue,mapMoney.get(moneyValue)+unitCount);
    }

    private Integer createMoneyWithdraw(Integer withdrawing, MoneyValueV2 moneyValueUnit){
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
