package base.dataaccess;

import base.dataaccess.utils.SparqlUtils;
import base.domain.Triple;
import org.apache.jena.query.*;
import org.apache.jena.sparql.engine.http.QueryExceptionHTTP;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@Lazy
public class SparqlDAO {
    private final Logger logger = Logger.getLogger(SparqlDAO.class.getName());
    public static final byte ROLE_SUBJECT = 0;
    public static final byte ROLE_OBJECT = 1;

    @Value("${sparql.endpoint}")
    private String endpoint;

    public Set<Triple> getTriplesWithNodePlayingRole(String node, byte role){
        Set<Triple> results = new HashSet<>();
        String query = "";
        if (ROLE_SUBJECT == role){
            query = "SELECT ?p ?o WHERE {"+ node +" ?p ?o}";
        } else if (ROLE_OBJECT == role)
            query = "SELECT ?s ?p WHERE {?s ?p "+ node +"}";

        boolean waitForConnection = false;
        do {
            try (QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint, query)) {
                qexec.setTimeout(5, TimeUnit.MINUTES);
                ResultSet rs = qexec.execSelect();
                while (rs.hasNext()) {
                    QuerySolution row = rs.next();

                    Triple triple = null;
                    String subject = null, object = null;
                    String predicate = row.get("?p").toString();
                    if (ROLE_SUBJECT == role) {
                        subject = node;
                        object = row.get("?o").toString();
                    } else if (ROLE_OBJECT == role) {
                        object = node;
                        subject = row.get("?s").toString();
                    }

                    triple = new Triple(subject, predicate, object);

                    results.add(triple);
                    waitForConnection = false;
                }
            } catch (QueryParseException e) {
                System.out.println("===============================================");
                logger.log(Level.SEVERE, "Error processing the query: \n" + query + "\n");
                e.printStackTrace();
                System.out.println("===============================================");
                waitForConnection = false;
            } catch (QueryExceptionHTTP e) {
                waitForConnection = true;
            }
        } while (waitForConnection);
        return results;
    }
}
