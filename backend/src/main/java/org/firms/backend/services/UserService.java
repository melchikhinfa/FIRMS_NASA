package org.firms.backend.services;


import org.firms.backend.exceptions.PasswordNotEqualsException;
import org.firms.backend.exceptions.UsernameExistsException;
import org.firms.backend.exceptions.UsernameNotFoundException;
import org.firms.backend.jsonEntities.in.user.ChangeAPIEntity;
import org.firms.backend.jsonEntities.in.user.ChangePasswordEntity;
import org.firms.backend.jsonEntities.in.user.LoginEntity;
import org.firms.backend.jsonEntities.in.user.SignUpEntity;
import org.firms.backend.jsonEntities.out.user.UserEntity;
import org.firms.backend.models.User;
import org.firms.backend.repositories.UserRepository;
import org.firms.backend.utils.Utils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

/**
 * Класс отвечающий за обработку информации о пользователе
 */
@Service
public class UserService {

    /**
     * Репозиторий пользователя. Константа
     */
    private final UserRepository userRepository;

    /**
     * Конструктор сервиса
     * @param userRepository репозиторий пользователя
     */
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Метод создания пользователя
     * @param entity SignUpEntity - сущность регистрации пользователя
     * @return - сущность пользователя
     * @throws UsernameExistsException - ошибка, при существовании пользователя с таким именем
     */
    public UserEntity signUp(@NotNull SignUpEntity entity) throws UsernameExistsException {
        User user = userRepository.findUserByUsername(entity.getUsername());
        if(user != null){
            throw new UsernameExistsException("Username '" + entity.getUsername() + "' exists");
        }

        User newUser = new User();
        newUser.setApiKey(entity.getApiKey());
        newUser.setPassword(Utils.encodeString(entity.getPassword()));
        newUser.setFirstName(entity.getFirstName());
        newUser.setLastName(entity.getLastName());
        newUser.setMiddleName(entity.getMiddleName());
        newUser.setUsername(entity.getUsername());
        userRepository.save(newUser);
        return new UserEntity(newUser.getUsername(), newUser.getFirstName(), newUser.getLastName(), newUser.getMiddleName());
    }

    /**
     * Метод авторизации пользователя
     * @param loginEntity - сущность авторизации
     * @return - сущность пользователя
     * @throws UsernameNotFoundException - Пользователь с таким именем не найден
     * @throws PasswordNotEqualsException - Введенный пароль не совпадает с хранимим в базе данных
     */
    public UserEntity signIn(@NotNull LoginEntity loginEntity) throws UsernameNotFoundException, PasswordNotEqualsException {
        User user = userRepository.findUserByUsername(loginEntity.getUsername());
        if(user == null){
            throw new UsernameNotFoundException("Username '" + loginEntity.getUsername() + "' not found");
        }

        if(!Utils.checkPasswords(user.getPassword(), loginEntity.getPassword())){
            throw new PasswordNotEqualsException("Password not equals");
        }

        return new UserEntity(user.getUsername(), user.getFirstName(), user.getLastName(), user.getMiddleName());

    }

    /**
     * Смена пароля
     * @param username - Имя пользователя
     * @param entity - Сущность смены пароля
     * @throws PasswordNotEqualsException - Старый пароль неверно введен
     */
    public void changePwd(String username, @NotNull ChangePasswordEntity entity) throws PasswordNotEqualsException {
        User user = userRepository.findUserByUsername(username);
        if(!Utils.checkPasswords(user.getPassword(), entity.getOldPassword())){
            throw new PasswordNotEqualsException("Password not equals");
        }
        user.setPassword(Utils.encodeString(entity.getNewPassword()));
        userRepository.save(user);
    }

    /**
     * Смена API ключа к FIRMS
     * @param username - имя пользователя
     * @param entity - сущность смены ключа
     */
    public void changeAPIKey(String username, @NotNull ChangeAPIEntity entity){
        User user = this.userRepository.findUserByUsername(username);
        user.setApiKey(entity.getApiKey());
        userRepository.save(user);
    }

}
