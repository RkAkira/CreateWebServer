package com.amaris.service;

import com.amaris.entity.POJO;

import java.util.ArrayList;
import java.util.List;

public class POJOService {

    private static ArrayList<POJO> pojos;

    public POJO createPojo(POJO pojo) {
        System.out.println("pojo = " + pojo);
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
