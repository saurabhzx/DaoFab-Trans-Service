package com.daofabservice.transactions.repository;

import com.daofabservice.transactions.models.dto.entity.Child;

import java.util.List;
import java.util.Optional;

public interface IDaoFabTransInstallAccessService {

    Optional<List<Child>> fetchAllTransInstallment();

    Optional<List<Child>> fetchTransInstallmentByParentId(Integer parentId);

    Optional<List<Child>> fetchTransInstallByParentIds(List<Integer> parentIds);

}
