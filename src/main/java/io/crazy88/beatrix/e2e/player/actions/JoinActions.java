package io.crazy88.beatrix.e2e.player.actions;

import io.crazy88.beatrix.e2e.player.components.PinCodeComponent;
import io.crazy88.beatrix.e2e.player.components.SignupComponent;
import io.crazy88.beatrix.e2e.player.dto.PlayerSignup;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JoinActions {

    @Autowired
    private SignupComponent signupComponent;


    public void fillSignUp(PlayerSignup playerSignup, List<String> signupConfiguration){
        signupComponent.fillForm(playerSignup, signupConfiguration);
    }

    @SneakyThrows
    public void submitSignUp() {
        //Fixme: workaround to make the test pass on prod until we found a better solution.
        Thread.sleep(2000);
        signupComponent.submitForm();
    }

    public void signUp(PlayerSignup playerSignup, List<String> signupConfiguration) {
        fillSignUp(playerSignup, signupConfiguration);
        submitSignUp();
    }

}
