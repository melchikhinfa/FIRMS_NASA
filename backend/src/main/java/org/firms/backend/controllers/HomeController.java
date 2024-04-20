package org.firms.backend.controllers;


import org.firms.backend.exceptions.PasswordNotEqualsException;
import org.firms.backend.exceptions.UsernameExistsException;
import org.firms.backend.exceptions.UsernameNotFoundException;
import org.firms.backend.jsonEntities.in.user.ChangeAPIEntity;
import org.firms.backend.jsonEntities.in.user.ChangePasswordEntity;
import org.firms.backend.jsonEntities.in.user.LoginEntity;
import org.firms.backend.jsonEntities.in.user.SignUpEntity;
import org.firms.backend.jsonEntities.out.user.UserEntity;
import org.firms.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST контроллер обработки информации по пользователю
 */
@RestController
@RequestMapping("/api")
public class HomeController {

    /**
     * Сервис пользователя
     */
    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    /**
     * POST запрос регистрации
     * @param entity сущность регистрации
     * @return
     */
    @PostMapping("/signUp")
    public ResponseEntity<?> postSignUpMethod(@RequestBody SignUpEntity entity){
        try{
            UserEntity userEntity = userService.signUp(entity);
            return new ResponseEntity<>(userEntity, HttpStatus.CREATED);
        } catch (UsernameExistsException e) {
            return new ResponseEntity<>("", HttpStatus.CONFLICT);
        }

    }

    /**
     * POST запрос авторизации
     * @param entity сущность авторизации
     * @return
     */
    @PostMapping("/auth")
    public ResponseEntity<?> postAuthMethod(@RequestBody LoginEntity entity){
        try{
            UserEntity userEntity = userService.signIn(entity);
            return new ResponseEntity<>(userEntity, HttpStatus.OK);
        }  catch (UsernameNotFoundException e) {
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        } catch (PasswordNotEqualsException e) {
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }


    }

    /**
     * POST запрос смены пароля
     * @param username имя пользователя
     * @param entity сущность смены пароля
     * @return
     */
    @PostMapping("/users/{username}/changePwd")
    public ResponseEntity<?> postChangePasswordMethod(@PathVariable String username, @RequestBody ChangePasswordEntity entity){
        try{
            userService.changePwd(username,entity);
            return new ResponseEntity<>("", HttpStatus.OK);
        } catch (PasswordNotEqualsException e) {
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

    }

    /**
     * Сущность смены API ключа
     * @param username имя пользвоателя
     * @param entity сущность смены API ключа
     * @return
     */
    @PutMapping("/users/{username}/changeApiKey")
    public ResponseEntity<?> changeApiKey(@PathVariable String username, @RequestBody ChangeAPIEntity entity){
        userService.changeAPIKey(username, entity);

        return new ResponseEntity<>("", HttpStatus.OK);

    }
}
