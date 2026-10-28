/*
 * Copyright 2026 Jonathan Chang, Chun-yien <ccy@musicapoetica.org>.
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
package io.ertyu.oe26.wk6;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.SchemaDO;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class RDFReasoning {

  public static void main(String[] args) {

    Model model = ModelFactory.createDefaultModel();
    String ns = "https://example.org/oe26/";
    Resource Student = model.createResource(ns + "Student", RDFS.Class)
            .addProperty(RDFS.subClassOf, SchemaDO.Person);
    Resource student_a = model.createResource(ns + "student_a", Student)
            .addProperty(SchemaDO.name, "John Doe");
    Property studentOf = model.createProperty(ns + "studentOf");
    studentOf.addProperty(RDFS.domain, Student)
            .addProperty(RDFS.range, SchemaDO.Person);
//asserted
    var tutor_b = model.createResource(ns + "tutor_b");
    student_a.addProperty(studentOf, tutor_b);
  }

}
