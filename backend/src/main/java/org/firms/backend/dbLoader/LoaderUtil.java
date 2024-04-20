package org.firms.backend.dbLoader;

import com.opencsv.exceptions.CsvValidationException;
import org.firms.backend.firmsRequests.FirmsRequests;
import org.firms.backend.jsonEntities.in.user.SignUpEntity;
import org.firms.backend.jsonEntities.out.subscription.FireEntity;
import org.firms.backend.models.Region;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Утилита по генерации сущностей для загрузки данных
 */
public class LoaderUtil {

    /**
     * Создание пользователей
     * @return
     */
    public static List<SignUpEntity> createUsers(){
        List<SignUpEntity> entities = new ArrayList<>();
        SignUpEntity admin = new SignUpEntity();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setFirstName("admin");
        admin.setLastName("admin");
        admin.setMiddleName("admin");
        admin.setApiKey("4fbd1f3e9f6e9600d70d5781a5b066da");

        SignUpEntity user = new SignUpEntity();
        user.setUsername("user");
        user.setPassword("user");
        user.setFirstName("user");
        user.setLastName("user");
        user.setMiddleName("user");
        user.setApiKey("b46224ca8a5183df21a4da53783f20d8");

        entities.add(admin);
        entities.add(user);
        return entities;
    }

    /**
     * Создание регионов
     * @return
     * @throws CsvValidationException
     * @throws IOException
     * @throws InterruptedException
     */
    public static List<Region> createRegions() throws CsvValidationException, IOException, InterruptedException {
        return FirmsRequests.getCountries();
    }

}
