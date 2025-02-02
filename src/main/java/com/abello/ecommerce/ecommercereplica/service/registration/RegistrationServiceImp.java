package com.abello.ecommerce.ecommercereplica.service.registration;

import com.abello.ecommerce.ecommercereplica.model.ConfirmationToken;
import com.abello.ecommerce.ecommercereplica.model.Customer;
import com.abello.ecommerce.ecommercereplica.model.History;
import com.abello.ecommerce.ecommercereplica.model.dto.request.CustomerRequestDTO;
import com.abello.ecommerce.ecommercereplica.model.dto.response.AuthenticationResponseDTO;
import com.abello.ecommerce.ecommercereplica.repository.CustomerRepository;
import com.abello.ecommerce.ecommercereplica.utils.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImp implements IRegistrationService{

    private final CustomerRepository customerRepository;
    private final EmailValidator emailValidator;
    private final IEmailSender iEmailSender;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenServiceImp confirmationTokenService;

    @Override
    public AuthenticationResponseDTO save(CustomerRequestDTO requestDTO) throws IllegalAccessException {
        if(requestDTO!=null){
            boolean customerExist = customerRepository.findCustomerByEmail(requestDTO.email()).isPresent();
            boolean isValidEmail = emailValidator.test(requestDTO.email());
            if(customerExist || !isValidEmail){
                throw new IllegalAccessException("email already taken or  email not valid");
            }else{
                Customer customer = Customer.builder()
                        .address(new ArrayList<>())
                        .cards(new ArrayList<>())
                        .history(new History(null,new ArrayList<>(), LocalDate.now()))
                        .roles(Arrays.asList(Role.CUSTOMER))
                        .tokens(new ArrayList<>())
                        .confirmationTokens(new ArrayList<>())
                        .enable(false)
                        .build();
                customer.setName(requestDTO.name());
                customer.setLastName(requestDTO.lastName());
                customer.setCellphone(requestDTO.cellphone());
                customer.setEmail(requestDTO.email());
                customer.setUsername(requestDTO.username());
                customer.setPwd(passwordEncoder.encode(requestDTO.pwd()));
                customerRepository.save(customer);
                return new AuthenticationResponseDTO(resendConfirmationEmail(customer.getEmail())); //confirmation token
            }

        }else{
            return null;
        }
    }
    public String resendConfirmationEmail(String email) {
        Customer customer = customerRepository.findCustomerByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        ConfirmationToken token = generateToken(customer);

        String link = "http://localhost:8080/api/v1/authentication/confirm?token=" + token.getToken();
        iEmailSender.send(customer.getEmail(), buildEmail(customer.getName(), link));
        return token.getToken();
    }
    public ConfirmationToken generateToken(Customer customer) {
        String tokenValue = UUID.randomUUID().toString();
        ConfirmationToken token = ConfirmationToken.builder()
                .token(tokenValue)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .customer(customer)
                .build();
        confirmationTokenService.saveConfirmationToken(token);
        return token;
    }

    @Transactional
    @Override
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token).orElse(null);
        if(confirmationToken!=null){
            if(confirmationToken.getConfirmedAt()!=null){
                return "Email already confirmed";
            }else{
                LocalDateTime expiredAt = confirmationToken.getExpiresAt();
                if(expiredAt.isBefore(LocalDateTime.now())){//is expired
                    resendConfirmationEmail(confirmationToken.getCustomer().getEmail());
                    System.out.println("Token expired, send another one");
                    return "the token was expired so we already send you another one";
                }else{
                    confirmationTokenService.setConfirmedAt(token);
                    Customer customer = confirmationToken.getCustomer();
                    customer.setEnable(true);
                    customerRepository.save(customer);
                    return "Email confirmed successfully";
                }
            }
        }else{
            return "Error confirmation token null";
        }
    }


    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

}
