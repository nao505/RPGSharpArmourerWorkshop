package com.binggre.rpgsharparmourerworkshop.objects;

import java.util.HashMap;
import java.util.Map;

public class Variables {

    private static final Variables instance = new Variables();
    public static final String NBT_KEY = "armourersWorkshop";
    public static final String SKIN_TYPE = "skinType";
    public static final String GLOBAL_ID = "globalId";
    public static final String LOCAL_ID = "localId";
    public static final String IDENTIFIER = "identifier";
    public static final String DYE_DATA = "dyeData";


    public static Variables getInstance() {
        return instance;
    }

    //dataCode, NBTBaseString
    public final Map<String, String> NBT_BASE;

    private Variables() {
        NBT_BASE = new HashMap<>();
    }
}