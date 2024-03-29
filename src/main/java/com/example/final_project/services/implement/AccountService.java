package com.example.final_project.services.implement;

import com.example.final_project.configures.JwtTokenUtils;
import com.example.final_project.dtos.requests.AccountDelete;
import com.example.final_project.dtos.requests.LoginRequest;
import com.example.final_project.dtos.requests.PasswordRequest;
import com.example.final_project.dtos.requests.RegisterRequest;
import com.example.final_project.dtos.responses.AccountResponse;
import com.example.final_project.dtos.responses.LoginResponse;
import com.example.final_project.models.Account;
import com.example.final_project.models.Classroom;
import com.example.final_project.models.Key;
import com.example.final_project.models.Role;
import com.example.final_project.repositories.AccountRepository;
import com.example.final_project.repositories.ClassroomRepository;
import com.example.final_project.repositories.KeyRepository;
import com.example.final_project.repositories.RoleRepository;
import com.example.final_project.services.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Autowired
    KeyRepository keyRepository;
    @Autowired
    JwtTokenUtils jwtTokenUtils;
    @Autowired
    ClassroomRepository classroomRepository;
    @Autowired
    JavaMailSender javaMailSender;

    public String getAlphaNumericString(int n) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return sb.toString();
    }

    public Long sendCodeToMail(String to) {
        Account account = accountRepository.findByEmail(to);
        if (account != null) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("thonglunnoen@gmail.com");
            message.setTo(to);
            System.out.println(to);
            message.setSubject("Hi " + account.getUsername() + ", please use this code to change your password.");
            String code = getAlphaNumericString(6);
            account.setPassword(bCryptPasswordEncoder.encode(code));
            accountRepository.save(account);
            message.setText("Your code is: " + code);
            javaMailSender.send(message);
            return account.getAccountId();
        } else
            return null;
    }

    public String avatarHandler(MultipartFile file, String oldAvatar) throws IOException {
        Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));
        Path staticPath = Paths.get("static");
        Path filePath = Paths.get("avatars");
        if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(filePath))) {
            Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(filePath));
        }
        String fileName = file.getOriginalFilename().replaceFirst(".", "");
        String extension = "";
        int index = fileName.lastIndexOf('.');
        if (index > 0) {
            extension = fileName.substring(index + 1);
        }
        System.out.println(oldAvatar);
        if (!oldAvatar.equals("avatars/default_avatar.png")) {
            File f = new File("static/" + oldAvatar);
            f.delete();
        }
        System.out.println(!oldAvatar.equals("avatars/default_avatar.png"));
        String renameFile = getAlphaNumericString(3) + "." + extension;
        Path path = CURRENT_FOLDER.resolve(staticPath)
                .resolve(filePath).resolve(renameFile);
        try (OutputStream os = Files.newOutputStream(path)) {
            os.write(file.getBytes());
        }
        return "" + filePath.resolve(renameFile);
    }

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
            account.setAvatar("avatars/default_avatar.png");
            account.setEmail(registerRequest.getEmail());
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
    public List<AccountResponse> getAllUserAccount() {
        List<AccountResponse> result = new ArrayList<>();
        try {
            List<Account> accounts = accountRepository.findAllByRole_RoleId(1L);
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
    public List<AccountResponse> getMemberNotInClass(Long id) {
        List<Long> temp = new ArrayList<>();
        List<AccountResponse> result = new ArrayList<>();
        Classroom classroom;
        try {
            classroom = classroomRepository.findById(id).get();
        } catch (Exception e) {
            return null;
        }
        temp.add(classroom.getRoomOwner().getAccountId());
        for (Account i : classroom.getRoomMembers()) {
            temp.add(i.getAccountId());
        }
        List<Account> a = accountRepository.findByAccountIdIsNotIn(temp);
        for (Account i : a) {
            result.add(jwtTokenUtils.modelMapper().map(i, AccountResponse.class));
        }
        return result;
    }

    @Override
    public boolean updateAccount(Long id, String fullName, String email) {
        try {
            Account account = accountRepository.findById(id).get();
            account.setFullName(fullName);
            account.setEmail(email);
            accountRepository.save(account);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String updateAvatar(Long id, MultipartFile avatar) {
        try {
            Account account = accountRepository.findById(id).get();
            String oldAvatar = account.getAvatar();
            if (avatar != null)
                account.setAvatar(avatarHandler(avatar, oldAvatar));
            accountRepository.save(account);
            return account.getAvatar();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean updateAccountPassword(Long id, PasswordRequest passwordRequest) {
        try {
            Account account = accountRepository.findById(id).get();
            if (bCryptPasswordEncoder.matches(passwordRequest.getOldPassword(), account.getPassword())) {
                account.setPassword(bCryptPasswordEncoder.encode(passwordRequest.getNewPassword()));
                accountRepository.save(account);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean resetPassword(Long id, String password) {
        try {
            Account account = accountRepository.findById(id).get();
            account.setPassword(bCryptPasswordEncoder.encode(password));
            accountRepository.save(account);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteAccount(AccountDelete accountDelete) {
        try {
            for (Long id : accountDelete.getIds())
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
