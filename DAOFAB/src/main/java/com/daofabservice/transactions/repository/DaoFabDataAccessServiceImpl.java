package com.daofabservice.transactions.repository;

import com.daofabservice.transactions.models.dto.entity.Child;
import com.daofabservice.transactions.models.dto.entity.Parent;
import com.daofabservice.transactions.models.dto.entity.ParentData;
import com.daofabservice.transactions.utils.Constants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DaoFabDataAccessServiceImpl implements IDaoFabTransDataAccessService {

    @Value("${parent.trans.path:Parent.json}")
    String parentJsonFilePath;

    private List<Parent> parentList;

    @Override
    public Optional<List<Parent>> fetchAllParentData(int pageNo, int pageSize) {
        int skipNumber = pageNo * pageSize;
        return Optional.of(this.parentList.stream()
                .skip(skipNumber)
                .limit(pageSize)
                .collect(Collectors.toList()));
    }

    @Override
    public Optional<Parent> fetchParentDataById(Integer parentId) {

        return this.parentList.stream()
                        .filter(item -> item.getId() == parentId)
                        .findFirst();
    }

    @PostConstruct
    @SneakyThrows
    public void loadAllParentChildData() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream(parentJsonFilePath);
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> jsonMap = objectMapper.readValue(inputStream, new TypeReference<Map<String,Object>>(){});
        if(jsonMap != null && jsonMap.containsKey(Constants.DATA)){
//            this.parentList = (ArrayList<Parent>) jsonMap.get(Constants.DATA);
            this.parentList = objectMapper.convertValue(jsonMap.get(Constants.DATA),new TypeReference<List<Parent>>() { });
            //Default sorting by id
            this.parentList = this.parentList.stream().sorted(Comparator.comparing(Parent::getId))
                    .collect(Collectors.toList());
        }

        if(this.parentList == null || this.parentList.isEmpty()){
            throw new Exception("Unable to load the Parent JSON file...");
        }
        System.out.print("Successfully loaded parent JSON file...");
    }
}
