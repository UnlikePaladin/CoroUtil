package com.corosus.coroutil.util;

import com.google.common.base.Preconditions;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;

import javax.annotation.Nullable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

import static org.spongepowered.asm.util.Bytecode.getDescriptor;
import static org.spongepowered.asm.util.Bytecode.getTypes;

public class OldUtil {

    static Field field_modifiers = null;

    public static Object getPrivateValue(Class var0, Object var1, String var2)
    {
        try
        {
            Field var3 = var0.getDeclaredField(var2);
            var3.setAccessible(true);
            return var3.get(var1);
        }
        catch (Exception var4)
        {
            return null;
        }
    }

    public static <T, E> void setPrivateValue(@NotNull final Class<? super T> classToAccess, @NotNull final T instance, @NotNull final String fieldName, @Nullable final E value) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException {
        try
        {
            CULog.log("Value: " + value);
            findField(classToAccess, fieldName, getTypes(value.getClass())).set(instance, value);
        }
        catch (IllegalAccessException e)
        {
            CULog.err("Unable to set any field " + fieldName + " on type " + classToAccess.getName());
            throw e;
        }
        catch (Exception e)
        {
            CULog.err("Unable to locate any field " + fieldName + " on type " + classToAccess.getName());
            throw e;
        }
    }



    @NotNull
    public static <T, E extends Type> Field findField(@NotNull final Class<? super T> clazz, @NotNull final String fieldName, E[] valueType) throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException {
        Preconditions.checkNotNull(clazz, "Class to find field on cannot be null.");
        Preconditions.checkNotNull(fieldName, "Name of field to find cannot be null.");
        Preconditions.checkArgument(!fieldName.isEmpty(), "Name of field to find cannot be empty.");
        try
        {
            MappingResolver resolver = FabricLoader.getInstance().getMappingResolver();

            Field f = clazz.getDeclaredField(
                    resolver.mapFieldName(
                            "intermediary",
                            resolver.unmapClassName("intermediary", clazz.getName()),
                            fieldName,
                            getDescriptor(valueType)
                    ));

            f.setAccessible(true);
            return f;
        }
        catch (Exception e)
        {
            throw e;
        }
    }
}
