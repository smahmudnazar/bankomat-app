package com.example.bankomat.component;

import com.example.bankomat.entity.Bank;
import com.example.bankomat.entity.Card;
import com.example.bankomat.entity.Role;
import com.example.bankomat.entity.User;
import com.example.bankomat.entity.enums.CardTypeEnum;
import com.example.bankomat.entity.enums.PermissionEnum;
import com.example.bankomat.entity.enums.RoleEnum;
import com.example.bankomat.repository.BankRepository;
import com.example.bankomat.repository.CardRepository;
import com.example.bankomat.repository.RoleRepository;
import com.example.bankomat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final CardRepository cardRepository;
    private final BankRepository bankRepository;
    @Value("${spring.sql.init.mode}")
    String mode;


    @Override
    public void run(String... args) throws Exception {
        if (mode.equals("always")) {
            PermissionEnum[] values = PermissionEnum.values();

            Set<Role> roles=new LinkedHashSet<>();
            Role directorRole = roleRepository.save(new Role(RoleEnum.DIRECTOR, Arrays.asList(values)));
            Role moderRole = roleRepository.save(new Role(RoleEnum.EMPLOYEE, Arrays.asList(PermissionEnum.CARD_CRUD, PermissionEnum.BANKOMAT_CRUD,PermissionEnum.READ_USER)));
            roles.add(directorRole);
            roles.add(moderRole);


            User user=new User("Jafar", passwordEncoder.encode("123"), roles);

            Bank bank = bankRepository.save(new Bank("Anor Bank", "Tashkent", user));
            List<PermissionEnum> permissionEnums=new ArrayList<>();
            permissionEnums.add(PermissionEnum.CARD_READ);

            Card card=cardRepository.save(new Card("1234567890123456", bank,"123","Stive Jobs", passwordEncoder.encode("7777"),
                    Date.valueOf(LocalDate.now().plusYears(5)),Date.valueOf(LocalDate.now()),
                     CardTypeEnum.UZCARD, permissionEnums));


        }
    }
}
