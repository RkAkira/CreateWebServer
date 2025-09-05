package com.amaris.service;

import com.amaris.entity.POJO;

import java.util.ArrayList;
import java.util.List;

public class POJOService {

    private static final List<POJO> pojos=new ArrayList<>();

    public POJO createPojo(POJO pojo) {
        pojos.add(pojo);
        return pojo;
    }

    public List<POJO> getPojos() {
        return pojos;
    }

    public List<POJO> deletePojo(String pojoName) {
        return pojos;
    }
}
