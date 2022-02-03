package com.daofabservice.transactions.service.impl;

import com.daofabservice.transactions.models.dto.entity.Child;
import com.daofabservice.transactions.models.dto.entity.Parent;
import com.daofabservice.transactions.models.dto.response.ChildDetailData;
import com.daofabservice.transactions.repository.IDaoFabTransDataAccessService;
import com.daofabservice.transactions.repository.IDaoFabTransInstallAccessService;
import com.daofabservice.transactions.service.IDaoFabChildTransService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class DaoFabChildTransServiceImpl implements IDaoFabChildTransService {

    @Autowired
    IDaoFabTransDataAccessService daoFabTransDataAccessService;

    @Autowired
    IDaoFabTransInstallAccessService daoFabTransInstallAccessService;

    @Override
    @SneakyThrows
    public List<ChildDetailData> fetchChildTransDataByParentId(Integer parentId) {

        final CompletableFuture<Optional<Parent>> parentFuture =
                CompletableFuture.supplyAsync(()-> daoFabTransDataAccessService.fetchParentDataById(parentId));

        final CompletableFuture<Optional<List<Child>>> childFurures =
                CompletableFuture.supplyAsync(()-> daoFabTransInstallAccessService.fetchTransInstallmentByParentId(parentId));

        Parent parentTrans = parentFuture.get()
                .orElse(null);

        List<Child> childList =  childFurures.get()
                .orElse(new ArrayList<Child>());

        if(parentTrans == null || childList.isEmpty()){
            return new ArrayList<ChildDetailData>();
        }

        return getChildDetailDataResponse(childList,parentTrans);
    }

    private List<ChildDetailData> getChildDetailDataResponse(List<Child> childList, Parent parentTrans){

        List<ChildDetailData> childDetailData = new ArrayList<ChildDetailData>();

        childList.forEach(item ->{
            childDetailData.add(ChildDetailData.builder()
                    .id(item.getId())
                    .receiver(parentTrans.getReceiver())
                    .sender(parentTrans.getSender())
                    .totalAmount(parentTrans.getTotalAmount())
                    .paidAmount(item.getPaidAmount())
                    .build());
        });

        return childDetailData.stream()
                .sorted(Comparator.comparing(ChildDetailData::getId))
                .collect(Collectors.toList());
    }
}
