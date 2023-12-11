package com.example.news.component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractMethodAccessInspector<T> {

    private Class<T> ControllerClass;

    public abstract boolean inspect(Long resourceId, Long userId);
}
