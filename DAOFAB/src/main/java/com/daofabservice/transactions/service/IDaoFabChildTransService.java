package com.daofabservice.transactions.service;

import com.daofabservice.transactions.models.dto.response.ChildDetailData;

import java.util.List;

public interface IDaoFabChildTransService {

    List<ChildDetailData> fetchChildTransDataByParentId(Integer parentId);
}
