package com.example.second;

import com.example.second.contracts.Contract;
import java.time.LocalDate;

public class ContractTable extends Contract {

    private boolean checkBox;

    public ContractTable(Contract contract) {
        super(contract.getDate(), contract.getNumber(), contract.getDateOfLastUpdate());
        this.checkBox = isCheckBox(contract.getDateOfLastUpdate(), contract.getDate());

    }

    //проверка разницы даты и даты последнего обновления
    public boolean isCheckBox(LocalDate dateOfLastUpdate, LocalDate date) {
        int difference = (dateOfLastUpdate.getYear() - date.getYear()) * 365
                + (dateOfLastUpdate.getMonthValue() - date.getMonthValue()) * 30
                + (dateOfLastUpdate.getDayOfMonth() - date.getDayOfMonth());
        return difference <= 60;
    }

    public void setCheckBox(boolean checkBox) {
        this.checkBox = checkBox;
    }

    public boolean isCheckBox() {
        return checkBox;
    }
}
