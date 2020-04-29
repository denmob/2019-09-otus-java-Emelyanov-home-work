package ru.otus.hw06.atm.moneywithdraw;

import ru.otus.hw06.atm.MoneyValue;
import ru.otus.hw06.atm.moneycell.MoneyCellImp;

import java.util.HashMap;
import java.util.Map;


public class MoneyWithdrawImp implements  MoneyWithdrawInterface{

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
            sum = sum + moneyValueCount* Integer.parseInt(moneyValue.toString());
            System.out.println(String.format("Будет выдано %s количество купюр номиналом %s  ", moneyValueCount,moneyValue.toString()));
        }
        if (sum > 0) {
            System.out.println("Общая сумма денежных стредств для выдачи = " + sum);
        }
    }

    @Override
    public boolean validateMoneyToWithdraw() {
        MoneyValue[] moneyValue = MoneyValue.values();
        int copyWithdrawingValue = this.withdrawingValue;
        for (int i=moneyValue.length-1;i>=0;i--) {
            copyWithdrawingValue = createMoneyWithdraw(copyWithdrawingValue,  moneyValue[i]);
        }
        if (copyWithdrawingValue >0) {
            System.out.println("Не хватает купюр для выдачи суммы: " + copyWithdrawingValue);
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
        int unitCount = withdrawing / Integer.parseInt(moneyValueUnit.toString());
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
