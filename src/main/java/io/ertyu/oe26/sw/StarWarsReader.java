package io.ertyu.oe26.sw;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class StarWarsReader {

  public static Model getModel() throws IOException {

    Path source = Path.of("star_wars_planets_dataset.ttl");

    Model model = ModelFactory.createDefaultModel();

    try (var ins = Files.newInputStream(source)) {
      model.read(ins, "", "TTL");
    }

    return model;
  }
}
