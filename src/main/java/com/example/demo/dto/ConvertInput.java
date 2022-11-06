package com.example.demo.dto;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class ConvertInput {

    @NotBlank(message = "Sort code is mandatory")
    private String sortCode;

    @NotBlank(message = "Account number is mandatory")
    private String accountNumber;

    @NotBlank(message = "Sum is mandatory")
    private String sum;

    @NotBlank(message = "Currency is mandatory")
    private String currency;

    public ConvertInput() {}

    public String getSortCode() {
        return sortCode;
    }
    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConvertInput that = (ConvertInput) o;

        if (!Objects.equals(sortCode, that.sortCode)) return false;
        if (!Objects.equals(accountNumber, that.accountNumber))
            return false;
        if (!Objects.equals(sum, that.sum)) return false;
        return Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        int result = sortCode != null ? sortCode.hashCode() : 0;
        result = 31 * result + (accountNumber != null ? accountNumber.hashCode() : 0);
        result = 31 * result + (sum != null ? sum.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ConvertInput{" +
                "sortCode='" + sortCode + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", sum='" + sum + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
