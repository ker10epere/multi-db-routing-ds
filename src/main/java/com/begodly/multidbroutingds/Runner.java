package com.begodly.multidbroutingds;

import com.begodly.multidbroutingds.dbconfig.DataSourceEnum;
import com.begodly.multidbroutingds.entity.Pet;
import com.begodly.multidbroutingds.service.PetService;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Runner implements CommandLineRunner {
    @Autowired
    PetService petService;

    @Override
    public void run(String... args) throws Exception {

        Faker faker = new Faker();

        for (int i = 0; i < 10; i++) {
            String funnyName = faker.funnyName().name();
            Pet entity = new Pet(null, funnyName);
            if (i % 2 == 0) {
                System.out.println("** DataSource " + DataSourceEnum.DATASOURCE_ONE.name());
                petService.repository(DataSourceEnum.DATASOURCE_ONE).save(entity);
                petService.repository(DataSourceEnum.DATASOURCE_ONE).takeByName(funnyName);
            } else {
                System.out.println("** DataSource " + DataSourceEnum.DATASOURCE_TWO.name());
                petService.repository(DataSourceEnum.DATASOURCE_TWO).save(entity);
                petService.repository(DataSourceEnum.DATASOURCE_TWO).takeByName(funnyName);
            }
        }

        System.out.println("--------------------- " + DataSourceEnum.DATASOURCE_ONE.name());
        petService.repository(DataSourceEnum.DATASOURCE_ONE).takeAll().forEach(System.out::println);

        System.out.println("--------------------- " + DataSourceEnum.DATASOURCE_TWO.name());
        petService.repository(DataSourceEnum.DATASOURCE_TWO).takeAll().forEach(System.out::println);
    }
}
