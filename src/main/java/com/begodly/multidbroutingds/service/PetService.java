package com.begodly.multidbroutingds.service;

import com.begodly.multidbroutingds.dbconfig.DataSourceContextHolder;
import com.begodly.multidbroutingds.dbconfig.DataSourceEnum;
import com.begodly.multidbroutingds.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PetService {
    private final PetRepository petRepository;
    private final DataSourceContextHolder contextHolder;

    public PetRepository repository(DataSourceEnum dataSourceEnum) {
        contextHolder.setBranchContext(dataSourceEnum);
        return petRepository;
    }
}
