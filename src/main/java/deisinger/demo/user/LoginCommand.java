package deisinger.demo.user;

import javax.validation.constraints.*;

public class LoginCommand {

    @NotBlank
    @Email
    private String username;
    private String password;
    @AssertTrue
    private boolean termsAccepted;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isTermsAccepted() {
        return termsAccepted;
    }

    public void setTermsAccepted(boolean termsAccepted) {
        this.termsAccepted = termsAccepted;
    }
}
