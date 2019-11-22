package ru.otus.hw06.ATM;


import ru.otus.hw06.ATM.MoneyCell.MoneyCellImp;
import ru.otus.hw06.ATM.MoneyWithdraw.MoneyWithdrawImp;

public class ATMImp implements ATMInterface {

    public enum MoneyValue {
        unit20,
        unit50,
        unit100,
        unit200
    }

    private MoneyCellImp moneyCellImp = new MoneyCellImp();

    private MoneyWithdrawImp moneyWithdraw = new MoneyWithdrawImp();

    @Override
    public void withdrawMoney(int value) {

        if (validateMoneyToWithdraw(value)) {
            moneyCellImp.getMoney(MoneyValue.unit20,moneyWithdraw.getUnit(MoneyValue.unit20));
            moneyCellImp.getMoney(MoneyValue.unit50,moneyWithdraw.getUnit(MoneyValue.unit50));
            moneyCellImp.getMoney(MoneyValue.unit100,moneyWithdraw.getUnit(MoneyValue.unit100));
            moneyCellImp.getMoney(MoneyValue.unit200,moneyWithdraw.getUnit(MoneyValue.unit200));
        }

    }

    private boolean validateMoneyToWithdraw(Integer withdrawingValue) {

        System.out.println("Необходимо выдать сумму: "+withdrawingValue);

        if (withdrawingValue >= 200) withdrawingValue = createMoneyWithdraw(withdrawingValue,200);
        if (withdrawingValue >= 100) withdrawingValue = createMoneyWithdraw(withdrawingValue,100);
        if (withdrawingValue >= 50) withdrawingValue = createMoneyWithdraw(withdrawingValue,50);
        if (withdrawingValue >= 20) withdrawingValue = createMoneyWithdraw(withdrawingValue,20);

        if (withdrawingValue >0) {
            System.out.println("Не хватает купюр для выдачи суммы: " + withdrawingValue);
            return false;
        }
        else {
            moneyWithdraw.printMoneyUnitForWithdraw();
            return true;
        }
    }


    private Integer createMoneyWithdraw(Integer withdrawing, Integer moneyValueUnit){

        int moneyCellCount;
        int unitCount = withdrawing / moneyValueUnit;
        if (unitCount > 0) {
            moneyCellCount = moneyCellImp.getMoneyValueCount(moneyValueUnit);
            if (unitCount <= moneyCellCount) {
                moneyWithdraw.setUnit(moneyValueUnit,unitCount);
                withdrawing = withdrawing - unitCount * moneyValueUnit;
            }
        }
        return withdrawing;
    }


    @Override
    public String depositMoney(MoneyValue moneyValue, Integer count) {
        if (count>0) {
            moneyCellImp.putMoney(moneyValue, count);
            return "Средства успешно внесены.";
        } else return "Количество купюр должно быть больше 0.";
    }

    @Override
    public void printATMValues() {
        int sum = 0;
        sum = sum + printMoneyUnitStateAndCalculateSum(20);
        sum = sum + printMoneyUnitStateAndCalculateSum(50);
        sum = sum + printMoneyUnitStateAndCalculateSum(100);
        sum = sum + printMoneyUnitStateAndCalculateSum(200);
        if (sum > 0) {
            System.out.println("Общая сумма денежных стредств в ATM = " + sum);
        }
    }

    private int printMoneyUnitStateAndCalculateSum(Integer unitMoneyValue){

        int moneyValueCount =  moneyCellImp.getMoneyValueCount(unitMoneyValue);
        if (moneyValueCount != 0) {
            System.out.println(String.format("Количество купюр номиналом %d - " + moneyValueCount,unitMoneyValue));
            return  moneyValueCount * unitMoneyValue;
        }
        return 0;
    }
}


