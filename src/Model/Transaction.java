package Model;

import java.sql.Timestamp;

public class Transaction {
     private int transactionID;
     private Integer memberID;
     private Timestamp transactionDate;
     private double amount;
     private String transactionType;

     // Getters
     public int getTransactionID() {
          return transactionID;
     }

     public Integer getMemberID() {
          return memberID;
     }

     public Timestamp getTransactionDate() {
          return transactionDate;
     }

     public double getAmount() {
          return amount;
     }

     public String getTransactionType() {
          return transactionType;
     }

     // Setters
     public void setTransactionID(int transactionID) {
          this.transactionID = transactionID;
     }

     public void setMemberID(Integer memberID) {
          this.memberID = memberID;
     }

     public void setTransactionDate(Timestamp transactionDate) {
          this.transactionDate = transactionDate;
     }

     public void setAmount(double amount) {
          this.amount = amount;
     }

     public void setTransactionType(String transactionType) {
          this.transactionType = transactionType;
     }
}
