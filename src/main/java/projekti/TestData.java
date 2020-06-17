///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package projekti;
//
//import java.util.HashSet;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import projekti.domain.Account;
//import projekti.domain.Connection;
//import projekti.domain.User;
//import projekti.service.AccountService;
//import projekti.service.UserService;
//
///**
// *
// * @author aleksi
// */
//public class TestData {
//    @Autowired
//    private AccountService accountService;
//    
//    @Autowired
//    private UserService userService;
//    
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    
//    public void addTestData() {
//        Account account1 = new Account("aleksi", passwordEncoder.encode("salasana"));
//        account1 = accountService.create(account1);
//        User user1 = new User(account1, "Aleksi Testaaja", null, new HashSet<Connection>(), new HashSet<Connection>(), null);
//        userService.create(user1);
//
//        Account account2 = new Account("joonas", passwordEncoder.encode("salasana"));
//        account2 = accountService.create(account1);
//        User user2 = new User(account2, "Joonas Testiukko", null, new HashSet<Connection>(), new HashSet<Connection>(), null);
//        userService.create(user2);
//
//        Account account3 = new Account("kameli", passwordEncoder.encode("salasana"));
//        account3 = accountService.create(account3);
//        User user3 = new User(account3, "Kameli Varvas", null, new HashSet<Connection>(), new HashSet<Connection>(), null);
//        userService.create(user3);
//
//        Account account4 = new Account("aleksei", passwordEncoder.encode("salasana"));
//        account4 = accountService.create(account4);
//        User user4 = new User(account4, "Aleksi Alipaine", null, new HashSet<Connection>(), new HashSet<Connection>(), null);
//        userService.create(user4);
//
//    }
//}
