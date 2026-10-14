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

import java.util.List;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class Triple {

  public static void main(String[] args) {

    Model model = ModelFactory.createDefaultModel();

    String ns = "https://example.org/course/";

    model.setNsPrefix("course", ns);
    
    var a = model.createResource(ns + "student_a");
    var b = model.createResource(ns + "student_b");
    var t = model.createResource(ns + "tutor_t");

    var friendOf = model.createProperty(ns + "friendOf");
    var studentOf = model.createProperty(ns + "studentOf");

    var stmt1 = model.createStatement(a, friendOf, b);
    var stmt2 = model.createStatement(b, friendOf, a);
    var stmt3 = model.createStatement(b, studentOf, t);
    var stmt4 = model.createStatement(a, studentOf, t);   

    model.add(List.of(stmt1, stmt2, stmt3, stmt4));

    model.write(System.out, "Turtle");
    
    RDFDataMgr.write(System.out, model, Lang.TTL); // RDFDataMgr工具物件提供更多選項
  }

}
