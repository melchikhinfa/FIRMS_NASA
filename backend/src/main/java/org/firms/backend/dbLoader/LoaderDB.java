package org.firms.backend.dbLoader;


import com.opencsv.exceptions.CsvValidationException;
import org.firms.backend.jsonEntities.in.user.SignUpEntity;
import org.firms.backend.models.Region;
import org.firms.backend.services.RegionService;
import org.firms.backend.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.List;

/**
 * Загрузка базы данных
 */
@Configuration
public class LoaderDB {

    /**
     * Логирование
     */
    private static final Logger log = LoggerFactory.getLogger(LoaderDB.class);

    /**
     * Метод инициализации базы данных
     * @param userService сервис пользователя
     * @param regionService сервис регионов
     * @return
     * @throws CsvValidationException
     * @throws IOException
     * @throws InterruptedException
     */
    @Bean
    CommandLineRunner init(UserService userService, RegionService regionService) throws CsvValidationException, IOException, InterruptedException {

        List<SignUpEntity> users = LoaderUtil.createUsers();
        List<Region> regions = LoaderUtil.createRegions();

        return args -> {
            log.info("Start Load users");
            for(SignUpEntity entity: users){
                log.info("Preloading Users: " + userService.signUp(entity));
            }
            log.info("End Load Users");

            log.info("Start Load Regions");
            for(Region region: regions){
                log.info("Preloading Regions: " + regionService.save(region));
            }
            log.info("End Load Regions");
        };
    }
}
