/*
 * Copyright 2026 ccyid.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.ertyu.oe26.sw;

import java.util.ArrayDeque;
import java.util.HashSet;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.OWL;

public class ConvorLoader {

  private static final String SOURCE
          = "https://nickdrummond.github.io/star-wars-ontology/ontologies/all.owl.ttl";

  private static final String DESTINATION
          = "http://localhost:3030/starwars";

  private static final String TRIPLE_COUNT
          = """
            SELECT (COUNT(*) AS ?tripleCount)
            WHERE {
                ?s ?p ?o .
            }
            """;

  public static void main(String[] args) {
    var combined = ModelFactory.createDefaultModel();

    try {
      var pending = new ArrayDeque<String>();
      var visited = new HashSet<String>();
      pending.add(SOURCE);

      while (!pending.isEmpty()) {
        var document = pending.remove();
        if (!visited.add(document))
          continue;

        System.out.println("Loading: " + document);
        var part = RDFDataMgr.loadModel(document);

        try {
          var imports = part.listStatements(null, OWL.imports, (RDFNode) null);
          try {
            while (imports.hasNext()) {
              var target = imports.nextStatement().getObject(); // triple Object, not OOP
              if (target.isURIResource())
                pending.add(target.asResource().getURI());
            }
          } finally {
            imports.close();
          }

          combined.add(part);
          combined.setNsPrefixes(part.getNsPrefixMap());
          System.out.println("  %,d triples; merged total: %,d"
                  .formatted(part.size(), combined.size()));
        } finally {
          part.close();
        }
      }

      System.out.println("Loaded %d documents and %,d distinct triples."
              .formatted(visited.size(), combined.size()));

      try (var connection = RDFConnection.connect(DESTINATION)) {
        // put(): Replace the dataset's default graph with the complete model.
        connection.put(combined);
        System.out.println("Uploaded to: " + DESTINATION);

        try (QueryExecution execution = connection.query(TRIPLE_COUNT)) {
          ResultSetFormatter.out(execution.execSelect());
        }
      }
    } finally {
      combined.close();
    }
  }
}
