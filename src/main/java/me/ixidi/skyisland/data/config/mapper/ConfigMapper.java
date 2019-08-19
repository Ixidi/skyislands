package me.ixidi.skyisland.data.config.mapper;

import me.ixidi.skyisland.data.config.Config;
import me.ixidi.skyisland.data.config.mapper.annotation.ConfigAfterMapping;
import me.ixidi.skyisland.data.config.mapper.annotation.ConfigExclude;
import me.ixidi.skyisland.data.config.mapper.annotation.ConfigName;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class ConfigMapper {

    private ConfigMapper() {
    }

    public static <T> T map(Class<T> tClass, Config config) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        T instance = tClass.newInstance();

        for (Field field : tClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(ConfigExclude.class)) {
                continue;
            }

            String name;
            if (field.isAnnotationPresent(ConfigName.class)) {
                name = field.getAnnotation(ConfigName.class).value();
            } else {
                name = field.getName();
            }

            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            Class type = field.getType();
            Object object;
            if (type == int.class) {
                object = config.getInt(name);
            } else if (type == long.class) {
                object = config.getLong(name);
            } else if (type == double.class) {
                object = config.getDouble(name);
            } else if (type == String.class) {
                object = config.getString(name);
            } else if (type == boolean.class) {
                object = config.getBoolean(name);
            } else if (type == List.class && ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0] == String.class) {
                object = config.getList(name);
            } else {
                throw new ConfigMapperException(String.format("Config mapper does not support %s type.", type.getSimpleName()));
            }

            field.set(instance, object);
        }

        for (Method method : Arrays.stream(tClass.getDeclaredMethods()).filter(method -> method.isAnnotationPresent(ConfigAfterMapping.class)).collect(Collectors.toList())) {
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            method.invoke(instance);
        }

        return instance;
    }

}
