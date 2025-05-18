package com.example.accounts.service.impl;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.example.accounts.constants.AccountsConstants;
import com.example.accounts.dto.CustomerDto;
import com.example.accounts.entity.Accounts;
import com.example.accounts.entity.Customer;
import com.example.accounts.exception.CustomerAlreadyExistsException;
import com.example.accounts.mapper.CustomerMapper;
import com.example.accounts.repository.AccountsRepository;
import com.example.accounts.repository.CustomerRepository;
import com.example.accounts.service.IAccountService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        customerRepository.findByMobileNumber(customerDto.getMobileNumber())
                .ifPresent(customerExist -> {
                    throw new CustomerAlreadyExistsException(
                            "Customer already registered with given mobile number" + customerDto.getMobileNumber());
                });
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Anonymous");
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    private Accounts createNewAccount(Customer customer) {
        Accounts newAccounts = new Accounts();
        newAccounts.setCustomerId(customer.getCustomerId());
        long randomAccountNumber = 1000000000L + new Random().nextLong(9000000000L);
        newAccounts.setAccountNumber(randomAccountNumber);
        newAccounts.setBranchAddress(AccountsConstants.ADDRESS);
        newAccounts.setAccountType(AccountsConstants.SAVINGS);
        newAccounts.setCreatedAt(LocalDateTime.now());
        newAccounts.setCreatedBy("Anonymous");

        return newAccounts;
    }

}
