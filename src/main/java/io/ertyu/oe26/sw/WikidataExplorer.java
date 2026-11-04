package io.ertyu.oe26.sw;

import java.io.IOException;
import java.util.Objects;
import org.apache.jena.vocabulary.RDFS;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class WikidataExplorer {

  public static void main(String[] args) throws IOException {

    var model = StarWarsReader.getModel();

    model.listSubjects().toList()
            .stream()
            .filter(r -> r.getLocalName().startsWith("Q"))
            .map(r -> r.getLocalName() + "\t" + (Objects.isNull(r.getProperty(RDFS.label)) ? null : r.getProperty(RDFS.label).getString()))
            .forEach(System.out::println);

  }
}
