package io.ertyu.oe26.sw;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class StarWars {

  public static void main(String[] args) throws IOException {

    var model = StarWarsReader.getModel();

    String wd = "http://www.wikidata.org/entity/";
    String wdt = "http://www.wikidata.org/prop/direct/";
    String sw = "https://example.org/starwars/";

    model.setNsPrefix("wd", wd);

    try (var outs = Files.newOutputStream(Path.of("star_wars_planets_dataset_wd.ttl"))) {
      model.write(outs, "TTL");
    }

    var instanceOf = model.createProperty(wdt + "P31");
    var wikidataPlanetClass = model.createResource(wd + "Q108059870");

    Model planetModel = ModelFactory.createDefaultModel();
    planetModel.setNsPrefixes(model.getNsPrefixMap());
    planetModel.setNsPrefix("sw", sw);
    planetModel.setNsPrefix("wd", wd);
    planetModel.setNsPrefix("wdt", wdt);
    planetModel.setNsPrefix("owl", OWL.getURI());
    planetModel.setNsPrefix("rdfs", RDFS.getURI());

    var propertyNames = Map.ofEntries(
            Map.entry("P1080", "fictionalUniverse"),
            Map.entry("P1082", "population"),
            Map.entry("P1441", "presentInWork"),
            Map.entry("P1889", "differentFrom"),
            Map.entry("P2146", "orbitalPeriod"),
            Map.entry("P2147", "rotationPeriod"),
            Map.entry("P2386", "diameter"),
            Map.entry("P276", "location"),
            Map.entry("P2927", "waterAreaPercentage"),
            Map.entry("P361", "partOf"),
            Map.entry("P376", "locatedOnAstronomicalBody"),
            Map.entry("P397", "parentAstronomicalBody"),
            Map.entry("P527", "hasPart")
    );

    var entityNames = Map.of(
            "Q108059870", "StarWarsPlanet",
            "Q19786052", "StarWarsUniverse"
    );

    var planets = model.listResourcesWithProperty(instanceOf, wikidataPlanetClass);
    try {
      while (planets.hasNext()) {
        var sourcePlanet = planets.nextResource();
        var planet = sourcePlanet;

        var statements = sourcePlanet.listProperties();
        try {
          while (statements.hasNext()) {
            var statement = statements.nextStatement();
            var predicate = statement.getPredicate();
            var object = statement.getObject();
            boolean isClassification = predicate.equals(instanceOf);

            if (isClassification) {
              predicate = RDF.type;
            } else if (predicate.getURI().startsWith(wdt)) {
              String id = predicate.getURI().substring(wdt.length());
              var localProperty = planetModel.createProperty(
                      sw + propertyNames.getOrDefault(id, id));
              planetModel.add(localProperty, OWL.equivalentProperty, predicate);
              predicate = localProperty;
            }

            if (object.isURIResource()) {
              object = localResource(
                      planetModel, object.asResource(), wd, sw, entityNames, isClassification);
            }

            planetModel.add(planet, predicate, object);
          }
        } finally {
          statements.close();
        }
      }
    } finally {
      planets.close();
    }

    var destination = Path.of("planet_model.ttl");
    try (var outs = Files.newOutputStream(destination)) {
      planetModel.write(outs, "TTL");
    } catch (IOException ex) {
      System.getLogger(StarWars.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
    }
  }

  private static Resource localResource(
          Model target, Resource source, String wd, String sw,
          Map<String, String> entityNames, boolean asClass) {

    if (!source.isURIResource() || !source.getURI().startsWith(wd)) {
      return source;
    }

    String id = source.getURI().substring(wd.length());
    String name = entityNames.get(id);
    if (name == null) {
      return source;
    }
    var local = target.createResource(sw + name);

//    String id = source.getURI().substring(wd.length());
//    var local = target.createResource(sw + entityNames.getOrDefault(id, id));
    if (asClass) {
      target.add(local, RDF.type, OWL.Class);
      target.add(local, OWL.equivalentClass, source);
    } else {
      target.add(local, OWL.sameAs, source);
    }

    return local;
  }
}
