package com.daofabservice.transactions.repository;

import com.daofabservice.transactions.models.dto.entity.Child;
import com.daofabservice.transactions.utils.Constants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DaoFabTransInstallAccessService implements IDaoFabTransInstallAccessService {

    @Value("${child.trans.path:Child.json}")
    String childJsonFilePath;

    private List<Child> childList;


    @Override
    public Optional<List<Child>> fetchAllTransInstallment() {
        return Optional.of(this.childList);
    }

    @Override
    public Optional<List<Child>> fetchTransInstallmentByParentId(Integer parentId) {
        return Optional.of(this.childList.stream()
                .filter(item -> item.getParentId() == parentId)
                .collect(Collectors.toList()));
    }

    @Override
    public Optional<List<Child>> fetchTransInstallByParentIds(List<Integer> parentIds) {
        return Optional.of(this.childList.stream()
                .filter(item -> parentIds.contains(item.getParentId()))
                .collect(Collectors.toList()));
    }

    @PostConstruct
    @SneakyThrows
    public void loadAllParentChildData() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream(childJsonFilePath);
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> jsonMap = objectMapper.readValue(inputStream, new TypeReference<Map<String,Object>>(){});
        if(jsonMap != null && jsonMap.containsKey(Constants.DATA)){
//            this.parentList = (ArrayList<Parent>) jsonMap.get(Constants.DATA);
            this.childList = objectMapper.convertValue(jsonMap.get(Constants.DATA),new TypeReference<List<Child>>() { });
        }

        if(this.childList == null || this.childList.isEmpty()){
            throw new Exception("Unable to load the Parent JSON file...");
        }
        System.out.print("Successfully loaded parent JSON file...");
    }
}
