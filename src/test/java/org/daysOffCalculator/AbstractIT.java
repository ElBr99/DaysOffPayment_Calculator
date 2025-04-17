package org.daysOffCalculator;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static java.util.Objects.requireNonNull;

@IT
public abstract class AbstractIT {

    public static String loadJson(String path) throws IOException {
        return IOUtils
                .toString(
                        requireNonNull(AbstractIT.class.getClassLoader().getResourceAsStream(path)),
                        StandardCharsets.UTF_8
                );
    }

}
