package com.tibco.utils.bw.model;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class BiMapTest {

  public static void main(String[] args) {

    BiMap<String, String> biMap = HashBiMap.create();

    biMap.put("k1", "v1");
    biMap.put("k2", "v2");

    System.out.println("k1 = " + biMap.get("k1"));
    System.out.println("v2 = " + biMap.inverse().get("v2"));
  }
}