package dev.sujeet.splitwise.service;

import dev.sujeet.splitwise.entity.Expense;
import dev.sujeet.splitwise.entity.Group;
import dev.sujeet.splitwise.entity.Transaction;
import dev.sujeet.splitwise.repository.GroupRepo;
import dev.sujeet.splitwise.service.strategy.SettleUpStrategy;
import dev.sujeet.splitwise.service.strategy.SettleUpStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
    @Autowired
    private GroupRepo groupRepo;
    public Group createGroup(Group group){
        return  null;
    }
    public List<Group> getAllGroups(){
        return null;
    }
    public  List<Expense> getAllExpensesByGroupId(Long id){
        return null;
    }
    public List<Transaction> getTransactionSettledUpByGroupId(Long id){
        Group group=groupRepo.findById(id).get();

        SettleUpStrategy settleUpStrategy= SettleUpStrategyFactory.getSettleUpStarategy();
        List<Transaction>transactions=settleUpStrategy.settleUp(group.getExpenses());
        return transactions;
    }
}
