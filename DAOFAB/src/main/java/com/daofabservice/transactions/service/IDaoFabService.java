package com.daofabservice.transactions.service;

import com.daofabservice.transactions.models.dto.response.ParentAmount;

import java.util.List;

public interface IDaoFabService {

    List<ParentAmount> fetchAllParentAmount(int pageNo , int pageSize);
}
