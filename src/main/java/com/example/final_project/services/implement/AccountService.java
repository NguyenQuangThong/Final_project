package com.example.final_project.services.implement;

import com.example.final_project.configures.JwtTokenUtils;
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
import org.springframework.web.multipart.MultipartFile;

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

    public String avatarHandler(MultipartFile file, String oldAvatar, Long id) throws IOException {
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
//        if (!oldAvatar.equals("static/avatars/default_avatar.png")) {
//            File f = new File(oldAvatar);
//            f.delete();
//        }
        System.out.println(!oldAvatar.equals("static/avatars/default_avatar.png"));
        String renameFile = id.toString() + "." + extension;
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
            account.setAvatar("/avatars/default_avatar.png");
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
    public boolean updateAccount(Long id, String fullName, MultipartFile avatar) {
        try {
            Account account = accountRepository.findById(id).get();
            account.setFullName(fullName);
            String oldAvatar = account.getAvatar();
            if (!avatar.isEmpty())
                account.setAvatar(avatarHandler(avatar, oldAvatar, account.getAccountId()));
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
