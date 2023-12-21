package mayton.probe.facet;


import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.facet.*;
import org.apache.lucene.facet.taxonomy.TaxonomyReader;
import org.apache.lucene.facet.taxonomy.TaxonomyWriter;
import org.apache.lucene.facet.taxonomy.directory.DirectoryTaxonomyReader;
import org.apache.lucene.facet.taxonomy.directory.DirectoryTaxonomyWriter;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import javax.xml.parsers.DocumentBuilder;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class ProbeFacet {

    // See: http://shaierera.blogspot.com/2012/11/lucene-facets-part-1.html
    //      http://shaierera.blogspot.com/2012/11/lucene-facets-part-2.html

    public static void main(String[] args) throws IOException {

        Directory indexDir = new RAMDirectory();
        Directory taxoDir = new RAMDirectory();
        IndexWriter indexWriter = new IndexWriter(indexDir, new IndexWriterConfig(new KeywordAnalyzer()));
        DirectoryTaxonomyWriter taxoWriter = new DirectoryTaxonomyWriter(taxoDir);
        //FacetField facetFields = new FacetField(taxoWriter);



        
    }

}
