package dev.sujeet.splitwise.service.strategy;

import dev.sujeet.splitwise.entity.*;

import java.util.*;

public class MaxBorrowerLenderMatchSettleUpStrategy implements SettleUpStrategy{
    @Override
    public List<Transaction> settleUp(List<Expense> expenses) {
        //calculate the outstanding
        HashMap<User,Double>userOutStandingMap=new HashMap<>();
        for(Expense expense:expenses){
            for(PayoutLedger pl:expense.getPayoutLedgers()){
                User user=pl.getUser();
                if(!userOutStandingMap.containsKey(user)){
                    userOutStandingMap.put(user, 0.0);
                }
                if(pl.getPayoutType().equals(PayoutType.PAID)){
                    userOutStandingMap.put(user,userOutStandingMap.get(user)+ pl.getAmount());
                }else{
                    userOutStandingMap.put(user,userOutStandingMap.get(user)- pl.getAmount());
                }
            }
        }
        PriorityQueue<UserExpensePair>borrowersHeap=new PriorityQueue<>(
                Comparator.comparingDouble(UserExpensePair::getAmount) );
        PriorityQueue<UserExpensePair>lendersHeap=new PriorityQueue<>(
                Comparator.comparingDouble(UserExpensePair::getAmount).reversed() );

        for(Map.Entry<User,Double> entry:userOutStandingMap.entrySet()){
            if(entry.getValue()>0.0){
                lendersHeap.add(new UserExpensePair(entry.getKey(), entry.getValue()));
            }
            else if(entry.getValue()<0.0){
                borrowersHeap.add(new UserExpensePair(entry.getKey(), entry.getValue()));
            }else{ //value=0.0
                System.out.println("Congratulations !"+entry.getKey().getName()+" Your bill is already settled up");
            }

        }
        //now start settling up the bill
        List<Transaction> minTrasaction=settleUpBill(borrowersHeap,lendersHeap);
        return minTrasaction;

    }
    List<Transaction> settleUpBill(PriorityQueue<UserExpensePair>minHeap,PriorityQueue<UserExpensePair>maxHeap){
        List<Transaction>minTrasactions=new ArrayList<>();
        while(!minHeap.isEmpty() && !maxHeap.isEmpty()){
            UserExpensePair borrower=minHeap.remove(); //negative
            UserExpensePair lender=maxHeap.remove(); //positive

            double borrowAmount = Math.abs(borrower.getAmount());
            double lendAmount = lender.getAmount();

            double settledAmount = Math.min(borrowAmount, lendAmount);

            Transaction t=new Transaction();
            t.setAmount(settledAmount);
            t.setPaidBy(borrower.getUser());
            t.setReceivedBy(lender.getUser());
            minTrasactions.add(t);

            if (borrowAmount > lendAmount) {
                minHeap.add(new UserExpensePair(
                        borrower.getUser(),
                        -(borrowAmount - lendAmount)
                ));
            }
            else if (lendAmount > borrowAmount) {
                maxHeap.add(new UserExpensePair(
                        lender.getUser(),
                        lendAmount - borrowAmount
                ));
            }
            // if equal → nothing to push


        }
        return  minTrasactions;
    }
}
 class UserExpensePair{
    private User user;
    private Double outStandingAmount;

     public UserExpensePair(User user, Double outStandingAmount) {
         this.user = user;
         this.outStandingAmount = outStandingAmount;
     }

     public User getUser() {
         return user;
     }

     public void setUser(User user) {
         this.user = user;
     }

     public Double getAmount() {
         return outStandingAmount;
     }

     public void setAmount(Double outStandingAmount) {
         this.outStandingAmount = outStandingAmount;
     }
 }
