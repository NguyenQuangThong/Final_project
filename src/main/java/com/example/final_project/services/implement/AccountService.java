package com.example.final_project.services.implement;

import com.example.final_project.configures.JwtTokenUtils;
import com.example.final_project.dtos.requests.AccountUpdate;
import com.example.final_project.dtos.requests.LoginRequest;
import com.example.final_project.dtos.requests.RegisterRequest;
import com.example.final_project.dtos.responses.AccountResponse;
import com.example.final_project.dtos.responses.LoginResponse;
import com.example.final_project.models.Account;
import com.example.final_project.models.Key;
import com.example.final_project.models.Role;
import com.example.final_project.repositories.AccountRepository;
import com.example.final_project.repositories.KeyRepository;
import com.example.final_project.repositories.RoleRepository;
import com.example.final_project.services.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class AccountService implements IAccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    KeyRepository keyRepository;
    @Autowired
    JwtTokenUtils jwtTokenUtils;

    @Override
    public LoginResponse login(LoginRequest loginRequest, Authentication authentication) throws NoSuchAlgorithmException, InvalidKeySpecException {
        User user = (User) authentication.getPrincipal();
        Key key = keyRepository.findById(1L).orElseThrow();
        byte[] bytes = Base64.getDecoder().decode(key.getPrivateKey());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(bytes));
        return new LoginResponse(jwtTokenUtils.generateToken(user, privateKey),
                jwtTokenUtils.modelMapper().map(accountRepository.findByUsername(user.getUsername()), AccountResponse.class));
    }

    @Override
    public boolean createAccount(RegisterRequest registerRequest) {
        if (accountRepository.findByUsername(registerRequest.getUsername()) == null) {
            Account account = new Account();
            account.setUsername(registerRequest.getUsername());
            account.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
            account.setFullName(registerRequest.getFullName());
            account.setRole(roleRepository.findById(1L).orElseThrow());
            accountRepository.save(account);
            return true;
        }
        return false;
    }

    @Override
    public List<AccountResponse> getAllAccounts() {
        List<AccountResponse> result = new ArrayList<>();
        try {
            List<Account> accounts = accountRepository.findAll();
            for (Account account : accounts)
                result.add(jwtTokenUtils.modelMapper().map(account, AccountResponse.class));
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public AccountResponse getAccountById(Long id) {
        try {
            return jwtTokenUtils.modelMapper().map(accountRepository.findById(id).get(), AccountResponse.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean updateAccount(Long id, AccountUpdate accountUpdate) {
        try {
            Account account = accountRepository.findById(id).get();
            account.setFullName(accountUpdate.getFullName());
            accountRepository.save(account);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteAccount(Long id) {
        try {
            accountRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        Role userRole = roleRepository.findRoleByAccounts(account);
        List<GrantedAuthority> grantList = new ArrayList<>();
        if (userRole != null) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.getRoleName());
            grantList.add(authority);
        }
        return new User(account.getUsername(), account.getPassword(), grantList);
    }
}
