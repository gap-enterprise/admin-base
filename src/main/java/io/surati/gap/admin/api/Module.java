package io.surati.gap.admin.api;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface Module {

    String code();

    String title();

    String description();

    List<Module> VALUES = new LinkedList<>();

    Map<String, Module> BY_CODE = new LinkedHashMap<>();

    static Module get(final String code) {
        return Module.BY_CODE.get(code);
    }

    default List<Access> accesses() {
        return Access.VALUES.stream().filter(
            acs -> acs.module().code().equals(this.code())
        ).collect(Collectors.toList());
    }
}
