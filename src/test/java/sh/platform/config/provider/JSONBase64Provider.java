package sh.platform.config.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.stream.Stream;

import static java.nio.file.Files.readAllBytes;

public class JSONBase64Provider implements ArgumentsProvider, AnnotationConsumer<JSONBase64> {

    private String file;

    @Override
    public void accept(JSONBase64 jsonBase64) {
        this.file = jsonBase64.value();
    }

    @Override
    public Stream<Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        URL url = JSONBase64Provider.class.getClassLoader().getResource(file);
        Path path = Paths.get(url.toURI());
        byte[] encode = Base64.getEncoder().encode(readAllBytes(path));
        return Stream.of(Arguments.of(new String(encode, StandardCharsets.UTF_8)));
    }
}
