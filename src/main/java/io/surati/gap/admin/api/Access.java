package io.surati.gap.admin.api;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface Access {

    String code();

    String title();

    String description();

    Module module();

    List<Access> VALUES = new LinkedList<>();

    Map<String, Access> BY_CODE = new LinkedHashMap<>();

    static Access get(final String code) {
        return Access.BY_CODE.get(code);
    }

}
