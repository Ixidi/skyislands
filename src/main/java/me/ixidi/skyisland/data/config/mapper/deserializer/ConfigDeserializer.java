package me.ixidi.skyisland.data.config.mapper.deserializer;

import me.ixidi.skyisland.data.config.Config;

public interface ConfigDeserializer<T> {

    T deserialize(Config section);

}
