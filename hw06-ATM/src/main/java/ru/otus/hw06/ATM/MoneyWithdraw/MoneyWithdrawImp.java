package ru.otus.hw06.ATM.MoneyWithdraw;

import ru.otus.hw06.ATM.MoneyCell.MoneyCellImp;
import ru.otus.hw06.ATM.MoneyValue;

import java.util.HashMap;
import java.util.Map;


public class MoneyWithdrawImp implements  MoneyWithdrawInterface{


    private final Map<MoneyValue,Integer> mapMoney = new HashMap<>();
    private final Integer withdrawingValue;
    private final MoneyCellImp moneyCellImp;


    public MoneyWithdrawImp(Integer withdrawingValue, MoneyCellImp moneyCellImp) {
        this.withdrawingValue = withdrawingValue;
        this.moneyCellImp = moneyCellImp;

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
            System.out.println(String.format("Будет выдано количество купюр номиналом %s - " + moneyValueCount,moneyValue.toString()));
        }
        if (sum > 0) {
            System.out.println("Общая сумма денежных стредств для выдачи = " + sum);
        }
    }

    @Override
    public boolean validateMoneyToWithdraw() {

        Integer iwithdrawingValue = withdrawingValue;
        for  (MoneyValue moneyValue: MoneyValue.values() ) {

        }
//        if (iwithdrawingValue >= 200) iwithdrawingValue = createMoneyWithdraw(iwithdrawingValue,200);
//        if (iwithdrawingValue >= 100) iwithdrawingValue = createMoneyWithdraw(iwithdrawingValue,100);
//        if (iwithdrawingValue >= 50) iwithdrawingValue = createMoneyWithdraw(iwithdrawingValue,50);
//        if (iwithdrawingValue >= 20) iwithdrawingValue = createMoneyWithdraw(iwithdrawingValue,20);

        if (iwithdrawingValue >0) {
            System.out.println("Не хватает купюр для выдачи суммы: " + iwithdrawingValue);
            return false;
        }
        else {
            printMoneyUnitForWithdraw();
            return true;
        }

    }

//
//    private Integer createMoneyWithdraw(Integer withdrawing, MoneyValue moneyValueUnit){
//
//        int moneyCellCount;
//        int unitCount = withdrawing / moneyValueUnit;
//        if (unitCount > 0) {
//            moneyCellCount = moneyCellImp.getMoneyValueCount(moneyValueUnit);
//            if (unitCount <= moneyCellCount) {
//                setUnit(moneyValueUnit,unitCount);
//                withdrawing = withdrawing - unitCount * moneyValueUnit;
//            }
//        }
//        return withdrawing;
//    }


}
