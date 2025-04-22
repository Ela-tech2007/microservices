package com.abcbank.accounts.service.impl;

import com.abcbank.accounts.constants.AccountConstants;
import com.abcbank.accounts.dto.AccountsDto;
import com.abcbank.accounts.dto.CustomerDto;
import com.abcbank.accounts.entity.Accounts;
import com.abcbank.accounts.entity.Customer;
import com.abcbank.accounts.mapper.AccountsMapper;
import com.abcbank.accounts.mapper.CustomerMapper;
import com.abcbank.accounts.repository.AccountsRepository;
import com.abcbank.accounts.repository.CustomerRepository;
import com.abcbank.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto){
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());

        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Ela");
        Customer savedCustomer=customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }




    private Accounts createNewAccount(Customer customer){
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber =  1000000000L+new Random().nextInt(900000000);


        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountConstants.SAVINGS);
        newAccount.setBranchAddress(AccountConstants.ADDRESS);
        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy("Ela");
        return newAccount;


    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber);
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId());
        CustomerDto customerDto  = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return customerDto;
    }
}

