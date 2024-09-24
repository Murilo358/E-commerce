package com.order.service.config.jackson;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import org.apache.avro.specific.SpecificRecordBase;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This is a module that allows ObjectMapper to serialize and deserialize
 * SpecificRecordBase instances, as you get them when you use the Avro code generator to
 * generate Java classes based on Avro schema definitions.
 *
 * It excludes the beanProperties `schema` and `specificData` from serialization using a
 * customized version of com.fasterxml.jackson.dataformat.avro.AvroSerializerModifier.
 */
public class JacksonAvroModule extends SimpleModule {

    public static final String NAME = "JacksonAvroModule";
    private static final Version VERSION = VersionUtil.versionFor(JacksonAvroModule.class);

    private static final Set<String> IGNORED_PROPERTIES = new HashSet<>(Arrays.asList("schema", "specificData"));

    /**
     * An ObjectMapper configuration that copies the global visibility settings
     * from org.apache.avro.util.internal.JacksonUtils.objectToMap.
     * With this mapper, serialization and deserialization of SpecificRecordBase instances works out of the box.
     *
     * Hint: usage is discouraged since this OM uses global settings that not only apply to SpecificRecordBase.
     */
    public static void configureObjectMapper(ObjectMapper om) {
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        om.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    public static boolean isSpecificRecordBase(BeanDescription beanDesc) {
        return SpecificRecordBase.class.isAssignableFrom(beanDesc.getBeanClass());
    }

    public JacksonAvroModule() {
        super(NAME, VERSION);
        this.setSerializerModifier(new IgnorePropertiesBeanSerializerModifier());
    }

    @Override
    public String toString() {
        return super.getModuleName() + "{version=" + version() + "}";
    }

    /**
     * The implementation is copied from com.fasterxml.jackson.dataformat.avro.AvroSerializerModifier
     * but their implementation just excludes `schema` not `specificData`.
     */
    public static class IgnorePropertiesBeanSerializerModifier extends BeanSerializerModifier {
        @Override
        public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
            if (isSpecificRecordBase(beanDesc)) {
                return beanProperties.stream()
                        .filter(property -> !IGNORED_PROPERTIES.contains(property.getName()))
                        .toList();
            }
            return beanProperties;
        }
    }
}